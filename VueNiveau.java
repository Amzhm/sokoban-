import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class VueNiveau extends JPanel{


    private Configuration config;
    private VueCase[][] cases;
    private int nbligne,nbcoln;
    private VueNiveau n;
    public VueNiveau(Configuration c){
        nbligne=c.getY();
        nbcoln=c.getX();
        setBackground(Color.BLACK);
        cases=new VueCase[nbligne][nbcoln];
        setLayout(new GridLayout(nbligne,nbcoln));
        config=c;
        for(int i=0;i<nbligne;i++){
            for(int j=0;j<nbcoln;j++){
                Position p=new Position(i, j);
                if(config.get(p).getType()==Type.MONDE){
                    if(config.equals((config.get(p))))
                        n=this;
                    else
                        n=new VueNiveau((Configuration)config.get(p));
                    add(n);
                }else{
                    VueCase vc=new VueCase(config.get(p));
                    cases[j][i]=vc;
                    add(cases[j][i]);
                }
                
            }
        }
    }
    /*@Override
    public void paintComponent(Graphics g){
        for(int i=0;i<nbligne;i++){
            for(int j=0;j<nbcoln;j++){
                cases[j][i]= new VueCase(config.get(new Position(i,j)));
                add(cases[j][i]);
            }
        }
    }*/


    /*@Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                config.bougerJoueurVers(Direction.Haut);
                break;
            case KeyEvent.VK_DOWN:
                config.bougerJoueurVers(Direction.Bas);
                break;
            case KeyEvent.VK_LEFT:
                config.bougerJoueurVers(Direction.Gauche);
                break;
            case KeyEvent.VK_RIGHT:
                config.bougerJoueurVers(Direction.Droite);
                break;
            default:
                break;
        }
        this.removeAll();
        this.revalidate();;
        f.repaint();
    }*/
    
}
