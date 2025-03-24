package se.yrgo.jumpybirb;

import javax.swing.text.FieldView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {

    Texture backgroundTexture; 
    FitViewport viewport;
    SpriteBatch spriteBatch; 

    @Override
    public void create() {
        backgroundTexture = new Texture("assets\\background.jpg");
        viewport = new FitViewport(8, 5);
        spriteBatch = new SpriteBatch(); 
        
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        draw();
    }

    public void draw(){
        ScreenUtils.clear(Color.BLACK); 
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, width, height);

        spriteBatch.end();


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