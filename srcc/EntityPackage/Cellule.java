/*
 * Cellule
 * 
 * Cette classe représente une Cellule à n états.
 * Les états permettent de stocker les valeurs à l'initialisation, à l'état n et à n+1
 *
 * 11/2021
 * 
 * Copyright notice
 */

package EntityPackage;


public class Cellule {

    private int Etat;
    private int Etat_prec;
    private int EtatInit;

    public int getEtat() {
        return Etat;
    }
    public void setEtat(int etat) {
        this.Etat = etat;
    }
    public int getEtat_prec() {
        return Etat_prec;
    }
    public void setEtat_prec(int etat_prec) {
        this.Etat_prec = etat_prec; 
    }
    public int getEtatInit() {
        return EtatInit;
    }
    public void setEtatInit(int etat_init) {
        this.EtatInit = etat_init;
    }

    /*
     * Le constructeur initialise la cellule à un état 0, donc les états actuels et précedent sont les mêmes.
     */
    public Cellule(int etat) {
        this.Etat = etat;
        this.EtatInit = etat;
        this.Etat_prec = etat;
    }

    @Override
    public String toString() {
        return "Cellule [Etat=" + Etat + ", EtatInit=" + EtatInit + ", Etat_prec=" + Etat_prec + "]";
    }

    /*
    * La néthode reinit fait revenir la cellule à son état 0 lors de la construction
    */
    public void reinit(){
        this.Etat = this.EtatInit;
        this.Etat_prec = this.EtatInit;
    }
    
}
