import java.util.ArrayList;

public class Joueur extends Mobile {
    private ArrayList<Direction> histo;

    public Joueur(Configuration c,Position p){
        super(Type.JOUEUR, c, p);
        histo=new ArrayList<Direction>();
    }
    public Joueur(Configuration c,Joueur j){
        super(Type.JOUEUR, j.getConfig(), j.gePosition());
        histo=j.getHisto();
    }
    public ArrayList<Direction> getHisto(){
        return histo;
    }
    @Override
    public String toString(){
        String s="@";
        return s;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Joueur other = (Joueur) obj;
        if (histo == null) {
            if (other.histo != null)
                return false;
        } else if (!histo.equals(other.histo))
            return false;
        return true;
    }

}
