

public class Mobile extends Element {
    private Configuration config;
    private Position p;

    public Mobile(Type t,Configuration c, Position p){
        super(t);
        config=c;
        this.p=p;
    }
    public Position gePosition(){
        return p;
    }
    public boolean setPosition(Position p){
        if(config.estVide(p)){
            this.p=p;
            return true;
        } 
        return false;   
    }
    public Configuration getConfig(){
        return config;
    }
    public void setConfig(Configuration c){
        config=c;
    }
    
}
