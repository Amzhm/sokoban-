public class Jeu {
    public static void main(String[] args) {
        Position p=new Position(0, 8);
        Position p1=new Position(8, 2);

        final Niveau n=new Niveau(9, 9,p,p1,null,null);
        n.affich();
       



    } 
        
    
    
}
