package com.chalkboyygames.Screens.PlayScreens.CasualMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Smackbang;
import com.chalkboyygames.Sprites.Obstacle;
import com.chalkboyygames.Sprites.Person;

import java.util.Random;

/**
 * Created by Francis on 6/3/2017.
 */
public class CasualModeScreen implements Screen, GestureDetector.GestureListener{
    public OrthographicCamera cam;
    private Sprite ground1,ground2,background1,background2;
    private Vector2 ground1Pos, ground2Pos, background1Pos,background2Pos;
    private Sound scorePlus,lose,poof;
    private Smackbang ez;
    private Person person;
    private Array<Obstacle> obstacle;
    private Random random;
    private InputMultiplexer inputMultiplexer;
    public GestureDetector gestureDetector;
    public static boolean playBabyCry;
    public static boolean swipeTrue;
    private CasualModeHud casualModeHud;

    public static float screenHeight = Gdx.graphics.getHeight();
    public static float screenWidth = Gdx.graphics.getWidth();
    public static float levitationHeight = screenHeight/5f;
    public static float groundHeight = screenHeight/2.8f;
    public static float groundWidth = 2*screenWidth;
    public static float backgroundWidth =2*screenWidth;
    public static float obstacleSpacing = screenWidth/1.2f;
    public static float initialPP = -screenWidth;
    public static int score;

