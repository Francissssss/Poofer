package com.chalkboyygames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.chalkboyygames.Screens.PlayScreens.CasualMode.CasualModeScreen;

/**
 * Created by Francis on 10/3/2017.
 */
public class Assetss {
    public static FreeTypeFontGenerator generator;
    public static BitmapFont font;
    public static FreeTypeFontGenerator generator1;
    public static BitmapFont font1;


    public static final AssetManager manager = new AssetManager();
    public static final String playbutton = "playbutton5.png";
    public static final String homebutton = "homebutton1.png";
    public static final String myfullbackground1 = "myownbackground6.png";
    public static final String storybackground = "storybackground.png";
    public static final String exitbutton = "exitbutton2.png";
    public static final String ground1 = "cloudground1.png";
    public static final String practicebutton = "practicebutton.png";
    public static final String backtomainmenubutton = "backtomainmenubutton.png";
    public static final String useless = "useless.png";
    public static final String transitionscreen = "transitionscreen.png";
    public static final String resultoverlay2 = "resultoverlay.png";
    public static final String replaybuttonbrown = "replaybutton1.png";
    public static final String homebuttonbrown = "homebutton1.png";
    public static final String casualbutton = "casualbutton.png";
    public static final String tick = "tick2.png";
    public static final String cross = "cross.png";
    public static final String myfullbackground2 = "myfullbackground2.png";
    public static final String startscreenbackground = "startscreenbackground.png";
    public static final String howtoplaytext = "howtoplaytext1.png";
    public static final String touchtostarttext = "touchtostarttext.png";
    public static final String practicemodetext = "practicemodetext.png";
    public static final String learnpoofanimation = "learnpoofanimation/pooftutorial.pack";
    public static final String walkinganimation = "walkinganimation/walking.pack";
    public static final String poofinganimation = "poofinganimation/poofing.pack";
    public static final String loseanimation = "loseanimation/lose.pack";
    public static final String demonanimation = "demonanimation/demon.pack";

    public static void load(){
        manager.load(playbutton, Texture.class);
        manager.load(myfullbackground1, Texture.class);
        manager.load(exitbutton, Texture.class);
        manager.load(ground1, Texture.class);
        manager.load(practicebutton, Texture.class);
        manager.load(homebutton, Texture.class);
        manager.load(useless, Texture.class);
        manager.load(transitionscreen, Texture.class);
        manager.load(resultoverlay2, Texture.class);
        manager.load(replaybuttonbrown, Texture.class);
        manager.load(homebuttonbrown, Texture.class);
        manager.load(tick, Texture.class);
        manager.load(cross, Texture.class);
        manager.load(myfullbackground2, Texture.class);
        manager.load(howtoplaytext, Texture.class);
        manager.load(touchtostarttext, Texture.class);
        manager.load(practicemodetext, Texture.class);
        manager.load(walkinganimation, TextureAtlas.class);
        manager.load(learnpoofanimation, TextureAtlas.class);
        manager.load(poofinganimation, TextureAtlas.class);
        manager.load(loseanimation, TextureAtlas.class);
        manager.load(startscreenbackground, Texture.class);
        manager.load(storybackground, Texture.class);
        manager.load(casualbutton, Texture.class);
        manager.load(backtomainmenubutton, Texture.class);
        manager.load(demonanimation, TextureAtlas.class);

        Assetss.manager.finishLoading();
    }

    public static void loadFonts(){
        //font generated for playscreen
        generator = new FreeTypeFontGenerator(Gdx.files.internal("hivnoretrofont/hivnotretro.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) CasualModeScreen.screenWidth/30;
        font = generator.generateFont(parameter);
        font.setColor(0,0,0,1);
        generator.dispose();

        //font generated for result overlay
        generator1 = new FreeTypeFontGenerator(Gdx.files.internal("hivnoretrofont/hivnotretro.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = (int) CasualModeScreen.screenWidth/20;
        font1 = generator1.generateFont(parameter1);
        font1.setColor(0,0,0,1);
        generator1.dispose();
    }

    public static void dispose(){
        manager.dispose();
        font.dispose();
        font1.dispose();
    }
}
