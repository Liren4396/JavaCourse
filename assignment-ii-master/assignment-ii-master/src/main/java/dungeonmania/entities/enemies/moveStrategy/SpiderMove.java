package dungeonmania.entities.enemies.moveStrategy;

import java.util.List;

import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SpiderMove implements MoveStrategy {
    private List<Position> movementTrajectory;
    private int nextPositionElement;
    private boolean forward;

    public SpiderMove(List<Position> movementTrajectory) {
        this.movementTrajectory = movementTrajectory;
        this.nextPositionElement = 1;
        this.forward = true;

    }
    private void updateNextPosition() {
        if (forward) {
            nextPositionElement++;
            if (nextPositionElement == 8) {
                nextPositionElement = 0;
            }
        } else {
            nextPositionElement--;
            if (nextPositionElement == -1) {
                nextPositionElement = 7;
            }
        }
    }
    @Override
    public void move(Enemy enemy, GameMap map) {
        Position nextPos = movementTrajectory.get(nextPositionElement);
        List<Entity> entities = map.getEntities(nextPos);
        if (entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder)) {
            forward = !forward;
            updateNextPosition();
            updateNextPosition();
        }
        nextPos = movementTrajectory.get(nextPositionElement);
        entities = map.getEntities(nextPos);
        if (entities == null || entities.size() == 0
                || entities.stream().allMatch(e -> e.canMoveOnto(map, enemy))) {
            map.moveTo(enemy, nextPos);
            updateNextPosition();
        }
    }
    
}