    public CasualModeScreen(Smackbang ez){
        this.ez = ez;

        //RESET ALL VALUES
        swipeTrue = false;
        playBabyCry = true;
        score = 0;
        person.DEFAULTMOVEMENT = 0.5f* CasualModeScreen.screenWidth;


        //SETTING UP AUDIO
        poof = Gdx.audio.newSound(Gdx.files.internal("poof.wav"));
        scorePlus = Gdx.audio.newSound(Gdx.files.internal("scoreplus.mp3"));
        lose = Gdx.audio.newSound(Gdx.files.internal("lose.wav"));


        //SETTING UP NEW SCREEN STUFFS
        cam = new OrthographicCamera();
        cam.setToOrtho(false,screenWidth,screenHeight);
        cam.position.x = initialPP/2-screenWidth/2;
        ground1 = new Sprite(Assetss.manager.get(Assetss.ground1,Texture.class));
        ground2 = new Sprite(Assetss.manager.get(Assetss.ground1,Texture.class));
        ground1.setSize(groundWidth,groundHeight);
        ground2.setSize(groundWidth,groundHeight);
        ground1Pos = new Vector2(-groundWidth,0);
        ground2Pos = new Vector2(0,0);
        background1 = new Sprite(Assetss.manager.get(Assetss.myfullbackground1,Texture.class));
        background1.setSize(backgroundWidth,screenHeight);
        background2 = new Sprite(Assetss.manager.get(Assetss.myfullbackground1,Texture.class));
        background2.setSize(backgroundWidth,screenHeight);
        background1Pos = new Vector2(-backgroundWidth,0);
        background2Pos = new Vector2(0,0);
        person = new Person(initialPP,0);
        random = new Random();
        obstacle = new Array<Obstacle>();
        int i;
        for(i=1;i<=4;i++){
            obstacle.add(new Obstacle(i*(Obstacle.obstacleWidth+obstacleSpacing) - random.nextInt((int)(obstacleSpacing/1.5f)),groundHeight/3));
        }


        //BRINGING IN HUD
        casualModeHud = new CasualModeHud(ez);


        //INPUT DETECTORS
        gestureDetector = new GestureDetector(this);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(casualModeHud.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        poof.play();
        swipeTrue = true;
        if(Math.signum(velocityX)==1.0f) {
            person.getPosition().x += velocityX*0.12f;
            //person.getPosition().x += (velocityX/screenWidth)*250;
        }
        return true;
    }

    @Override
    public void render(float delta) {
        //SETTING CAMERA TO FOLLOW PERSON
        ez.batch.setProjectionMatrix(cam.combined); //this fixes what the player can see/ on the screen which is cam. What the player can see is now cam.
        cam.position.x = cam.position.x+((person.getPosition().x + screenWidth/1.8f)-cam.position.x)*0.055f;
        cam.update();


        //UPDATE PERSON
        person.update(delta);


        // REPOSITION OUT OF SCREEN (OBSTACLE AND BOUNDS)
        for(Obstacle holdObstacle:obstacle) {
            if (holdObstacle.getPosition().x + holdObstacle.obstacleWidth < cam.position.x - screenWidth/2) {
                holdObstacle.getPosition().set(holdObstacle.getPosition().x+ 4*(Obstacle.obstacleWidth+obstacleSpacing)- random.nextInt((int)(obstacleSpacing/1.9f)), holdObstacle.getPosition().y);

                holdObstacle.getObstacleBound().setPosition(holdObstacle.getPosition().x, holdObstacle.getPosition().y);
                holdObstacle.getScoreBound().setPosition(holdObstacle.getPosition().x+holdObstacle.obstacleWidth+person.personWidth,holdObstacle.getPosition().y);



                //AFTER OUT OF SCREEN REPOSITION
                //REPOSITION IF 2 OVERSTACLE OVERLAPS
                if(obstacle.get(0).getObstacleBound().overlaps(obstacle.get(3).getObstacleBound())){
                    holdObstacle.getPosition().x+=holdObstacle.obstacleWidth;
                }
                if(obstacle.get(1).getObstacleBound().overlaps(obstacle.get(0).getObstacleBound())){
                    holdObstacle.getPosition().x+=holdObstacle.obstacleWidth;
                }
                if(obstacle.get(2).getObstacleBound().overlaps(obstacle.get(1).getObstacleBound())){
                    holdObstacle.getPosition().x+=holdObstacle.obstacleWidth;
                }
                if(obstacle.get(3).getObstacleBound().overlaps(obstacle.get(2).getObstacleBound())){
                    holdObstacle.getPosition().x+=holdObstacle.obstacleWidth;
                }

                holdObstacle.getObstacleBound().setPosition(holdObstacle.getPosition().x, holdObstacle.getPosition().y);
                holdObstacle.getScoreBound().setPosition(holdObstacle.getPosition().x+holdObstacle.obstacleWidth+person.personWidth,holdObstacle.getPosition().y);
            }
        }


        //OUT OF SCREEN (BACKGROUND AND GROUND)
        if(ground1Pos.x+groundWidth<cam.position.x - screenWidth){
            ground1Pos.add(2*groundWidth,0);
        }
        if(ground2Pos.x+groundWidth<cam.position.x - screenWidth){
            ground2Pos.add(2*groundWidth,0);
        }

        if(background1Pos.x+backgroundWidth<cam.position.x-screenWidth){
            background1Pos.add(2*backgroundWidth,0);
        }
        if(background2Pos.x+backgroundWidth<cam.position.x-screenWidth) {
            background2Pos.add(2 * backgroundWidth, 0);
        }


        //OVERLAPS
        for(Obstacle holdObstacle:obstacle) {
            //UPDATE DEMON ANIMATION TIME
            holdObstacle.loopingAnimationTimer+=delta;
            //PERSON HITS OBSTACLE BOUND
            if (holdObstacle.getObstacleBound().overlaps(person.getPerson2Bound())) {
                //STOPS ALL ACTIVITY
                inputMultiplexer.removeProcessor(gestureDetector);
                cam.position.x = cam.position.x+((person.getPosition().x - screenWidth/2f)-cam.position.x)*0.055f;
                person.DEFAULTMOVEMENT = 0;

                //MAKE THE FOLLOWING CODE RUN ONCE ONLY! (since person is always overlapping, we don't want this to run over and over)
                casualModeHud.scoreLabel.addAction(Actions.fadeOut(0.3f));
                if(playBabyCry == true) {
                    playBabyCry = false;
                    //PLAY SOUND
                    lose.play();
                    //RESULTS
                    casualModeHud.resultOverlay.getColor().a=0;
                    casualModeHud.resultOverlay.setVisible(true);
                    casualModeHud.resultOverlay.addAction(Actions.fadeIn(1.5f));
                    casualModeHud.homeButton.getColor().a=0;
                    casualModeHud.homeButton.setVisible(true);
                    casualModeHud.homeButton.addAction(Actions.fadeIn(0.7f));
                    casualModeHud.replayButton.setVisible(true);
                    casualModeHud.replayButton.addAction(Actions.fadeIn(0.7f));
                    casualModeHud.scoreRS.setText(String.format("Score: %s",score));
                    casualModeHud.scoreRS.setVisible(true);

                    //SAVING HIGHSCORE (if score is higher than highscore)
                    if(score > CasualModeHud.getHighScore()){
                        casualModeHud.newhighscoreRS.setVisible(true);
                        CasualModeHud.prefs.putInteger("HIGHSCORE", score);
                        CasualModeHud.prefs.flush();
                    }
                    else if(score<=CasualModeHud.getHighScore()) {
                        casualModeHud.highscoreRS.setVisible(true);
                    }
                }
            }

            //PERSON PASSES AN OBSTACLE
            if(holdObstacle.getScoreBound().overlaps(person.getPerson2Bound())){
                holdObstacle.getScoreBound().setX(-10000*screenWidth);
                scorePlus.play();
                score+=1;
                casualModeHud.scoreLabel.setText(String.format("Score: %s",score));

            }
        }

        //DRAWING STUFFS TO THE SCREEN
        ez.batch.begin();
        ez.batch.draw(background1,background1Pos.x,background1Pos.y,background1.getWidth(),background1.getHeight());
        ez.batch.draw(background2,background2Pos.x,background2Pos.y,background2.getWidth(),background2.getHeight());
        for(Obstacle holdObstacle: obstacle) {
            ez.batch.draw((TextureRegion)holdObstacle.demon.getKeyFrame(holdObstacle.loopingAnimationTimer,true), holdObstacle.getPosition().x, holdObstacle.getPosition().y,
                    holdObstacle.obstacleWidth, holdObstacle.obstacleHeight);
        }
        ez.batch.draw(person.currentTextureRegion,person.getPosition().x,person.getPosition().y,
                person.personWidth,person.personHeight);
        ez.batch.draw(ground1,ground1Pos.x,0,ground1.getWidth(),ground1.getHeight());
        ez.batch.draw(ground2,ground2Pos.x,0,ground2.getWidth(),ground2.getHeight());
        ez.batch.end();

        //FINALLY DRAW STAGE ON TOP OF SCREEN (my method :D)
        casualModeHud.stage.act(delta);
        casualModeHud.stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        //stage.dispose();  STAGE CANNOT BE DISPOSED BECAUSE TRANSITION SCREEN WILL BE DISPOSED TRY IY!
        scorePlus.dispose();
        lose.dispose();
        poof.dispose();
        CasualModeHud.dispose();
        System.out.println("playscreen disposed");
    }

    @Override
    public void show() {
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

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
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
}
