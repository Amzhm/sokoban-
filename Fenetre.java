import javax.swing.*;
import java.awt.*;
public class Fenetre extends JFrame {
    private JPanel panel=new JPanel();
    private VueNiveau panel1;
    public Fenetre(Configuration c){
        setTitle("SOKO");
        setSize(getToolkit().getScreenSize());
        panel.setBackground(Color.BLACK);
        //panel1=new VueNiveau(c,this);
        panel.setLayout(null);
        panel1.setBounds(50,50,this.getSize().width-115,this.getSize().height-150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.add(panel1);

        setContentPane(panel);
        //add(panel);
        //this.addKeyListener(new AdaptateurClavier(this, c));
        setVisible(true);

    }
    /*public void repaint() {
		panel.repaint();
	}*/

}
