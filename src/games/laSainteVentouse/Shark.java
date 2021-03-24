package games.laSainteVentouse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import app.AppLoader;

public class Shark {
	private int posx;
	private int posy;
	private int sens;
	private double speed;

	private Image shark;
	private Image shark1;
	private Image shark2;
	private String urlShark1 = "/images/laSainteVentouse/requin1.png";
	private String urlShark2 = "/images/laSainteVentouse/requin2.png";

	public Shark(int x, int dir) {
		posx = x;
		posy = 652-32;
		sens = dir;
		speed = 0.05+Math.random()*0.2;

		shark1 = AppLoader.loadPicture(urlShark1);
		shark2 = AppLoader.loadPicture(urlShark2);

		if(dir == 1){
			shark = shark1;
		}else{
			shark = shark2;
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if(sens == 1){
			posx += delta*speed;
			if(posx>980-32){
				sens = 1 - sens;
				shark = shark2;
			}
		}else{
			posx -= delta*speed;
			if(posx < 100){
				sens = 1 - sens;
				shark = shark1;
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.drawImage(shark,posx,posy);
	}

	public int getPosX(){
		return posx;
	}

	public int getPosy(){
		return posy;
	}
}
