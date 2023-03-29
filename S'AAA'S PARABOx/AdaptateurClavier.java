import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AdaptateurClavier extends KeyAdapter {
    static maFentre inter;
    static Configuration config;
    static ArrayList<Direction> direction=new ArrayList<Direction>();
    static ArrayList<Element> elements=new ArrayList<Element>();
    static boolean arriere=false;

    public AdaptateurClavier(maFentre i, Configuration c){
        inter=i;
        config=c;
    }
//Fonction qui permet de bouger le joueur dans la configuration, et de rafraichir la fenetre afin d'afficher toutes les modifications faites.
    public static void deplacer(Direction d){
        if(d!=null){
            if(!arriere){
                if(config.bougerJoueurVers(d))
                    direction.add(d);
                if(Chemin.chemin.contains(config.getJoueur().gePosition()))
                    Chemin.chemin.remove(config.getJoueur().gePosition());
                config=config.getJoueur().getConfig();
                if(!Element.maListe.isEmpty())
            			elements.add(Element.maListe.get(0));
            }else{
                elements.remove(elements.size()-1).bougerVers(d);
                config=config.getJoueur().getConfig();
            }
            
        }
        JPanel bg=new JPanel();
        bg.setBackground(Color.BLACK);
        VueNiveau panel=new VueNiveau(config,0);
        bg.setLayout(null);
        panel.setBounds(0,0,inter.getSize().width,inter.getSize().height);
        //panel.setBounds(30,30,inter.getSize().width-80,inter.getSize().height-100);
        bg.add(panel);
        inter.setContentPane(bg);
        inter.revalidate();
        inter.repaint();
    }

//Fonction qui permet de charger le niveau suivant et de l'afficher(ou d'afficher la fin du jeu).
public void niveauSuivant(){
    Soko.n++;
    if(Soko.n<=21){

        LectureFichier.safeguard(Soko.n);
        config=LectureFichier.ChargerFichier(Soko.niveaux.get(Soko.n-1),0);
        JPanel bg=new JPanel();
        bg.setBackground(Color.BLACK);
        VueNiveau panel=new VueNiveau(config,0);
        bg.setLayout(null);
        panel.setBounds(0,0,inter.getSize().width,inter.getSize().height);
        //panel.setBounds(50,50,inter.getSize().width-115,inter.getSize().height-150);
        bg.add(panel);
        inter.setContentPane(bg);
        inter.revalidate();
        inter.repaint();
    }else{
        inter.setContentPane(new VueCase(null, null, config)); 
        inter.revalidate();
        inter.repaint();  
    }
    
}
public void setConfig(Configuration c){
    config=c;
}
    @Override
    public void keyPressed(KeyEvent e){
        arriere=false;
        Element.maListe.clear();
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
            case KeyEvent.VK_ENTER:
                if(config.victoire())
                    niveauSuivant();
                break;
            case KeyEvent.VK_R:
                if(e.isControlDown()){
                    config=LectureFichier.ChargerFichier(Soko.niveaux.get(Soko.n-1), 0);
                    deplacer(null);
                }
                break;
            case KeyEvent.VK_Z :
                if(e.isControlDown()){
                    arriere=true;
                    deplacer(Direction.contraireDirection(direction.remove(direction.size()-1)));
                }
            	break;
            case KeyEvent.VK_ESCAPE:
                inter.setContentPane(inter.Menu());
                inter.revalidate();
                inter.repaint();
                break;
            case KeyEvent.VK_SPACE:
                deplacer(null);
                break;
            default:
                break;
        }
    }  
}

