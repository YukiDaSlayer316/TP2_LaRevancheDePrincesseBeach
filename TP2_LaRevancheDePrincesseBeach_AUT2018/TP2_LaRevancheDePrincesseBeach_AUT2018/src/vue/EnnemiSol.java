/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1749637
 */
public class EnnemiSol extends Ennemi {

    private int delaiAnimation = 0;
    private ArrayList<Image> listeImage = new ArrayList();
    private SpriteSheet spriteSheet;
    private boolean tireEnCours = false;

    public EnnemiSol(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 3, 2);
        listeImage.add(spriteSheet.getSubImage(0, 3));
        listeImage.add(spriteSheet.getSubImage(1, 3));
        listeImage.add(spriteSheet.getSubImage(2, 3));
        this.spriteSheet = spriteSheet;
        deltaX = 1.6f;

    }

    @Override
    public void bouger() {
        if (delaiAnimation <= 50) {
         tireEnCours = false;
            image = listeImage.get(2);

        } else if (delaiAnimation > 50 && delaiAnimation <= 100) {

            image = listeImage.get(1);

        } else if (delaiAnimation > 100 && delaiAnimation <= 150) {

            image = listeImage.get(0);

        } else {
            tireEnCours = true;
            delaiAnimation = 0;
        }

        delaiAnimation++;
        x = x - deltaX;

    }

    protected void tirer() {

        tireEnCours = true;

    }

    protected boolean tireEnCours() {

        return tireEnCours;

    }

}
