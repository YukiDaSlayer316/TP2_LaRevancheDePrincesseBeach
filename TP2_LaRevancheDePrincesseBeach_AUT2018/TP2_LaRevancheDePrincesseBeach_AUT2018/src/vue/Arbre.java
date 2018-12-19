package vue;

import java.awt.image.BufferedImage;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
public class Arbre extends Background {

    private SpriteSheet spriteSheet;
    private int hauteur = 4;

    public Arbre(float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 1, 5);
        this.spriteSheet = spriteSheet;     
       
        setImage();  
        y=y-hauteur*32-this.height;
        setLocation(x, y);
   
    }

   

    private void setImage() {
         this.image = spriteSheet.getSubImage(160, 32, 32, 64);

    }

    
    protected int getHauteur() {
        return hauteur;
    }

    protected void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }


    @Override
    public void setLocation(float x, float y) { // Pour déplacer l’élément depuis le Jeu 
        this.x = x;
        this.y = y;
       
    }

}
