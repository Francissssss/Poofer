package com.chalkboyygames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chalkboyygames.Screens.StartScreen;

public class Smackbang extends Game{
	public SpriteBatch batch;
	public static Music music,backGroundMusic;

	@Override
	public void create () {
		//load all resources
		Assetss.load();
		Assetss.loadFonts();

		//start playing game music
		music = Gdx.audio.newMusic(Gdx.files.internal("hideandseek.mp3"));
		music.setLooping(true);
		music.play();

		backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("inpursuit.mp3"));
		backGroundMusic.setLooping(true);
		backGroundMusic.play();
		backGroundMusic.pause();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
		backGroundMusic.dispose();
		Assetss.dispose();
		System.out.println("Assets and font disposed");
	}

}
