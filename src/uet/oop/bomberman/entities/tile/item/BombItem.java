package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {

	public BombItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	boolean check = true;
	@Override
	public boolean collide(Entity e) {
		// xử lý Bomber ăn Item
		if(e instanceof Bomber) {
			if(check==true)
			{
				remove();
				Game.addBombRate(1);
				Thread t = new Thread(new Sound(Action.itemGet, false));
				t.start();
				System.out.println("Số lượng bom: "+Game.getBombRate());
			}
			check = false;
			return true;
		}

		return false;
	}

}
