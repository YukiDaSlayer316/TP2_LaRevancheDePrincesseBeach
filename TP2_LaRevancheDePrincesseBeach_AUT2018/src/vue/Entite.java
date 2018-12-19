package vue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author 1767250
 */
public abstract class Entite {
    protected float x, y, width, height; // position et taille
    protected Image image; // L’image de l’entité
    protected boolean detruire = false; // Ne pas détruire si false
   // private boolean estInterieurEcran=true;
    protected int buffer=50;

    public Entite(float x, float y, float width, float height, String imagepath)     
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        try {
            image = new Image(imagepath);
        } catch (SlickException e) {
            System.out.println("Image non trouvée pour " + getClass());
        }
    }
    
    
    /**
     * Constructeur d'Entite #2 - Avec SpriteSheet
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param spriteSheet SpriteSheet qui contient l'image
     * @param ligne la ligne dans le SpriteSheet de l'image
     * @param colonne la colonne dans le SpriteSheet de l'image
     */

    
    public Entite(float x, float y, SpriteSheet spriteSheet, int ligne, int colonne) {
        this.x = x;
        this.y = y;
        this.image = spriteSheet.getSubImage(colonne, ligne);        
        this.width = image.getWidth();
        this.height = image.getHeight();        
        
    }
    


    public float getX() { // Position en X du coin supérieur gauche de l’entite
        return x;
    }

    public float getY() { // Position en Y du coin supérieur gauche de l’entite
        return y;
    }

    public float getWidth() { // Largeur de l’entite
        return width;
    }

    public float getHeight() { // Hauteur de l’entite
        return height;
    }
    
    public void setHeight(float height){
    this.height=height;
    }
    
    
    public void setWidth(float width){
    
    this.width=width;
    }

    public Image getImage() { // Retourne l’image de l’entité
        return image;
    }

    public Rectangle getRectangle(){ // Retourne le rectangle qui englobe l’entité
       return new Rectangle(x, y, width, height);
    }

    public void setLocation(float x, float y) { // Pour déplacer l’élément depuis le Jeu 
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
    
    public boolean getDetruire(){ // Si l’objet doit être détruit --> true, false sinon
        return detruire;
    }
    
    //public abstract void determinerDetruire();

    public void setDetruire(boolean detruire) {
        this.detruire = detruire;
    }
    
    
}

