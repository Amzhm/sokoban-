import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Soko {
	static ArrayList<Element> maListe= new ArrayList<Element>();
	static ArrayList<Direction> D=new ArrayList<Direction>();
	static ArrayList<String> niveaux=new ArrayList<String>();
	static int n;
public static void main(String[] args) {
	niveaux=loadgame("fichierdeniveau.txt");
	Scanner clavier = new Scanner(System.in);
	System.out.print("debuter une nouvelle partie selectionnez 0\n ");
	System.out.print("reprendre la partie selectionnez 1\n ");
	int h=clavier.nextInt();
	if(h==0)
		n = 1;
	else{
		n=loadbackup("fichiersauvgarde.txt");
	}
	while(true){
		Configuration c=ChargerFichier(niveaux.get(n-1),0);
      	jouer(c);
	}
	}
	public static void safeguard(int n){
		try {
			// Créer un objet File qui représente le fichier texte à ouvrir
			File file = new File("fichiersauvgarde.txt");

			// Créer un objet FileWriter pour écrire dans le fichier et écraser le contenu existant
			FileWriter writer = new FileWriter(file, false);

			// Écrire des données dans le fichier
			writer.write(niveaux.get(n-1));

			// Fermer le fichier
			writer.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static int loadbackup(String fn){
		BufferedReader br=null;
		String sauvgarde=null;
		try {
			br=new BufferedReader(new FileReader(fn));
			String l="";
			while ((l=br.readLine()) != null){
				 sauvgarde=l;
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally{
			if (br!=null)
				try{
					br.close();
				}catch (IOException z){
					z.printStackTrace();
				}
       
    }
	return niveaux.indexOf(sauvgarde)+1;
	
	}
	public static ArrayList<String> loadgame(String fn){
		BufferedReader br =null;
		ArrayList<String> nj=new ArrayList<String>();
	try {
		br=new BufferedReader(new FileReader(fn));
		String l="";
		while ((l=br.readLine()) != null){
			nj.add(l);
		}
	}catch(Exception e) {
		e.printStackTrace();
	} finally{
		if (br!=null)
			try{
				br.close();
			}catch (IOException i){
				i.printStackTrace();
			}
	}
	return nj;
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
						sokoban=ChargerFichier(niveaux.get(n-1),0);
						

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
		System.out.println("Felicitation vous avez gagnez le niveau "+n);
		n++;
		if(n>30)
			System.out.println("fin du jeu");
		safeguard(n);
		Configuration tmp=ChargerFichier(niveaux.get(n-1), 0);
		jouer(tmp);
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
    public static Configuration ChargerNiveau(String k){
		Niveau niveau = null;
		Configuration config = null,joueur=null,monde=null;
		ArrayList<Position> murs= new ArrayList<Position>();
		ArrayList<Position> cibles = new ArrayList<Position>();
		ArrayList<Position> poscaisses = new ArrayList<Position>();
		ArrayList<Configuration> caisses = new ArrayList<Configuration>();
		Position posjoueur=null,sortie_est=null,sortie_nord=null,sortie_sud=null,sortie_ouest=null,pos_monde=null, ciblejoueuer=null;
		int j=0;
		int x = 0;
		for (String line : k.split("\n")){
			for (int i=0;i<line.length();i++){
				switch (line.charAt(i)) {
				case '#':
					murs.add(new Position(j,i));
					break;
				case '.':
					cibles.add(new Position(j,i));
					break;
				case ',':
					ciblejoueuer= new Position(j,i);
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
					joueur=ChargerFichier(niveaux.get(n-1), 1);
						joueur.setType(Type.JOUEUR);
						posjoueur=new Position(j,i);
						break;
				case '2':
					caisses.add(ChargerFichier(niveaux.get(n-1), 2));
					poscaisses.add(new Position(j,i));
					break;
				case '3':
					caisses.add(ChargerFichier(niveaux.get(n-1), 3));
					poscaisses.add(new Position(j,i));
					break;
				case '4':
					caisses.add(ChargerFichier(niveaux.get(n-1), 4));
					poscaisses.add(new Position(j,i));
					break;
				case '5':
					caisses.add(ChargerFichier(niveaux.get(n-1), 5));
					poscaisses.add(new Position(j,i));
					break;
				case '6':
					caisses.add(ChargerFichier(niveaux.get(n-1), 6));
					poscaisses.add(new Position(j,i));
					break;
				case '7':
					caisses.add(ChargerFichier(niveaux.get(n-1), 7));
					poscaisses.add(new Position(j,i));
					break;
				case '8':
					caisses.add(ChargerFichier(niveaux.get(n-1), 8));
					poscaisses.add(new Position(j,i));
					break;
				case '9':
					caisses.add(ChargerFichier(niveaux.get(n-1), 9));
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
		niveau = new Niveau(j, x,sortie_nord,sortie_est,sortie_sud,sortie_ouest,ciblejoueuer);
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
		}if(ciblejoueuer!=null){
			if(!niveau.addCiblejoueur(ciblejoueuer)){
				System.err.println("Erreur : ciblejoueur "+ciblejoueuer+" impossible à poser");
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
