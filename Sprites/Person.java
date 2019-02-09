package com.chalkboyygames.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;

/**
 * Created by Francis on 6/3/2017.
 */
public class Person {
    //private Sprite person1;
    //Vector2 velocity;
    private Vector2 position;
    private Rectangle person2Bound;
    private TextureAtlas atlas2,atlas3;
    private Animation walking,poofing,losing;
    private static float animationTimer,loopingAnimationTimer;
    public TextureRegion poofingTextureRegion, walkingTextureRegion, losingTextureRegoin, currentTextureRegion;

    //private static float GRAVITY = -0.1f* PlayScreen.screenHeight;
    public static float DEFAULTMOVEMENT = 0.5f* CasualModeScreen.screenWidth;
    public static float personHeight = CasualModeScreen.screenHeight/5;
    public static float personWidth = CasualModeScreen.screenWidth/10;

    public Person(float x, float y){
        //reset value whenever new playsreen() is called
        animationTimer = 0;
        loopingAnimationTimer = 0;

        person2Bound = new Rectangle(x,y,personWidth,personHeight);
        position = new Vector2(x,y);
        //velocity = new Vector2(0,0);
        atlas2 = Assetss.manager.get(Assetss.walkinganimation,TextureAtlas.class);
        walking = new Animation(0.1f,atlas2.getRegions());

        atlas3 = Assetss.manager.get(Assetss.poofinganimation,TextureAtlas.class);
        poofing = new Animation(0.13f,atlas3.getRegions());

        atlas3 = Assetss.manager.get(Assetss.loseanimation,TextureAtlas.class);
        losing = new Animation(0.3f,atlas3.getRegions());

    }

    public void update(float dt){
        //velocity.add(0,GRAVITY);
        //velocity.scl(dt);//adding GRAVITY TO velocity every second
        position.add(DEFAULTMOVEMENT*dt,/*velocity.y*/0);
        //velocity.scl(1/dt);

        //setting person1 must be on the ground
        if(position.y < CasualModeScreen.levitationHeight){
            position.y = CasualModeScreen.levitationHeight;
        }

        /*//setting person1 cannot over the sky
        if(position.y+person1.getHeight()>PlayScreen.screenHeight){
            position.y=PlayScreen.screenHeight-person1.getHeight();
        }*/

        //increasing the speed as time passes


        //this must be the last statement!!!
        person2Bound.setPosition(position.x,position.y);


        poofingTextureRegion = (TextureRegion) poofing.getKeyFrame(animationTimer,true);
        walkingTextureRegion = (TextureRegion) walking.getKeyFrame(loopingAnimationTimer,true);
        losingTextureRegoin = (TextureRegion) losing.getKeyFrame(loopingAnimationTimer,true);
        if(CasualModeScreen.swipeTrue == true){
            animationTimer+=Gdx.graphics.getDeltaTime();
            currentTextureRegion = poofingTextureRegion;
            if(animationTimer>=poofing.getAnimationDuration()) {
                CasualModeScreen.swipeTrue = false;
                animationTimer =0;
            }
        }
        //playBabyCry same as overlap
        else if(CasualModeScreen.playBabyCry == false){
            loopingAnimationTimer+=Gdx.graphics.getDeltaTime();
            currentTextureRegion = losingTextureRegoin;
        }
        else{
            loopingAnimationTimer+=Gdx.graphics.getDeltaTime();
            currentTextureRegion = walkingTextureRegion;
        }
    }

    /*public void teleport(){
        position.x += PlayScreen.screenWidth/3;
    }

    public void teleportBack(){
        position.x -= PlayScreen.screenWidth/3;
    }*/

    public void dispose(){
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getPerson2Bound() {
        return person2Bound;
    }
}
