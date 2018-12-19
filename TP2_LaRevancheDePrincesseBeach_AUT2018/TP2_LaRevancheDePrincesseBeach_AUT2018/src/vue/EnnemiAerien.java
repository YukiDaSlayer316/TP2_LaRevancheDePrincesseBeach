/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import static controleur.Controleur.LONGUEUR;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1749637
 */
public class EnnemiAerien extends Ennemi {

 private float deltaX=2.5f;
    private int tempsAnimation=0;
    private int posImage=0;
    
    /* public EnnemiAerien(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, 1, 2);
    }*/
    private ArrayList<Image> listeAnimation = new ArrayList<>();

    public EnnemiAerien(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 2, 2);
        listeAnimation.add(spriteSheet.getSubImage(2, 2));
        listeAnimation.add(spriteSheet.getSubImage(3, 2));
    }

    @Override
    public void bouger() {
        super.bouger(); //To change body of generated methods, choose Tools | Templates.
        x-=deltaX;
        changerAnimation();
    }

    private void changerAnimation() {
         if (tempsAnimation == 0) {
           image=listeAnimation.get(0);
           
        } else if (tempsAnimation == 30) {
         image=listeAnimation.get(1);
        } else if (tempsAnimation == 60) {
          tempsAnimation=-1;
        }
         tempsAnimation++;
         

    }
   
    
}
