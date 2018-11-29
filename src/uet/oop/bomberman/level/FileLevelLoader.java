package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.net.URL;


public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;

	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}

	@Override
	public void loadLevel(int level) {
		//  đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		//  cập nhật các giá trị đọc được vào _width, _height, _level, _map

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("./levels/Level"+level+".txt");
		String path = url.getPath();

		System.out.println(path);


		String line = "";
		try {
			FileReader fileReader =
					new FileReader(path);

			BufferedReader bufferedReader =
					new BufferedReader(fileReader);

			line = bufferedReader.readLine();

			String data[]=line.split(" ");

			int curLevel = Integer.parseInt(data[0]);
			int height = Integer.parseInt(data[1]);
			int width = Integer.parseInt(data[2]);

			char map[][] = new char[height][width];

			for (int i = 0; i < height; i++) {
				line = bufferedReader.readLine();
				for (int j = 0; j < width; j++) {
					map[i][j] = line.charAt(j);
					System.out.print(map[i][j]);
				}
				System.out.println("");
			}

			_map = map;
			_width = width;
			_height = height;
			_level = curLevel;

			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createEntities() {
		//  tạo các Entity của màn chơi
		//  sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game
		//  phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		//  hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall
		System.out.println(getWidth());
		System.out.println(getHeight());

		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int pos = x + y * getWidth();
				Sprite sprite = Sprite.grass;
				switch (_map[y][x]) {
					case '#':
						_board.addEntity(pos, new Wall(x, y, Sprite.wall));
						break;
					case 'b':
						LayeredEntity layer = new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new BombItem(x,y,sprite.powerup_bombs),
								new Brick(x ,y, Sprite.brick)
								);


//						if(_board.isPowerupUsed(x, y, _level) == false) {
//							layer.addBeforeTop(new PowerupBombs(x, y, _level, Sprite.powerup_bombs));
//						}

						_board.addEntity(pos, layer);
						break;
					case 's':
						layer = new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new SpeedItem(x,y,sprite.powerup_speed),
								new Brick(x ,y, Sprite.brick)

						);

//						if(_board.isPowerupUsed(x, y, _level) == false) {
//							layer.addBeforeTop(new PowerupSpeed(x, y, _level, Sprite.powerup_speed));
//						}

						_board.addEntity(pos, layer);
						break;
					case 'f':
						layer = new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new FlameItem(x,y,sprite.powerup_flames),
								new Brick(x ,y, Sprite.brick));

//						if(_board.isPowerupUsed(x, y, _level) == false) {
//							layer.addBeforeTop(new PowerupFlames(x, y, _level, Sprite.powerup_flames));
//						}

						_board.addEntity(pos, layer);
						break;
					case '*':
						_board.addEntity(pos, new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Brick(x ,y, Sprite.brick)) );
						break;
					case 'x':
						_board.addEntity(pos, new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Portal(x ,y, Sprite.portal,_board),
								new Brick(x ,y, Sprite.brick)) );
						break;
					case ' ':
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case 'p':
						_board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
						Screen.setOffset(0, 0);

						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					//Enemies
					case '1':
						_board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case '2':
						_board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
//					case '3':
//						_board.addCharacter( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
//						_board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
//						break;
//					case '4':
//						_board.addMob( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
//						_board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
//						break;
					case '5':
						_board.addCharacter( new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					default:
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
				}
			}
		}

		// thêm Bomber


		// thêm Enemy


		// thêm Brick

		// thêm Item kèm Brick che phủ ở trên

	}

}