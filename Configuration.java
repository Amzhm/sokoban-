import java.util.ArrayList;

public class Configuration extends Mobile {
    private ArrayList<Configuration> caisses;
    private Configuration joueur;
    private Niveau niveau;

    public Configuration(Configuration c,Position p,Niveau n,Configuration c1){
        super(Type.MONDE, c, p);
        caisses=new ArrayList<Configuration>();
        niveau=n;
        joueur=c1;
    }
    public Configuration(Configuration c){
        super(c.getType(),c.getConfig(),c.gePosition());
        niveau=c.getNiveau();
        joueur=c.getJoueur();
        caisses=c.getCaisses();
    }
    public boolean addCaisse(Configuration c,Position p){
        if(!estVide(p))
            return false;
        c.setConfig(this);
        c.setPosition(p);
        return caisses.add(c);
    }

    public boolean ajouterElement(Mobile m,Position p){
        m.setConfig(this);
        if(m.getType()==Type.JOUEUR)
            joueur=(Configuration)m;
        else
            caisses.add((Configuration)m);
       return m.setPosition(p);
    }

    public void retirerElement(Mobile m){
        if(m.getType()==Type.JOUEUR)
            joueur=null;
        else{
                caisses.remove(m);
        }
            
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
    public Position getSortie(Direction d){
        return niveau.getSortie(d);
    }
    public Position getContraireSortie(Direction d){
        return niveau.getContraireSortie(d);
    }
    public boolean ouvert(){
        return niveau.ouvert();
    }
    public Element get(Position p){
        try{
            if(joueur.gePosition().equals(p))
                return joueur;
            for(int i=0;i<caisses.size();i++){
                if(caisses.get(i).gePosition().equals(p))
                    return caisses.get(i);
            }
        
        }catch(Exception e){}
        
        return niveau.get(p);

    }
    public Configuration getJoueur(){
        return joueur;
    }
    public ArrayList<Configuration> getCaisses(){
        return caisses;
    }
    public boolean estVide(Position p){
        if(joueur!=null && joueur.gePosition()!=null){
            if(joueur.gePosition().equals(p))
            return false;
        }
        
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
        try{
            return joueur.gePosition().equals(p);
        }catch(Exception e){}
        return false;
        
    }
    public boolean estMoi(Position p){
        try{
            for(int i=0;i<caisses.size();i++){
                if(caisses.get(i).gePosition().equals(p))
                    return this.gePosition().equals(p);
            }
        }catch(Exception e){}
        return false;
    }
    public void affich(){
        for(int i=0;i<getX();i++){
            for(int j=0;j<getY();j++){
                Position p=new Position(i, j);
                if(estCible(p)){
                    if(estJoueur(p) || estCaisse(p))
                        System.out.print("+");
                    else
                        System.out.print(".");
                }
                    else{
                        if(estJoueur(p))
                            System.out.print("@");
                        else{
                            if(estMoi(p))
                                System.out.print("*");
                            else{
                                if(estCaisse(p))
                                    System.out.print("$");
                                else{
                                    if(estMonde(p))
                                        System.out.print("&");
                                    else
                                        System.out.print(get(p).toString());
                                }
                                    
                            }
                            }
                                
                            
                    }
                       
                }
                        
                
                System.out.println(); 
            }
            
        }
    public boolean estCaisse(Position p) {
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p)){
                if(caisses.get(i).getType()==Type.CAISSE)
                    return true;
            }
                
        }
        return false;
    }
    public boolean estMonde(Position p) {
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p)){
                if(caisses.get(i).getType()==Type.MONDE)
                    return true;
            }
                
        }
        return false;
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
        //+
        Soko.fini=true;
        //fin +
        return true;
    }

}
