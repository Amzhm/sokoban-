import java.util.ArrayList;

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

    public Position getContraireSortie(Direction d){
        if(d==Direction.Haut)
            return sortie_sud;
        if(d==Direction.Bas)
            return sortie_nord;
        if(d==Direction.Droite)
            return sortie_ouest;
        return sortie_est;
    }

    public Position getSortie(Direction d){
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
            System.out.println();
        }
    }
    
}
