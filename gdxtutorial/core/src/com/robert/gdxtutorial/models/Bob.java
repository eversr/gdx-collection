package com.robert.gdxtutorial.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Robert on 30/07/2016.
 * Bob class from tutorial on Obviam.net
 */
public class Bob {

    public enum State {
        IDLE, WALKING, JUMPING, DYING
    }

    public static final float SPEED = 2f;
    public static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f;

    Vector2 position = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    boolean facingLeft = true;
    float stateTime = 0;

    public Bob(Vector2 position){
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void update(float delta) {
        stateTime += delta;
        position.add(velocity.cpy().scl(delta));
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getAcceleration() {
        return this.acceleration;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public void setFacingLeft(boolean leftOrNo) {
        facingLeft = leftOrNo;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }


    public boolean isFacingLeft(){
        return facingLeft;
    }

    public float getStateTime()
    {
        return stateTime;
    }

    public State getState()
    {
        return state;
    }


}
