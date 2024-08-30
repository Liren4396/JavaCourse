package dungeonmania.entities.enemies.moveStrategy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class InvisibilityPotionMove implements MoveStrategy {
    private Random randGen = new Random();
    public InvisibilityPotionMove() {
    }

    @Override
    public void move(Enemy enemy, GameMap map) {
        List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        Position nextPos;
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }
        map.moveTo(enemy, nextPos);
    }
    
}
