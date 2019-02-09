package com.chalkboyygames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;
import com.chalkboyygames.Smackbang;

/**
 * Created by Francis on 17/3/2017.
 */

public class SelectMenuScreen implements Screen {
    private Smackbang ez;
    private Sound buttonsound;
    private Image startScreenBackground,casualButton,practiceButton,backToMainMenuButton,someButton;
    private OrthographicCamera camera;
    private Viewport viewport;
    public static Stage stage;

    public SelectMenuScreen(Smackbang ez) {
        this.ez = ez;
        buttonsound = Gdx.audio.newSound(Gdx.files.internal("buttonsound.wav"));
        startScreenBackground = new Image(Assetss.manager.get(Assetss.startscreenbackground,Texture.class));
        startScreenBackground.setSize(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);

        practiceButton = new Image(Assetss.manager.get(Assetss.practicebutton,Texture.class));
        practiceButton.setSize(0.15f* CasualModeScreen.screenWidth,0.24f* CasualModeScreen.screenHeight);
        practiceButton.setPosition(0.16f* CasualModeScreen.screenWidth,0.5f* CasualModeScreen.screenHeight - practiceButton.getWidth()/2);
        practiceButton.setOrigin(practiceButton.getWidth()/2,practiceButton.getHeight()/2);

        casualButton = new Image(Assetss.manager.get(Assetss.casualbutton,Texture.class));
        casualButton.setSize(0.15f* CasualModeScreen.screenWidth,0.24f* CasualModeScreen.screenHeight);
        casualButton.setPosition(0.5f* CasualModeScreen.screenWidth - 0.5f*casualButton.getWidth(),0.5f* CasualModeScreen.screenHeight-casualButton.getWidth()/2);
        casualButton.setOrigin(casualButton.getWidth()/2,casualButton.getHeight()/2);

        someButton = new Image(Assetss.manager.get(Assetss.casualbutton,Texture.class));
        someButton.setSize(0.15f* CasualModeScreen.screenWidth,0.24f* CasualModeScreen.screenHeight);
        someButton.setPosition(0.7f* CasualModeScreen.screenWidth,0.5f* CasualModeScreen.screenHeight-someButton.getWidth()/2);
        someButton.setOrigin(someButton.getWidth()/2,someButton.getHeight()/2);

        backToMainMenuButton = new Image(Assetss.manager.get(Assetss.backtomainmenubutton,Texture.class));
        backToMainMenuButton.setSize(0.08f* CasualModeScreen.screenWidth,0.13f* CasualModeScreen.screenHeight);
        backToMainMenuButton.setPosition(0.05f* CasualModeScreen.screenWidth,0.05f* CasualModeScreen.screenHeight);
        backToMainMenuButton.setOrigin(backToMainMenuButton.getWidth()/2,backToMainMenuButton.getHeight()/2);

        //setting up stage
        //*Understanding: this viewport will fit the world height&width in the parameter and fit to camera's view*
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        viewport = new StretchViewport(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight,camera);
        stage = new Stage(viewport,ez.batch);

        stage.addActor(startScreenBackground);
        stage.addActor(casualButton);
        stage.addActor(practiceButton);
        stage.addActor(backToMainMenuButton);
        stage.addActor(someButton);
        //stage.addActor();

        //transition to this page
        stage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));

        //adding input to actors
        Gdx.input.setInputProcessor(stage);
        casualButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                casualButton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                casualButton.setScale(1f);
                setHowToPlayScreen();
            }
        });

        practiceButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                practiceButton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Smackbang.music.pause();
                practiceButton.setScale(1f);
                setPracticeScreen();
            }
        });

        backToMainMenuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                backToMainMenuButton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Smackbang.music.pause();
                backToMainMenuButton.setScale(1f);
                setStartScreen();
            }
        });
    }

    @Override
    public void render(float delta) {
        ez.batch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        //u haven dispose anything yet!!!!!!!!!!!!!!!!!!!!
    }

    public void setPracticeScreen(){
        ez.setScreen(new com.chalkboyygames.Screens.PlayScreens.PracticeScreen(ez));
    }

    public void setHowToPlayScreen(){
        ez.setScreen(new HowToPlayScreen(ez));
    }

    public void setStartScreen(){
        ez.setScreen(new StartScreen(ez));
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
    public void show() {

    }

}
