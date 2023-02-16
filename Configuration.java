import java.util.ArrayList;


public class Configuration extends Mobile {
    private ArrayList<Configuration> caisses;
    private Configuration joueur;
    private Niveau niveau;
    private Configuration monde;

    public Configuration(Type t,Configuration c,Position p,Niveau n,Configuration j,Configuration m){
        super(t, c, p);
        caisses=new ArrayList<Configuration>();
        niveau=n;
        joueur=j;
        monde=m;
       
    }
    public Configuration(Configuration c1){
        super(c1.getType(),c1.getConfig(), c1.gePosition());
        niveau=c1.getNiveau();
        joueur=c1.getJoueur();
        caisses=c1.getCaisses();
        monde=c1.getMonde();
  
    }
    public void setMonde(Configuration m){
        monde=m;
    }
    public void addJoueur(Configuration j){
        joueur=j;
    }
    public boolean addCaisse(Position p,Configuration c){
        if(!estVide(p))
            return false;
        c.setConfig(this);
        c.setPosition(p);
        return caisses.add(c);
    }
    public boolean ouvert(){
        return niveau.ouvert();
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
    public Configuration getMonde(){
        return monde;
    }
    public Position getSortie(Direction d){
        return niveau.getSortie(d);
    }
    public Position getContraireSortie(Direction d){
        return niveau.getContraireSortie(d);
    }
    public Element get(Position p){
        if(joueur!=null && joueur.gePosition()!=null){
            if(joueur.gePosition().equals(p))
            return joueur;
        }
        
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p))
                return caisses.get(i);
        }
        if(monde!=null && monde.gePosition()!=null){
            if(monde.gePosition().equals(p))
            return monde;
        }
        
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
        if(monde!=null && monde.gePosition()!=null){
            if(monde.gePosition().equals(p))
                return false;
        }
        return niveau.estVide(p);
    }
    public boolean estCible(Position p){
        return niveau.estCible(p);
    }
    public boolean estSortie(Position p){
        return niveau.estSortie(p);
    }
    public boolean estJoueur(Position p){
       try{
        return joueur.gePosition().equals(p);
       }catch(Exception e){
        return false;
       }
        
    }
    public boolean estMonde(Position p){
        return monde.gePosition().equals(p);
    }
    public boolean estCaisse(Position p){
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p)){
                if(caisses.get(i).getType()==Type.CAISSE)
                    return true;
            }
                
        }
        return false;
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
    public void affich(){
        for(int i=0;i<getX();i++){
            for(int j=0;j<getY();j++){
                Position p=new Position(i, j);
                if(estCible(p)){
                    if(estJoueur(p) || estCaisse(p))
                        System.out.print("+");
                    else
                        System.out.print(",");
                }/*else{
                    if(estSortie(p)){
                        if(niveau.nord(p))
                            System.out.print("^");
                        else{
                            if(niveau.est(p))
                                System.out.print(">");
                            else{
                                if(niveau.sud(p))
                                    System.out.print("v");
                                else 
                                    System.out.print("<");
                            }
                        }
                    }*/
                    else{
                        if(estJoueur(p))
                            System.out.print("@");
                        else{
                            if(estCaisse(p))
                                System.out.print("$");
                            else
                                System.out.print(get(p).toString());
                        }
                            
                    }
                       
                }
                        
                
                System.out.println(); 
            }
            
        }
    
    @Override
    public String toString(){
        String s="*";
        return s;
    }

    public boolean bougerJoueurVers(Direction d){
        //try{
            return joueur.bougerVers(d);
       /*  }catch(Exception e){
            System.out.println("Mouvement impossible");
            return false;
        }*/
        
    }
    public boolean victoire(){
        /*if(monde==null){
            if(monde.victoire())
             return true;
        }*/
        
        for(int i=0;i<caisses.size();i++){
            if(!estCible(caisses.get(i).gePosition()))
                return false;
        }
        /*if(!estCible(joueur.gePosition()))
            return false;*/
        return true;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Configuration other = (Configuration) obj;
        if (caisses == null) {
            if (other.caisses != null)
                return false;
        } else if (!caisses.equals(other.caisses))
            return false;
        if (joueur == null) {
            if (other.joueur != null)
                return false;
        } else if (!joueur.equals(other.joueur))
            return false;
        if (niveau == null) {
            if (other.niveau != null)
                return false;
        } else if (!niveau.equals(other.niveau))
            return false;
        if (monde == null) {
            if (other.monde != null)
                return false;
        } else if (!monde.equals(other.monde))
            return false;
        return true;
    }

}
