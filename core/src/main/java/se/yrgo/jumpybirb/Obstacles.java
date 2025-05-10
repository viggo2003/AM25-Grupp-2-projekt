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

    private static final float HITBOX_OFFSET_X = 1f;
    private static final float HITBOX_OFFSET_Y = 5f;
    private static final float HITBOX_WIDTH = TUBE_WIDTH - 2f;
    private static final float HITBOX_HEIGHT_TOP = 15f;
    private static final float HITBOX_HEIGHT_BOTTOM = 13f;

    public Obstacles(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        float bottomY = 5f;
        float gap = 15f;
        float tubeHeight = 20f;

        posBottomTube = new Vector2(x, bottomY);
        posTopTube = new Vector2(x, bottomY + gap + tubeHeight);

        boundsTop = new Rectangle(posTopTube.x + HITBOX_OFFSET_X, posTopTube.y + HITBOX_OFFSET_Y, HITBOX_WIDTH, HITBOX_HEIGHT_TOP);
        boundsBottom = new Rectangle(posBottomTube.x + HITBOX_OFFSET_X, posBottomTube.y + HITBOX_OFFSET_Y, HITBOX_WIDTH, HITBOX_HEIGHT_BOTTOM);
    }

    public void reposition(float x) {
        float bottomY = 5f;
        float gap = 15f;
        float tubeHeight = 20f;

        posBottomTube.set(x, bottomY);
        posTopTube.set(x, bottomY + gap + tubeHeight);

        boundsTop.setPosition(posTopTube.x + HITBOX_OFFSET_X, posTopTube.y + HITBOX_OFFSET_Y);
        boundsBottom.setPosition(posBottomTube.x + HITBOX_OFFSET_X, posBottomTube.y + HITBOX_OFFSET_Y);
    }

    public void updateX(float delta) {
        float speed = 20f * delta;
        posBottomTube.x -= speed;
        posTopTube.x -= speed;

        boundsBottom.setPosition(posBottomTube.x + HITBOX_OFFSET_X, posBottomTube.y + HITBOX_OFFSET_Y);
        boundsTop.setPosition(posTopTube.x + HITBOX_OFFSET_X, posTopTube.y + HITBOX_OFFSET_Y);
    }

    public boolean collides(Rectangle player) {
        boolean topCollision = player.overlaps(boundsTop);
        boolean bottomCollision = player.overlaps(boundsBottom);

        if (topCollision || bottomCollision) {
            System.out.println("Collision detected");
        }
        return topCollision || bottomCollision;
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

    // Add getters for bounds so Main can access them for drawing hitboxes
    public Rectangle getBoundsTop() {
        return boundsTop;
    }

    public Rectangle getBoundsBottom() {
        return boundsBottom;
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }

}
