package com.chalkboyygames.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.chalkboyygames.Assetss;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;
import com.chalkboyygames.Smackbang;

/**
 * Created by Francis on 13/3/2017.
 */
public class HowToPlayScreen implements Screen{
    private Smackbang ez;
    private Image background,howToPlayText,touchToStartText;
    private OrthographicCamera camera;
    private Viewport viewport;
    public static Stage stage;
    private TextureAtlas atlas;
    private float timeElapsed;
    private Animation swipeAnimation;

    public HowToPlayScreen(Smackbang ez) {
        this.ez = ez;
        atlas = Assetss.manager.get(Assetss.learnpoofanimation,TextureAtlas.class);
        swipeAnimation = new Animation(0.7f,atlas.getRegions());
        timeElapsed = 0f;

        background = new Image(Assetss.manager.get(Assetss.myfullbackground2,Texture.class));
        background.setSize(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        howToPlayText = new Image(Assetss.manager.get(Assetss.howtoplaytext,Texture.class));
        howToPlayText.setSize(0.6f* CasualModeScreen.screenWidth,0.7f* CasualModeScreen.screenHeight);
        howToPlayText.setPosition(CasualModeScreen.screenWidth/2-howToPlayText.getWidth()/2,0.23f* CasualModeScreen.screenHeight);
        touchToStartText = new Image(Assetss.manager.get(Assetss.touchtostarttext,Texture.class));
        touchToStartText.setSize(0.2f* CasualModeScreen.screenWidth,0.1f* CasualModeScreen.screenHeight);
        touchToStartText.setPosition(CasualModeScreen.screenWidth/2-touchToStartText.getWidth()/2,0.08f* CasualModeScreen.screenHeight);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CasualModeScreen.screenWidth, CasualModeScreen.screenHeight);
        viewport = new StretchViewport(CasualModeScreen.screenWidth, CasualModeScreen.screenHeight,camera);
        stage = new Stage(viewport,ez.batch);
        stage.addActor(background);
        stage.addActor(touchToStartText);
        stage.addActor(howToPlayText); //THE LAST ACTOR CANNOT HAVE ANY ACTIONS!!!


        //transition to this page
        stage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));

        //actions for actors
        touchToStartText.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(1f),Actions.fadeOut(0.9f))));
    }


    @Override
    public void render(float delta) {
        timeElapsed +=delta;
        if(Gdx.input.justTouched()){
            ez.setScreen(new CasualModeScreen(ez));
        }
        stage.act(delta);
        stage.draw();
        ez.batch.setProjectionMatrix(camera.combined);
        ez.batch.begin();
        ez.batch.draw((TextureRegion) swipeAnimation.getKeyFrame(timeElapsed,true),0.25f* CasualModeScreen.screenWidth,0.35f* CasualModeScreen.screenHeight,0.5f* CasualModeScreen.screenWidth,0.2f* CasualModeScreen.screenHeight);
        ez.batch.end();



    }


    @Override
    public void hide() {
        dispose();

    }

    @Override
    public void dispose() {
        stage.dispose();
        System.out.println("howtoplayscreen disposed");
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
