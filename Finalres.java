import java.util.*;

public class Resolution {
    private int n;
    private Case[][] plateau;
    
   public Resolution(Case[][] plateau) {
    this.n = plateau.length * plateau[0].length;
    this.plateau = plateau;
}

public List<Case> PlusCourtChemin(int debutX, int debutY, int finX, int finY) {
    Case debut = plateau[debutX][debutY];
    Case fin = plateau[finX][finY];
    int[] distance = new int[n];
    boolean[] visitee = new boolean[n];
    Case[] precedent = new Case[n];
    PriorityQueue<Case> queue = new PriorityQueue<>(Comparator.comparingInt(c -> distance[c.getId()]));
    
    for (int i = 0; i < n; i++) {
        distance[i] = Integer.MAX_VALUE;
        visitee[i] = false;
        precedent[i] = null;
    }
    
    distance[debut.getId()] = 0;
    queue.offer(debut);
    
    while (!queue.isEmpty()) {
        Case case_actl = queue.poll();
        if (visitee[case_actl.getId()]) {
            continue;
        }
        visitee[case_actl.getId()] = true;
        
        for (Case voisin : getVoisins(case_actl)) {
            int distance_nvl = distance[case_actl.getId()] + 1;
            if (distance_nvl < distance[voisin.getId()]) {
                distance[voisin.getId()] = distance_nvl;
                precedent[voisin.getId()] = case_actl;
                queue.offer(voisin);
            }
        }
    }
    
    List<Case> chemin = new ArrayList<>();
    for (Case c = fin; c != null; c = precedent[c.getId()]) {
        chemin.add(c);
    }
    Collections.reverse(chemin) ; //
    
    return chemin;
}

private List<Case> getVoisins(Case c) {
    List<Case> voisins = new ArrayList<>();
    int x = c.getX();
    int y = c.getY(); 
    
    if (x > 0 && plateau[x-1][y].getType() != Type.MUR) {
        voisins .add(plateau[x-1][y]);
    }
    if (x < plateau.length-1 && plateau[x+1][y] != Type.MUR) {
        voisins.add(plateau[x+1][y]);
    }
    if (y > 0 && plateau[x][y-1] != Type.MUR) {
        voisins.add(plateau[x][y-1]);
    }
    if (y < plateau[0].length - 1 && plateau[x][y+1] != Type.MUR) {
        voisins.add(plateau[x][y+1]);
    }
    
    return voisins;
}

// dans le main 
public void drawPlusCourtChemin(List<Case> chemin) {
    Graphics g = getGraphics(); // recuperer la grille du jeu 
    g.setColor(Color.RED); 
    for (int i = 0; i < chemin.size() - 1; i++) {
        Case c1 = chemin.get(i);
        Case c2 = chemin.get(i + 1);
        g.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY());
    }
}



 List<Case> chemin = Resolution.PlusCourtChemin(debut, fin);//(case ou se trouve le joeur,case selectionnee)
 drawPlusCourtChemin(chemin); 












// JavaScript source code
