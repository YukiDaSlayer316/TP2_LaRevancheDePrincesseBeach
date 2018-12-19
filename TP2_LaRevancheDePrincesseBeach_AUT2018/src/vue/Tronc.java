/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author 1767250
 */
public class Tronc extends Background{
    
    public Tronc(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 2, 5);
        
    }
    
    protected void setImage(Image image){
    this.image=image;
    
    
    }
    
    
}
