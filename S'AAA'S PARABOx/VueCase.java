
import javax.swing.*;
import java.awt.*;
public class VueCase extends JComponent{
    private Element moi;
    private Position p;
    private Configuration c;
    public VueCase(Element e,Position p,Configuration c){
        super();
        moi=e;
        this.p=p;
        this.c=c;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Image image;
        Graphics2D g2d = (Graphics2D) g;
        if(moi==null){
            if(Soko.n==22){//Afficher l'image de la fin de jeu
                image=getToolkit().getImage("./data/win.jpg");
                g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
            }else{//Afficher la première image du jeu
                image=getToolkit().getImage("./data/Menu.png");
                g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
            }
        }else{  //Savoir le numéro du niveau à l'interieur du fichier, se qui nous permettra de donner différentes couleurs à chaque sous monde
                if(c.getNumNiv()==0){
                    if(moi.getType()==Type.JOUEUR){
                        image=getToolkit().getImage("./data/joueur.jpg");
                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                    }else{
                        if(moi.getType()==Type.MUR){
                            image=getToolkit().getImage("./data/Mur.jpeg");
                            g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                        }
                        else{ 
                                if(moi.getType()==Type.CAISSE){
                                    image=getToolkit().getImage("./data/Caisse.jpg");
                                    g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                }else{
                                    if(!Chemin.chemin.isEmpty() && Chemin.chemin.contains(p)){
                                        image=getToolkit().getImage("./data/chemin.jpg");
                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                    }else{
                                            if(c.estCibleJoueur(p)){
                                                image=getToolkit().getImage("./data/CibleJ.jpg");
                                                g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                            }else{
                                                    if(c.estCible(p)){
                                                        image=getToolkit().getImage("./data/Cible.jpg");
                                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this); 
                                                }else{
                                                    image=getToolkit().getImage("./data/case.jpg");
                                                    g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                                }
                                            }
                                    }
                                }
                        }
                    }
                }else{
                    if(c.getNumNiv()==3){
                        if(moi.getType()==Type.JOUEUR){
                            image=getToolkit().getImage("./data/joueur.jpg");
                            g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                        }else{
                            if(moi.getType()==Type.MUR){
                                image=getToolkit().getImage("./data/Mur2.jpeg");
                                g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                            }
                            else{       
                                    if(moi.getType()==Type.CAISSE){
                                        image=getToolkit().getImage("./data/Caisse.jpg");
                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                    }else{
                                        if(!Chemin.chemin.isEmpty() && Chemin.chemin.contains(p)){
                                            image=getToolkit().getImage("./data/chemin.jpg");
                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                        }else{
                                                if(c.estCibleJoueur(p)){
                                                    image=getToolkit().getImage("./data/CibleJ.jpg");
                                                    g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                                }else{
                                                        if(c.estCible(p)){
                                                            image=getToolkit().getImage("./data/Cible2.jpeg");
                                                            g2d.drawImage(image,0,0,getSize().width,getSize().height,this); 
                                                    }else{
                                                        image=getToolkit().getImage("./data/case2.jpeg");
                                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                                    }
                                                }
                                        }
                                    }
                            }
                        }
                    }else{
                        if(moi.getType()==Type.JOUEUR){
                            image=getToolkit().getImage("./data/joueur.jpg");
                            g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                        }else{
                            if(moi.getType()==Type.MUR){
                                image=getToolkit().getImage("./data/Mur4.jpeg");
                                g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                            }
                            else{       
                                    if(moi.getType()==Type.CAISSE){
                                        image=getToolkit().getImage("./data/Caisse.jpg");
                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                    }else{
                                        if(!Chemin.chemin.isEmpty() && Chemin.chemin.contains(p)){
                                            image=getToolkit().getImage("./data/chemin.jpg");
                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                        }else{
                                                if(c.estCibleJoueur(p)){
                                                    image=getToolkit().getImage("./data/CibleJ4.jpg");
                                                    g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                                }else{
                                                        if(c.estCible(p)){
                                                            image=getToolkit().getImage("./data/Cible4.jpeg");
                                                            g2d.drawImage(image,0,0,getSize().width,getSize().height,this); 
                                                    }else{
                                                        image=getToolkit().getImage("./data/case4.jpeg");
                                                        g2d.drawImage(image,0,0,getSize().width,getSize().height,this);
                                                    }
                                                }
                                        }
                                    }
                            }
                        }
                    }
                    
                }
        }
    }
        
}

