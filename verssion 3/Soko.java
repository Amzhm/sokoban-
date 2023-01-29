import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Soko {
public static void main(String[] args) {
      Configuration c=loadSokoban("./microban.txt", 5);
      jouer(c);
      
    }
    public static void jouer(Configuration config){
        Configuration sokoban = new Configuration(config);
        Scanner clavier = new Scanner(System.in);
        int s;
        sokoban.affich();
        while(!sokoban.victoire()){
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
					case 0:
						sokoban= new Configuration(config);
					default:
						break;
            }
            if (d !=null)
					sokoban.bougerJoueurVers(d);
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
		Position posjoueur=null;
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
				
				default:
					break;
				}
			}
			if (x<line.length())
				x = line.length();
			j++;
		}
		niveau = new Niveau(x, j);
		config = new Configuration(niveau, posjoueur);
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

    
}
