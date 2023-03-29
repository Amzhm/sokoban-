

public class Mobile extends Element {
    private Configuration config;
    private Position p;
    /***************************************************************************************************************************** */
    //constructeurs
    public Mobile(Type t,Configuration c, Position p){
        super(t);
        config=c;
        this.p=p;
    }
    /**************************************************************************************************************************** */
    //getters
    public Position gePosition(){
        return p;
    }
    public Configuration getConfig(){
        return config;
    }
    /**************************************************************************************************************************** */
    //setters
    public boolean setPosition(Position p){
        if(config.estVide(p)){
            this.p=p;
            return true;
        } 
        return false;   
    }
    public void setConfig(Configuration c){
        config=c;
    }
    /*************************************************************************************************************************** */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mobile other = (Mobile) obj;
        if (config == null) {
            if (other.config != null)
                return false;
        } else if (!config.equals(other.config))
            return false;
        if (p == null) {
            if (other.p != null)
                return false;
        } else if (!p.equals(other.p))
            return false;
        return true;
    }
    
}
