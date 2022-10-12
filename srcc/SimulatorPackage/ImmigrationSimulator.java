package SimulatorPackage;
/*
 * ImmigrationSimulator
 * 
 * Cette classe permet de manier l'interface graphique pour afficher le jeu de l'immigration
 * Il s'agit d'une extension de la classe ConwaySimulator, qui override les méthodes qui changent 
 * d'un jeu à l'autre
 * Elle contient toutes les méthodes nécessaires à afficher, passer et revenir au début.
 *
 * 11/2021
 * 
 * Copyright notice
 */

import java.awt.Color;

import EntityPackage.Immigration;
import gui.* ;

public class ImmigrationSimulator extends ConwaySimulator {

    private static int NOMBRE_COULEURS = 4;

    //On se sert du constructeur hérité
    public ImmigrationSimulator(GUISimulator gui, int x, int y) {
        super(gui, x, y);
        this.jeu = new Immigration(x, y);
    }

    @Override
    public void animate() {
        super.animate();
    }


    @Override
    public Color intToCol(int colNum){
        int rgbNum = 255 - (int) (((float)colNum/NOMBRE_COULEURS)*255.0);
        return new Color (rgbNum,rgbNum,rgbNum);
    }
    
}
