package com.aleksiunasdarius.thepingpong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SurvivalScreen implements Screen, InputProcessor {

	ThePingPong game;
	
	private boolean twoSound = true;
	private boolean oneSound = true;
	private boolean zeroSound = true;
	
	private boolean gameRunning = true;
	private boolean lrMiss = false;
	private boolean rrMiss = false;

	private boolean isCountDown = true;
	private float countDown = 3;

	private float lrX = 9;
	private float lrY = 19;

	private float rrX = 70;
	private float rrY = 19;
	private int score = 0;

	private float bX = 39;
	private float bY = 23;
	private Vector2 ballDirection = new Vector2(1, 0);
	private float ballSpeed = 40;
	private float ballSpeedIncrement = 2;

	public SurvivalScreen(ThePingPong game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(game.camera.combined);
		game.batch.begin();
		game.batch.draw(game.gameBack, 0, 0);


		game.rrFont.draw(game.batch, "Score:" + score, 1, 48);


		if (gameRunning) {
			game.redRacket.setPosition(lrX, lrY);
			game.blueRacket.setPosition(rrX, rrY);
			game.greenBall.setPosition(bX, bY);

			game.redRacket.draw(game.batch);
			game.blueRacket.draw(game.batch);
			game.greenBall.draw(game.batch);

			//game.font.draw(game.batch,
					//"fps:" + Gdx.graphics.getFramesPerSecond(), 1, 8);

			if(isCountDown){
				countDown -= 1 * Gdx.graphics.getDeltaTime();
				if(countDown>2 && twoSound){
					if(game.soundOn)game.tto.play();
					twoSound = false;
				}
				else if(countDown>1 &&countDown<2 && oneSound){
					if(game.soundOn)game.tto.play();
					oneSound = false;
				}
				else if(countDown<1 && zeroSound){
					if(game.soundOn)game.tto.play();
					zeroSound = false;
				}
				if(countDown <= 0){
					twoSound = true;
					oneSound = true;
					zeroSound = true;
					
					isCountDown = false;
					countDown = 3;
					if(game.soundOn)game.go.play();
				}

				game.countDownFont.draw(game.batch, "" + (int)countDown , 30, 42);
			}
			else{
				ballMovementX();
				ballMovementY();
			}			

		}

		else if (lrMiss) {
			game.font.draw(game.batch, "missed!!!", 23, 18);
		} else if (rrMiss) {
			game.font.draw(game.batch, "missed!!!", 23, 18);
		}

		game.batch.end();
		
		ballTrackingRacket();
		handleInput();
	}

	private void handleInput() {
		for (int i = 0; i < 5; i++) {
			if (Gdx.input.isTouched(i)) {
				int xTouch = Gdx.input.getX(i);
				int yTouch = Gdx.input.getY(i);

				Vector3 touchVect = new Vector3(xTouch, yTouch, 0);
				game.camera.unproject(touchVect);

				if (touchVect.x > 50) {
					if (touchVect.y <= game.blueRacket.getHeight() / 2) {
						rrY = 0;
					} else if (touchVect.y >= game.SCREEN_HEIGHT
							- (game.blueRacket.getHeight() / 2)) {
						rrY = game.SCREEN_HEIGHT - game.blueRacket.getHeight();
					} else {
						rrY = touchVect.y - (game.blueRacket.getHeight() / 2);
					}
				}
			}
		}

		if (Gdx.input.justTouched()) {
			if(lrMiss || rrMiss){
				lrMiss = false;
				rrMiss = false;
				gameRunning = true;
				isCountDown = true;
				
				//Saving Scores
				if(Integer.parseInt(game.prefs.getString("highScore1","0"))<score){
				game.prefs.putString("highScore1", ""+score);
				game.prefs.flush();				
				}
				score = 0;
				ballSpeed = 50;
			}
		}

	}

	private void ballMovementX() {

		bX = bX + (ballSpeed * ballDirection.x * Gdx.graphics.getDeltaTime());

		if (bX >= game.SCREEN_WIDTH - game.greenBall.getWidth()) {
			gameRunning = false;
			rrMiss = true;
			bX = 39;
			bY = 23;
			ballDirection = new Vector2(1, 0);
			if(game.vibroOn)Gdx.input.vibrate(1000);
		}
		if (bX <= 0) {
			gameRunning = false;
			lrMiss = true;
			bX = 39;
			bY = 23;
			ballDirection = new Vector2(-1, 0);
		}
	}

	private void ballMovementY() {

		bY = bY + (ballSpeed * ballDirection.y * Gdx.graphics.getDeltaTime());
		if (bY >= game.SCREEN_HEIGHT - game.greenBall.getHeight()
				&& ballDirection.angle() < 180) {
			ballDirection.y = -ballDirection.y;
			if(game.vibroOn)Gdx.input.vibrate(game.VIBRO_LENGTH);
			if(game.soundOn)game.puff.play();
			
			
		}
		if (bY <= 0 && ballDirection.angle() > 180) {
			ballDirection.y = -ballDirection.y;
			if(game.vibroOn)Gdx.input.vibrate(game.VIBRO_LENGTH);
			if(game.soundOn)game.puff.play();
			
		}
		// tracks if ball touches left racket
		if ((int) bX <= lrX && (int) bX >= lrX - 2 && bY + game.greenBall.getHeight() >= lrY
				&& bY <= lrY + game.redRacket.getHeight()) 
		{
			ballDirection.setAngle((float) (-35 + (Math.random() * 70)));
			ballSpeed += ballSpeedIncrement;
			if(game.vibroOn)Gdx.input.vibrate(game.VIBRO_LENGTH);
			if(game.soundOn)game.puff.play();
			
		}
		// tracks if ball touches right racket
		if ((int) bX + game.greenBall.getWidth() >= rrX
				&&(int) bX + game.greenBall.getWidth() <= rrX + 2				
				&& bY + game.greenBall.getHeight() >= rrY
				&& bY <= rrY + game.blueRacket.getHeight()) 
		{
			score++;
			ballDirection.setAngle((float) (-35 + (Math.random() * 70)));
			ballDirection.x = -ballDirection.x;
			if(game.vibroOn)Gdx.input.vibrate(game.VIBRO_LENGTH);
			if(game.soundOn)game.puff.play();			
			
		}

	}

	private void ballTrackingRacket() {
		if(bY > game.redRacket.getHeight()/2 && bY < game.SCREEN_HEIGHT - game.redRacket.getHeight()/2){
			lrY = bY - (game.redRacket.getHeight()/2) + (game.greenBall.getHeight()/2);
		}
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
		if (keycode == Keys.BACK) {
			game.setScreen(new MainMenuScreen(game));
			dispose();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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
