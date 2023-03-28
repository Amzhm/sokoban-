import java.util.*;

public class Resolution {
    private Niveau niveau;
    private Joueur joueur;

    public Resolution(Niveau niveau, Joueur joueur) {
        this.niveau = niveau;
        this.joueur = joueur;
    }

    public List<Position> trouverCheminPlusCourt(Position arrivee) {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visitee= new HashSet<>();
        Map<Position, Integer> distances = new HashMap<>();
        Map<Position, Position> predecesseurs = new HashMap<>();

        queue.add(joueur.gePosition());
        visitee.add(joueur.gePosition());
        distances.put(joueur.gePosition(), 0);

        while (!queue.isEmpty()) {
            Position courrante = queue.poll();

            for (Direction direction : Direction.values()) {
                Position next = courrante.add(direction.getOffset());

                if (niveau.getType(next) == Type.MUR || visitee.contains(next)) {
                    continue;
                }

                int distance = distances.get(courrante) + 1;

                if (!distances.containsKey(next) || distance < distances.get(next)) {
                    distances.put(next, distance);
                    predecesseurs.put(next, courrante);

                    if (next.equals(arrivee)) {
                        // Récupération du chemin le plus court
                        List<Position> chemin = new ArrayList<>();
                        Position position = arrivee;
                        while (position != null) {
                            chemin.add(position);
                            position = predecesseurs.get(position);
                        }
                        Collections.reverse(chemin); //pour avoir le bon ordre 
                        return chemin;
                    }

                    queue.add(next);
                    visitee.add(next);
                }
            }
        }

        // Aucun chemin trouvé
        return null;
    }
}

// dans le main 
public void drawPlusCourtChemin(List<Position> chemin) {
    Graphics g = getGraphics(); // Récupérer la grille du jeu 
    g.setColor(Color.RED); 
    for (int i = 0; i < chemin.size() - 1; i++) {
        Position p1 = chemin.get(i);
        Position p2 = chemin.get(i + 1);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}


 List<Case> chemin = Resolution.PlusCourtChemin(Pos);//(case selectionnee)
 drawPlusCourtChemin(chemin); 
