import java.util.ArrayList;

public class Configuration {
    private ArrayList<Caisse> caisses;
    private Joueur joueur;
    private Niveau niveau;

    public Configuration(Niveau n,Position p){
        caisses=new ArrayList<Caisse>();
        niveau=n;
        joueur=new Joueur(this, p);
    }
    public Configuration(Configuration c){
        niveau=c.getNiveau();
        joueur=c.getJoueur();
        caisses=c.getCaisses();
    }
    public boolean addCaisse(Position p){
        if(!estVide(p))
            return false;
        return caisses.add(new Caisse(this, p));
    }
    public int getX(){
        return niveau.getX();
    }
    public int getY(){
        return niveau.getY();
    }
    public Niveau getNiveau(){
        return niveau;
    }
    public Element get(Position p){
        if(joueur.gePosition().equals(p))
            return joueur;
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p))
                return caisses.get(i);
        }
        return niveau.get(p);

    }
    public Joueur getJoueur(){
        return joueur;
    }
    public ArrayList<Caisse> getCaisses(){
        return caisses;
    }
    public boolean estVide(Position p){
        if(joueur.gePosition().equals(p))
            return false;
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p))
                return false;
        }
        return niveau.estVide(p);
    }
    public boolean estCible(Position p){
        return niveau.estCible(p);
    }
    public boolean estJoueur(Position p){
        return joueur.gePosition().equals(p);
    }
    
    public void affich(){
        for(int i=0;i<getX();i++){
            for(int j=0;j<getY();j++){
                Position p=new Position(i, j);
                if(estCible(p)){
                    if(estJoueur(p))
                        System.out.print("*");
                    else
                        System.out.print(",");
                }
                    
                else
                     System.out.print(get(p).toString());
            }
            System.out.println();
        }
    }
    public boolean bougerJoueurVers(Direction d){
        return joueur.bougerVers(d);
    }
    public boolean victoire(){
        for(int i=0;i<caisses.size();i++){
            if(!estCible(caisses.get(i).gePosition()))
                return false;
        }
        if(!estCible(joueur.gePosition()))
            return false;
        return true;
    }

}
