package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Position;

public class SnakeBody extends Enemy {
    public SnakeBody(Position position, double health, double attack) {
        super(position, health, attack);
    }

    public SnakeBody(SnakeBody snakeBody) {
        super(snakeBody.getPosition(), snakeBody.getBattleStatistics().getHealth(),
                snakeBody.getBattleStatistics().getAttack());
    }

    @Override
    public void move(Game game) {
        return;
    }
}
