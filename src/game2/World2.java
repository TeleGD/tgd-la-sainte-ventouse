package game2;

import general.ui.Button;
import general.ui.TGDComponent;
import general.utils.FontUtils;
import menus.MainMenu;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.util.*;

public class World2 extends BasicGameState {
	
	public static int ID=77;
	public static String name = "Tetris PolyBridge";
	private Player player;
	private Dieu dieu;
	
	private static Shape shape1;
	private static Shape shape2;
	private static Shape shape3;
	
	private static ArrayList<Tetris> tetrisList;
	private ArrayList<Cloud> cloudList;
	
	private int time;
	
	private Image fond;
	private String urlFond = "images/TetrisPolyBridge/background.png";
	
	private Cloud cloud;
	

    @Override
    public void init(final GameContainer container, final StateBasedGame game) throws SlickException {
    	player = new Player(45,400 - 16, new Rectangle(6, 0, 20, 32));
    	dieu = new Dieu();
    	
    	tetrisList = new ArrayList<Tetris>();
    	cloudList = new ArrayList<Cloud>();
    	
    	time = 0;
    	fond = new Image(urlFond);
    	
    	
    	shape1 = new Rectangle(0,400, 100, 720);
    	shape2 = new Rectangle(980, 400, 1080, 720);
    	shape3 = new Rectangle(100, 572, 980, 720);
    	
    	cloud = new Cloud();
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    	//fond avec image
    	g.drawImage(fond, 0, 0);
    	
    	//les trucs
    	cloud.render(container, game, g);
    	dieu.render(container, game, g);
    	player.render(container, game, g);
    	
    	for(Tetris u:tetrisList){
    		u.render(container, game, g);
    	}

    	g.setColor(Color.black);
    	g.fillRect(1080, 0, 1280, 720);
    	g.setColor(Color.white);
    	g.drawString("Time : " + ((time/1000)/60)/60 + " h " + (time/1000)/60 + " min " + ((time/1000)%60)%60  + " s", 1085, 100);
    	
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	if(cloud.getPosX()<1080){
    		cloud.update(container, game, delta);
    	}
    	
    	
    	
    	dieu.update(container, game, delta);
    	player.update(container, game, delta);
    	time += delta;
    }
    
    public static void reset() {

    }

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	public void keyPressed(int key, char c){
		dieu.keyPressed(key, c);
		player.keyPressed(key, c);
	}
	
	public void keyReleased(int key, char c){
		dieu.keyReleased(key, c);
		player.keyReleased(key, c);
	}
	
	public static ArrayList<Tetris> getTetrisList(){
		return tetrisList;
	}
	
	public static void addTetrisList(Tetris tet){
		tetrisList.add(tet);
	}

	public static void setTetrisList(ArrayList<Tetris> tetList){
		tetrisList = tetList;
	}
	
	public static Shape getShape1(){
		return shape1;
	}
	
	public static Shape getShape2(){
		return shape2;
	}
	
	public static Shape getShape3(){
		return shape3;
	}

}
