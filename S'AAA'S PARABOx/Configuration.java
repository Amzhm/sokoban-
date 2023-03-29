import java.util.ArrayList;


public class Configuration extends Mobile {
    private ArrayList<Configuration> caisses;
    private Configuration joueur;
    private Niveau niveau;
    private int numNiv;

    public Configuration(Configuration c,Position p,Niveau n,Configuration j,int niv){
        super(Type.MONDE, c, p);
        caisses=new ArrayList<Configuration>();
        niveau=n;
        joueur=j;
        numNiv=niv;
    }

    public Configuration(Configuration c1){
        super(c1.getType(),c1.getConfig(), c1.gePosition());
        niveau=c1.getNiveau();
        joueur=c1.getJoueur();
        caisses=c1.getCaisses();
    }
/************************************************************************************************************** */
    // Les getteurs:
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
    public Element get(Position p){
        if(joueur!=null && joueur.gePosition()!=null){
            if(joueur.getConfig().equals(this) && joueur.gePosition().equals(p))
            return joueur;
        }
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p))
                return caisses.get(i);
        }
        return niveau.get(p);
    }

    public Configuration getJoueur(){
        return joueur;
    }

    public ArrayList<Configuration> getCaisses(){
        return caisses;
    }
    public int getNumNiv(){
        return numNiv;
    }
/**************************************************************************************************************************************************** */
    //Les setteurs:
    public void setNumNiv(int n){
        numNiv=n;
    }
/************************************************************************************************************************************* */
        //ajouter une caisse dans la configuration à une position spécifiée.
    public boolean addCaisse(Position p,Configuration c){
        if(!estVide(p))
            return false;
        c.setConfig(this);
        c.setPosition(p);
        return caisses.add(c);
    }
//Fonction qui permet d'ajouter un Mobile m dans cette configuration et de le positionner dans une position précise.
    public boolean ajouterElement(Mobile m,Position p){
        m.setConfig(this);
        if(m.getType()==Type.JOUEUR)
            joueur=(Configuration)m;
        else
            caisses.add((Configuration)m);
       return m.setPosition(p);
    }
//Fonction qui permet de retirer un element de cette configuration
    public void retirerElement(Mobile m){
        if(m.getType()==Type.JOUEUR)
            joueur=null;
        else{
                caisses.remove((Configuration)m);
        }        
    }
/************************************************************************************************************************* */
    //Méthodes de vérifications
        //vérifie si cette configuration a une entré/sortie.
    public boolean ouvert(){
        return niveau.ouvert();
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
    public boolean estCaisse(Position p){
        for(int i=0;i<caisses.size();i++){
            if(caisses.get(i).gePosition().equals(p)){
                if(caisses.get(i).getType()==Type.CAISSE)
                    return true;
            }        
        }
        return false;
    }
    public boolean estCibleJoueur(Position p){
        return niveau.estCibleJoueur(p);
    }
/************************************************************************************************************************************************ */
    public void affich(){
        for(int i=0;i<getX();i++){
            for(int j=0;j<getY();j++){
                Position p=new Position(i, j);
                if(estCible(p)){
                    if( estCaisse(p))
                        System.out.print("+");
                    else{
                        if(estJoueur(p))
                            System.out.print("-");
                        else
                            System.out.print(".");
                    }     
                }else{
                        if(estCibleJoueur(p)){
                            if(estJoueur(p) )
                                System.out.print("+");
                            else {
                                if(estCaisse(p))
                                    System.out.print("-");
                                else
                                    System.out.print(",");
                            }   
                        }else{
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
                }
                System.out.println(); 
            } 
        }
    
    @Override
    public String toString(){
        String s="*";
        return s;
    }
/**************************************************************************************************************************************** */
    public boolean bougerJoueurVers(Direction d){
            return joueur.bougerVers(d);
       
        }
    public boolean victoire(){
        if(this.getConfig()!=null){
            if(this.getConfig()!=this){
                if(!this.getConfig().victoire())
                    return false;
            }
            
        }
        for(int i=0;i<caisses.size();i++){
            if(!estCible(caisses.get(i).gePosition()) && caisses.get(i).getType()!=Type.MONDE)
                return false;
        }
        if(joueur!=null && !estCibleJoueur(joueur.gePosition()))
            return false;
        return true;
    }
/********************************************************************************************************************************************* */
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
        return true;
    }
    
}
