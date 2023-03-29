import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public  class LectureFichier {

    //Fonction qui permet de charger le niveau voulu dans une chaine de charactère afin de pouvoir le construire par la suite.
    public static Configuration ChargerFichier(String fn,int level){
		Configuration config = null;
		try{
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(fn)))) {
                String line="";
                String levelString="";
                int nblev=0;
				//permet de placer le cursseur sur le niveau voulu
                while ((nblev<level && (line=buffer.readLine())!=null))
                	if (line.contains(";"))	nblev++;
				//permet de charger le niveau dans la variable levelstring
                while (((line=buffer.readLine())!=null) && (!line.contains(";"))){
                	levelString+=line+"\n";

                }
                config = ChargerNiveau(levelString);
            }
		}
		catch (Exception e){
			e.printStackTrace();;
		}
		config.setNumNiv(level);
		return config;
	}
    
    //Fonction qui s'occupe de construire la configuration
    public static Configuration ChargerNiveau(String n){
		Niveau niveau = null;
		Configuration config = null,joueur=null,monde=null;
		ArrayList<Position> murs= new ArrayList<Position>();
		ArrayList<Position> cibles = new ArrayList<Position>();
		ArrayList<Position> poscaisses = new ArrayList<Position>();
		ArrayList<Configuration> caisses = new ArrayList<Configuration>();
		Position posjoueur=null,sortie_est=null,sortie_nord=null,sortie_sud=null,sortie_ouest=null,pos_monde=null,cibleJ=null;
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
				case ',':
					cibleJ=new Position(j, i);
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
					joueur=ChargerFichier(Soko.niveaux.get(Soko.n-1), 1);
					joueur.setType(Type.JOUEUR);
					posjoueur=new Position(j,i);
					break;
				case '2':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 2));
					poscaisses.add(new Position(j,i));
					break;
				case '3':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 3));
					poscaisses.add(new Position(j,i));
					break;
				case '4':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 4));
					poscaisses.add(new Position(j,i));
					break;
				case '5':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 5));
					poscaisses.add(new Position(j,i));
					break;
				case '6':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 6));
					poscaisses.add(new Position(j,i));
					break;
				case '7':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 7));
					poscaisses.add(new Position(j,i));
					break;
				case '8':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 8));
					poscaisses.add(new Position(j,i));
					break;
				case '9':
					caisses.add(ChargerFichier(Soko.niveaux.get(Soko.n-1), 9));
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
		niveau = new Niveau(j, x,cibleJ,sortie_nord,sortie_est,sortie_sud,sortie_ouest);
		config = new Configuration(null,pos_monde,niveau,joueur,0);
		config.setConfig(config);
		//si c'est un monde recursif
		if(pos_monde!=null){
			monde=config;
		}
		//ajout du joueur à la configutaion
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
		//Ajouter le meme monde dans sa arrayliste de caisses
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
//Fonction qui a pour but de charger tout les noms des fichiers niveaux et de les mettres dans une liste 
    public static ArrayList<String> loadgame(String fn){
		BufferedReader br =null;
		ArrayList<String> nj=new ArrayList<String>();
	    try {
		    br=new BufferedReader(new FileReader(fn));
		    String l="";
			//lecture du fichier des niveaux ligne par ligne 
		    while ((l=br.readLine()) != null){
			nj.add(l);// ajouter le contenu de la ligne à une arraylist
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
//Fonction qui se charger de determiner le niveau ou le joueur s'est arreté
    public static int loadbackup(String fn){
		BufferedReader br=null;
		String sauvgarde=null;
		//lecture du fichier de sauvgarde
		//ouverture avec fichier Bufferreader
		// lecture avec FileReader
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
		// parcours de la arraylist des niveau et compare avec la sauvgarde et renvoie lindex du prochain niveau
	    return Soko.niveaux.indexOf(sauvgarde)+1;
	}
//Fonction de sauvegarde du niveau courant
    public static void safeguard(int n){
		try{//ouverture du fichier avec FileWriter tout en effacant son contenu precedent
			File file = new File("./data/fichiersauvgarde.txt");
			FileWriter writer = new FileWriter(file, false);
			//ecrit le chemin du niveau actuel 
			writer.write(Soko.niveaux.get(n-1));
			writer.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//parcours du fichier README.txt et affichage de son contenu sur le terminal 
	public static void AfficherREADME(){
	try(BufferedReader br=new BufferedReader(new FileReader("./README.txt"))){
		String ligne;
		while((ligne=br.readLine())!=null){
			System.out.println(ligne);
		}
	}catch(IOException e){
		e.printStackTrace();
	}
	}
}
