import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Soko {
	static ArrayList<Element> maListe= new ArrayList<Element>();
	static ArrayList<Direction> D=new ArrayList<Direction>();
	static int n;
public static void main(String[] args) {
	int j;
	Scanner clavier = new Scanner(System.in);
	System.out.print("Veuillez choisir votre niveau:");
	n = clavier.nextInt();
	while(true){
		Configuration c=ChargerFichier("./jeux2.txt",n);
      	jouer(c);
		  System.out.println("Felicitation vous avez gagnez le niveau "+n);
		  System.out.print("Voulez vous continuer 0/1: ");
		  j = clavier.nextInt();
		  if(j==0)
		  	return;
		n++;
	}
      
    }
    public static void jouer(Configuration config){
        Configuration sokoban = new Configuration(config);
        Scanner clavier = new Scanner(System.in);
        int s;
		
        sokoban.affich();
        while(!sokoban.victoire()){
            Direction d = null;
			boolean arriere=false;
            System.out.print("la direction");
            s = clavier.nextInt();
            switch(s){
                case 5:
						Element.maListe.clear();
						d = Direction.Haut;
						break;
					case 2:
						Element.maListe.clear();
						d = Direction.Bas;
						break;
					case 1:
						Element.maListe.clear();
						d = Direction.Gauche;
						break;
					case 3:
						Element.maListe.clear();
						d = Direction.Droite;
						break;
					case 0:
						d=D.get(D.size()-1).contraireDirection(D.remove(D.size()-1));
						arriere=true;
						break;
					case 7:
						sokoban=ChargerFichier("./jeux2.txt",n);
						

					default:
						break;
						
            }if(d !=null){
				if (!arriere){
					if(sokoban.bougerJoueurVers(d))
						Soko.D.add(d);
					sokoban=sokoban.getJoueur().getConfig();
					if(!Element.maListe.isEmpty())
						Soko.maListe.add(Element.maListe.get(0));
					
						
				}else{
					Soko.maListe.remove(Soko.maListe.size()-1).bougerVers(d);
					sokoban=sokoban.getJoueur().getConfig();
	
				}
			}
            
					

            sokoban.affich();
        }
    }
    public static Configuration ChargerFichier(String fn,int level){
		Configuration config = null;
		try{
			BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(fn)));
			String line="";
			String levelString="";
			int nblev=0;
			while ((nblev<level && (line=buffer.readLine())!=null))
				if (line.contains(";"))	nblev++;
			while (((line=buffer.readLine())!=null) && (!line.contains(";"))){
				levelString+=line+"\n";
		
			}
			config = ChargerNiveau(levelString);
		}
		catch (Exception e){
			e.printStackTrace();;
		}
		return config;
	}
    public static Configuration ChargerNiveau(String n){
		Niveau niveau = null;
		Configuration config = null,joueur=null,monde=null;
		ArrayList<Position> murs= new ArrayList<Position>();
		ArrayList<Position> cibles = new ArrayList<Position>();
		ArrayList<Position> poscaisses = new ArrayList<Position>();
		ArrayList<Configuration> caisses = new ArrayList<Configuration>();
		Position posjoueur=null,sortie_est=null,sortie_nord=null,sortie_sud=null,sortie_ouest=null,pos_monde=null;
		int j=0;
		int x = 0;
		for (String line : n.split("\n")){
			for (int i=0;i<line.length();i++){
				switch (line.charAt(i)) {
				case '#':
					murs.add(new Position(j,i));
					break;
				case '.':
					cibles.add(new Position(j,i));
					break;
				case '>':
					sortie_est = new Position(j,i);
					break;
				case '<':
					sortie_ouest = new Position(j,i);
					break;
				case 'v':
					sortie_sud = new Position(j,i);
					break;
				case '^':
					sortie_nord = new Position(j,i);
					break;
				case '1':
					joueur=ChargerFichier("./jeux2.txt", 1);
						joueur.setType(Type.JOUEUR);
						posjoueur=new Position(j,i);
						break;
				case '2':
					caisses.add(ChargerFichier("./jeux2.txt", 2));
					poscaisses.add(new Position(j,i));
					break;
				case '3':
					caisses.add(ChargerFichier("./jeux2.txt", 3));
					poscaisses.add(new Position(j,i));
					break;
				case '4':
					caisses.add(ChargerFichier("./jeux2.txt", 4));
					poscaisses.add(new Position(j,i));
					break;
				case '5':
					caisses.add(ChargerFichier("./jeux2.txt", 5));
					poscaisses.add(new Position(j,i));
					break;
				case '6':
					caisses.add(ChargerFichier("./jeux2.txt", 6));
					poscaisses.add(new Position(j,i));
					break;
				case '7':
					caisses.add(ChargerFichier("./jeux2.txt", 7));
					poscaisses.add(new Position(j,i));
					break;
				case '8':
					caisses.add(ChargerFichier("./jeux2.txt", 8));
					poscaisses.add(new Position(j,i));
					break;
				case '9':
					caisses.add(ChargerFichier("./jeux2.txt", 9));
					poscaisses.add(new Position(j,i));
					break;
				case '0':
					pos_monde=new Position(j,i);
					break;
				default:
					break;
				}
			}
			if (x<line.length())
				x = line.length();
			j++;
		}
		niveau = new Niveau(j, x,sortie_nord,sortie_est,sortie_sud,sortie_ouest);
		config = new Configuration(null,pos_monde,niveau,joueur);
		config.setConfig(config);
		if(pos_monde!=null){
			monde=config;
		}
		if(joueur!=null){
			joueur.setConfig(config);
			joueur.setPosition(posjoueur);
		}
		for (Position position: murs)
			if (!niveau.addMur(position)) {
				System.err.println("Erreur : mur "+position+" impossible à poser");
				return null;
			}
			int i=0;
		for (Configuration caisse: caisses){
			if(!caisse.ouvert())
				caisse.setType(Type.CAISSE);
			if (!config.addCaisse(poscaisses.get(i),caisse)){
				System.err.println("Erreur : caisse "+poscaisses.get(i)+" impossible à poser");
				return null;
			}
			i++;
		}
		if(pos_monde!=null){
			if (!config.addCaisse(pos_monde,monde)){
				System.err.println("Erreur : caisse "+poscaisses.get(i)+" impossible à poser");
				return null;
			}
		}
		
			
		for (Position position: cibles)
			if (!niveau.addCible(position)){
				System.err.println("Erreur : cible "+position+" impossible à poser");
				return null;
			}
		return config;
	}


    
}
