import java.util.ArrayList;

public class Element {
    Type type;
    static ArrayList<Element> maListe= new ArrayList<Element>();

    public Element(Type p){
        type=p;
    }
    //getters et setters
    public Type getType(){
        return type;
    }
    public void setType(Type t){
        type=t;
    }
    /**************************************************************************************************************************** */
    public boolean bougerVers(Direction d){
        //si l'objet est de type MUR mouvement impossible
        if(getType()==Type.MUR)
            return false;
        // si l'objet est de type case le mouvement est toujours possible
        if(getType()==Type.CASE)
            return true;
        // arrivé à cette partie, l'objet est surement mobile
        Mobile m=(Mobile) this;
        //on recupere la position du mobile
        Position p=new Position(m.gePosition()),p2;
        //on recupere la position suivante dans la meme direction
        p=p.add(d);
        try{ 
            if(!m.setPosition(p)){//si la position p n'est pas libre
                if(!m.getConfig().get(p).bougerVers(d) && m.getConfig().get(p).getType()==Type.MONDE){//si l'élément de la position p ne peut bouger et il est de type monde 
                    if(((Configuration) m.getConfig().get(p)).getSortie(d)==null){//si le monde est fermé dans cette dirrection(sortie1)
                        if(((Configuration) m.getConfig().get(p)).getContraireSortie(d)==null){//si la sortie opposé de la sortie1 est aussi fermer(impossible d'accéder au sous monde)
                            /*VERSION I'M WORLD : apres le joueur c'est un monde*/
                            //meme commentaire que la verssion absorber d'écrite ci-dessous
                            if(m.getType()==Type.JOUEUR && ((Configuration)m).getContraireSortie(d)!=null){
                                if(!(m.getConfig().get(p) instanceof Mobile))
                                    return false;
                                Mobile n=(Mobile)m.getConfig().get(p);
                                if(!((Configuration) m).ajouterElement(n, ((Configuration)m).getContraireSortie(d))){
                                    n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(Direction.contraireDirection(d));
                                    if(n.setPosition(n.getConfig().getContraireSortie(d))){
                                        if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                            m.getConfig().retirerElement(n);
                                        return m.bougerVers(d);
                                    }
                                        m.getConfig().ajouterElement(n, n.gePosition());
                                        ((Configuration)m).retirerElement(n);
                                        return false;
                                }else{
                                    if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                        m.getConfig().retirerElement(n);
                                    return m.bougerVers(d);
                                    }
                            }
                            return false;
                        }
                        /*VERSION ABSORBEUR*/
                        p2=p.add(d);//position suivante de p dans la meme direction
                        if((m.getConfig().get(p2) instanceof Mur))//si l'element de la position p2 est un Mur donc impossible de l'absorber
                            return false;
                        Mobile n=(Mobile)m.getConfig().get(p2);
                        if(!((Configuration) m.getConfig().get(p)).ajouterElement(n, ((Configuration) n.getConfig().get(p)).getContraireSortie(d))){//l'ajoute de l'élément n dans le sous-mondea échoué (position occuper)
                            n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(Direction.contraireDirection(d));//esseyer de bouger l'élément qui géne l'ajout de n dans la direction contraire de d
                            if(n.setPosition(n.getConfig().getContraireSortie(d))){//si l'élément n a été ajouté avec succés 
                                if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                    n.getConfig().getConfig().retirerElement(n);// retirer n de son ancienne configuration
                                //bouger m dans la direction souhaité
                                if(m.bougerVers(d)){ 
                                    Element.maListe.add(m);
                                    return true;
                                }  
                                return false;
                            }//si l'élément n n'a pas été ajouté comme prévue  on le retire du sous-monde et on le remet à sa position dans la configuration initial
                            n.getConfig().getConfig().ajouterElement(n, p2);
                            ((Configuration)n.getConfig().get(p)).retirerElement(n);  
                                return false;
                        }else{//si l'ajout est un succés (la position ou on voulait ajouté n été libre)
                            if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                n.getConfig().getConfig().retirerElement(n);//on le retire de sa configuration initial 
                                //bouger m dans la direction souhaité
                                if(m.bougerVers(d)){
                                    Element.maListe.add(m);
                                    return true;
                                }  
                                return false;
                            }
                    }  
                    /*ENTRER DANS UN SOUS MONDE*/  
                    if(!((Configuration) m.getConfig().get(p)).ajouterElement(m,((Configuration) m.getConfig().get(p)).getSortie(d))){//l'ajout de m dans le sous monde(qui occupe la position p) a échoué 
                        m.getConfig().get(m.getConfig().getSortie(d)).bougerVers(d);//esseyer de bouger l'élément qui géne l'ajout de m dans la direction d
                        if(m.setPosition(m.getConfig().getSortie(d))){//si l'élément m a été ajouté avec succés 
                            if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                                m.getConfig().getConfig().retirerElement(m);// retirer n de son ancienne configuration
                            Element.maListe.add(m);
                            return true;
                        }//si l'élément m n'a pas été ajouté comme prévue dans le sous-monde on le retire et on le remet à sa position dans la configuration initial
                        m.getConfig().getConfig().ajouterElement(m, m.gePosition());
                        if(m.getType()!=Type.JOUEUR)
                            ((Configuration)m.getConfig().get(p)).retirerElement(m);
                        return false;
                            
                    }else {//si l'ajout est un succés (la position ou on voulait ajouté n été libre)
                        if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                             m.getConfig().getConfig().retirerElement(m);//on le retire de sa configuration initial 
                        Element.maListe.add(m);
                         return true;
                    }
                        
                }else{//l'élément suivant m n'est pas un monde
                    if(m.setPosition(p)){
                        Element.maListe.add(m);
                    return true;
                    }
                    /*VERSION I'M WORLD : apres le joueur c'est pas un monde*/
                    if(m.getType()==Type.JOUEUR && ((Configuration)m).getContraireSortie(d)!=null){//on verifie si le joueur est un monde (meme principe que ABSORBEUR )
                        if(!(m.getConfig().get(p) instanceof Mobile))
                            return false;
                        Mobile n=(Mobile)m.getConfig().get(p);
                        if(!((Configuration) m).ajouterElement(n, ((Configuration)m).getContraireSortie(d))){
                            n.getConfig().get(n.getConfig().getContraireSortie(d)).bougerVers(Direction.contraireDirection(d));
                            if(n.setPosition(n.getConfig().getContraireSortie(d))){
                                if(n.getType()==Type.CAISSE || (n.getType()==Type.MONDE))
                                    m.getConfig().retirerElement(n);
                                return m.bougerVers(d);
                            }
                            m.getConfig().ajouterElement(n, n.gePosition());
                            ((Configuration)m).retirerElement(n);
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
            /*SORTIR D'UN MONDE*/
            if(m.getConfig().gePosition()!=null){//savoir si on est dans un sous monde où que notre configuration est récursive(elle contient elle meme)
                Position p1=new Position(m.getConfig().gePosition());
                p2=p1.add(d);//la position ou on veut se placer(position de sortie)
                if(!m.getConfig().getConfig().ajouterElement(m, p2)){//si l'ajout a la position p2 à échouer(sois par la présence d'une caisse ou un mur)
                    if(!m.getConfig().get(p2).bougerVers(d)){//si l'élément qui occupe la position p2  est impossible à bouger dans la direction d
                        //on remet m à sa place dans son monde initial
                        m.setConfig((Configuration) m.getConfig().get(p1));
                        if(m.getConfig()!=m.getConfig().get(p1))
                            m.getConfig().getConfig().retirerElement(m);
                        return false;
                    }else{// la sortie de m est un succés alors on le retire de son monde initial
                        if(m.setPosition(p2)){
                            if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                                ((Configuration) m.getConfig().get(p1)).retirerElement(m);
                            Element.maListe.add(m);
                            return true;
                            
                        }else 
                            return false;
                    }
                    
                }
                else {//la sortie de m est un succés alors on le retire de son monde initial(dès le premier essai)
                    if(m.getType()==Type.CAISSE || (m.getType()==Type.MONDE))
                         ((Configuration) m.getConfig().get(p1)).retirerElement(m);
                    
                    Element.maListe.add(m);
                     return true;
                    }
            }
                  
        }
        return false;
}
/********************************************************************************************************************************* */
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
