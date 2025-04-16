package se.yrgo.jumpybirb;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Obstacles {

    public static final int TUBE_WIDTH = 10;
    private static final int FLUCTUATION = 15;
    private static final int TUBE_GAP = 2;
    private static final int LOWEST_OPENING = 10;
    

    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Rectangle boundsTop, boundsBottom;
    private Random rand;

    public Obstacles(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();
        

        float bottomY = 5f; // Nedre röret börjar 5 enheter upp från botten
        float gap = 15f; // Mellanrum mellan rör

        posBottomTube = new Vector2(x, bottomY);
        posTopTube = new Vector2(x, bottomY + gap + 20f); // 20 = höjden på ett rör i din draw-metod

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, TUBE_WIDTH, topTube.getHeight());
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y, TUBE_WIDTH, bottomTube.getHeight());
    }

    public void reposition(float x) {
      
            float bottomY = 5f; // samma som i konstruktorn
            float gap = 15f;
        
            posBottomTube.set(x, bottomY);
            posTopTube.set(x, bottomY + gap + 20f);
        
            boundsTop.setPosition(posTopTube.x, posTopTube.y);
            boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
        
        
    }

    public void updateX(float delta) {
        float speed = 20f * delta;
        posBottomTube.x -= speed;
        posTopTube.x -= speed;
    
        boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
    }
    

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
