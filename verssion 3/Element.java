public class Element {
    Type type;

    public Element(Type p){
        type=p;
    }
    public Type getType(){
        return type;
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
            return m.setPosition(p);
        }
        return true;
    }
    
}
