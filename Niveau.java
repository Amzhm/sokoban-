import java.util.ArrayList;
import java.util.Arrays;

public class Niveau {
   
    private Immobile[][] grille;
    private ArrayList<Position> cibles;
    private Position sortie_nord;
    private Position sortie_est;
    private Position sortie_sud;
    private Position sortie_ouest;

    public Niveau(int x,int y,Position nord,Position est,Position sud,Position ouest){
        grille=new Immobile[x][y];
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                grille[i][j]=new Case();
            }
        }
        cibles=new ArrayList<Position>();
        sortie_nord=nord;
        sortie_est=est;
        sortie_sud=sud;
        sortie_ouest=ouest;
    }

    public int getX(){
        return grille.length;
    }

    public int getY(){
        return grille[0].length;
    }

    public boolean addCible(Position p){
        return cibles.add(p);
    }

    public boolean addMur(Position p){
        if(estVide(p)){
            grille[p.getX()][p.getY()]=new Mur();
            return true;
        }
        return false;
    }

    public boolean estCible(Position p){
        return cibles.contains(p);
    }

    public boolean estVide(Position p){
        if(get(p).getType()==Type.MUR)
            return false;
        return true;
    }
    public Element get(Position p){
        return grille[p.getX()][p.getY()];
    }
    
    public Position getSortie(Direction d){
        if(d==Direction.Haut)
            return sortie_sud;
        if(d==Direction.Bas)
            return sortie_nord;
        if(d==Direction.Droite)
            return sortie_ouest;
        return sortie_est;
    }

    public Position getContraireSortie(Direction d){
        if(d==Direction.Bas)
         return sortie_sud;
    if(d==Direction.Haut)
        return sortie_nord;
    if(d==Direction.Gauche)
        return sortie_ouest;
    return sortie_est;
    }

    public boolean ouvert(){
        if(sortie_est!=null || sortie_nord!=null || sortie_ouest!=null || sortie_sud!=null)
            return true;
        return false;
    }

    public void affich(){
        for(int i=0;i<getX();i++){
            for(int j=0;j<getY();j++){
                Position p=new Position(i, j);
                if(estCible(p))
                    System.out.print(","); 
                else
                        System.out.print(get(p).toString());
                }   
                    
            }
            System.out.println();
        }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Niveau other = (Niveau) obj;
        if (!Arrays.deepEquals(grille, other.grille))
            return false;
        if (cibles == null) {
            if (other.cibles != null)
                return false;
        } else if (!cibles.equals(other.cibles))
            return false;
        if (sortie_nord == null) {
            if (other.sortie_nord != null)
                return false;
        } else if (!sortie_nord.equals(other.sortie_nord))
            return false;
        if (sortie_est == null) {
            if (other.sortie_est != null)
                return false;
        } else if (!sortie_est.equals(other.sortie_est))
            return false;
        if (sortie_sud == null) {
            if (other.sortie_sud != null)
                return false;
        } else if (!sortie_sud.equals(other.sortie_sud))
            return false;
        if (sortie_ouest == null) {
            if (other.sortie_ouest != null)
                return false;
        } else if (!sortie_ouest.equals(other.sortie_ouest))
            return false;
        return true;
    }
}
