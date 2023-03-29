import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class maFentre extends JFrame{
    //private VueNiveau panel;
    private Configuration c;
    static AdaptateurClavier AC;
    public maFentre(){
        //(Soko.niveaux.get(Soko.n-1)
        Soko.n=LectureFichier.loadbackup("./data/fichiersauvgarde.txt");
        c=LectureFichier.ChargerFichier(Soko.niveaux.get(Soko.n-1),0);
        setTitle("SOKO");
        setSize(getToolkit().getScreenSize());
        AC=new AdaptateurClavier(this, c);
        this.addKeyListener(AC);
        this.setContentPane(PremiereFenetre());
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public JComponent PremiereFenetre(){
        JComponent bg=new VueCase(null, null, null);
        
        return bg;
    }

    public JPanel Menu(){
        JPanel panelBoutons = new JPanel(new GridLayout(5, 1));
	    panelBoutons.setBackground(Color.black);
        
        JButton boutonLevel1 = new JButton("Recommencer le jeu");
	    boutonLevel1.setFont(new Font("Verdana", 1, 20));
	    boutonLevel1.setForeground(Color.WHITE);
	    boutonLevel1.setBackground(Color.black);
	    boutonLevel1.setFocusable(false);
        boutonLevel1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        		Soko.n=1;
                    c=LectureFichier.ChargerFichier(Soko.niveaux.get(Soko.n-1),0);
                    maFentre.AC.setConfig(c);
                    AdaptateurClavier.deplacer(null);
                    // lancer soko sur niveau 1
	        }
	    });
        JButton cnt = new JButton("Reprendre");
	    cnt.setFont(new Font("Verdana", 1, 20));
	    cnt.setForeground(Color.WHITE);
	    cnt.setBackground(Color.black);
	    cnt.setFocusable(false);
        cnt.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
                AdaptateurClavier.deplacer(null);
	        }
	    });
        JButton aide = new JButton("Aide");
	    aide.setFont(new Font("Verdana", 1, 20));
	    aide.setForeground(Color.WHITE);
	    aide.setBackground(Color.black);
	    aide.setFocusable(false);
        panelBoutons.add(cnt);
        panelBoutons.add(boutonLevel1);
        panelBoutons.add(aide);
        JPanel panelPrincipal = new JPanel(new BorderLayout());
	    panelPrincipal.setBackground(Color.black);
        panelPrincipal.add(panelBoutons, BorderLayout.CENTER);
        return panelPrincipal;


    }

}
