import java.awt.event.*;
public class AdaptateurSouris implements MouseListener{
    private Configuration config;
    private Position p;

    public AdaptateurSouris(Configuration e,Position p){
        config=e;
        this.p=p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Chemin.chemin.clear();
        Chemin.ParcoursLargeur(config.getJoueur().gePosition(),p, config);
        /*for(int i=0;i<Chemin.chemin.size();i++){
            config.getJoueur().setPosition(Chemin.chemin.get(Chemin.chemin.size()-1-i));
            AdaptateurClavier.deplacer(null);   
        }*/
        AdaptateurClavier.deplacer(null);   
        
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
    
    
}
