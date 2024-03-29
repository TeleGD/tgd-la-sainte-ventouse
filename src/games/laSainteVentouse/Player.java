package games.laSainteVentouse;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

import games.laSainteVentouse.Tetris;
import games.laSainteVentouse.World;

public class Player {

	private World world;
	private double x ;
	private double y ;
	private double jumpSpeed;
	private double speedX;
	private double speedY;
	private double gravity;
	private boolean rightPress;
	private boolean leftPress;
	private boolean isInJump;
	private boolean canMoveRight;
	private boolean canMoveLeft;
	private boolean jumpReleased;
	private boolean collapseOn;
	private ArrayList<Tetris> tetrisList;
	private Shape hitBoxChar ;
	private Image image;


	public Player (World world, int x,  int y, Shape hitBoxChar){
		this.world = world;
		this.x=x;
		this.y=y;
		rightPress = false;
		collapseOn = false;
		leftPress = false;
		isInJump = false;
		canMoveRight = true;
		canMoveLeft = true;
		jumpReleased = true;
		gravity=0.005;
		speedX=0.1;
		speedY=0.1;
		jumpSpeed=1;
		this.hitBoxChar=hitBoxChar;
		image=AppLoader.loadPicture("/images/laSainteVentouse/player.png");
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) {
		arg2.setColor(Color.green);
		arg2.drawImage(image, (float)(x-image.getWidth()/2), (float)(y-image.getHeight()/2));
		arg2.draw(this.hitBoxChar);

	}



	public void update(GameContainer arg0, StateBasedGame arg1, int delta) {
		speedY = speedY - gravity*delta;
		move(delta);
		collapse(delta);



	}

	public void collapse(int delta) {
		collapseOn = false;
		if (this.x<=100 && this.y<=420 && this.y>=385){
			this.y = 385;
			this.hitBoxChar.setX((float)this.x-10);
			this.hitBoxChar.setY((float)this.y-16);
			collapseOn=true;
			isInJump = false;
		}
		if (this.y>=556 && this.x<=100){
			this.x = 116;
			this.hitBoxChar.setX((float)this.x-10);
			this.hitBoxChar.setY((float)this.y-16);
			collapseOn=true;
			canMoveLeft = false;
			leftPress=false;
		}
		if (this.y>=572){
			this.y = 556;
			this.hitBoxChar.setX((float)this.x-10);
			this.hitBoxChar.setY((float)this.y-16);
			collapseOn=true;
			isInJump = false;
		}
		if (this.y>=556 && this.x >=980){
			this.x = 964;
			this.hitBoxChar.setX((float)this.x-10);
			this.hitBoxChar.setY((float)this.y-16);
			collapseOn=true;
			canMoveRight = false;
			rightPress = false;
		}
		if (this.x>=980 && this.y<=420 && this.y>=385){
			this.y = 385;
			this.hitBoxChar.setX((float)this.x-10);
			this.hitBoxChar.setY((float)this.y-16);
			collapseOn=true;
			isInJump = false;
		}
		tetrisList = this.world.getTetrisList();

		for (int k=0; k < tetrisList.size(); k++){
			if (Math.abs(tetrisList.get(k).getXcentre()-this.x)<32){
				for (int i=0; i < tetrisList.get(k).getMatrice().length ; i++){
					for (int j=0; j < tetrisList.get(k).getMatrice()[i].length ; j++){
						if (tetrisList.get(k).getMatrice()[i][j] == null)continue;
						if(this.hitBoxChar.intersects(tetrisList.get(k).getMatrice()[i][j].getHitbox())==true){
							collapseOn = true;

							if (rightPress == true ){
								System.out.println("zozo");
								this.x = this.x - speedX*delta ;
								this.hitBoxChar.setX((float)this.x-10);
								this.hitBoxChar.setY((float)this.y-16);
								canMoveRight = false;
								canMoveLeft = true;
								rightPress = false;
								speedX=0;
							}
							if (leftPress == true ){
								System.out.println("momo");
								this.x = this.x + speedX*delta ;
								this.hitBoxChar.setX((float)this.x-10);
								this.hitBoxChar.setY((float)this.y-16);
								canMoveLeft = false;
								canMoveRight = true;
								leftPress = false;
								speedX=0;
							}
							if (speedY > 0){
								System.out.println("coco");
								this.y = this.y - speedY*delta ;
								this.hitBoxChar.setX((float)this.x-10);
								this.hitBoxChar.setY((float)this.y-16);
								isInJump = false;
								speedY=0;
							}
							if (speedY < 0){
								System.out.println("toto");
								this.y = this.y + speedY*delta ;
								this.hitBoxChar.setX((float)this.x-10);
								this.hitBoxChar.setY((float)this.y-16);
								speedY=0;
							}
						}

					}
				}
			}

		}
		if (collapseOn == false){
			isInJump = true;
			canMoveLeft = true;
			canMoveRight = true;
		}
	}


	public void keyPressed (int key, char c) {

			if (key == Input.KEY_RIGHT && leftPress == false ) {
				rightPress = true;
			}

			if (key == Input.KEY_LEFT && rightPress == false ) {
				leftPress = true;
			}

			if (key == Input.KEY_SPACE && isInJump == false && jumpReleased == true ) {
				speedY = jumpSpeed;
				isInJump = true;
			}

		}

	public void keyReleased(int key, char c) {

		if (key == Input.KEY_RIGHT) {
			rightPress = false;
	}
		if (key == Input.KEY_LEFT) {
			leftPress = false;
	}
		if (key == Input.KEY_SPACE){
			jumpReleased = true;
		}
		}

	public void move(double delta) {
			if (rightPress == true && x<1064 && canMoveRight == true) {
				this.x = this.x + speedX*delta;
			}
			if (leftPress == true && x>0 && canMoveLeft == true) {
				this.x = this.x - speedX*delta;
			}
			if (isInJump == true && y>=0 && y<720  ){
				this.y = this.y - speedY*delta;
		}
			this.hitBoxChar.setX((float)this.x-10);
			this.hitBoxChar.setY((float)this.y-16);

	}
}
