import java.util.ArrayList;
import java.util.Scanner;

public class Soko {

	static ArrayList<String> niveaux;
	static int n;
public static void main(String[] args) {

	niveaux=LectureFichier.loadgame("./data/fichierdeniveau.txt");
	System.out.println("Bienvenue dans notre jeu, avez vous pris le soins de lire le fichoier README?");
	System.out.println("OUI: tapez 1");
	System.out.println("NON: tapez 0");
	Scanner clavier = new Scanner(System.in);
	int h=clavier.nextInt();
	if(h==0){LectureFichier.AfficherREADME();}
	System.out.println("Veuillez maintenant choisir une version du jeu:");
	System.out.println("Version Graphique: tapez 1");
	System.out.println("Version Textuelle: tapez 0");
	h=clavier.nextInt();
	if(h==1){
		maFentre f1=new maFentre();
	}else 
	 	VersionText.jouer();
	
    }
}