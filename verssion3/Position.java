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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    
}