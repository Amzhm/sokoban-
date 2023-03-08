import javax.swing.*;
import java.awt.*;
public class maFentre extends JFrame{
    private VueNiveau panel;
    public maFentre(Configuration c){
        setTitle("SOKO");
        setSize(getToolkit().getScreenSize());
        JPanel bg=new JPanel();
        bg.setBackground(Color.BLACK);
        panel=new VueNiveau(c);
        this.addKeyListener(new AdaptateurClavier(this, c));
        bg.setLayout(null);
        panel.setBounds(50,50,this.getSize().width-115,this.getSize().height-150);
        bg.add(panel);
        setContentPane(bg);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
}
