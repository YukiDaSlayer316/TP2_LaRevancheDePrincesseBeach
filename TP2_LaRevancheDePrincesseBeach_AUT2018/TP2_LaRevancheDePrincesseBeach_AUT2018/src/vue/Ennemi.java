/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author emuli
 */
public abstract class Ennemi extends Entite implements Bougeable,Collisionnable {

    public float deltaX = 2.8f, deltaY = 0.3f;
    //private int nbEnnemi

    public Ennemi(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
    }

    @Override
    public void bouger() {

        if (x < -buffer) {
            this.setDetruire(true);
        }
    }

    /*  protected void setDeltaX(int deltaX) {
        this.deltaX = deltaX;

    }

    protected void setDeltaY(int deltaY) {
        this.deltaY = deltaY;

    }*/

 /* @Override
    public void determinerDetruire(){
        
    }*/
    @Override
    public void setDetruire(boolean detruire) {

        this.detruire = detruire;

    }
}
