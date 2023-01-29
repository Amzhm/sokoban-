import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Soko {
public static void main(String[] args) {
	int n,j;
	Scanner clavier = new Scanner(System.in);
	System.out.print("Veuillez choisir votre niveau:");
	n = clavier.nextInt();
	while(true){
		Configuration c=loadSokoban("jeux.txt",n);
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
            System.out.print("la direction");
            s = clavier.nextInt();
            switch(s){
                case 5:
						d = Direction.Haut;
						break;
					case 2:
						d = Direction.Bas;
						break;
					case 1:
						d = Direction.Gauche;
						break;
					case 3:
						d = Direction.Droite;
						break;

					default:
						break;
            }
            if (d !=null){
				sokoban.bougerJoueurVers(d);
				sokoban=sokoban.getJoueur().getConfig();
					
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
		Configuration config = null;
		ArrayList<Position> murs= new ArrayList<Position>();
		ArrayList<Position> cibles = new ArrayList<Position>();
		ArrayList<Position> caisses = new ArrayList<Position>();
		Position posjoueur=null,sortie_est=null,posMonde=null,sortie_nord=null,sortie_sud=null,sortie_ouest=null;
		int moi=-1;
		int n=myAtoi(levelString);
		int j=0;
		int x = 0;
		for (String line : levelString.split("\n")){
			for (int i=0;i<line.length();i++){
				switch (line.charAt(i)) {
				case '#':
					murs.add(new Position(i,j));
					break;
				case '@':
					posjoueur = new Position(i,j);
					break;
				case '.':
					cibles.add(new Position(i,j));
					break;
				case '$':
					caisses.add(new Position(i,j));
					break;
				case '>':
					sortie_est = new Position(i,j);
					break;
				case '<':
					sortie_ouest = new Position(i,j);
					break;
				case 'v':
					sortie_sud = new Position(i,j);
					break;
				case '^':
					sortie_nord = new Position(i,j);
					break;
				case '*':
					posMonde=new Position(i, j);
					//moi=2;
					break;
				case '1':
					posMonde=new Position(i, j);
					moi=1;
					break;
				case '2':
					posMonde=new Position(i, j);
					moi=2;
					break;
				case '3':
					posMonde=new Position(i, j);
					moi=3;
					break;
				case '4':
					posMonde=new Position(i, j);
					moi=4;
					break;
				case '5':
					posMonde=new Position(i, j);
					moi=5;
					break;
				case '6':
					posMonde=new Position(i, j);
					moi=6;
					break;
				case '7':
					posMonde=new Position(i, j);
					moi=7;
					break;
				case '8':
					posMonde=new Position(i, j);
					moi=8;
					break;
				case '9':
					posMonde=new Position(i, j);
					moi=9;
					break;
				case '0':
					posMonde=new Position(i, j);
					moi=0;
					break;
				
				
				default:
					break;
				}
			}
			if (x<line.length())
				x = line.length();
			j++;
		}
		niveau = new Niveau(x, j,sortie_nord,sortie_est,sortie_sud,sortie_ouest);
		if(moi!=-1){
			if(moi==n){
				config = new Configuration(Type.MONDE,null,posMonde,niveau, posjoueur,null);
				config.setConfig(config);
				config.setMonde(config);
			}else{
				config = new Configuration(Type.MONDE,null,null,niveau, posjoueur,null);
				Configuration m=loadSokoban("./jeux.txt",moi);
				m.setConfig(config);
				m.setPosition(posMonde);
				config.setMonde(m);
			}
		}else{
			config = new Configuration(Type.MONDE,null,posMonde,niveau, posjoueur,null);
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
		
		for (Position position: murs)
			if (!niveau.addMur(position)) {
				System.err.println("Erreur : mur "+position+" impossible à poser");
				return null;
			}
		for (Position position: caisses)
			if (!config.addCaisse(position)){
				System.err.println("Erreur : caisse "+position+" impossible à poser");
				return null;
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
