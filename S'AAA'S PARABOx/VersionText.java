import java.util.ArrayList;
import java.util.Scanner;

public class VersionText {
    static ArrayList<Direction> direction=new ArrayList<Direction>();
    static ArrayList<Element> elements=new ArrayList<Element>();
    static boolean debuter=false;
    //methode qui permet à l'utilisateur de lancer la version textuelle et d'y jouer
    public static void jouer(){
        Scanner clavier = new Scanner(System.in);

        if(!debuter){//si l'utilisateur viens de commencer a jouer
                System.out.println("debuter une nouvelle partie selectionnez 0 ");
                System.out.println("reprendre la partie selectionnez 1 ");
                int h=clavier.nextInt();
                if(h==0)
                    Soko.n = 1;
                else{
                    //Charger le dernier niveau jouer
                    Soko.n=LectureFichier.loadbackup("./data/fichiersauvgarde.txt");
                }
        }
            //Construire l'univers(la configuration)
            Configuration c=LectureFichier.ChargerFichier(Soko.niveaux.get(Soko.n-1),0);
            String s;
            boolean fin=false;
            //c.affich();
            while(!c.victoire()){
                c.affich();
                debuter=true;
                Direction d = null;
            	boolean arriere=false;
                System.out.println("la direction: ");
                s = clavier.nextLine();
                switch(s){
                    case "z":
            				Element.maListe.clear();
            				d = Direction.Haut;
            				break;
            			case "s":
            				Element.maListe.clear();
            				d = Direction.Bas;
            				break;
            			case "q":
            				Element.maListe.clear();
            				d = Direction.Gauche;
            				break;
            			case "d":
            				Element.maListe.clear();
            				d = Direction.Droite;
            				break;
            			case "a":
            				d=Direction.contraireDirection(VersionText.direction.remove(VersionText.direction.size()-1));
            				arriere=true;
            				break;
            			case "r":
            				c=LectureFichier.ChargerFichier(Soko.niveaux.get(Soko.n-1),0);
            			default:
            				break;
            	}
                if(fin)
                    break;
                if(d !=null){
            		if (!arriere){
            			if(c.bougerJoueurVers(d))
            				VersionText.direction.add(d);//ajout de la direction jouer a la liste ce qui nous permettera par la suite le retour en arriére
            			c=c.getJoueur().getConfig();
            			if(!Element.maListe.isEmpty())
            				VersionText.elements.add(Element.maListe.get(0));//ajout du permier élément bougé à la liste ce qui nous permettera par la suite le retour en arriére		
            		}else{// si l'utlisateur souhaite retourner à l'état précédant(retour en arrière)
            			VersionText.elements.remove(VersionText.elements.size()-1).bougerVers(d);
            			c=c.getJoueur().getConfig();
            		}
            	}
                
            }
            if(!fin){
                c.affich();
                System.out.println("Felicitation vous avez gagnez le niveau "+Soko.n);
                Soko.n++;
            }
            if(Soko.n>=30)
                System.out.println("fin du jeu");
            LectureFichier.safeguard(Soko.n);
            jouer();
    }
}



