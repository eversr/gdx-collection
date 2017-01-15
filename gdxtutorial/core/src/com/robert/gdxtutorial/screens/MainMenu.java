package com.robert.gdxtutorial.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.robert.gdxtutorial.gdxtutorial;
import com.robert.gdxtutorial.tween.ActorAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Robert on 23/07/2016.
 */
public class MainMenu implements Screen {

    private Stage stage; // done
    private TextureAtlas atlas; // done
    private Skin skin; // done
    private Table table; // done
    private TextButton buttonPlay, buttonExit, buttonLevels, buttonBob;
    private BitmapFont white, black; // done
    private Label heading;
    private TweenManager tweenManager;

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/atlas.pack");
        skin = new Skin(Gdx.files.internal("UI/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // creating heading
        heading = new Label(gdxtutorial.TITLE, skin, "large");

        buttonPlay = new TextButton("WORLD 1", skin, "simpleButtonLarge");
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ActualGame());
            }
        });
        buttonPlay.pad(15);

        buttonLevels = new TextButton("LEVELS", skin, "simpleButtonLarge");
        buttonLevels.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });

        buttonBob = new TextButton("WORLD 2", skin, "simpleButtonLarge");
        buttonBob.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new BobWorld());
            }
        });
        buttonBob.pad(15);

        buttonExit = new TextButton("EXIT", skin, "simpleButtonLarge");
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonExit.pad(15);

        // putting stuff together
        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(15);
        table.row();
        table.add(buttonBob);
        table.getCell(buttonBob).spaceBottom(15);
        table.row();
//        table.add(buttonLevels);
//        table.getCell(buttonLevels).spaceBottom(15);
//        table.row();
        table.add(buttonExit);
        stage.addActor(table);

        // animation stuff
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        // heading color animation
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);

        // menu fade in
        Timeline.createSequence().beginSequence()
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonBob, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttonBob, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
                .end().start(tweenManager);

        // table fade in
        Tween.from(table, ActorAccessor.ALPHA, .5f).target(0);
        Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

        tweenManager.update(Gdx.graphics.getDeltaTime());

        white = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        table.setFillParent(true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }
}
