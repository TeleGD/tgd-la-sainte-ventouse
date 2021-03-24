package games.laSainteVentouse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Bulle {
	private int posx;
	private int posy;

	private Image bulle;
	private String urlBulle = "/images/laSainteVentouse/bulle.png";

	public Bulle(int x, int y) {
		bulle = AppLoader.loadPicture(urlBulle);

		posx = x;
		posy = 720 - y;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		posy -= 1;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(bulle,posx,posy);
	}

	public int getPosX(){
		return posx;
	}

	public int getPosy(){
		return posy;
	}
}
