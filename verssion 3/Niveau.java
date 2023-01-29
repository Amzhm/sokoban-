import java.util.ArrayList;

public class Niveau {
    private Immobile[][] grille;
    private ArrayList<Position> cibles;

    public Niveau(int x,int y){
        grille=new Immobile[x][y];
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                grille[i][j]=new Case();
            }
        }
        cibles=new ArrayList<Position>();
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
