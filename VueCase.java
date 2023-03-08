import javax.swing.*;
import java.awt.*;
public class VueCase extends JPanel{
    private Element moi;

    public VueCase(Element e){
        super();
        moi=e;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(moi.getType()==Type.JOUEUR){
            g.setColor(Color.GREEN);
            g.fillOval(0,0, getSize().width,getSize().height); 
        }else{
            if(moi.getType()==Type.MUR){
                g2d.setColor(new Color(132,46,27));
                        g2d.fillRoundRect(0,0, getSize().width, getSize().height, 0, 0);
            }
            else{
                if(moi.getType()==Type.MONDE){
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fillRoundRect(0,0, getSize().width, getSize().height, 0, 0);
                }else{
                    if(moi.getType()==Type.CAISSE){
                        g2d.setColor(Color.BLUE);
                        g2d.fillRoundRect(0,0, getSize().width, getSize().height, 0, 0);
                    }else{
                        g2d.setColor(Color.BLACK);
                    g2d.fillRoundRect(0,0, getSize().width, getSize().height, 0, 0);
                    }
                }
            }
        }
    }
}
