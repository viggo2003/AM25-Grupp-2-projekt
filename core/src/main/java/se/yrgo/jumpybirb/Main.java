package se.yrgo.jumpybirb;

import java.math.BigDecimal;
import java.nio.BufferOverflowException;

import javax.swing.text.FieldView;
import javax.swing.text.Position;

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

    Texture backgroundTexture;
    FitViewport viewport;
    SpriteBatch spriteBatch;

    Texture birbTexture;
    Sprite birbSprite;
    Birb birb;

    float velocityY = 0;
    float gravity = -180f;
    float jumpForce = 50f;
    float maxYVelocity = 100f;

    @Override
    public void create() {

        birbTexture = new Texture("assets\\bird.png");
        birbSprite = new Sprite(birbTexture);
        birbSprite.setSize(10, 10);
        birbSprite.setCenterY(25);
        birbSprite.setCenterX(25);
        birb = new Birb(birbTexture, birbSprite);
        backgroundTexture = new Texture("assets\\background.jpg");
        viewport = new FitViewport(75, 50);
        spriteBatch = new SpriteBatch();

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
        // birbSprite.

        spriteBatch.end();
    }

    public void input(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            velocityY = jumpForce;
        }

        velocityY += gravity * delta;

        if (velocityY > maxYVelocity) {
            velocityY = maxYVelocity;
        }

        birbSprite.translateY(velocityY * delta);

        if (birbSprite.getY() < 0) {
            birbSprite.setY(0);
            velocityY = 0;
        }
    }

    public void logic() {

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
        // Destroy application's resources here.
    }
}