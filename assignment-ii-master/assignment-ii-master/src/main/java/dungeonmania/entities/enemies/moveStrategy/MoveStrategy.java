package dungeonmania.entities.enemies.moveStrategy;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;

public interface MoveStrategy {
    void move(Enemy enemy, GameMap map);
}
