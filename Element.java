import java.util.ArrayList;

public class Element {
    Type type;
    static ArrayList<Element> maListe= new ArrayList<Element>();

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
                    if(((Configuration) m.getConfig().get(p)).getSortie(d)==null){
                        if(((Configuration) m.getConfig().get(p)).getContraireSortie(d)==null){
                            //version i'm world1 : apres le joueur c'est un monde
                            if(m.getType()==Type.JOUEUR && ((Configuration)m).getContraireSortie(d)!=null){
                                if(!(m.getConfig().get(p) instanceof Mobile))
                                    return false;
                                Mobile n=(Mobile)m.getConfig().get(p);
                                if(!((Configuration) m).ajouterElement(n, ((Configuration)m).getContraireSortie(d))){
                                    n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(d.contraireDirection(d));
                                    if(n.setPosition(n.getConfig().getContraireSortie(d))){
                                        if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                            m.getConfig().retirerElement(n);
                                        return m.bougerVers(d);
                                    }else   
                                        return false;
                                }else{
                                    if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                        m.getConfig().retirerElement(n);
                                    return m.bougerVers(d);
                                    }
                            }
                            return false;
                        }
                        //un monde qui absorbe des elements
                        p2=p.add(d);
                        if((m.getConfig().get(p2) instanceof Mur))
                            return false;
                        Mobile n=(Mobile)m.getConfig().get(p2);
                        if(!((Configuration) m.getConfig().get(p)).ajouterElement(n, ((Configuration) n.getConfig().get(p)).getContraireSortie(d))){
                            n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(d.contraireDirection(d));
                            if(n.setPosition(n.getConfig().getContraireSortie(d))){
                                if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                     n.getConfig().getConfig().retirerElement(n);
                                if(m.bougerVers(d)){
                                    Element.maListe.add(m);
                                    return true;
                                }
                                n.getConfig().getConfig().ajouterElement(n, p2);
                                ((Configuration)n.getConfig().get(p)).retirerElement(n);  
                                return false;
                            }
                            n.getConfig().getConfig().ajouterElement(n, p2);
                            ((Configuration)n.getConfig().get(p)).retirerElement(n);  
                                return false;
                        }else{
                            if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                n.getConfig().getConfig().retirerElement(n);
                            return m.bougerVers(d);
                            }
                    }  
                    //entrer dans un monde  
                    if(!((Configuration) m.getConfig().get(p)).ajouterElement(m,((Configuration) m.getConfig().get(p)).getSortie(d))){
                        m.getConfig().get(m.getConfig().getSortie(d)).bougerVers(d);
                        if(m.setPosition(m.getConfig().getSortie(d))){
                            if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                                m.getConfig().getConfig().retirerElement(m);
                            Element.maListe.add(m);
                            return true;
                        }
                        m.getConfig().getConfig().ajouterElement(m, m.gePosition());
                        ((Configuration)m.getConfig().get(p)).retirerElement(m);
                        return false;
                            
                    }else {
                        if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                             m.getConfig().getConfig().retirerElement(m);
                        Element.maListe.add(m);
                         return true;
                    }
                        
                }else{
                    if(m.setPosition(p)){
                        Element.maListe.add(m);
                    return true;
                    }
                    //version i'm world1 : apres le joueur c'est pas un monde
                    if(m.getType()==Type.JOUEUR && ((Configuration)m).getContraireSortie(d)!=null){
                        if(!(m.getConfig().get(p) instanceof Mobile))
                            return false;
                        Mobile n=(Mobile)m.getConfig().get(p);
                        if(!((Configuration) m).ajouterElement(n, ((Configuration)m).getContraireSortie(d))){
                            n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(d.contraireDirection(d));
                            if(n.setPosition(n.getConfig().getContraireSortie(d))){
                                if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                    m.getConfig().retirerElement(n);
                                return m.bougerVers(d);
                            }else   
                                return false;
                        }else{
                            if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                m.getConfig().retirerElement(n);
                            return m.bougerVers(d);
                            }
                    }
                    return false;
                }
            }
            Element.maListe.add(m);
            return true;
        }catch(Exception e){
            //sortir d'un monde
            if(m.getConfig().gePosition()!=null){
                Position p1=new Position(m.getConfig().gePosition());
                p2=p1.add(d);
                if(!m.getConfig().getConfig().ajouterElement(m, p2)){
                    if(!m.getConfig().get(p2).bougerVers(d)){
                        m.setConfig((Configuration) m.getConfig().get(p1));
                        if(m.getConfig()!=m.getConfig().get(p1))
                            m.getConfig().getConfig().retirerElement(m);
                        return false;
                    }else{
                        if(m.setPosition(p2)){
                            if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                                ((Configuration) m.getConfig().get(p1)).retirerElement(m);
                            Element.maListe.add(m);
                            return true;
                            
                        }else 
                            return false;
                    }
                    
                }
                else {
                    if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                         ((Configuration) m.getConfig().get(p1)).retirerElement(m);
                    Element.maListe.add(m);
                     return true;
                    }
            }
                  
        }
        return false;
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
