package com.chalkboyygames.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;

import java.util.Random;

public class Obstacle {

    //private Sprite obstacle;
    private Vector2 position;
    private Rectangle obstacleBound;
    private Rectangle scoreBound;
    private TextureAtlas atlas;
    public Animation demon;
    public float loopingAnimationTimer;
    private Random random;

    public static float obstacleWidth = CasualModeScreen.screenWidth/8;
    public static float obstacleHeight = 0.85f* CasualModeScreen.screenHeight;

    public Obstacle(float x, float y) {
        random = new Random();
        loopingAnimationTimer = random.nextFloat()*(3);
        atlas = Assetss.manager.get(Assetss.demonanimation,TextureAtlas.class);
        demon = new Animation(0.1f,atlas.getRegions());
        //obstacle = new Sprite(Assetss.manager.get(Assetss.cuteobstacle,Texture.class));
        //obstacle.setSize(obstacleWidth, obstacleHeight);
        position = new Vector2(x, y);
        obstacleBound = new Rectangle(x, y, obstacleWidth, obstacleHeight);
        scoreBound = new Rectangle(x+obstacleWidth+Person.personWidth,y, CasualModeScreen.screenWidth,obstacleHeight);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getObstacleBound() {
        return obstacleBound;
    }

    public Rectangle getScoreBound() {
        return scoreBound;
    }
}
