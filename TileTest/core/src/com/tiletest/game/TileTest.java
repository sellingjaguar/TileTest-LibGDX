package com.tiletest.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class TileTest extends ApplicationAdapter {

	//sprite batch
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	//grid
	int gridSpace;
	int[][] gridMatrix;

	//main box
	private Sprite box;

	//center position of the main box
	private Sprite centerBox;

	//relative positions to the main box
	private Sprite leftBox;
	private Sprite rightBox;
	private Sprite topBox;
	private Sprite botBox;

	//initialize our objects
	public void create () {

		//sprite batch
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		//camera
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 700);

		//calculate grid box
		gridSpace = 50;

		gridMatrix = new int[(int)Math.ceil(1200.0 / gridSpace)][(int)Math.ceil(700.0 / gridSpace)];

		for (int i = 0; i < gridMatrix.length; i++){
			for (int j = 0; j < gridMatrix[0].length; j++){
				gridMatrix[i][j] = new Random().nextInt(3);
			}
		}

		//set main box sprite to the center of the screen
		box = new Sprite(new Texture(Gdx.files.internal("green_square.png")));
		box.setPosition(camera.viewportWidth/2 - box.getWidth()/2, camera.viewportHeight/2 - box.getHeight()/2);

		//create a sprite for the relative boxes
		centerBox = new Sprite(new Texture(Gdx.files.internal("debugBox.png")));
		leftBox = new Sprite(new Texture(Gdx.files.internal("debugBox.png")));
		rightBox = new Sprite(new Texture(Gdx.files.internal("debugBox.png")));
		topBox = new Sprite(new Texture(Gdx.files.internal("debugBox.png")));
		botBox = new Sprite(new Texture(Gdx.files.internal("debugBox.png")));

	}

	public void render () {

		//clear screen and make the background black
		ScreenUtils.clear(0, 0, 0, 1);

		/* keyboard controls */

		//if the A key is pressed the arrow keys will have the alternative controls
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {

			//rotate to the left
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
				box.setRotation(box.getRotation() + 200 * Gdx.graphics.getDeltaTime());

			//rotate to the right
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				box.setRotation(box.getRotation() - 200 * Gdx.graphics.getDeltaTime());

			//upscale
			if(Gdx.input.isKeyPressed(Input.Keys.UP))
				box.setScale(box.getScaleX() + 1 * Gdx.graphics.getDeltaTime());

			//downscale
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
				box.setScale(box.getScaleX() - 1 * Gdx.graphics.getDeltaTime());

		}
		else
		{
			//position controls
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) box.setX(box.getX() - 200 * Gdx.graphics.getDeltaTime());
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) box.setX(box.getX() + 200 * Gdx.graphics.getDeltaTime());
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) box.setY(box.getY() + 200 * Gdx.graphics.getDeltaTime());
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) box.setY(box.getY() - 200 * Gdx.graphics.getDeltaTime());

		}

		/* position calculations */

		//get the center of the main box (minus the center of the relative box because the start of each box is in the bottom left)
		float centerX = box.getX() + box.getWidth()/2 - centerBox.getWidth()/2;
		float centerY = box.getY() + box.getHeight()/2 - centerBox.getHeight()/2;

		//set the values
		centerBox.setX(centerX);
		centerBox.setY(centerY);
		centerBox.setRotation(box.getRotation());

		//calculate the relative position of the left box based on the distance from the center of the new box
		float leftBoxPosX = centerX - box.getWidth()/2 * box.getScaleX() * MathUtils.cosDeg(box.getRotation());
		float leftBoxPosY = centerY - box.getHeight()/2 * box.getScaleX() * MathUtils.sinDeg(box.getRotation());

		//set the values
		leftBox.setX(leftBoxPosX);
		leftBox.setY(leftBoxPosY);
		leftBox.setRotation(box.getRotation());

		//calculate the relative position of the right box based on the distance from the center of the new box
		float rightBoxPosX = centerX + box.getWidth()/2 * box.getScaleX() * MathUtils.cosDeg(box.getRotation());
		float rightBoxPosY = centerY + box.getHeight()/2 * box.getScaleY() * MathUtils.sinDeg(box.getRotation());

		//set the values
		rightBox.setX(rightBoxPosX);
		rightBox.setY(rightBoxPosY);
		rightBox.setRotation(box.getRotation());

		//calculate the relative position of the right box based on the distance from the center of the new box
		float topBoxPosX= centerX + box.getWidth()/2 * box.getScaleX() * MathUtils.sinDeg(box.getRotation());
		float topBoxPosY = centerY - box.getHeight()/2 * box.getScaleY() * MathUtils.cosDeg(box.getRotation());

		//set the values
		topBox.setX(topBoxPosX);
		topBox.setY(topBoxPosY);
		topBox.setRotation(box.getRotation());

		//calculate the relative position of the right box based on the distance from the center of the new box
		float botBoxPosX = centerX - box.getWidth()/2 * box.getScaleX() * MathUtils.sinDeg(box.getRotation());
		float botPoxPosY = centerY + box.getHeight()/2 * box.getScaleY() * MathUtils.cosDeg(box.getRotation());

		//set the values
		botBox.setX(botBoxPosX);
		botBox.setY(botPoxPosY);
		botBox.setRotation(box.getRotation());

		/* start drawing on screen */

		//render grid boxes
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		//loop trough grid matrix
		for (int i = 0; i < gridMatrix.length; i++){
			for (int j = 0; j < gridMatrix[0].length; j++){

				//if grid value is 0 change to black (background is already black but added it for easy reading purposes)
				if (gridMatrix[i][j] == 0){
					shapeRenderer.setColor(Color.BLACK);
				}

				//if grid value is 1 change to white
				else if (gridMatrix[i][j] == 1){
					shapeRenderer.setColor(Color.WHITE);
				}

				//if grid value is 2 change to gray
				else if (gridMatrix[i][j] == 2){
					shapeRenderer.setColor(Color.GRAY);
				}

				shapeRenderer.rect(gridSpace * i, gridSpace * j, gridSpace, gridSpace);

			}
		}

		shapeRenderer.end();

		//can't have both ShapeRenderer and SpriteBatch opened at the same time

		batch.begin();

		//draw main box
		box.draw(batch);

		//draw relative boxes
		centerBox.draw(batch);
		leftBox.draw(batch);
		rightBox.draw(batch);
		topBox.draw(batch);
		botBox.draw(batch);

		batch.end();

		//can't have both ShapeRenderer and SpriteBatch opened at the same time

		//draw a box below the value display to improve visibility
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.setColor(0,0,0,1);
		shapeRenderer.rect(0, 0, 400, 80);

		shapeRenderer.end();

		//display values on screen
		batch.begin();
		BitmapFont font = new BitmapFont();
		font.setColor(Color.GREEN);

		font.draw(batch, "Move with arrow keys and press A access alternative controls", 10, 75);
		font.draw(batch, "X = " + box.getX() + " | Y = " + box.getY(), 10, 55);
		font.draw(batch, "Rotation: " + box.getRotation() + " degrees (" + box.getRotation() * MathUtils.degreesToRadians + " radians)", 10, 35);
		font.draw(batch, "Scale:    " + box.getScaleX(), 10, 15);

		batch.end();
	}

	public void dispose () {
		batch.dispose();
	}
}
