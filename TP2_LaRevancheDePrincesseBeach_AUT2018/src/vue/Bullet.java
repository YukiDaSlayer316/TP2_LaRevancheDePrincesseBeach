package vue;

import controleur.Controleur;
import static controleur.Controleur.HAUTEUR;
import static controleur.Controleur.HAUTEUR_PLANCHER;
import static controleur.Controleur.LONGUEUR;
import org.newdawn.slick.SpriteSheet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 1767250
 */
public class Bullet extends Entite implements Bougeable {

    private int deltaX = 12, deltaY = 0;

    public Bullet(float x, float y) {
        super(x, y, 16,16,"images/boulet.png");
    }

    @Override
    public void bouger() {
        x = x + deltaX;
        y = y + deltaY;

        if (x > LONGUEUR + buffer||y>HAUTEUR-HAUTEUR_PLANCHER||y<0) {
            this.setDetruire(true);
        }

    }

    protected void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    /* protected void setDeltaX(int deltaX) {
        this.deltaX = deltaX;

    }*/
}
