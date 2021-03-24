package games.laSainteVentouse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Cloud {
	private int posx;
	private int posy;
	private double speed;

	private Image cloud;
	private String urlCloud = "/images/laSainteVentouse/cloud.png";

	public Cloud() {
		cloud = AppLoader.loadPicture(urlCloud);

		posx = 0;
		posy = (int) (50 + Math.random() * 250);
		speed=0.075+Math.random()*0.275;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		posx += delta*speed;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(cloud,posx,posy);
	}

	public int getPosX(){
		return posx;
	}

	public int getPosy(){
		return posy;
	}
}
