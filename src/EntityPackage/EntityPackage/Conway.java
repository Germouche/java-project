/*
 * Conway
 * 
 * Cette classe permet de manier un tableau de cellules.
 * Elle contient toutes les méthodes pour appliquer les règles du jeu de la vie et pour instantier le tableau du jeu.
 *
 * 11/2021
 * 
 * Copyright notice
 */

package EntityPackage;

 public class Conway {

    protected Cellule[][] grille;

    public Cellule[][] getGrille() {
        return grille;
    }
    public void setGrille(Cellule[][] grille) {
        this.grille = grille;
    }

    //Constructeur
    public Conway(int x, int y){
        this.grille = new Cellule[x][y];
        remplisseur();
    }

    //Fonctions pour récupérer la taille de la matrice
    public int getSizeX(){
        return this.grille.length;
    }
    public int getSizeY(){
        return this.grille[0].length;
    }
    
    public void remplisseur(){
        //on initialise les valeurs de Cellules à des valeurs random 0 ou 1
        for (int i = 0; i<getSizeX(); i++){
            for (int j = 0; j<getSizeY(); j++){
                Cellule NewCell = new Cellule(Math.random()<0.5?0:1);
                this.grille[i][j] = NewCell;
            }
        }
    }

    public void AnimateJeu(){
        int N = getSizeX();
        int M = getSizeY();
        for (int x = 0; x<N; x++){
            for (int y = 0; y<M; y++){
                int sum = 0;
                //On fait la somme des 8 voisins Math.floorMod(-1, 2)
                sum = (this.grille[x][Math.floorMod(y-1, M)].getEtat_prec() + this.grille[x][(y+1)%M].getEtat_prec() +
                       this.grille[Math.floorMod(x-1, N)][y].getEtat_prec() + this.grille[(x+1)%N][y].getEtat_prec() +
                       this.grille[Math.floorMod(x-1, N)][Math.floorMod(y-1, M)].getEtat_prec() + this.grille[Math.floorMod(x-1, N)][(y+1)%M].getEtat_prec() +
                       this.grille[(x+1)%N][Math.floorMod(y-1, M)].getEtat_prec() + this.grille[(x+1)%N][(y+1)%M].getEtat_prec());
                //On applique le jeu de la vie
                if (this.grille[x][y].getEtat() == 0){
                    this.grille[x][y].setEtat(sum==3?1:0);
                } else if (this.grille[x][y].getEtat() == 1){
                    if (sum!=3 && sum!=2){
                        this.grille[x][y].setEtat(0);
                    }
                }
            }
        }
        NextEtatGrille();
    }

    public void NextEtatGrille(){
        for(Cellule[] colonne : grille){
            for(Cellule cell : colonne){
                cell.setEtat_prec(cell.getEtat());
            }
        }
    }

    public void reinitGrille(){
        for(Cellule[] colonne : grille){
            for(Cellule cell : colonne){
                cell.reinit();
            }
        }
    }
}
