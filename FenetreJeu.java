
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*import java.net.NoRouteToHostException;

import static java.awt.BorderLayout.NORTH;
import static javax.swing.SwingConstants.*;*/
public class FenetreJeu extends JPanel implements KeyListener{

        private Configuration terrain;
        private int tailleCase = 50;
        private int hauteur, largeur;
        private int size=85;
        private JFrame frame;
        private Image player;

        public FenetreJeu(Configuration t) {
            this.hauteur = t.getY();
            this.largeur = t.getX();
            this.terrain = new Configuration(t);

            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(largeur * tailleCase, hauteur * tailleCase+32));
            this.setBounds(0,0,largeur*tailleCase,hauteur*tailleCase);
            JFrame frame = new JFrame("Sokob");
            this.frame = frame;
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.getContentPane().add(this);
            frame.pack();
            frame.setVisible(true);
            frame.addKeyListener(this);

        }
        public JFrame getFrame(){
            return this.frame;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            player=new ImageIcon("playerimg.png").getImage();
            //parcourt toutes les cases
            for (int l = 0; l < this.hauteur; l++) {
                for (int c = 0; c < this.largeur; c++) {
                    Position p=new Position(c, l);
                    Element tmp = terrain.get(p);
                    if (tmp instanceof Immobile) {
                        if( tmp.getType().name().equals("MUR")){
                            g2d.setColor(new Color(132,46,27));
                            g2d.fillRoundRect(l*size, c*size, size, size, 20, 20);
                            //g.setColor(Color.GRAY);
                            //g.fillRect(cc.col*tailleCase,cc.lig*tailleCase,tailleCase,tailleCase);
                        }
                        if(terrain.estCible(p)) {
                            g2d.setColor(Color.white);
                            g2d.fillRoundRect(l * size, c * size, size, size, 20, 20);
                        }
                    }
                    if (tmp instanceof Mobile) {

                        if(terrain.estJoueur(p)){
                        
                            
                            g.setColor(Color.GREEN);
                            g.fillOval(l*size, c*size, size, size);
                        }
                        if(tmp.getType().name().equals("CAISSE")){
                            g2d.setColor(Color.yellow);
                            g2d.fillRoundRect(l*size, c*size, size, size, 20, 20);

                        }
                    }

                }
            }

        }
        /*  public void ecranFinal() {
            frame.remove(this);
            JLabel label = new JLabel("YOU WIN");
            label.setFont(new Font("Verdana", 1, 20));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setSize(this.getSize());
            frame.getContentPane().add(label);
            JButton restart= new JButton("Rejouer");
            restart.setFont(new Font("Verdana", 1, 20));
            restart.setForeground(Color.black);
            restart.setHorizontalAlignment(SwingConstants.CENTER);
            restart.setFocusable(false);
            restart.setBounds((largeur/2)*tailleCase-100,(hauteur/2)*tailleCase+50,200,70);
            restart.setVisible(true);
            frame.getContentPane().add(restart);
            restart.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            frame.repaint();

        }*/

        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {
            Direction d=null;
            boolean arriere=false;
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    Element.elt.clear();
                    d = Direction.Gauche;
                    break;
                case KeyEvent.VK_RIGHT:
                    Element.elt.clear();
                    d = Direction.Droite;
                    break;
                case KeyEvent.VK_UP:Element.elt.clear();
                    d = Direction.Haut;
                    break;
                case KeyEvent.VK_DOWN:
                    Element.elt.clear();
                    d = Direction.Bas;
                    break;
                case KeyEvent.VK_Z:
                    d=Direction.contraireDirection(Soko.directions.remove(Soko.directions.size()-1));
					arriere=true;
					break;
                case KeyEvent.VK_C:
                    terrain=Soko.ChargerFichier("./src/jeux.txt", 0);
                    break;
            }
            if(d !=null){
                if (!arriere){
                    if(terrain.bougerJoueurVers(d))
                        Soko.directions.add(d);
                    terrain=terrain.getJoueur().getConfig();
                    if(!Element.elt.isEmpty())
                        Soko.elt.add(Element.elt.get(0));
                    
                        
                }else{
                    Soko.elt.remove(Soko.elt.size()-1).bougerVers(d);
                }
            }
            frame.repaint();

            }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }


