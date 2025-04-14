package se.yrgo.jumpybirb;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main implements ApplicationListener {

    private static final int OBSTACLE_SPACING = 40;
    private static final int OBSTACLE_COUNT = 2;
    private static final float OBSTACLE_SPEED = 20f;

    Texture backgroundTexture;
    FitViewport viewport;
    SpriteBatch spriteBatch;

    Texture birbTexture;
    Sprite birbSprite;
    Birb birb;

    Obstacles[] obstacles;
    Texture obstaclesTexture;

    float velocityY = 0;
    float gravity = -180f;
    float jumpForce = 50f;
    float maxYVelocity = 100f;

    @Override
    public void create() {
        birbTexture = new Texture("bird.png");
        birbSprite = new Sprite(birbTexture);
        birbSprite.setSize(10, 10);
        birbSprite.setCenterY(25);
        birbSprite.setCenterX(25);
        birb = new Birb(birbTexture, birbSprite);

        backgroundTexture = new Texture("background.jpg");

        float aspect = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float worldHeight = 50f;
        float worldWidth = worldHeight * aspect;
        viewport = new FitViewport(worldWidth, worldHeight);

        spriteBatch = new SpriteBatch();

        obstacles = new Obstacles[OBSTACLE_COUNT];
        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            float x = 60 + i * (OBSTACLE_SPACING + Obstacles.TUBE_WIDTH);
            obstacles[i] = new Obstacles(x);
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input(Gdx.graphics.getDeltaTime());
        draw();
        logic();
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, width, height);
        birbSprite.draw(spriteBatch);

        float tubeWidth = 10; // justera efter smak
        float tubeHeight = 20; // justera efter din värld

        for (Obstacles obs : obstacles) {
            // ÖVRE RÖRET - ritas från toppen och FLIPPAS
            spriteBatch.draw(
                    obs.getTopTube(),
                    obs.getPosTopTube().x,
                    obs.getPosTopTube().y + tubeHeight, // justera för flip
                    tubeWidth,
                    -tubeHeight);

            // UNDRE RÖRET - normalt
            spriteBatch.draw(
                    obs.getBottomTube(),
                    obs.getPosBottomTube().x,
                    obs.getPosBottomTube().y,
                    tubeWidth,
                    tubeHeight);
        }

        // birbSprite.

        spriteBatch.end();
    }

    public void input(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            velocityY = jumpForce;
        }

        velocityY += gravity * delta;

        if (velocityY > maxYVelocity) {
            velocityY = maxYVelocity;
        }

        birbSprite.translateY(velocityY * delta);

        // temporärt tak
        if (birbSprite.getY() > viewport.getWorldHeight()) {
            velocityY = 0;
            birbSprite.setY(viewport.getWorldHeight() - 3);
        }

        // en faktiskt mark
        if (birbSprite.getY() < 4) {
            birbSprite.setY(4);
            velocityY = 0;
        }
    }

    public void logic() {
        for (Obstacles obs : obstacles) {

            obs.getPosTopTube().x -= 20 * Gdx.graphics.getDeltaTime();
            obs.getPosBottomTube().x -= 20 * Gdx.graphics.getDeltaTime();

            if (obs.getPosTopTube().x + Obstacles.TUBE_WIDTH < 0) {
                obs.reposition(viewport.getWorldWidth() + Obstacles.TUBE_WIDTH);
            }

            if (obs.collides(birbSprite.getBoundingRectangle())) {
                System.out.println("\uD83D\uDCA5 Kollision! Game Over");
                // TODO: Stoppa spelet eller visa meny
            }
        }

        if (birbSprite.getY() < 5) {
            System.out.println("game over rip");
        }

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        birbTexture.dispose();
        spriteBatch.dispose();
        for (Obstacles obs : obstacles) {
            obs.dispose();
        }
    }
}