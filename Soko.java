import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Soko {

	
	static ArrayList<Direction> directions=new ArrayList<Direction>();
	static ArrayList<Element> elt= new ArrayList<Element>();
	
	static boolean fini=false;
	
public static void main(String[] args) {
	 Configuration c=ChargerFichier("./src/jeux.txt", 0);
	//final FenetreJeu fenetre =new FenetreJeu(c);
     c.affich();
	 jouer(c);
	}


    public static void jouer(Configuration config){
        Configuration sokoban = new Configuration(config);
        try (Scanner clavier = new Scanner(System.in)) {
			int s;
			while(!sokoban.victoire()){
				Direction d = null;
				boolean arriere=false;
				s = clavier.nextInt();
				switch(s){
					case 5:
							Element.elt.clear();
							d = Direction.Haut;
							break;
						case 2:
							Element.elt.clear();
							d = Direction.Bas;
							break;
						case 1:
							Element.elt.clear();
							d = Direction.Gauche;
							break;
						case 3:
							Element.elt.clear();
							d = Direction.Droite;
							break;
						case 0:
							d=Direction.contraireDirection(directions.remove(directions.size()-1));
							arriere=true;
							break;
						default:
							break;
							
				}
				if(d !=null){
					if (!arriere){
						if(sokoban.bougerJoueurVers(d))
							Soko.directions.add(d);
						sokoban=sokoban.getJoueur().getConfig();
						if(!Element.elt.isEmpty())
							Soko.elt.add(Element.elt.get(0));
						
							
					}else{
						Soko.elt.remove(Soko.elt.size()-1).bougerVers(d);
						sokoban=sokoban.getJoueur().getConfig();
		
					}
				}
				
				sokoban.affich();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
		

    public static Configuration ChargerFichier(String fichier,int niveau){
		Configuration config = null;
		try{
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fichier)))) {
				String ligne="";
				String n="";
				int nblev=0;
				while ((nblev<niveau && (ligne=buffer.readLine())!=null))
					if (ligne.contains(";"))	nblev++;
				while (((ligne=buffer.readLine())!=null) && (!ligne.contains(";"))){
					n+=ligne+"\n";

				}
				config = ChargerNiveau(n);
			}
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
					joueur=ChargerFichier("./src/jeux.txt", 1);
						joueur.setType(Type.JOUEUR);
						posjoueur=new Position(j,i);
						break;
				case '2':
					caisses.add(ChargerFichier("./src/jeux.txt", 2));
					poscaisses.add(new Position(j,i));
					break;
				case '3':
					caisses.add(ChargerFichier("./src/jeux.txt", 3));
					poscaisses.add(new Position(j,i));
					break;
				case '4':
					caisses.add(ChargerFichier("./src/jeux.txt", 4));
					poscaisses.add(new Position(j,i));
					break;
				case '5':
					caisses.add(ChargerFichier("./src/jeux.txt", 5));
					poscaisses.add(new Position(j,i));
					break;
				case '6':
					caisses.add(ChargerFichier("./src/jeux.txt", 6));
					poscaisses.add(new Position(j,i));
					break;
				case '7':
					caisses.add(ChargerFichier("./src/jeux.txt", 7));
					poscaisses.add(new Position(j,i));
					break;
				case '8':
					caisses.add(ChargerFichier("./src/jeux.txt", 8));
					poscaisses.add(new Position(j,i));
					break;
				case '9':
					caisses.add(ChargerFichier("./src/jeux.txt", 9));
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
		if(pos_monde!=null){
			config.setConfig(config);
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
			if (!config.addCaisse(caisse,poscaisses.get(i))){
				System.err.println("Erreur : caisse "+poscaisses.get(i)+" impossible à poser");
				return null;
			}
			i++;
		}
		if(pos_monde!=null){
			if (!config.addCaisse(monde,pos_monde)){
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
