package com.chalkboyygames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;
import com.chalkboyygames.Smackbang;

/**
 * Created by Francis on 15/3/2017.
 */
public class StoryScreen implements Screen {
    private Image skipButton,storyBackground;
    private OrthographicCamera camera;
    private Viewport viewport;
    public static Stage stage;
    private Smackbang ez;
    private Sound buttonsound;

    public StoryScreen(Smackbang ez) {
        Smackbang.backGroundMusic.play();
        buttonsound = Gdx.audio.newSound(Gdx.files.internal("buttonsound.wav"));
        this.ez = ez;
        storyBackground = new Image(Assetss.manager.get(Assetss.storybackground,Texture.class));
        storyBackground.setSize(2f* CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        skipButton = new Image(Assetss.manager.get(Assetss.playbutton,Texture.class));
        skipButton.setSize(0.08f* CasualModeScreen.screenWidth,0.13f* CasualModeScreen.screenHeight);
        skipButton.setPosition(0.89f* CasualModeScreen.screenWidth,0.83f* CasualModeScreen.screenHeight);
        skipButton.setOrigin(skipButton.getWidth()/2,skipButton.getHeight()/2);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        viewport = new StretchViewport(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight,camera);
        stage = new Stage(viewport,ez.batch);
        stage.addActor(storyBackground);
        stage.addActor(skipButton);
        storyBackground.addAction(Actions.sequence(Actions.moveBy(0,0,2f),Actions.moveBy(-CasualModeScreen.screenWidth,0,5f),Actions.moveBy(0,0,2f),Actions.run(new Runnable() {
            @Override
            public void run() {
                setSelectMenuScreen();
            }
        })));

        //adding input to actors
        Gdx.input.setInputProcessor(stage);
        skipButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonsound.play();
                skipButton.setScale(0.6f);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                skipButton.setScale(1f);
                Smackbang.music.pause();
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setSelectMenuScreen();
                    }
                })));
            }
        });
    }

    @Override
    public void render(float delta) {
        ez.batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        buttonsound.dispose();
        System.out.println("storyscreen disposed");

    }

    public void setSelectMenuScreen(){
        ez.setScreen(new SelectMenuScreen(ez));
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
