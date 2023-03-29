import java.util.ArrayList;

public class Chemin {
    private static ArrayList<Position> V=new ArrayList<Position>();
    private static ArrayList<Position> Keys=new ArrayList<Position>();
    private static ArrayList<Position> Values=new ArrayList<Position>();
    static ArrayList<Position> chemin=new ArrayList<Position>();

    //Fonction qui permet de lister les voisins éventuels d'une position donnée dans la configuration
    public static ArrayList<Position> ListeVoisins(Position depart,Configuration jeu){
        int n =jeu.getX();
        int c=jeu.getY();
        ArrayList<Position> Voisins=new ArrayList<Position>();
        ArrayList<Position> PV=new ArrayList<Position>();
        PV.add(depart.add(Direction.Haut));
        PV.add(depart.add(Direction.Droite));
        PV.add(depart.add(Direction.Bas));
        PV.add(depart.add(Direction.Gauche));
        for(int i=0;i<PV.size();i++){
            Position p=PV.get(i);
            if(0<p.getX() && p.getX()<n && 0<p.getY() && p.getY()<c && jeu.estVide(p) )
                Voisins.add(p);
        }
        return Voisins;

    }

 // Fonction qui permet de trouver toutes les positions qui construisent le plus court chemin d'une position de départ vers une position d'arrivée
 // En s'appuiyant sur le principe du parcours en largeur, pour construire un dictionnaire qui contiendera pour chaque position donnée son pére,
 //ce qui nous permettra par la suite d'extraire ce chemin
    public static void ParcoursLargeur(Position depart,Position Arrive,Configuration jeu){
        Keys.clear();
        Values.clear();
        V.clear();
        ArrayList<Position> File=new ArrayList<Position>();
        File.add(depart);
        Position sommet;
        while(!File.isEmpty()){
            sommet=File.remove(0);
            V.add(sommet);
            for(Position v:ListeVoisins(sommet,jeu)){
                if(!V.contains(v) && !File.contains(v)){
                    File.add(v);
                    Keys.add(sommet);
                    Values.add(v);

                }
                    
            }
        }
        sommet=Arrive;
        while(!sommet.equals(depart)){
            chemin.add(sommet);
            sommet=Keys.get(indexOf(sommet));
        }
        
    }

    public static int indexOf(Position p){
        for(int i=0;i<Keys.size();i++){
            if(Values.get(i).equals(p))
                return i;
        }
        return -1;
    }

}
