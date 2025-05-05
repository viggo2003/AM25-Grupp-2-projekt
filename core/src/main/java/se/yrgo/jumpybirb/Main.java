package se.yrgo.jumpybirb;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main implements ApplicationListener {

    private static final int OBSTACLE_SPACING = 40;
    private static final int OBSTACLE_COUNT = 2;
    private static final float OBSTACLE_SPEED = 20f;
    private boolean isGameOver = false;
    private Rectangle birbHitbox;

    Texture backgroundTexture;
    FitViewport viewport;
    SpriteBatch spriteBatch;
    BitmapFont font;
    GlyphLayout layout;

    Texture birbTexture;
    Sprite birbSprite;
    Birb birb;

    Obstacles[] obstacles;
    Texture obstaclesTexture;

    float velocityY = 0;
    float gravity = -180f;
    float jumpForce = 50f;
    float maxYVelocity = 100f;

    // Add ShapeRenderer for drawing hitboxes
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        birbTexture = new Texture("bird.png");
        birbSprite = new Sprite(birbTexture);
        birbSprite.setSize(10, 10);
        birbSprite.setCenterY(25);
        birbSprite.setCenterX(25);
        birb = new Birb(birbTexture, birbSprite);
        birbHitbox = new Rectangle();
        updateBirbHitbox();

        font = new BitmapFont();
        font.setColor(Color.DARK_GRAY);
        font.getData().setScale(0.5f);
        layout = new GlyphLayout();

        backgroundTexture = new Texture("background.jpg");

        float aspect = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float worldHeight = 50f;
        float worldWidth = worldHeight * aspect;
        viewport = new FitViewport(worldWidth, worldHeight);

        spriteBatch = new SpriteBatch();

        // Initialize ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        obstacles = new Obstacles[OBSTACLE_COUNT];
        for (int i = 0; i < OBSTACLE_COUNT; i++) {
            float x = 60 + i * (OBSTACLE_SPACING + Obstacles.TUBE_WIDTH);
            obstacles[i] = new Obstacles(x);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        if (!isGameOver) {
            input(Gdx.graphics.getDeltaTime());
            logic();
        }
        draw();

        if (isGameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                restart();
            }
        }

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

        float tubeWidth = 10; 
        float tubeHeight = 20;

        for (Obstacles obs : obstacles) {
            spriteBatch.draw(
                    obs.getTopTube(),
                    obs.getPosTopTube().x,
                    obs.getPosTopTube().y + tubeHeight,
                    tubeWidth,
                    -tubeHeight);

            spriteBatch.draw(
                    obs.getBottomTube(),
                    obs.getPosBottomTube().x,
                    obs.getPosBottomTube().y,
                    tubeWidth,
                    tubeHeight);
        }

        if (isGameOver) {
            String gameOverText = "GAME OVER\nPress R to Restart";
            layout.setText(font, gameOverText);
            float textWidth = layout.width;
            float textHeight = layout.height;

            font.draw(
                    spriteBatch,
                    gameOverText,
                    (viewport.getWorldWidth() - textWidth) / 2,
                    (viewport.getWorldHeight() + textHeight) / 2);
        }

        spriteBatch.end();

        // Draw hitboxes on top using ShapeRenderer
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Draw birb's hitbox for reference
        Rectangle birbBounds = birbSprite.getBoundingRectangle();
        shapeRenderer.rect(birbHitbox.x, birbHitbox.y, birbHitbox.width, birbHitbox.height);
        // Draw obstacles hitboxes
        for (Obstacles obs : obstacles) {
            Rectangle topBounds = obs.getBoundsTop();
            Rectangle bottomBounds = obs.getBoundsBottom();
            shapeRenderer.rect(topBounds.x, topBounds.y, topBounds.width, topBounds.height);
            shapeRenderer.rect(bottomBounds.x, bottomBounds.y, bottomBounds.width, bottomBounds.height);
        }

        shapeRenderer.end();
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

        if (birbSprite.getY() > viewport.getWorldHeight()) {
            velocityY = 0;
            birbSprite.setY(viewport.getWorldHeight() - 3);
        }

        if (birbSprite.getY() < 4) {
            birbSprite.setY(4);
            velocityY = 0;
        }
    }

    public void logic() {
        if (isGameOver) {
            return;
        }

        updateBirbHitbox();

        for (Obstacles obs : obstacles) {
            obs.updateX(Gdx.graphics.getDeltaTime());

            if (obs.getPosTopTube().x + Obstacles.TUBE_WIDTH < 0) {
                obs.reposition(viewport.getWorldWidth() + Obstacles.TUBE_WIDTH);
            }

            if (obs.collides(birbSprite.getBoundingRectangle())) {
                System.out.println("\uD83D\uDCA5 Kollision! Game Over");
                isGameOver = true;
            }
        }

        if (birbSprite.getY() < 5) {
            System.out.println("game over rip");
            isGameOver = true;
        }

    }

    public void restart() {
        birbSprite.setPosition(viewport.getWorldWidth() / 4, viewport.getWorldHeight() / 2);
        velocityY = 0;
        isGameOver = false;

        for (int i = 0; i < obstacles.length; i++) {
            float x = 60 + i * (OBSTACLE_SPACING + Obstacles.TUBE_WIDTH);
            obstacles[i].reposition(x);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        birbTexture.dispose();
        spriteBatch.dispose();
        font.dispose();
        shapeRenderer.dispose();
        for (Obstacles obs : obstacles) {
            obs.dispose();
        }
    }

    private void updateBirbHitbox() {
        float width = birbSprite.getWidth();
        float height = birbSprite.getHeight();
        float x = birbSprite.getX();
        float y = birbSprite.getY();
    
        birbHitbox.set(x, y, width, height);
    }
}
