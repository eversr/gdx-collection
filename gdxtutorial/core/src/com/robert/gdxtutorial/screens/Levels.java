package com.robert.gdxtutorial.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Robert on 23/07/2016.
 */
public class Levels implements Screen {

    private Stage stage;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private List list;
    private ScrollPane scrollPane;
    private TextButton play, back;
    private Label levelHeader;

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/atlas.pack");
        skin = new Skin(Gdx.files.internal("UI/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setFillParent(true);

        list = new List(skin, "medList");
        list.setItems(new String[] {"Monkey Beach", "Horse Farm", "Mouse Hole", "Wolf Island", "Bear Cave", "Ostrich Meadow", "Kangaroo Desert", "Shark Lagoon"});

        scrollPane = new ScrollPane(list, skin, "darkPane");

        levelHeader = new Label("SELECT LEVEL", skin, "large");

        play = new TextButton("PLAY", skin, "simpleButtonLarge");
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ActualGame());
            }
        });
        play.pad(15);

        back = new TextButton("BACK", skin, "simpleButtonDefault");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
        back.pad(10);

        // putting stuff
        table.add(levelHeader).colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).uniformX().expandY().top().left();
        table.add(play).uniformX();
        table.add(back).uniformX().bottom().right();

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
