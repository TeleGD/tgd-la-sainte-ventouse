package menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



public class MainMenu extends Menu{

	public static int ID = -3;	
	
	public MainMenu(){
		super.setTitrePrincipal("CECI EST UN TITRE QU'IL FAUDRA CHANGER AVEC UN MAGNIFIQUE JEU DE MOT !");
		super.setTitreSecondaire("CECI EST LE SOUS TITRE SOUS LE TITRE");
		
		super.setItems(game1.World1.name,game2.World2.name,"Scores", "Quitter");

		super.setEnableClignote(false);
		super.setCouleurClignote(Color.red);
		super.setTempsClignote(400);
	}
	
	@Override
	public void onOptionItemFocusedChanged(int position) {
		time=System.currentTimeMillis();
	}

	@Override
	public void onOptionItemSelected(int position) {
		switch (position) {
		case 0:
			game1.World1.reset();
			game.enterState(game1.World1.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case 1:
			game2.World2.reset();
			game.enterState(game2.World2.ID, new FadeOutTransition(),
					new FadeInTransition());
			break;
		case 2:
			System.out.println("exit");
			System.exit(0);
			break;
		}
	}
	
	@Override
	public int getID() {
		return ID;
	}

}