import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class AdaptateurClavier extends KeyAdapter {
    maFentre inter;
    Configuration config;

    public AdaptateurClavier(maFentre i, Configuration c){
        inter=i;
        config=c;
    }

    public void deplacer(Direction d){
        config.bougerJoueurVers(d);
        config=config.getJoueur().getConfig();
        JPanel bg=new JPanel();
        bg.setBackground(Color.BLACK);
        VueNiveau panel=new VueNiveau(config);
        bg.setLayout(null);
        panel.setBounds(50,50,inter.getSize().width-115,inter.getSize().height-150);
        bg.add(panel);
        inter.setContentPane(bg);
        inter.revalidate();
        inter.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                deplacer(Direction.Haut);
                break;
            case KeyEvent.VK_DOWN:
                deplacer(Direction.Bas);
                break;
            case KeyEvent.VK_LEFT:
                deplacer(Direction.Gauche);
                break;
            case KeyEvent.VK_RIGHT:
                deplacer(Direction.Droite);
                break;
            default:
                break;
        }
    }
    
}

