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
 * Created by Francis on 6/3/2017.
 */
public class StartScreen implements Screen {
    private final Smackbang ez;
    private Image playbutton,exitbutton,startScreenBackground;
    private OrthographicCamera camera;
    private Viewport viewport;
    public static Stage stage;
    private Sound buttonsound;

    public StartScreen(Smackbang ez){
        Smackbang.music.play();
        this.ez = ez;
        buttonsound = Gdx.audio.newSound(Gdx.files.internal("buttonsound.wav"));
        startScreenBackground = new Image(Assetss.manager.get(Assetss.startscreenbackground,Texture.class));
        startScreenBackground.setSize(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        playbutton = new Image(Assetss.manager.get(Assetss.playbutton,Texture.class));
        playbutton.setSize(0.6f* CasualModeScreen.screenWidth,0.96f* CasualModeScreen.screenHeight);
        playbutton.setPosition(CasualModeScreen.screenWidth/2 - playbutton.getWidth()/2, CasualModeScreen.screenHeight/2 - playbutton.getHeight()/2);
        playbutton.setOrigin(playbutton.getWidth()/2,playbutton.getHeight()/2);
        exitbutton = new Image(Assetss.manager.get(Assetss.exitbutton,Texture.class));
        exitbutton.setSize(0.08f* CasualModeScreen.screenWidth,0.13f* CasualModeScreen.screenHeight);
        exitbutton.setPosition(0.05f* CasualModeScreen.screenWidth,0.05f* CasualModeScreen.screenHeight);
        exitbutton.setOrigin(exitbutton.getWidth()/2,exitbutton.getHeight()/2);

        //setting up stage
        //*Understanding: this viewport will fit the world height&width in the parameter and fit to camera's view*
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        viewport = new StretchViewport(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight,camera);
        stage = new Stage(viewport,ez.batch);
        stage.addActor(startScreenBackground);
        stage.addActor(playbutton);
        stage.addActor(exitbutton);

        //transition to this page
        stage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));

        //adding input to actors
        Gdx.input.setInputProcessor(stage);
        playbutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                playbutton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playbutton.setScale(1f);
                Smackbang.music.pause();
                stage.addAction(Actions.sequence(Actions.fadeOut(0.8f),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setStoryScreen();
                    }
                })));
            }
        });
        exitbutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                exitbutton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                exitbutton.setScale(1f);
                Gdx.app.exit();
            }
        });
    }


    //methods to set new screens
    public void setStoryScreen(){
        ez.setScreen(new StoryScreen(ez));
    }


    @Override
    public void render(float delta) {
        ez.batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
        //ez.batch.begin();
        //ez.batch.draw((TextureRegion) walkinganimation.getKeyFrame(timeElapsed,true),camera.position.x+PlayScreen.screenWidth/4,ground.getHeight(),PlayScreen.screenWidth/8,PlayScreen.screenHeight/5);
        //ez.batch.end();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        buttonsound.dispose();
        System.out.println("startscreen disposed");
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
}
