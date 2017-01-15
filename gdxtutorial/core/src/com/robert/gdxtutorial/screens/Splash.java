package com.robert.gdxtutorial.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.robert.gdxtutorial.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class Splash implements Screen{

    private SpriteBatch batch;
    private Sprite splashSprite;
    private TweenManager tweenManager;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // use OpenGL to clear the color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // to draw, we need to open the batch
        batch.begin();
        splashSprite.draw(batch); // draw the sprite on the batch
        batch.end();

        // delta = the time and seconds from the last frame to the current frame
        // so we can have the same speed for any animation no matter what frame count is
        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        splashSprite.setSize(width, height);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Texture splashTexture= new Texture("img/splash.jpg");
        splashSprite = new Sprite(splashTexture);
        splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splashSprite, SpriteAccessor.ALPHA, 1).target(1).repeatYoyo(1, 1f).setCallback(new TweenCallback(){
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }).start(tweenManager);

        tweenManager.update(Float.MIN_VALUE);



    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        splashSprite.getTexture().dispose();
    }

}
