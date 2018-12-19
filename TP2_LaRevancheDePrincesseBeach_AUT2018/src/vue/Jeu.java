package vue;

import controleur.Controleur;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import modele.Modele;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
public class Jeu extends BasicGame implements Observer {

    private ArrayList<Bougeable> listeBougeable = new ArrayList<>(); // Tous ce qui bouge
    private ArrayList<Entite> listeEntite = new ArrayList<>(); // Toutes les entités
    private ArrayList<KeyCode> listeKeys = new ArrayList<>(); // Les touches enfoncées
    private ArrayList<Coeur> listeCoeurs = new ArrayList<>(); // Les touches enfoncées
    private Thread timeEnnemi = new Thread();
    private Input input; // L’entrée (souris/touches de clavier, etc.)
    private long tempsEnnemi = System.currentTimeMillis(),tempsBonusTriple=0;;

    private int nbVies = 3, nbPoints = 0;
    // private Ennemi ennemi;
    private Joueur joueur;
    //private Bullet bullet;
    private int LONGUEUR, HAUTEUR, xd = 200, yd = 200, HAUTEUR_SOL = 64, maxArbre = 2, nbArbre = 0;
    private Random random = new Random();
    private SpriteSheet spriteSheetBonus;// = new SpriteSheet("/images/sprites_divers.png", 32, 32);
    private SpriteSheet spriteSheetTiles;// = new SpriteSheet("/images/tiles.png", 32, 32);
    private SpriteSheet spriteSheetPrincesse;// = new SpriteSheet("/images/sprites_princess.png", 32, 64);
    private SpriteSheet spriteSheetMonde;
    private SpriteSheet spriteSheetNuage;
    private SpriteSheet spriteSheetCoeur;
    private SpriteSheet spriteSheetEnnemi;

    //  private SpriteSheet spriteSheet;
    /*  private Plancher plancher;
    private Ciel ciel;
    private Nuage nuage;
    private Arbre arbre;*/
    private boolean finDePartie = false, compteARebours = false;
    public static final float buffer = 50;
    private Controleur controleur;
    private Modele modele;

