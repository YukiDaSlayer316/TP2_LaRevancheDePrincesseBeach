/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author 1749637
 */
public class Joueur implements Observer{
    private int healthPoints=3;
    private int points=0;

    @Override
    public void update(Observable o, Object arg) {



    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setHealthPoints() {
        this.healthPoints -= 1;
    }
    
                
}
