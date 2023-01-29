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
        try{
            if(!m.setPosition(p)){
                if(!m.getConfig().get(p).bougerVers(d) && m.getConfig().get(p).getType()==Type.MONDE){
                    if(m.getConfig().getMonde().getSortie(d)==null){
                        p=p.add(d);
                        if(!(m.getConfig().get(p) instanceof Mobile))
                            return false;
                        Mobile n=(Mobile)m.getConfig().get(p);
                        if(!m.getConfig().getMonde().ajouterElement(n, n.getConfig().getMonde().getContraireSortie(d))){
                            n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(d.contraireDirection(d));
                            if(n.setPosition(n.getConfig().getContraireSortie(d))){
                                if(n instanceof Caisse)
                                     n.getConfig().getConfig().retirerElement(n);
                                return m.bougerVers(d); 
                            }else   
                                return false;
                        }else{
                            if(n instanceof Caisse)
                                n.getConfig().getConfig().retirerElement(n);
                            return m.bougerVers(d);
                        }

                    }
                        
                    if(!m.getConfig().getMonde().ajouterElement(m,m.getConfig().getMonde().getSortie(d))){
                        m.getConfig().get(m.getConfig().getSortie(d)).bougerVers(d);
                        if(m.setPosition(m.getConfig().getSortie(d))){
                            if(m instanceof Caisse)
                                m.getConfig().getConfig().retirerElement(m);
                            return true;
                        }else
                            return false;
                    }else {
                        if(m instanceof Caisse)
                             m.getConfig().getConfig().retirerElement(m);
                         return true;
                    }
                        
                }else
                    return m.setPosition(p);
            }
            return true;
        }catch(Exception e){
            Position p1=new Position(m.getConfig().gePosition());
            p1=p1.add(d);
            if(!m.getConfig().getConfig().ajouterElement(m, p1)){
                if(!m.getConfig().get(p1).bougerVers(d)){
                    m.setConfig(m.getConfig().getMonde());
                    return false;
                }else{
                    if(m.setPosition(p1)){
                        if(m instanceof Caisse)
                            m.getConfig().getMonde().retirerElement(m);
                        return true;
                        
                    }else 
                        return false;
                }
                
            }
            else {
                if(m instanceof Caisse)
                     m.getConfig().getMonde().retirerElement(m);
                 return true;
           

        }
    }
}
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Element other = (Element) obj;
        if (type != other.type)
            return false;
        return true;
    }
    
}
