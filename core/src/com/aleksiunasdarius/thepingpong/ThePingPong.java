package com.aleksiunasdarius.thepingpong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ThePingPong extends Game {
	
	public static OrthographicCamera camera;
	public static SpriteBatch batch;
	
	public static Preferences prefs;
	
	public static BitmapFont font;
	
	public static BitmapFont lrFont;
	public static BitmapFont rrFont;
	
	public static BitmapFont countDownFont;
	
	public static final int SCREEN_HEIGHT = 48;
	public static final int SCREEN_WIDTH = 80;
	
	public static final int VIBRO_LENGTH = 50;
	
	public Sound puff;
	public Sound tto;
	public Sound go;
	
	Music backMusic;
	
	public Texture menuBack;
	public Texture buttonTxt;
	public Texture buttonDownTxt;

	
	public Texture gameBack;
	public Texture blueRacketTxt;
	public Texture redRacketTxt;
	public Texture greenBallTxt;
	
	public Sprite blueRacket;
	public Sprite redRacket;
	public Sprite greenBall;
	
	public boolean soundOn;
	public boolean musicOn;
	public boolean vibroOn = true;
	
	@Override
	public void create () {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();		
		prefs = Gdx.app.getPreferences("MyPreferences");
		
		font = new BitmapFont(Gdx.files.internal("font/pixelfont.fnt"));
		lrFont = new BitmapFont(Gdx.files.internal("font/pixelfont.fnt"));
		lrFont.setColor(Color.RED);
		lrFont.scale(-0.2f);
		rrFont = new BitmapFont(Gdx.files.internal("font/pixelfont.fnt"));
		rrFont.setColor(Color.BLUE);
		rrFont.scale(-0.2f);
		
		countDownFont = new BitmapFont(Gdx.files.internal("font/pixelfont.fnt"));
		countDownFont.scale(3);
		countDownFont.setColor(Color.GREEN);
		
		menuBack = new Texture (Gdx.files.internal("menu/background.png"));
		buttonTxt = new Texture (Gdx.files.internal("menu/button.png"));
		buttonDownTxt = new Texture (Gdx.files.internal("menu/buttondown.png"));
		
		gameBack = new Texture(Gdx.files.internal("game/background.png"));
		blueRacketTxt = new Texture(Gdx.files.internal("game/blueracket.png"));
		redRacketTxt = new Texture(Gdx.files.internal("game/redracket.png"));
		greenBallTxt= new Texture(Gdx.files.internal("game/greenball.png"));
		
		blueRacket = new Sprite(blueRacketTxt);
		redRacket = new Sprite(redRacketTxt);
		greenBall = new Sprite(greenBallTxt);
		
		soundOn = prefs.getBoolean("Sound",true);
		musicOn = prefs.getBoolean("Music",true);
		
		puff = Gdx.audio.newSound(Gdx.files.internal("sounds/puff.wav"));
		tto = Gdx.audio.newSound(Gdx.files.internal("sounds/321.wav"));
		go = Gdx.audio.newSound(Gdx.files.internal("sounds/go.wav"));
		backMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/drumstylin.mp3"));
		backMusic.setLooping(true);
		
		if(musicOn){
			backMusic.play();
		}
		if(musicOn == false){
			backMusic.stop();
		}
		
		Gdx.input.setCatchBackKey(true);
		
		vibroOn = prefs.getBoolean("Vibro",true);
		
		setScreen(new MainMenuScreen(this));
		
	}

	@Override
	public void dispose() {
		menuBack.dispose();
		buttonTxt.dispose();
		gameBack.dispose();
		blueRacketTxt.dispose();
		redRacketTxt.dispose();
		greenBallTxt.dispose();
		puff.dispose();
		tto.dispose();
		go.dispose();
		backMusic.dispose();
		
		font.dispose();
		super.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		super.setScreen(screen);
	}

	@Override
	public Screen getScreen() {
		// TODO Auto-generated method stub
		return super.getScreen();
	}

	@Override
	public void render () {
		super.render();
	}
}
