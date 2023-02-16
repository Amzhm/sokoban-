public enum Direction {
    Haut,
    Droite,
    Bas,
    Gauche;

    public Direction contraireDirection(Direction d){
        if(d==Direction.Haut)
            return Direction.Bas;
        if(d==Direction.Bas)
            return Direction.Haut;
        if(d==Direction.Droite)
            return Direction.Gauche;
        return Direction.Droite;
    }
}
