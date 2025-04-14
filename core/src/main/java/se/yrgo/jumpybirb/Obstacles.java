package se.yrgo.jumpybirb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Obstacles {

    public static final int TUBE_WIDTH = 80;
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 150;
    private static final int LOWEST_OPENING = 100;

    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Rectangle boundsTop, boundsBottom;

    public Obstacles(float x) {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        float offset = (float)(Math.random() * FLUCTUATION);

        posTopTube = new Vector2(x, offset + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, offset + LOWEST_OPENING);

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, TUBE_WIDTH, topTube.getHeight());
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y, TUBE_WIDTH, bottomTube.getHeight());
    }

    public void reposition(float x) {
        float offset = (float) (Math.random() * FLUCTUATION);
        posTopTube.set(x, offset + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, offset + LOWEST_OPENING);

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
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
