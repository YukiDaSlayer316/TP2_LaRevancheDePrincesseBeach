/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1749637
 */
public abstract class Bonus extends Entite implements Bougeable{
    
    public Bonus(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
    }

    @Override
    public void bouger() {
        x=x-1.6f;
        
     if( x<-buffer){
         this.setDetruire(true);
     }
    }
    
    public abstract void activerPouvoir();
    
    
}
