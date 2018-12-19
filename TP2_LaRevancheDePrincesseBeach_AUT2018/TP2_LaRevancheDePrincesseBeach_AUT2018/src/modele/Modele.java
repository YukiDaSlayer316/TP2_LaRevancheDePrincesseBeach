/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author 1767250
 */
public class Modele extends Observable {

     private Random rand = new Random();
    private int LONGUEUR, HAUTEUR, healthPointsMax = 3, healthPoints, points = 0;
    private boolean finDePartie = false;
    

    public Modele(int LONGUEUR, int HAUTEUR) {
        this.LONGUEUR = LONGUEUR;
        this.HAUTEUR = HAUTEUR;
        this.healthPoints = healthPointsMax;
    }

   

    public float getNouvellePosY(float maxY) {

        return rand.nextFloat() * maxY;

    }

    public void resetHealthPoints() {
        healthPoints = healthPointsMax;
    }

    public void majObservers() {
        setChanged();
        notifyObservers();
    }

    public void compteARebours() {
        long temps = System.currentTimeMillis();

        while (System.currentTimeMillis() < temps + 2000) {
        }

        finDePartie = false;

    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void enleverHealthPoints() {
        this.healthPoints -= 1;
        if (healthPoints < 1) {
            finDePartie = true;
        }
        majObservers();
    }

    public void ajouterPoints(int points) {
        this.points += points;
        majObservers();
    }

    public int getPoints() {
        return points;
    }

    public boolean isFinDePartie() {
        return finDePartie;
    }

    public void resetPoints() {
        points = 0;

    }

    public float[][] getPositionRoue() {
        int rayon = 100;
        float angle = 0;
        float[][] positionRoue = new float[3][8];

        for (int i = 0; i < 8; i++) {
            positionRoue[0][i] = (float) cos(angle) * rayon;
            positionRoue[1][i] = (float) sin(angle) * rayon;
            positionRoue[2][i] = angle;
            angle += PI / 4;
        }

        return positionRoue;

    }

    public void ajouterHealthPoints() {
        if (healthPoints < healthPointsMax) {
            this.healthPoints++;
        }
    }

    public int getHealthPointsMax() {
        return healthPointsMax;
    }

}
