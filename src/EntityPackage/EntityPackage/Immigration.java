/*
 * Schelling
 * 
 * Cette classe permet d'étendre la classe Conway pour appliquer les règles du jeu de l'Immigration
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

public class Immigration extends Conway{

    private static int NOMBRE_COULEURS = 4;
    private static int NOMBRE_VOISINS = 8;

    public Immigration(int x, int y) {
        super(x, y);
    }

    //On modifie la méthode de remplissage car n valeurs
    @Override
    public void remplisseur(){
        //on initialise les valeurs de Cellules à des valeurs random 0 ou 1
        for (int i = 0; i<getSizeX(); i++){
            for (int j = 0; j<getSizeY(); j++){
                Random r = new Random();
                Cellule NewCell = new Cellule(r.nextInt(NOMBRE_COULEURS+1));
                this.grille[i][j] = NewCell;
            }
        }
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
                
                //On applique les règles du jeu de l'immigration
                for (int count = 0; count<NOMBRE_VOISINS; count++){
                    if (voisins[count] != (this.grille[x][y].getEtat()+1)%(NOMBRE_COULEURS+1)){
                        continue;
                    }
                    int occurrences = CountOccurrences(voisins, voisins[count]);
                    if (occurrences >= 3){
                        this.grille[x][y].setEtat(voisins[count]);
                    }
                    break; 
                }
            }
        }
        NextEtatGrille();
    }

    public int CountOccurrences(int arr[], int x){
        int res = 0;
        for (int i=0; i<NOMBRE_VOISINS; i++)
            if (x == arr[i])
              res++;
        return res;
    }

    
}