    public Jeu(int LONGUEUR, int HAUTEUR, Controleur controleur, Modele modele) {
        super("Game");
        this.controleur = controleur;
        this.modele = modele;
        modele.addObserver(this);
        this.LONGUEUR = LONGUEUR;
        this.HAUTEUR = HAUTEUR;

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        spriteSheetBonus = new SpriteSheet("images/sprites_divers.png", 32, 32);
        spriteSheetTiles = new SpriteSheet("images/sprites_monde.png", 32, 32);
        spriteSheetPrincesse = new SpriteSheet("images/sprites_princess.png", 32, 64);
        spriteSheetMonde = new SpriteSheet("/images/sprites_monde.png", 32, 32);
        spriteSheetCoeur = new SpriteSheet("/images/coeur.png", 32, 32);
        spriteSheetEnnemi = new SpriteSheet("/images/sprites_divers.png", 32, 32);
        input = container.getInput();

        for (int f = 0; f < 65; f += 32) {
            for (int i = 0; i <= LONGUEUR; i += 32) {

                Plancher plancher = new Plancher(i, HAUTEUR - f, spriteSheetTiles);
                listeEntite.add(plancher);
                listeBougeable.add(plancher);
            }
        }
        for (int f = 0; f < LONGUEUR; f += 32) {
            for (int i = 0; i < HAUTEUR - HAUTEUR_SOL; i += 32) {
                Ciel ciel = new Ciel(f, i, spriteSheetTiles);
                listeEntite.add(ciel);
                listeBougeable.add(ciel);
            }

        }

        int[][] positionArbre =getPositionBackground(LONGUEUR - 100, HAUTEUR - 96, 100);

        for (int i = 0; i < maxArbre; i++) {

            Arbre arbre = new Arbre(positionArbre[i][0], HAUTEUR - 96, spriteSheetMonde);

            listeEntite.add(arbre);
            listeBougeable.add(arbre);

            for (int f = 0; f <= arbre.getHauteur(); f++) {
                Tronc tronc = new Tronc(positionArbre[i][0], HAUTEUR - 96 - arbre.getHauteur() * 32 + f * 32, spriteSheetMonde);
                listeEntite.add(tronc);
                listeBougeable.add(tronc);
            }

            nbArbre++;

        }

        joueur = new Joueur(64, HAUTEUR - 128, spriteSheetPrincesse);
        listeEntite.add(joueur);

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        if (!finDePartie) {
            if (System.currentTimeMillis() > tempsEnnemi + 2500) {
                declencherWaveEnnemis();
                tempsEnnemi = System.currentTimeMillis();
            }

            for (Bougeable bougeable : listeBougeable) {
                bougeable.bouger();
            }

            deplacerBackground();
            enleverEntitesExterieurEcran();

            getKeys();
            traiterKeys();

            gererCollisionJoueurEnnemi();
            gererCollisionBulletEnnemi();
            gererCollisionJoueurBonus();
            gererArbre();
            tireEnnemi();
        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        for (Entite entite : listeEntite) {
            //entite.setLocation(entite.getX(), entite.getY());

            g.drawImage(entite.getImage(), entite.getX(), entite.getY());
        }
        //afficherPoints();
        g.drawString("Points: " + controleur.getPointsJoueur(), LONGUEUR - buffer * 5, 10);/*  //afficherCoeur();*/
        int posXCoeur = 16, posYCoeur = 32;
        for (int i = 0; i < nbVies; i++) {
            g.drawImage(spriteSheetCoeur, posXCoeur, posYCoeur);
            //posXCoeur += coeur.getWidth();
            posXCoeur += 32;
        }

        if (compteARebours) {
            g.drawString(" GAME OVER", LONGUEUR / 2 - 10, HAUTEUR / 2);

        }

    }
    
    

    private void getKeys() {
        if (input.isKeyDown(Input.KEY_SPACE)) {
            if (!listeKeys.contains(KeyCode.SPACE)) {
                listeKeys.add(KeyCode.SPACE);
            }
        } else {
            listeKeys.remove(KeyCode.SPACE);
        }

        if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            if (!listeKeys.contains(KeyCode.RIGHT)) {
                listeKeys.add(KeyCode.RIGHT);
            }
        } else {
            listeKeys.remove(KeyCode.RIGHT);
        }

        if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            if (!listeKeys.contains(KeyCode.LEFT)) {
                listeKeys.add(KeyCode.LEFT);
            }
        } else {
            listeKeys.remove(KeyCode.LEFT);
        }

        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            if (!listeKeys.contains(KeyCode.UP)) {
                listeKeys.add(KeyCode.UP);
            }
        } else {
            listeKeys.remove(KeyCode.UP);
        }

        if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            if (!listeKeys.contains(KeyCode.DOWN)) {
                listeKeys.add(KeyCode.DOWN);
            }
        } else {
            listeKeys.remove(KeyCode.DOWN);
        }

        if (input.isKeyDown(Input.KEY_A)) {
            if (!listeKeys.contains(KeyCode.A)) {
                listeKeys.add(KeyCode.A);
            }
        } else {
            listeKeys.remove(KeyCode.A);
        }

        if (input.isKeyDown(Input.KEY_S)) {
            if (!listeKeys.contains(KeyCode.S)) {
                listeKeys.add(KeyCode.S);
            }
        } else {
            listeKeys.remove(KeyCode.S);
        }
        if (input.isKeyDown(Input.KEY_D)) {
            if (!listeKeys.contains(KeyCode.D)) {
                listeKeys.add(KeyCode.D);
            }
        } else {
            listeKeys.remove(KeyCode.D);
        }
        if (input.isKeyDown(Input.KEY_W)) {
            if (!listeKeys.contains(KeyCode.W)) {
                listeKeys.add(KeyCode.W);
            }
        } else {
            listeKeys.remove(KeyCode.W);
        }
    }

    private void traiterKeys() throws SlickException {
        joueur.bouger(listeKeys);
        if (listeKeys.contains(KeyCode.SPACE) && joueur.peutAttaquer()) {
            tirerBalle();
        }
    }
    public boolean isBonusTriple() {

        if (tempsBonusTriple+10000<System.currentTimeMillis()) {

            return false;
        }
        
        return true;

    }

    private void tirerBalle() throws SlickException {
        if (!isBonusTriple()) {

            Bullet bullet = new Bullet(joueur.getX() + joueur.getWidth(), joueur.getY() + (joueur.getHeight() / 4));
            listeBougeable.add(bullet);
            listeEntite.add(bullet);
        } else {
            Bullet bullet = new Bullet(joueur.getX() + joueur.getWidth(), joueur.getY() + (joueur.getHeight() / 4));
            listeBougeable.add(bullet);
            listeEntite.add(bullet);
            Bullet bullet1 = new Bullet(joueur.getX() + joueur.getWidth(), joueur.getY() + (joueur.getHeight() / 4));
            bullet.setDeltaY(12);
            listeBougeable.add(bullet1);
            listeEntite.add(bullet1);
            Bullet bullet2 = new Bullet(joueur.getX() + joueur.getWidth(), joueur.getY() + (joueur.getHeight() / 4));
            bullet2.setDeltaY(-12);
            listeBougeable.add(bullet2);
            listeEntite.add(bullet2);

        }
    }

    private void gererCollisionJoueurEnnemi() {
        ArrayList<Entite> listeTemp = new ArrayList();

        for (Bougeable bougeable : listeBougeable) {
            if (bougeable instanceof Collisionnable) {
                if (joueur.getRectangle().intersects(bougeable.getRectangle())) {
                    listeTemp.add((Entite) bougeable);
                    controleur.perdVie();
                    // listeCoeurs.remove(listeCoeurs.size()-1);
                }
            }
        }

        listeEntite.removeAll(listeTemp);
        listeBougeable.removeAll(listeTemp);

    }

    private void gererCollisionJoueurBonus() {
        ArrayList<Entite> listeTemp = new ArrayList();
        Bonus bonus = null;

        for (Bougeable bougeable : listeBougeable) {
            if (bougeable instanceof Bonus) {
                if (joueur.getRectangle().intersects(bougeable.getRectangle())) {
                    listeTemp.add((Entite) bougeable);

                    //appeler le controleur pour dire que le bonus a été pris
                    //controleur.activerBonus(bonus);
                    controleur.ramasseBonus();
                    bonus = (Bonus) bougeable;
                    // bonus.activerPouvoir();
                    //activerPouvoirBonus(bonus);
                }
            }
        }

        if (!listeTemp.isEmpty()) {
            // bonus.activerPouvoir();
            activerPouvoirBonus(bonus);

            //System.out.println(" efsdf");
        }

        listeBougeable.removeAll(listeTemp);
        listeEntite.removeAll(listeTemp);

    }

    private void gererCollisionBulletEnnemi() throws SlickException {

        ArrayList<Entite> listeTemp = new ArrayList();

        for (Bougeable b1 : listeBougeable) {

            for (Bougeable b2 : listeBougeable) {
                if ((b1 instanceof Ennemi && b2 instanceof Bullet)) {

                    if (b1.getRectangle().intersects(b2.getRectangle())) {

                        // spondBonus(b1.getRectangle().getX(), b1.getRectangle().getY());
                        //enlever lennemi et la bullet
                        listeTemp.add((Entite) b1);
                        listeTemp.add((Entite) b2);

                        //ajouter pts
                        controleur.elimineEnnemis();
                    }
                }

            }
        }

        /*int nbEnnemisElimines=listeTemp.size();
controleur.elimineEnnemis(nbEnnemisElimines);*/
        //random pour spond un bonus au besoin
        if (!listeTemp.isEmpty()) {
            int r = random.nextInt(10);
            if (r == 1) {
                //int posX=listeTemp.get(0).getRectangle().getX(), posY=listeTemp.get(0).getRectangle().getY();
                spondBonus(listeTemp.get(0).getRectangle().getX(), listeTemp.get(0).getRectangle().getY());
            }
        }

        listeEntite.removeAll(listeTemp);
        listeBougeable.removeAll(listeTemp);

    }

    private void reset() {

        ArrayList<Entite> listeTemp = new ArrayList();

        for (Entite entite : listeEntite) {

            if (entite instanceof Ennemi) {

                listeTemp.add(entite);

            }
        }
        listeEntite.removeAll(listeTemp);
        //      listeBougeable.removeAll(listeTemp);

        modele.resetHealthPoints();
        modele.resetPoints();
        nbVies = modele.getHealthPointsMax();
        joueur.setLocation(64, HAUTEUR - 128);

    }

    @Override
    public void update(Observable o, Object arg) {
        //enleverVie

        finDePartie = modele.isFinDePartie();
        if (finDePartie) {
            //pop message de fin de partie
            //demander s'il veut recommencer une partie

            //compteARebours=true;
            declencherCompteARebours();

            reset();
            //compteARebours=false;

            // recommencerPartie();
        }
        // listeCoeurs.remove(listeCoeurs.size() - 1);
        //listeEntite.remove();

        //maj points
        //setlbl(modele.getpoints)
        nbVies = modele.getHealthPoints();
        nbPoints = modele.getPoints();

    }

    private void enleverEntitesExterieurEcran() {
        ArrayList<Entite> listeTemp = new ArrayList();

        for (Entite entite : listeEntite) {
            if (entite.getDetruire()) {
                listeTemp.add(entite);
            }
        }
        listeEntite.removeAll(listeTemp);
        listeBougeable.remove(listeTemp);
    }
    
    public int[][] getPositionBackground(int maxX, int maxY, int width) {
        int[][] positionXY = new int[4][2];
        int[] positionPrise = new int[4];
        int x = x = random.nextInt(maxX), y = 0, max = 0, min = 0;
        boolean bonnePosition = false, superpose = false;

        for (int i = 0; i < positionXY.length; i++) {

            while (!bonnePosition) {
                x = random.nextInt(maxX);
                for (int j : positionPrise) {
                    while (j == x) {
                        x = random.nextInt(maxX);
                    }
                    if (j > x) {
                        max = j;
                        min = x;
                    } else {
                        max = x;
                        min = j;
                    }

                    if (max - (min + width) < 0 || min - (max - width) > 0) {

                        superpose = true;
                    }

                }
                if (superpose) {
                    superpose = false;
                } else {
                    bonnePosition = true;
                }
            }
            bonnePosition = false;
            positionXY[i][0] = x;
            positionPrise[i] = x;

        }

        for (int i = 0; i < positionXY.length; i++) {
            y = random.nextInt(maxY);
            positionXY[i][1] = y;

        }

        return positionXY;

    }

    private void gererArbre() throws SlickException {
        Arbre arbre1 = null, arbre2 = null;
        int difference, nouvellePos;
        float ancienX;
        ArrayList<Tronc> listeTronc = new ArrayList();
        int hauteur1, hauteur2, pos1 = 0, pos2 = 0, pos = 0;
        for (Entite entite : listeEntite) {
            if (entite instanceof Arbre && arbre1 == null) {
                arbre1 = (Arbre) entite;
                pos1 = pos;
            } else if (entite instanceof Arbre) {
                arbre2 = (Arbre) entite;
                pos2 = pos;
            }
            pos++;
        }

        if (arbre1.getX() + arbre1.getWidth() - 5 < 0) {
            hauteur1 = random.nextInt(9) + 1;
            difference = hauteur1 - arbre1.getHauteur();
            arbre1.setHauteur(hauteur1);
            arbre1.setLocation(arbre1.getX(), arbre1.getY() - difference * 32);
            if (difference > 0) {

                for (int i = 0; i < difference; i++) {
                    Tronc tronc = new Tronc(arbre1.getX(), (arbre1.getY()) + i * 32 + 64, spriteSheetMonde);
                    listeTronc.add(tronc);
                }

                listeEntite.addAll(listeTronc);
                listeBougeable.addAll(listeTronc);

            } else if (difference < 0) {

                for (Entite entite : listeEntite) {
                    if (entite instanceof Tronc && entite.getX() == arbre1.getX() && entite.getY() <= arbre1.getY()) {

                        listeTronc.add((Tronc) entite);
                    }
                }
                listeEntite.removeAll(listeTronc);
                listeBougeable.removeAll(listeTronc);
            }

        }

        listeTronc.clear();

        if (arbre2.getX() + arbre2.getWidth() - 5 < 0) {

            hauteur2 = random.nextInt(9) + 1;
            difference = hauteur2 - arbre2.getHauteur();
            arbre2.setHauteur(hauteur2);
            arbre2.setLocation(arbre2.getX(), arbre2.getY() - difference * 32);
            if (difference > 0) {

                for (int i = 0; i <= difference; i++) {
                    Tronc tronc = new Tronc(arbre2.getX(), (arbre2.getY()) + i * 32 + 64, spriteSheetMonde);
                    listeTronc.add(tronc);

                }

                listeEntite.addAll(listeTronc);
                listeBougeable.addAll(listeTronc);

            } else if (difference < 0) {

                for (Entite entite : listeEntite) {
                    if (entite instanceof Tronc && entite.getX() == arbre2.getX() && entite.getY() <= arbre2.getY()) {

                        listeTronc.add((Tronc) entite);
                    }
                }
                listeEntite.removeAll(listeTronc);
                listeBougeable.removeAll(listeTronc);
            }

            if (random.nextInt(2) == 1) {
                ancienX = arbre2.getX();
                arbre2.setLocation(arbre1.getX() + random.nextInt(500) + 70 + LONGUEUR, arbre2.getY());
                for (Entite entite : listeEntite) {
                    if (entite instanceof Tronc && entite.getX() == ancienX) {
                        entite.setX(arbre2.getX());
                    }
                }

            }

        }

    }

    private void deplacerBackground() {

        for (Entite entite : listeEntite) {
            if (entite instanceof Background) {
                if (entite.getX() + entite.getWidth() <= 0) {
                    entite.setX(LONGUEUR);
                }
            }
        }
      

    }

    private void declencherWaveEnnemis() throws SlickException {
        int choixTypeEnnemi = random.nextInt(8);
        //choixTypeEnnemi=2;
        int nbEnnemis;
        do {
            nbEnnemis = random.nextInt(6) * 2;//pour que ca soit pair
        } while (nbEnnemis == 0);

        //  Ennemi ennemi;
        switch (choixTypeEnnemi) {
            case 0:
                creerEnnemiSol(nbEnnemis, spriteSheetEnnemi);
                break;
            case 1:
                creerEnnemiAerienVertical(nbEnnemis, spriteSheetEnnemi);
                break;

            case 2:
                creerEnnemiAerienDiagonalHaut(nbEnnemis, spriteSheetEnnemi);
                break;

            case 3:
                creerEnnemiAerienDiagonalBas(nbEnnemis, spriteSheetEnnemi);
                break;

            case 4:
                creerEnnemiAerienZigZag(nbEnnemis, spriteSheetEnnemi);
                break;

            case 5:
                creerEnnemiAerienCarre(nbEnnemis, spriteSheetEnnemi);
                break;

            case 6:
                creerEnnemiAerienCercle(nbEnnemis, spriteSheetEnnemi);
                break;
            case 7:
                creerEnnemiAerienRoue(spriteSheetEnnemi);
                break;
            default:
                break;

        }
    }

    private void spondBonus(float x, float y) throws SlickException {
        int choixBonus = random.nextInt(3);

        Bonus bonus = null;
        switch (choixBonus) {
            case 0:
                bonus = new BoostEnergie(x, y, spriteSheetBonus);
                break;
            case 1:
                bonus = new BombeMega(x, y, spriteSheetBonus);
                break;
            case 2:
                bonus = new ArmeABalles(x, y, spriteSheetBonus);
                break;
            //  default:
        }
        listeBougeable.add((Bougeable) bonus);
        listeEntite.add(bonus);
    }

    private void tireEnnemi() {
        ArrayList<BulletEnnemi> listeBullet = new ArrayList();
        for (Entite entite : listeEntite) {
            if (entite instanceof EnnemiSol) {
                if (((EnnemiSol) (entite)).tireEnCours()) {

                    BulletEnnemi bulletEnnemi = new BulletEnnemi(entite.getX(), entite.getY() + 32, spriteSheetBonus);
                    listeBullet.add(bulletEnnemi);
                }

            }
        }

        listeEntite.addAll(listeBullet);
        listeBougeable.addAll(listeBullet);

    }

    private void creerEnnemiSol(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {
        if (nbEnnemis > 4) {
            nbEnnemis = random.nextInt(5) + 1;

        }
        for (int i = 0; i < nbEnnemis; i++) {
            EnnemiSol ennemiSol = new EnnemiSol(LONGUEUR + buffer + i * 32, HAUTEUR - HAUTEUR_SOL - 32, spriteSheetEnnemi);
            listeEntite.add(ennemiSol);
            listeBougeable.add(ennemiSol);
        }

    }

    private void creerEnnemiAerienVertical(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {
        //checker si ca depasse le plancher

        int posY = random.nextInt(HAUTEUR / 16) + 20;
        for (int i = 0; i < nbEnnemis; i++) {
            EnnemiAerien ennemiAerien = new EnnemiAerien(LONGUEUR + buffer, posY + i * 32, spriteSheetEnnemi);
            listeEntite.add(ennemiAerien);
            listeBougeable.add(ennemiAerien);
            posY += 15;
        }
    }

    private void creerEnnemiAerienDiagonalHaut(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {
        int posY = random.nextInt(HAUTEUR / 16) + 20;
  
        for (int i = 0; i < nbEnnemis; i++) {
            EnnemiAerien ennemiAerien = new EnnemiAerien(LONGUEUR + buffer + i * 32, posY + i * 32, spriteSheetEnnemi);
            listeEntite.add(ennemiAerien);
            listeBougeable.add(ennemiAerien);
            posY += 15;
        }
    }

    private void creerEnnemiAerienZigZag(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {

        int posY = random.nextInt(200) + 100;
        for (int i = 0; i < nbEnnemis; i++) {
            EnnemiAerienZigZag ennemiAerienZigZag = new EnnemiAerienZigZag(LONGUEUR + buffer + i * 42, posY, spriteSheetEnnemi);
            listeEntite.add(ennemiAerienZigZag);
            listeBougeable.add(ennemiAerienZigZag);
        }

    }

    private void creerEnnemiAerienCercle(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {

        int posY = random.nextInt(200) + 200;
        for (int i = 0; i < nbEnnemis; i++) {
            EnnemiAerienCercle ennemiAerienCercle = new EnnemiAerienCercle(LONGUEUR + buffer + i * 38, posY, spriteSheetEnnemi);
            listeEntite.add(ennemiAerienCercle);
            listeBougeable.add(ennemiAerienCercle);
        }

    }

    private void creerEnnemiAerienDiagonalBas(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {
        int posY = random.nextInt((HAUTEUR / 16) + 420);
   
        for (int i = 0; i < nbEnnemis; i++) {
            EnnemiAerien ennemiAerien = new EnnemiAerien(LONGUEUR + buffer + i * 32, posY - i * 32, spriteSheetEnnemi);
            listeEntite.add(ennemiAerien);
            listeBougeable.add(ennemiAerien);
            posY -= 15;
        }
    }

    private void creerEnnemiAerienRoue(SpriteSheet spriteSheetEnnemi) {
        int posY = random.nextInt(250) + 150;
        posY = 400;

        float[][] positionRoue = modele.getPositionRoue();
        for (int i = 0; i < positionRoue[0].length; i++) {

            Ennemi ennemiAerienRoue = new EnnemiAerienRoue(LONGUEUR + buffer + positionRoue[0][i], posY - positionRoue[1][i],
                    spriteSheetEnnemi, positionRoue[2][i], posY);

            listeEntite.add(ennemiAerienRoue);
            listeBougeable.add(ennemiAerienRoue);
        }
    }

    private void creerEnnemiAerienCarre(int nbEnnemis, SpriteSheet spriteSheetEnnemi) {
        int posY = random.nextInt((HAUTEUR / 4) + 100);

        if (nbEnnemis > 6) {

            nbEnnemis = 6;
        }
        for (int i = 0; i < nbEnnemis; i++) {
            for (int j = 0; j < nbEnnemis; j++) {
                EnnemiAerien ennemiAerien = new EnnemiAerien(LONGUEUR + buffer + j * 32, posY + i * 32,
                        spriteSheetEnnemi);
                listeEntite.add(ennemiAerien);
                listeBougeable.add(ennemiAerien);
            }

        }

    }

    /*
    private void afficherPoints() {
    }*/
    private void activerPouvoirBonus(Bonus bonus) {
        ArrayList<Entite> listeTemp = new ArrayList();

        if (bonus instanceof BombeMega) {
            //  activerBombeMega();
            for (Bougeable bougeable : listeBougeable) {
                if (bougeable instanceof Ennemi && ((Entite) bougeable).getX() < LONGUEUR) {
                    listeTemp.add((Entite) bougeable);
                }
            }

            int nbEnnemisTues = listeTemp.size();
            controleur.ramasseBombeMega(nbEnnemisTues);
            //  controleur.elimineEnnemis(nbEnnemisTues);

            listeEntite.removeAll(listeTemp);
            listeBougeable.removeAll(listeTemp);
        }
        if (bonus instanceof BoostEnergie) {
            //activerBoostEngergie();
            controleur.ramasseBoostEnergie();
        }
        if (bonus instanceof ArmeABalles) {
           tempsBonusTriple=System.currentTimeMillis();
            // controleur.
            //activer timer 10 sec
        }

    }

    private void activerPouvoirBonus(BoostEnergie boost) {

    }

    /* private void activerBombeMega() {
        ArrayList<Entite> listeTemp = new ArrayList();

        for (Bougeable bougeable : listeBougeable) {
            if (bougeable instanceof Ennemi) {
                listeTemp.add((Entite) bougeable);
            }
        }

        int nbEnnemisTues = listeTemp.size();
        controleur.elimineEnnemis(nbEnnemisTues);

        listeEntite.removeAll(listeTemp);
        listeBougeable.removeAll(listeTemp);

    }*/

 /* private void activerBoostEngergie() {
        controleur.ramasseBoostEnergie();
    }*/
    private void activerArmesBalle() {
    }

    private void declencherCompteARebours() {
        long temps = System.currentTimeMillis() + 2000;

        if ((System.currentTimeMillis() < temps)) {
            reset();
        }

    }

}
