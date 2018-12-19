/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import static controleur.Controleur.HAUTEUR;
import static controleur.Controleur.LONGUEUR;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1767250
 */
public class BulletEnnemi extends Entite implements Collisionnable,Bougeable {

    private float deltaY = -6.0f;
    private int tempsAnimation = 0;
    private long temps;
    private boolean finAnimation = false;
    private ArrayList<Image> listeImage = new ArrayList();

    public BulletEnnemi(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 5, 5);
        listeImage.add(spriteSheet.getSubImage(3, 5));
        listeImage.add(spriteSheet.getSubImage(4, 5));
        listeImage.add(spriteSheet.getSubImage(5, 5));

    }

    @Override
    public void bouger() {
        y = y + deltaY;
        x -= 1.6f;
        if (y + height < 0 || x > LONGUEUR || x + width < 0) {
            this.setDetruire(true);
        }
        animation();

    }

    private void animation() {
        if (!finAnimation) {
            if (tempsAnimation == 0) {             
                image = listeImage.get(2);
            } else if (tempsAnimation == 15) {
                image = listeImage.get(1);
            } else if (tempsAnimation == 30) {
                image = listeImage.get(0);
                finAnimation = true;
            }

            tempsAnimation++;

        }

    }

}
