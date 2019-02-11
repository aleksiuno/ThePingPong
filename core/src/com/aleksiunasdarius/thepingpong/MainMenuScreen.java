package com.aleksiunasdarius.thepingpong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen, InputProcessor {
	
	private ThePingPong game;
	
	private final int BUTTON_COORD_X = 8;
	private final int OP_BUTTON_COORD_Y = 38;
	private final int TP_BUTTON_COORD_Y = 27;
	private final int OPT_BUTTON_COORD_Y = 16;
	private final int HS_BUTTON_COORD_Y = 5;
	
	private final int FONT_INCREMENT = 9;
	
	private boolean buttonDown = false;
	private int buttonDownY;
	
	public MainMenuScreen(ThePingPong game) {
		
		this.game = game;
		Gdx.input.setInputProcessor(this);

	}

	public void Screen(){
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.setProjectionMatrix(game.camera.combined);
		game.batch.begin();
		
		game.batch.draw(game.menuBack, 0, 0);
		game.batch.draw(game.buttonTxt, BUTTON_COORD_X, OP_BUTTON_COORD_Y);
		game.batch.draw(game.buttonTxt, BUTTON_COORD_X, TP_BUTTON_COORD_Y);
		game.batch.draw(game.buttonTxt, BUTTON_COORD_X, OPT_BUTTON_COORD_Y);
		game.batch.draw(game.buttonTxt, BUTTON_COORD_X, HS_BUTTON_COORD_Y);
		
		if(buttonDown){
		game.batch.draw(game.buttonDownTxt, BUTTON_COORD_X, buttonDownY);
		}
		
		game.font.draw(game.batch, "1 player", BUTTON_COORD_X + 10, OP_BUTTON_COORD_Y + FONT_INCREMENT);
		game.font.draw(game.batch, "2 player", BUTTON_COORD_X + 7, TP_BUTTON_COORD_Y + FONT_INCREMENT);
		game.font.draw(game.batch, "options", BUTTON_COORD_X + 12, OPT_BUTTON_COORD_Y + FONT_INCREMENT);
		game.font.draw(game.batch, "highscores", BUTTON_COORD_X + 4, HS_BUTTON_COORD_Y + FONT_INCREMENT);
		
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK){
			Gdx.app.exit();
	        }
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 touchVect = new Vector3(screenX, screenY, 0);
		game.camera.unproject(touchVect);
		
		if (touchVect.x > BUTTON_COORD_X && touchVect.x < BUTTON_COORD_X + game.buttonTxt.getWidth()){
			if(touchVect.y > OP_BUTTON_COORD_Y && touchVect.y < OP_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				buttonDown = true;
				buttonDownY = OP_BUTTON_COORD_Y;
			}
			if(touchVect.y > TP_BUTTON_COORD_Y && touchVect.y < TP_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				buttonDown = true;
				buttonDownY = TP_BUTTON_COORD_Y;
			}
			if(touchVect.y > OPT_BUTTON_COORD_Y && touchVect.y < OPT_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				buttonDown = true;
				buttonDownY = OPT_BUTTON_COORD_Y;
			}
			if(touchVect.y > HS_BUTTON_COORD_Y && touchVect.y < HS_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				buttonDown = true;
				buttonDownY = HS_BUTTON_COORD_Y;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		buttonDown = false;
		
		Vector3 touchVect = new Vector3(screenX, screenY, 0);
		game.camera.unproject(touchVect);
		
		if (touchVect.x > BUTTON_COORD_X && touchVect.x < BUTTON_COORD_X + game.buttonTxt.getWidth()){
			if(touchVect.y > OP_BUTTON_COORD_Y && touchVect.y < OP_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				game.setScreen(new OnePlayerMenuScreen(game));
				dispose();
			}
			if(touchVect.y > TP_BUTTON_COORD_Y && touchVect.y < TP_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				game.setScreen(new TwoPlayerScreen(game, 50, 20));
				dispose();
			}
			if(touchVect.y > OPT_BUTTON_COORD_Y && touchVect.y < OPT_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				game.setScreen(new Options(game));
				dispose();
			}
			if(touchVect.y > HS_BUTTON_COORD_Y && touchVect.y < HS_BUTTON_COORD_Y + game.buttonTxt.getHeight()){
				game.setScreen(new HighScoreScreen(game));
				dispose();
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
