import javax.swing.*;
import java.awt.*;
public class VueNiveau extends JPanel{


    private Configuration config;
    private int nbligne,nbcoln;
    private VueNiveau n;
    public VueNiveau(Configuration c,int k){
        nbligne=c.getY();
        nbcoln=c.getX();
        setBackground(Color.BLACK);
        setLayout(new GridLayout(nbligne,nbcoln));
        config=c;
        for(int i=0;i<nbligne;i++){
            for(int j=0;j<nbcoln;j++){
                Position p=new Position(i, j);
                if(config.get(p).getType()==Type.MONDE){
                    if(k<=4){
                        //Construction des sous mondes
                        n=new VueNiveau((Configuration)config.get(p),++k);
                        add(n);
                    }
                    
                }else{
                    //Construction des différents éléments du jeu
                    VueCase vc=new VueCase(config.get(p),p,config);
                    vc.addMouseListener(new AdaptateurSouris(config, p));
                    add(vc);
                }
                
            }
        }
    }
}
