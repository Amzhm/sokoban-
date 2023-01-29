public class Position{
    private int x;
    private int y;

    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Position(Position p){
        x=p.getX();
        y=p.getY();
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Position add(Direction d){
        if(d==Direction.Haut)
            return new Position(getX()-1, getY());
        if(d==Direction.Droite)
            return new Position(getX(), getY()+1);
        if(d==Direction.Bas)
            return new Position(getX()+1, getY());
        if(d==Direction.Gauche)
            return new Position(getX(), getY()-1);
        return null;
    }

    public Position sub(Direction d){
        if(d==Direction.Haut)
            return new Position(getX(), getY()-1);
        if(d==Direction.Droite)
            return new Position(getX()-1, getY());
        if(d==Direction.Bas)
            return new Position(getX(), getY()+1);
        if(d==Direction.Gauche)
            return new Position(getX()+1, getY());
        return null;
    }
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Position))
            return false;
        Position p=(Position) o;
        if(getX()!=p.getX())
            return false;
        return getY()==p.getY();
    }
    
}