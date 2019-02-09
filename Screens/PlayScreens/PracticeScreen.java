package com.chalkboyygames.Screens.PlayScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.SelectMenuScreen;
import com.chalkboyygames.Smackbang;
import com.chalkboyygames.Sprites.Obstacle;
import com.chalkboyygames.Sprites.Person;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;

/**
 * Created by Francis on 12/3/2017.
 */
public class PracticeScreen implements Screen,GestureDetector.GestureListener {
    private final Smackbang ez;
    private OrthographicCamera cam,camera;
    private Viewport viewport;
    public static Stage stage;
    private Image homeButton,tick,cross,practiceModeText;
    public Sprite background1,background2,ground1,ground2;
    private Vector2 ground1Pos, ground2Pos, background1Pos,background2Pos;
    private Person person;
    private Array<Obstacle> obstacle;
    private InputMultiplexer inputMultiplexer;
    public GestureDetector gestureDetector;
    private Sound buttonsound,poof;
    private Music error;//didnt put as sound because for sound, interval is very short when overlapping.

    public PracticeScreen(Smackbang ez) {
        Smackbang.backGroundMusic.play();
        CasualModeScreen.playBabyCry = true;//this is so that lose animation will nvr play
        CasualModeScreen.swipeTrue = false;
        this.ez = ez;
        poof = Gdx.audio.newSound(Gdx.files.internal("poof.wav"));
        error = Gdx.audio.newMusic(Gdx.files.internal("error.wav"));
        buttonsound = Gdx.audio.newSound(Gdx.files.internal("buttonsound.wav"));
        //setting up camera
        cam = new OrthographicCamera();
        cam.setToOrtho(false, CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        cam.position.x = CasualModeScreen.initialPP/2- CasualModeScreen.screenWidth;

        //setting up screen
        background1 = new Sprite(Assetss.manager.get(Assetss.myfullbackground1,Texture.class));
        background1.setSize(CasualModeScreen.backgroundWidth, CasualModeScreen.screenHeight);
        background2 = new Sprite(Assetss.manager.get(Assetss.myfullbackground1,Texture.class));
        background2.setSize(CasualModeScreen.backgroundWidth, CasualModeScreen.screenHeight);
        background1Pos = new Vector2(-CasualModeScreen.backgroundWidth,0);
        background2Pos = new Vector2(0,0);
        ground1 = new Sprite(Assetss.manager.get(Assetss.ground1,Texture.class));
        ground2 = new Sprite(Assetss.manager.get(Assetss.ground1,Texture.class));
        ground1.setSize(CasualModeScreen.groundWidth, CasualModeScreen.groundHeight);
        ground2.setSize(CasualModeScreen.groundWidth, CasualModeScreen.groundHeight);
        ground1Pos = new Vector2(-CasualModeScreen.groundWidth,0);
        ground2Pos = new Vector2(0,0);
        person = new Person(0,0);
        person.DEFAULTMOVEMENT=0;
        obstacle = new Array<Obstacle>();
        int i;
        for(i=1;i<=4;i++){
            obstacle.add(new Obstacle(i*(Obstacle.obstacleWidth+ CasualModeScreen.obstacleSpacing), CasualModeScreen.groundHeight/3));
        }


        //setting up stage stuffs
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        viewport = new StretchViewport(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight,camera);
        stage = new Stage(viewport,ez.batch);
        homeButton = new Image(Assetss.manager.get(Assetss.homebuttonbrown,Texture.class));
        homeButton.setSize(0.08f* CasualModeScreen.screenWidth,0.13f* CasualModeScreen.screenHeight);
        homeButton.setPosition(0.89f* CasualModeScreen.screenWidth,0.83f* CasualModeScreen.screenHeight);
        homeButton.setOrigin(homeButton.getWidth()/2,homeButton.getHeight()/2);

        tick = new Image(Assetss.manager.get(Assetss.tick,Texture.class));
        tick.setSize(0.24f* CasualModeScreen.screenWidth,0.26f* CasualModeScreen.screenHeight);
        tick.setPosition(CasualModeScreen.screenWidth/2-tick.getWidth()/2,0.65f* CasualModeScreen.screenHeight);

        cross = new Image(Assetss.manager.get(Assetss.cross,Texture.class));
        cross.setSize(0.16f* CasualModeScreen.screenWidth,0.26f* CasualModeScreen.screenHeight);
        cross.setPosition(CasualModeScreen.screenWidth/2-cross.getWidth()/2,0.65f* CasualModeScreen.screenHeight);

        practiceModeText = new Image(Assetss.manager.get(Assetss.practicemodetext,Texture.class));
        practiceModeText.setSize(0.17f* CasualModeScreen.screenWidth,0.05f* CasualModeScreen.screenHeight);
        practiceModeText.setPosition(0.02f* CasualModeScreen.screenWidth,0.92f* CasualModeScreen.screenHeight);

        stage.addActor(homeButton);
        stage.addActor(cross);
        stage.addActor(tick);
        stage.addActor(practiceModeText);
        cross.setVisible(false);
        tick.setVisible(true);

        //adding input to actors
        homeButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                homeButton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Smackbang.backGroundMusic.pause();
                homeButton.setScale(1f);
                setSelectMenuScreen();
            }
        });

        gestureDetector = new GestureDetector(this);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public void setSelectMenuScreen(){
        ez.setScreen(new SelectMenuScreen(ez));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //setting cam to follow person1
        ez.batch.setProjectionMatrix(cam.combined);
        cam.position.x = cam.position.x+(person.getPosition().x-cam.position.x+ CasualModeScreen.screenWidth/3)*0.055f;
        cam.update();

        //update person1 & person1bound movement
        person.update(delta);

        //update obstacle and obstacleBound and scorebound position
        //outofscreen
        for(Obstacle holdObstacle:obstacle) {
            if (holdObstacle.getPosition().x + holdObstacle.obstacleWidth < cam.position.x - CasualModeScreen.screenWidth/2) {
                holdObstacle.getPosition().set(holdObstacle.getPosition().x+ 4*(Obstacle.obstacleWidth+ CasualModeScreen.obstacleSpacing), holdObstacle.getPosition().y);

                holdObstacle.getObstacleBound().setPosition(holdObstacle.getPosition().x, holdObstacle.getPosition().y);
                holdObstacle.getScoreBound().setPosition(holdObstacle.getPosition().x+holdObstacle.obstacleWidth,holdObstacle.getPosition().y);
            }
        }

        //reposition ground and background
        if(ground1Pos.x+ CasualModeScreen.groundWidth<cam.position.x- CasualModeScreen.screenWidth/1.5f){
            ground1Pos.add(2* CasualModeScreen.groundWidth,0);
        }
        if(ground2Pos.x+ CasualModeScreen.groundWidth<cam.position.x- CasualModeScreen.screenWidth/1.5f){
            ground2Pos.add(2* CasualModeScreen.groundWidth,0);
        }

        if(background1Pos.x+ CasualModeScreen.backgroundWidth<cam.position.x- CasualModeScreen.screenWidth){
            background1Pos.add(2* CasualModeScreen.backgroundWidth,0);
        }
        if(background2Pos.x+ CasualModeScreen.backgroundWidth<cam.position.x- CasualModeScreen.screenWidth) {
            background2Pos.add(2 * CasualModeScreen.backgroundWidth, 0);
        }

        cross.setVisible(false);
        tick.setVisible(true);
        //checking for overlap
        for(Obstacle holdObstacle:obstacle) {
            holdObstacle.loopingAnimationTimer+=delta;
            //obstaclebound overlaps person
            if (holdObstacle.getObstacleBound().overlaps(person.getPerson2Bound())) {
                error.play();
                cross.setVisible(true);
                tick.setVisible(false);
            }
        }

        //update demon

        ez.batch.setProjectionMatrix(cam.combined);
        ez.batch.begin();
        ez.batch.draw(background1,background1Pos.x,background1Pos.y,background1.getWidth(),background1.getHeight());
        ez.batch.draw(background2,background2Pos.x,background2Pos.y,background2.getWidth(),background2.getHeight());
        for(Obstacle holdObstacle: obstacle) {
            ez.batch.draw((TextureRegion) holdObstacle.demon.getKeyFrame(holdObstacle.loopingAnimationTimer,true), holdObstacle.getPosition().x, holdObstacle.getPosition().y,
                    holdObstacle.obstacleWidth, holdObstacle.obstacleHeight);
        }
        ez.batch.draw(person.currentTextureRegion,person.getPosition().x,person.getPosition().y,
                person.personWidth,person.personHeight);
        ez.batch.draw(ground1,ground1Pos.x,0,ground1.getWidth(),ground1.getHeight());
        ez.batch.draw(ground2,ground2Pos.x,0,ground2.getWidth(),ground2.getHeight());
        ez.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        buttonsound.dispose();
        error.dispose();
        stage.dispose();
        System.out.println("practicescreen disposed");
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        poof.play();
        CasualModeScreen.swipeTrue = true;
        if(Math.signum(velocityX)==1.0f) {
            //person.getPosition().x += (velocityX/screenWidth)*250;
            person.getPosition().x += velocityX*0.12f;
        }
        return true;
        /*poof.play();
        PlayScreen.swipeTrue = true;
        if(Math.signum(velocityX)==1.0f) {
            person.getPosition().x += (velocityX/PlayScreen.screenWidth)*150;  //this formula for some reason the higher resolution devices move super slow and applies to low resolutions also
        }
        return true;*/
        /* poof.play();
        PlayScreen.swipeTrue = true;
        if(Math.signum(velocityX)==1.0f) {
            person.getPosition().x += velocityX/10000*PlayScreen.screenWidth;
            //add flingY oso? added difficulty!
        }
        //if overlap then score. ESTIMATION GAME??
        return true;*/
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

}
