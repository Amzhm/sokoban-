import javax.swing.*;
import java.awt.*;

public class NiveauGraphique extends JPanel{
    Configuration config;
    int largeurCase,hauteurCase;

    public NiveauGraphique(Configuration c,int l,int h){
        config=c;
        largeurCase=l;
        hauteurCase=h;
        setBackground(Color.BLACK);
}

    @Override 
    public void paintComponent(Graphics g){

        NiveauGraphique petit=null;
        super.paintComponent(g);
        Graphics2D drawable=(Graphics2D) g;
        //drawable.clearRect(0, 0, width, height);
        largeurCase=largeurCase/config.getX();
        hauteurCase=hauteurCase/config.getY();
        largeurCase=Math.min(largeurCase, hauteurCase);
        hauteurCase=largeurCase;
        for(int l=0;l<config.getY();l++){
            for(int c=0;c<config.getX();c++){
                int x=l*largeurCase;
                int y=c*hauteurCase;
                Position p=new Position(c, l);
                Element e= config.get(p);
                if(e.getType()== Type.MUR){
                        drawable.setColor(new Color(132,46, 27));
                        drawable.fillRoundRect(x, y, largeurCase, hauteurCase,20,20);
                    }else{
                        if(config.estCible(p)){
                            drawable.setColor(Color.white);
                            drawable.fillRoundRect(x, y, largeurCase, hauteurCase,20,20);
    
                        }else{
                            if(e.getType()==Type.JOUEUR){
                                drawable.setColor(Color.green);
                                drawable.fillOval(x, y, largeurCase,hauteurCase);
                            }else{
                                if(e.getType()==Type.CAISSE){
                                    drawable.setColor(Color.yellow);
                                    drawable.fillRoundRect(x, y, largeurCase, hauteurCase,20,20);
                                }else{
                                    if(e.getType()==Type.MONDE){
                                        petit=new NiveauGraphique((Configuration)e, largeurCase, hauteurCase);
                                        petit.setBounds(x,y,this.getSize().width,this.getSize().height);
                                        this.setLayout(null);
                                        


                                    }else{
                                        drawable.setColor(Color.black);
                                        drawable.fillRoundRect(x, y, largeurCase, hauteurCase,20,20);
                                    }
                                }
                            }
                        }
                    }
            }
        }
        this.add(petit);

    }
}
    

