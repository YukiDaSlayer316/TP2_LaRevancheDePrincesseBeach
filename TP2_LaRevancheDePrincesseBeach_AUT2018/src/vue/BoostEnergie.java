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
public class BoostEnergie extends Bonus{
    
    public BoostEnergie(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 5, 4);
    }

    @Override
    public void activerPouvoir() {
        //modele.activerBonusBoostEnergie();
    }
    
}
