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
		Configuration c=loadSokoban("./verssion3/jeux2.txt",n);
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
        while(!sokoban.victoire() || !sokoban.getMonde().victoire()){
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
						sokoban=loadSokoban("./verssion3/jeux2.txt",n);
						

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
    public static Configuration loadSokoban(String fn,int level){
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
			config = loadSokoban(levelString);
		}
		catch (Exception e){
			e.printStackTrace();;
		}
		return config;
	}
    public static Configuration loadSokoban(String levelString){
		Niveau niveau = null;
		Configuration config = null,joueur=null;
		ArrayList<Position> murs= new ArrayList<Position>();
		ArrayList<Position> cibles = new ArrayList<Position>();
		ArrayList<Position> posCaisses = new ArrayList<Position>();
		ArrayList<Configuration> caisses = new ArrayList<Configuration>();
		Position posjoueur=null,sortie_est=null,posMonde=null,sortie_nord=null,sortie_sud=null,sortie_ouest=null;
		int moi=-2;
		int n1=myAtoi(levelString);
		int j=0;
		int x = 0;
		for (String line : levelString.split("\n")){
			for (int i=0;i<line.length();i++){
				switch (line.charAt(i)) {
				case '#':
					murs.add(new Position(j,i));
					break;
				case '@':
					posjoueur = new Position(j,i);
					break;
				case '.':
					cibles.add(new Position(j,i));
					break;
				/*case '$':
					caisses.add(new Position(j,i));
					break;*/
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
				case '*':
					posMonde=new Position(j,i);
					moi=-1;
					break;
				case '1':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 1));
					posCaisses.add(new Position(j,i));
					break;
				case '2':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 2));
					posCaisses.add(new Position(j,i));
					break;
				case '3':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 3));
					posCaisses.add(new Position(j,i));
					break;
				case '4':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 4));
					posCaisses.add(new Position(j,i));
					break;
				case '5':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 5));
					posCaisses.add(new Position(j,i));
					break;
				case '6':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 6));
					posCaisses.add(new Position(j,i));
					break;
				case '7':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 7));
					posCaisses.add(new Position(j,i));
					break;
				case '8':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 8));
					posCaisses.add(new Position(j,i));
					break;
				case '9':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 9));
					posCaisses.add(new Position(j,i));
					break;
				case '0':
					caisses.add(loadSokoban("./verssion3/jeux2.txt", 0));
					posCaisses.add(new Position(j,i));
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
		if(posjoueur!=null){
			joueur=loadSokoban("./verssion3/jeux2.txt", 1);
			joueur.setType(Type.JOUEUR);
			//joueur.setPosition(posjoueur);
		}
		if(moi!=-2){
			if(moi==n1 || moi==-1){
				config = new Configuration(Type.MONDE,null,posMonde,niveau, joueur,null);
				config.setConfig(config);
				config.setMonde(config);
			}else{
				config = new Configuration(Type.MONDE,null,null,niveau, joueur,null);
				Configuration m=loadSokoban("./verssion3/jeux2.txt",moi);
				m.setConfig(config);
				m.setPosition(posMonde);
				config.setMonde(m);
			}
		}else{
			config = new Configuration(Type.MONDE,null,posMonde,niveau, joueur,null);
			//config.setConfig(config);
			//config.setMonde(config);
		}
			

			/*config = new Configuration(Type.MONDE,null,null,niveau, posjoueur,null);
			Configuration m=loadSokoban("./jeux.txt",moi);
			m.setConfig(config);
			m.setPosition(posMonde);
			config.setMonde(m);*/
			
			
		/*else{
			config = new Configuration(Type.MONDE,null,posMonde,niveau, posjoueur,null);
			config.setConfig(config);
			config.setMonde(config);

		}*/
		if(joueur!=null){
			joueur.setConfig(config);
			joueur.setPosition(posjoueur);
		}
		/*for(int i=0;i<caisses.size();++i){
			caisses.get(i).setPosition(posCaisses.get(i));
		}*/
		for (Position position: murs){
			if (!niveau.addMur(position)) {
				System.err.println("Erreur : mur "+position+" impossible à poser");
				return null;
			}
		}
		int i=0;	
		for (Configuration c: caisses){
			if (!config.addCaisse(posCaisses.get(i),c)){
				System.err.println("Erreur : caisse "+c.gePosition()+" impossible à poser");
				return null;
			}
			if(!c.ouvert())
				c.setType(Type.CAISSE);
			i++;
		}
			
		for (Position position: cibles)
			if (!niveau.addCible(position)){
				System.err.println("Erreur : cible "+position+" impossible à poser");
				return null;
			}
		return config;
	}
	static int myAtoi(String str)
    {
       
        
        if (str == "" || str.matches("[a-zA-Z]+") || str.matches(".*[0-9].*")) {
            return 0;
        }
        
        int res = 0;
 
        
        for (int i = 0; i < str.length(); ++i)
            res = res * 10 + str.charAt(i) - '0';
 
        // return result.
        return res;
    }

    
}
