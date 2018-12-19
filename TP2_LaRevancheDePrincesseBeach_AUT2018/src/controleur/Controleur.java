package controleur;

import java.io.File;
import modele.Modele;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import vue.Bougeable;
import vue.Jeu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 1767250
 */
public class Controleur {

    public static final int LONGUEUR = 1024, HAUTEUR = 768,HAUTEUR_PLANCHER=64;
    private int pointsEnnemiTues=5, pointsBonusRamasse=25;
    private Modele modele = new Modele(LONGUEUR,HAUTEUR);

    public Controleur() {
        System.setProperty("org.lwjgl.librarypath", new File("slick2dlib").getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", new File("slick2dlib").getAbsolutePath());
        try {
            AppGameContainer app;
            app = new AppGameContainer(new Jeu(LONGUEUR, HAUTEUR, this, modele));
            app.setDisplayMode(LONGUEUR, HAUTEUR, false);
            app.setShowFPS(true);
            app.setVSync(true);
            app.start();
        
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public void perdVie(){
        modele.enleverHealthPoints();
    }
     
    
    public void elimineEnnemis(){
        modele.ajouterPoints(pointsEnnemiTues);
    }
    
    public void ramasseBonus(){
        modele.ajouterPoints(pointsBonusRamasse);
    }

    public void ramasseBoostEnergie(){
        modele.ajouterHealthPoints();
    }
    
    public void activerBonus(Bougeable bonus) {
       //
    }
    
    public int getPointsJoueur(){
       return modele.getPoints();
    }

    public void ramasseBombeMega(int nbEnnemisTues) {
        modele.ajouterPoints(nbEnnemisTues*pointsEnnemiTues);        
    }
 
   

}
