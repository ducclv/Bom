package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	boolean check = true;
	@Override
	public boolean collide(Entity e) {
		//  xử lý Bomber ăn Item
		if(e instanceof Bomber) {
			if(check==true)
			{
				Game.addBomberSpeed(0.2);
				remove();
				Thread t = new Thread(new Sound(Action.itemGet, false));
				t.start();
				System.out.println("Tốc độ: "+Game.getBombRate());
			}
			check = false;
			return true;
		}
		return false;
	}
}
