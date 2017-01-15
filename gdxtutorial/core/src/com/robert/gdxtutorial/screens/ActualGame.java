package com.robert.gdxtutorial.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.robert.gdxtutorial.controllers.InputController;

/**
 * Created by Robert on 24/07/2016.
 */
public class ActualGame implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private float speed = 450;
    private Body box;
    private Vector2 movement = new Vector2();

    @Override
    public void show() {
        Gdx.app.log("ActualGame", "Show()");
        Gdx.input.setCatchBackKey(true);

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor((new InputController() {
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode) {
                    case Input.Keys.ESCAPE:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                        break;
                    case Input.Keys.BACK:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                        break;
                    case Input.Keys.W:
                        movement.y = speed;
                        break;
                    case Input.Keys.A:
                        movement.x = -speed;
                        break;
                    case Input.Keys.S:
                        movement.y = -speed;
                        break;
                    case Input.Keys.D:
                        movement.x = speed;
                        break;

                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    case Input.Keys.W:
                    case Input.Keys.S:
                        movement.y = 0;
                        break;
                    case Input.Keys.A:
                    case Input.Keys.D:
                        movement.x = 0;
                        break;

                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Gdx.app.log("TouchDown Coords:", "X: " + screenX + " Y: " + screenY);
                if(screenX < Gdx.graphics.getWidth() / 2)
                    movement.x = -speed;
                else if(screenX > Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2)
                    movement.x = speed;

                if(screenY < Gdx.graphics.getHeight() / 2)
                    movement.y = speed;
                else if(screenY > Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2)
                    movement.y = -speed;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if(screenX < Gdx.graphics.getWidth() / 2)
                    movement.x = -speed;
                else if(screenX > Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2)
                    movement.x = speed;

                if(screenY < Gdx.graphics.getHeight() / 2)
                    movement.y = speed;
                else if(screenY > Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2)
                    movement.y = -speed;
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                movement.x = 0;
                movement.y = 0;

                return true;
            }
        }));


        // body definition
        BodyDef bodyDef = new BodyDef();
        BodyDef groundDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 20); // 1 meter up Y axis

        // ball shape
        CircleShape shape = new CircleShape();
        shape.setRadius(2f);

        // fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f; // 2.5 kg
        fixtureDef.friction = .25f; // 1 means it cannot slide, 0 means it is super slippery
        fixtureDef.restitution = .75f; // 1 means 100% of the force is reflected, 0 means the object will never bounce.

        FixtureDef groundFixture = new FixtureDef();

        world.createBody(bodyDef).createFixture(fixtureDef);
        bodyDef.position.set(-10, 10); // 1 meter up Y axis
        world.createBody(bodyDef).createFixture(fixtureDef);
        bodyDef.position.set(10, 30); // 1 meter up Y axis
        world.createBody(bodyDef).createFixture(fixtureDef);
        shape.dispose();

        // GROUND
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0,0);

        // ground shape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] {new Vector2(-50, 0), new Vector2(50,0)});
        ChainShape roofShape = new ChainShape();
        roofShape.createChain(new Vector2[] {new Vector2(-50, 50), new Vector2(50,50)});
        ChainShape wallShapeLeft = new ChainShape();
        wallShapeLeft.createChain(new Vector2[] {new Vector2(-50, 0), new Vector2(-50,50)});
        ChainShape wallShapeRight = new ChainShape();
        wallShapeRight.createChain(new Vector2[] {new Vector2(50, 0), new Vector2(50,50)});

        // fixture
        groundFixture.shape = groundShape;
        groundFixture.friction = .5f;
        groundFixture.restitution = 0;

        world.createBody(groundDef).createFixture(groundFixture);
        groundFixture.shape = roofShape;
        world.createBody(groundDef).createFixture(groundFixture);
        groundFixture.shape = wallShapeLeft;
        world.createBody(groundDef).createFixture(groundFixture);
        groundFixture.shape = wallShapeRight;
        world.createBody(groundDef).createFixture(groundFixture);

        groundShape.dispose();

        // BOX
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.5f, 10);

        // box shape
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(.5f, 1);

        // fixture definition
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;

        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);

        boxShape.dispose();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        box.applyForceToCenter(movement, true);

        camera.position.set(box.getPosition().x, box.getPosition().y, 0);
        camera.update();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height / 25;
        camera.viewportWidth = width / 25;
        camera.update();
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
        world.dispose();
        debugRenderer.dispose();
    }
}
