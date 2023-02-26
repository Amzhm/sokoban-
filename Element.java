import java.util.ArrayList;

public class Element {
    Type type;
    static ArrayList<Element> elt= new ArrayList<Element>();

    public Element(Type p){
        type=p;
    }
    public Type getType(){
        return type;
    }
    public void setType(Type t){
        type=t;
    }
    public boolean bougerVers(Direction d){
        if(getType()==Type.MUR)
            return false;
        if(getType()==Type.CASE)
            return true;
        Mobile m=(Mobile) this;
        Position p=new Position(m.gePosition());
        p=p.add(d);
        if(!m.setPosition(p)){
            m.getConfig().get(p).bougerVers(d);
            if(m.setPosition(p)){
                Element.elt.add(m);
                return true;
            }
            return false;
        }
        Element.elt.add(m);
        return true;
    }
    
}
