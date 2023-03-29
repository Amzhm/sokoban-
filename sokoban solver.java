import java.util.LinkedList;
import java.util.List;

public class SokobanSolver {
    private Niveau niveau;
    private boolean[][] visitee;
    private LinkedList<Direction> directions;
    
    public void sokobanSolver(Niveau niveau){
        this.niveau = niveau ;
        visitee = new boolean[niveau.getX()][niveau.getY()];
        directions = new LinkedList<>();
    }

    public List<Direction> solve(){
        Position joueuPosition = niveau.getPosition();
        check(joueurPostion);
        return this.directions;
        }
    public boolean dfs(Position position) {
        visitee[position.getX()][Position.getY()]= true;

        // Verifier si le joueur est sur une cible
        if(niveau.estCible(position)){
            niveau.addCible(position)
        }
        //Verifier si toutes les caisses sont sur des cibles 
        boolean allCaisseSurCible= true;
        for(Caisse caisse :niveau){
            Position CaissePosition = caisse.gePosition();
            if(niveau.estCible(CaissePosition)){
                allCaisseSurCible = false;
                break;
            }
        }
        if(allCaisseSurCible){
            return true;
        }
        //La on essaie tous les mouvements ou deplacement possible
        for(Direction direction : this ){
            Position newPosition = position.add(direction);
            if(!niveau.estSortie(newPosition)) || visitee[newPosition.getX()][newPosition.getY()]{
                continue;
            }
        }
            
            Type type = niveau.getType(newPosition);
            if(type == Type.MUR){
                continue;

            }

            boolean pushedBox = false;
            if(type == Type.CAISSE){
                Position newCaissePosition= newPosition.add(direction);
                if(!niveau.estSortie(newCaissePosition)) || (!(niveau.estVide(newCaissePosition))){
                    continue;
                }
                niveau.addCible(newCaissePosition);
                directions.add(niveau.getSortie(direction));

                if(check(newCaissePosition)) {
                    return true;
                }

                //Annuler les mouvement si il represente pas une sortie
                directions.removeLast();
                
            }
        
            return false;
        }


            
}

      
  


    

