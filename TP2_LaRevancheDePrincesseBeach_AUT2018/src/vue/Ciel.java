package vue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1767250
 */
public class Ciel extends Background {
     public Ciel(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 13, 0);
    }

    @Override
    public void bouger() {
        //n'a pas besoin de bouger
    }
     
    
}
