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
        Position p=new Position(m.gePosition()),p2;
        p=p.add(d);
        try{
            if(!m.setPosition(p)){
                if(!m.getConfig().get(p).bougerVers(d) && m.getConfig().get(p).getType()==Type.MONDE){
                    if(((Configuration) m.getConfig().get(p)).getContraireSortie(d)==null){
                        //absorber un element
                        p2=p.add(d);
                        if(!(m.getConfig().get(p2) instanceof Mobile))
                            return false;
                        Mobile n=(Mobile)m.getConfig().get(p2);
                        if(!((Configuration) m.getConfig().get(p)).ajouterElement(n, ((Configuration) n.getConfig().get(p)).getSortie(d))){
                            n.getConfig().get(n.getConfig().getSortie(d)).bougerVers(Direction.contraireDirection(d));
                            if(n.setPosition(n.getConfig().getSortie(d))){
                                if(m.getType()==Type.CAISSE)
                                     n.getConfig().getConfig().retirerElement(n);
                                return m.bougerVers(d); 
                            }else   
                                return false;
                        }else{
                            if(n.getType()==Type.CAISSE)
                                n.getConfig().getConfig().retirerElement(n);
                            return m.bougerVers(d);
                        }
                    }
                        
                    //Entrer dans un monde
                    if(!((Configuration) m.getConfig().get(p)).ajouterElement(m,((Configuration) m.getConfig().get(p)).getContraireSortie(d))){
                        m.getConfig().get(m.getConfig().getContraireSortie(d)).bougerVers(d);
                        if(m.setPosition(m.getConfig().getContraireSortie(d))){
                            if(m.getType()==Type.CAISSE)
                                m.getConfig().getConfig().retirerElement(m);
                            Element.elt.add(m);
                            return true;
                        }else {
                            m.getConfig().getConfig().ajouterElement(m, p.sub(d));
                            ((Configuration)m.getConfig().get(p)).retirerElement(m);
                            return false;
                        }
                    }else{
                        if(m.getType()==Type.CAISSE)
                             m.getConfig().getConfig().retirerElement(m);
                        Element.elt.add(m);
                            return true;
                    }
                            
                }else{
                    if(m.setPosition(p)){
                        Element.elt.add(m);
                        return true;
                    }else
                        return false;
                }
            }
            Element.elt.add(m);
            return true;
        }catch(Exception e){
            //Sortir d'un monde
            if(m.getConfig().gePosition()!=null){
                Position p1=new Position(m.getConfig().gePosition());
                p2=p1.add(d);
                if(!m.getConfig().getConfig().ajouterElement(m, p2)){
                    if(!m.getConfig().get(p2).bougerVers(d)){
                        m.setConfig((Configuration) m.getConfig().get(p1));
                        return false;
                    }else{
                        if(m.setPosition(p2)){
                            if(m.getType()==Type.CAISSE)
                                ((Configuration) m.getConfig().get(p1)).retirerElement(m);
                            Element.elt.add(m);
                            return true;
                            
                        }else 
                            return false;
                    }
                }else {
                    if(m.getType()==Type.CAISSE)
                         ((Configuration) m.getConfig().get(p1)).retirerElement(m);
                    Element.elt.add(m);
                     return true;
            }
            }else
                return false;
        }
    }
    
}
