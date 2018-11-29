package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Portal extends Tile {
	protected Board _board;
	public Portal(int x, int y, Sprite sprite, Board _board) {
		super(x, y, sprite);
		this._board = _board;
	}

	@Override
	public boolean collide(Entity e) {
		//  xử lý khi Bomber đi vào
		if( e instanceof Bomber) {
			if( _board.detectNoEnemies() ) {
				_board.nextLevel();
				Thread t = new Thread(new Sound(Action.pass, false));
				t.start();
				return true;
			}
			else return true;
		}
		if( e instanceof Enemy) {
			return true;
		}
		if( e instanceof Flame) {
			int xE = (int)_x;
			int yE = (int)_y;
			int _width = _board.getWidth();
			for ( int i = 0; i< 4; i++) {
				_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
				_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
			}
			_board.addEntity( xE + yE * _width, new Portal(xE, yE, Sprite.portal,_board));
		}
		return false;
	}

}