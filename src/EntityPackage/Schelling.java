/*
 * Schelling
 * 
 * Cette classe permet d'étendre la classe Immigration pour appliquer les règles de Schelling
 * Elle contient toutes les méthodes pour appliquer les règles du modèle de Schelling
 * et pour instantier un état initial random.
 * 
 *
 * 11/2021
 * 
 * Copyright notice
 */

package EntityPackage;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

public class Schelling extends Immigration{

    private static int NOMBRE_COULEURS = 5;
    private static int NOMBRE_VOISINS = 8;
    private static int SEUIL = 4;

    private Queue<int[]> file;

    public Schelling(int x, int y) {
        super(x, y);
    }

    //On modifie la méthode de remplissage car n valeurs
    @Override
    public void remplisseur(){
        this.file = new LinkedTransferQueue<>();
        //on initialise les valeurs de Cellules à des valeurs random 
        for (int i = 0; i<getSizeX(); i++){
            for (int j = 0; j<getSizeY(); j++){
                Random r = new Random();
                int etat = r.nextInt(NOMBRE_COULEURS+1);
                if (etat == 0){
                    int[] coord = {i,j};
                    file.add(coord);
                }
                Cellule NewCell = new Cellule(etat);
                this.grille[i][j] = NewCell;
                System.out.println(NewCell);
            }
        }
        System.out.println(this.file.size());
    }

    @Override
    public void AnimateJeu() {
        int N = getSizeX();
        int M = getSizeY();
        for (int x = 0; x<N; x++){
            for (int y = 0; y<M; y++){
                //On stocke les valeurs des voisins
                int[] voisins = {this.grille[x][Math.floorMod(y-1, M)].getEtat_prec(), this.grille[x][(y+1)%M].getEtat_prec(),
                       this.grille[Math.floorMod(x-1, N)][y].getEtat_prec(), this.grille[(x+1)%N][y].getEtat_prec(),
                       this.grille[Math.floorMod(x-1, N)][Math.floorMod(y-1, M)].getEtat_prec(), this.grille[Math.floorMod(x-1, N)][(y+1)%M].getEtat_prec(),
                       this.grille[(x+1)%N][Math.floorMod(y-1, M)].getEtat_prec(), this.grille[(x+1)%N][(y+1)%M].getEtat_prec()};
                
                //On applique les règles de Schelling
                int occurrences = CountOccurrences(voisins, this.grille[x][y].getEtat());
                int occurencesZero = CountOccurrences(voisins, 0);
                if (this.grille[x][y].getEtat_prec()==0){
                    continue;
                }
                if (NOMBRE_VOISINS-occurrences-occurencesZero>SEUIL){
                    int[] MaisonVide = this.file.remove();
                    int[] NouvMaisonVide = new int[] {x,y};
                    this.file.add(NouvMaisonVide);
                    this.grille[MaisonVide[0]][MaisonVide[1]].setEtat(this.grille[x][y].getEtat());
                    this.grille[x][y].setEtat(0);
                }
            }
        }
        System.out.println(this.file.size());
        NextEtatGrille();
    }

    @Override
    public void reinitGrille(){
        // On reinitialise la file
        this.file.clear();
        super.reinitGrille();
        for (int i = 0; i<getSizeX(); i++){
            for (int j = 0; j<getSizeY(); j++){
                if (this.grille[i][j].getEtat() == 0){
                    int[] coord = {i,j};
                    file.add(coord);
                }
            }
        }
    }
}
