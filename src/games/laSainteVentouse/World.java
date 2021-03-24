package games.laSainteVentouse;

import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;
import app.ui.Button;
import app.ui.TGDComponent;
import app.ui.TGDComponent.OnClickListener;

public class World extends BasicGameState {

	private static Shape shape1;
	private static Shape shape2;
	private static Shape shape3;

	static {
		World.shape1 = new Rectangle(0,400, 100, 320);
		World.shape2 = new Rectangle(980, 400, 100, 320);
		World.shape3 = new Rectangle(100, 572, 880, 148);
	}

	private int ID;
	private int state;

	private Player player;
	private Dieu dieu;

	private Button jouer,rejouer,quitter;

	private ArrayList<Tetris> tetrisList;
	private ArrayList<Cloud> cloudList;
	private ArrayList<Shark> sharkList;
	private ArrayList<Bulle> bulleList;

	private int time;

	private boolean gameOn;

	private Image fond;
	private String urlFond = "/images/laSainteVentouse/background.png";

	private Audio mainMusic;

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(final GameContainer container, final StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
		mainMusic=AppLoader.loadAudio("/musics/laSainteVentouse/tetris.ogg");

		fond = AppLoader.loadPicture(urlFond);

		jouer = new Button("Jouer",container,350,250,TGDComponent.AUTOMATIC,TGDComponent.AUTOMATIC);
		jouer.setTextSize(32);
		jouer.setBackgroundColor(new Color(255,255,255));
		jouer.setSize(420,140);
		jouer.setTextColor(Color.black);
		jouer.setPadding(70,100,70,100);
		jouer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(TGDComponent componenent) {
				startGame();
			}});

		rejouer = new Button("Rejouer",container,1104,240,TGDComponent.AUTOMATIC,TGDComponent.AUTOMATIC);
		rejouer.setTextSize(20);
		rejouer.setBackgroundColor(new Color(255,255,255));
		rejouer.setSize(150,50);
		rejouer.setTextColor(Color.black);
		rejouer.setPadding(70,100,70,100);
		rejouer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(TGDComponent componenent) {
				initGame();
				startGame();
			}});

		quitter = new Button("Quitter",container,1104,320,TGDComponent.AUTOMATIC,TGDComponent.AUTOMATIC);
		quitter.setTextSize(20);
		quitter.setBackgroundColor(new Color(255,255,255));
		quitter.setSize(150,50);
		quitter.setTextColor(Color.black);
		quitter.setPadding(70,100,70,100);
		quitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(TGDComponent componenent) {
				game.enterState(1 /* Choice */, new FadeOutTransition(), new FadeInTransition());
			}});
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	public void startGame(){
		gameOn = true;
	}

	public void initGame() {
		gameOn = false;
		player = new Player(this, 45,400 - 16, new Rectangle(6, 0, 20, 32));
		dieu = new Dieu(this);
		tetrisList = new ArrayList<Tetris>();
		cloudList = new ArrayList<Cloud>();
		sharkList = new ArrayList<Shark>();
		sharkList.add(new Shark(110, 1));
		sharkList.add(new Shark(400, 1));
		sharkList.add(new Shark(700, 1));
		sharkList.add(new Shark(300, 0));
		sharkList.add(new Shark(500, 0));
		sharkList.add(new Shark(948, 0));
		bulleList = new ArrayList<Bulle>();
		time = 0;
		mainMusic.playAsMusic(1f, .3f, true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		/* Méthode exécutée environ 60 fois par seconde */

		//fond avec image
		g.drawImage(fond, 0, 0);

		g.setColor(Color.black);
		g.fillRect(1080, 0, 1280, 720);

		if (!gameOn){
			jouer.render(container, game, g);
		}



		if (gameOn) {



			//les trucs
			for(Cloud u:cloudList){
				u.render(container, game, g);
			}

			g.setColor(Color.black);
			g.fillRect(1080, 0, 1280, 720);

			rejouer.render(container, game, g);
			quitter.render(container, game, g);

			for(Shark u:sharkList){
				u.render(container, game, g);
			}

			for(Bulle u:bulleList){
				u.render(container, game, g);
			}

			dieu.render(container, game, g);
			player.render(container, game, g);

			for(Tetris u:tetrisList){
				u.render(container, game, g);
			}

			g.draw(shape1);
			g.draw(shape2);
			g.draw(shape3);


			g.setColor(Color.white);
			g.drawString("Time : ", 1160, 65);
			g.drawString(((time/1000)/60)/60 + " h " + (time/1000)/60 + " min " + ((time/1000)%60)%60  + " s", 1130, 90);

			//*
			Tetris next = dieu.getNextBlock();
			next.setXcentre(1150);
			next.setYcentre(600);
			next.rotate(0);
			next.render(container, game, g);
			//*/
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (gameOn && input.isKeyDown(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		if (gameOn){
			for(Cloud u:cloudList){
				if(u.getPosX()>1080){
					cloudList.remove(u);
					break;
				}

			}

			if(cloudList.size()>0){
			 	for(Cloud u:cloudList){
					u.update(container, game, delta);
				}
			}

			if(Math.random() * 1.001 > 0.997 ){
				cloudList.add(new Cloud());
			}

			for(int i = 0; i < bulleList.size(); i++){
				for(Bulle u: bulleList){
					if(u.getPosy()<652){
						bulleList.remove(u);
						break;
					}

				}
			}


			for(Shark u:sharkList){
				u.update(container, game, delta);
			}

			if(bulleList.size()>0){
				for(Bulle u:bulleList){
					u.update(container, game, delta);
				}
			}

			if(Math.random() * 1.001 > 0.9 ){
				bulleList.add(new Bulle((int) (Math.random() * (960 - 120) + 120), 0));
			}

			dieu.update(container, game, delta);
			player.update(container, game, delta);
			time += delta;
		}
	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		this.initGame();
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	@Override
	public void keyPressed(int key, char c){
		if (gameOn){
			dieu.keyPressed(key, c);
			player.keyPressed(key, c);
		}
	}

	@Override
	public void keyReleased(int key, char c){
		if (gameOn){
			dieu.keyReleased(key, c);
			player.keyReleased(key, c);
		}
	}

	public ArrayList<Tetris> getTetrisList(){
		return tetrisList;
	}

	public void addTetrisList(Tetris tet){
		tetrisList.add(tet);
	}

	public void setTetrisList(ArrayList<Tetris> tetList){
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
