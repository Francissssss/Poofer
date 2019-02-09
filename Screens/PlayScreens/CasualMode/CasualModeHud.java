package com.chalkboyygames.Screens.PlayScreens.CasualMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.SelectMenuScreen;
import com.chalkboyygames.Smackbang;

/**
 * Created by Francis on 17/3/2017.
 */
public class CasualModeHud {
    private Smackbang ez;
    protected Image useless = new Image(Assetss.manager.get(Assetss.useless, Texture.class)),transitionFadeOfScreen,resultOverlay,replayButton,homeButton;
    protected OrthographicCamera camera1;
    private Viewport viewport;
    protected Stage stage;
    protected Table table;
    protected Label scoreLabel,scoreRS,newhighscoreRS,highscoreRS;
    protected static Preferences prefs;
    private static Sound buttonsound;

    public CasualModeHud(Smackbang ez){
        this.ez = ez;

        //SETTING UP AUDIO
        buttonsound = Gdx.audio.newSound(Gdx.files.internal("buttonsound.wav"));




        //SETTING UP NEW STAGE STUFFS
        camera1 = new OrthographicCamera();
        viewport = new ScreenViewport(camera1);
        stage = new Stage(viewport,ez.batch);

        scoreLabel = new Label("Score: 0", new Label.LabelStyle(Assetss.font, Color.BLACK));
        useless.setSize(1,1);
        useless.setPosition(0,2*CasualModeScreen.screenHeight);
        transitionFadeOfScreen = new Image(Assetss.manager.get(Assetss.transitionscreen,Texture.class));
        transitionFadeOfScreen.setSize(1.5f*CasualModeScreen.screenWidth,1.5f*CasualModeScreen.screenHeight);
        transitionFadeOfScreen.setPosition(0,0);
        resultOverlay = new Image(Assetss.manager.get(Assetss.resultoverlay2,Texture.class));
        resultOverlay.setSize(0.7f*CasualModeScreen.screenWidth,0.7f*CasualModeScreen.screenHeight);
        resultOverlay.setPosition(resultOverlay.getX()+CasualModeScreen.screenWidth/2 - resultOverlay.getWidth()/2,CasualModeScreen.screenHeight/8);
        scoreRS = new Label("Score: 10",new Label.LabelStyle(Assetss.font1,Color.BROWN));
        scoreRS.setPosition(scoreRS.getX()+CasualModeScreen.screenWidth/2 - scoreRS.getWidth()/2,0.4f*CasualModeScreen.screenHeight);
        replayButton = new Image(Assetss.manager.get(Assetss.replaybuttonbrown,Texture.class));
        replayButton.setSize(0.12f*CasualModeScreen.screenWidth,0.20f*CasualModeScreen.screenHeight);
        replayButton.setPosition(replayButton.getX()-replayButton.getWidth()/2+CasualModeScreen.screenWidth/2.2f - replayButton.getWidth()/2,CasualModeScreen.screenHeight/6);
        replayButton.setOrigin(replayButton.getWidth()/2,replayButton.getHeight()/2);
        homeButton = new Image(Assetss.manager.get(Assetss.homebuttonbrown,Texture.class));
        homeButton.setSize(0.12f*CasualModeScreen.screenWidth,0.20f*CasualModeScreen.screenHeight);
        homeButton.setPosition(homeButton.getX()-homeButton.getWidth()/2+2f/3f*CasualModeScreen.screenWidth - homeButton.getWidth()/2,CasualModeScreen.screenHeight/6);
        homeButton.setOrigin(homeButton.getWidth()/2,homeButton.getHeight()/2);
        prefs = Gdx.app.getPreferences("dataList");
        if(!prefs.contains("HIGHSCORE")){
            prefs.putInteger("HIGHSCORE", 0);
        }
        newhighscoreRS = new Label("You broke your highscore!",new Label.LabelStyle(Assetss.font1,Color.BROWN));
        newhighscoreRS.setPosition(newhighscoreRS.getX()+CasualModeScreen.screenWidth/2 - newhighscoreRS.getWidth()/2,0.5f*CasualModeScreen.screenHeight);
        highscoreRS = new Label(String.format("Your Highscore is %s", getHighScore()),new Label.LabelStyle(Assetss.font1,Color.BROWN));
        highscoreRS.setPosition(highscoreRS.getX()+CasualModeScreen.screenWidth/2 - highscoreRS.getWidth()/2,0.5f*CasualModeScreen.screenHeight);




        //ADDINNG ACTORS TO STAGE
        table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(scoreLabel).expandX().padTop(CasualModeScreen.screenHeight/20);
        stage.addActor(table);
        stage.addActor(resultOverlay);
        resultOverlay.setVisible(false);
        resultOverlay.getColor().a=0;
        stage.addActor(scoreRS);
        scoreRS.setVisible(false);
        stage.addActor(highscoreRS);
        highscoreRS.setVisible(false);
        stage.addActor(newhighscoreRS);
        newhighscoreRS.setVisible(false);
        stage.addActor(transitionFadeOfScreen);//THIS NEEDS TO BE BEFORE REPLAY BUTTON. 3hrs... gone :(
        stage.addActor(replayButton);
        replayButton.setVisible(false);
        replayButton.getColor().a=0;
        stage.addActor(homeButton);
        homeButton.setVisible(false);
        homeButton.getColor().a=0;
        stage.addActor(useless); //THE LAST ONE NEEDS TO BE AN ASSET THAT REAMINS ON THE SCREEN. THIS I PUT IT TO SOLVE EVERYTHINGGGG.. its actually useless in game. 7hrs gone..:((




        //TRANSITION TO THIS PAGE
        scoreLabel.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(1.8f)));
        transitionFadeOfScreen.addAction(Actions.fadeOut(1f));





        //ADDING INPUT TO ACTORS
        replayButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                replayButton.setScale(0.7f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                replayButton.setScale(1f);
                transitionFadeOfScreen.addAction(Actions.fadeIn(1f));
                resultOverlay.addAction(Actions.fadeOut(0.4f));
                replayButton.addAction(Actions.fadeOut(1f));
                homeButton.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setPlayScreen();
                    }
                })));
            }
        });

        homeButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                homeButton.setScale(0.7f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                homeButton.setScale(1f);
                Smackbang.backGroundMusic.pause();
                transitionFadeOfScreen.addAction(Actions.fadeIn(0.2f));
                replayButton.addAction(Actions.fadeOut(0.2f));
                homeButton.addAction(Actions.sequence(Actions.fadeOut(0.2f),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setMenuSelectScreen();
                    }
                })));
            }
        });


    }

    protected static int getHighScore(){
        return prefs.getInteger("HIGHSCORE");
    }

    public void setMenuSelectScreen(){
        ez.setScreen(new SelectMenuScreen(ez));
    }
    public void setPlayScreen(){
        ez.setScreen(new CasualModeScreen(ez));
    }

    public static void dispose(){
        buttonsound.dispose();
    }
}
