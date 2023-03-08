import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class FenetreJ extends JFrame{
    private Configuration config;
    private NiveauGraphique ng;

    public FenetreJ(Configuration c){
        setTitle("SOKO");
        setSize(getToolkit().getScreenSize());
        JPanel panel=new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        config=new Configuration(c);
        ng=new NiveauGraphique(config,getSize().width,getSize().height);
        ng.setBounds(50,50,this.getSize().width,this.getSize().height);
       // panel.add(ng);
        setContentPane(ng);
        //add(ng);
        //addKeyListener(new AdaptateurClavier(this, config));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
    public void repaint() {
		ng.repaint();
	}
}
