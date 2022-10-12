package SimulatorPackage;

import gui.GUISimulator;
import EventPackage.EventManager;
import gui.*;

/**
 * Classe abstraite améliorant l'interface Simulable avec les usages communs
 * aux BallsSimulator et BoidSimulator
 *
 * TODO : Passer balls ou boids en attribut de la classe abstraite en concevant
 * une superclasse "Entité" par exemple
 * (on aurait plus à implémenter restart par exemple et le squelette commun aux
 * constructeurs n'aura pas à être retapé)
 */
public abstract class Simulator implements Simulable{
    protected GUISimulator gui;
    protected EventManager eManager;

    public Simulator(GUISimulator gui){
        this.gui = gui;
        this.eManager = new EventManager();
    }


    /**
     * Iméplémentation du contrôle "Suivant" de la GUI
     */
    public void next(){
        animate();
        eManager.next();
        this.gui.reset();
        printElements();
    }

    public GUISimulator getGui(){
        return this.gui;
    }

    public abstract void animate(); // Implémente les règles de mouvement, spécifique au type d'entité
    public abstract void restart(); // Contrôle de reset GUI
    public abstract void printElements(); // Affichage graphique des éléments

}