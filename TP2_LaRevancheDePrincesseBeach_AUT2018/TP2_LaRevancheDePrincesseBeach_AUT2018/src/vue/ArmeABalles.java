/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1749637
 */
public class ArmeABalles extends Bonus{
    
    public ArmeABalles(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 2, 1);
    }

    @Override
    public void activerPouvoir() {
        //modele.activerArmesABalles();
    }
    
}
