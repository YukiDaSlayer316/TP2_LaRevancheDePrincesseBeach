/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1767250
 */
public class Background extends Entite implements Bougeable{
    
    private float deplacementEcranX=1.6f;
    //maxY;
    
    public Background(float x, float y, SpriteSheet spriteSheet,int xSprite,int ySprite) {
        super(x, y, spriteSheet, xSprite, ySprite);
    }

    @Override
    public void bouger() {
        x=x-deplacementEcranX;
    }

   /* @Override
    public void determinerDetruire() {
        
    }*/
    
    
}
