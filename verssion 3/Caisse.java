public class Caisse extends Mobile {
    public Caisse(Configuration c,Position p){
        super(Type.CAISSE, c, p);
    }
    @Override
    public String toString(){
        String s="$";
        return s;
    }
    
}
