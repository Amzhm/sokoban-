public class Jeu {
    public static void main(String[] args) {
        final Niveau n=new Niveau(9, 9);
        Position p=new Position(1, 2);
        Position p3=new Position(3, 2);
        n.addCible(p);
        n.addCible(p3);
        n.affich();
        Position p1=new Position(1, 1);
        Configuration c=new Configuration(n, p1);
        Position p2=new Position(2, 2);
        
        c.addCaisse(p2);
        
        c.affich();
        c.bougerJoueurVers(Direction.Bas);
        c.bougerJoueurVers(Direction.Bas);
        c.bougerJoueurVers(Direction.Droite);
        c.affich();
        if(c.victoire())
        System.out.println("t'as gagne");
        c.bougerJoueurVers(Direction.Haut);
        c.bougerJoueurVers(Direction.Bas);
        c.affich();
        if(c.victoire())
            System.out.println("t'as gagne");



    } 
        
    
    
}
