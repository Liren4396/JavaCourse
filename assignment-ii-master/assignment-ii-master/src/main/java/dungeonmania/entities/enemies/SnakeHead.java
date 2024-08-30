package dungeonmania.entities.enemies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Wall;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeHead extends Enemy {
    private List<SnakeBody> body;
    private int length;
    private double snakeAttackArrowBuff;
    private double snakeHealthTreasureBuff;
    private double snakeHealthKeyBuff;
    private int flag;

    public SnakeHead(Position position, double health, double attack, double treasureBuff, double keyBuff,
            double arrowBuff) {
        super(position, health, attack);
        this.length = 0;
        this.body = new ArrayList<>();
        this.snakeAttackArrowBuff = arrowBuff;
        this.snakeHealthKeyBuff = keyBuff;
        this.snakeHealthTreasureBuff = treasureBuff;
        this.flag = 0;
    }

    public SnakeHead(Position position, double health, double attack, double treasureBuff, double keyBuff,
            double arrowBuff, List<SnakeBody> snakeBodies, int flag) {
        super(position, health, attack);
        this.length = 0;
        this.body = snakeBodies;
        this.snakeAttackArrowBuff = arrowBuff;
        this.snakeHealthKeyBuff = keyBuff;
        this.snakeHealthTreasureBuff = treasureBuff;
        this.flag = flag;

    }

    public void setBody(List<SnakeBody> body) {
        this.body = body;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Entity findClosestEntity(List<Entity> entities) {
        Entity closestEntity = null;
        int minDistance = Integer.MAX_VALUE;
        Position snakePosition = this.getPosition();

        for (Entity entity : entities) {
            if (entity instanceof Treasure || entity instanceof Arrow || entity instanceof Potion
                    || entity instanceof Key) {
                Position entityPosition = entity.getPosition();

                int distance = Math.abs(entityPosition.getX() - snakePosition.getX())
                        + Math.abs(entityPosition.getY() - snakePosition.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        return closestEntity;
    }

    public void checkHeadWithPlayerPositon(GameMap map, Position curr) {
        if (curr.equals(map.getPlayer().getPosition())) {

            System.out.println("get snake");
            for (int i = 0; i < length; i++) {
                map.destroyEntity(body.get(i));
                (body.get(i)).onDestroy(map);
            }
            body.clear();
            length = 0;
            map.destroyEntity(this);
            super.onDestroy(map);
        }
    }

    @Override
    public void onDestroy(GameMap map) {
        for (int i = 0; i < length; i++) {
            map.destroyEntity(body.get(i));
            (body.get(i)).onDestroy(map);
        }
        body.clear();
        length = 0;
        //map.destroyEntity(this);
        super.onDestroy(map);
    }

    public void cutBodyInvinPotion(Game game, BattleStatistics battleStatistics) {
        GameMap map = game.getMap();
        Position playerPos = map.getPlayer().getPosition();
        Iterator<SnakeBody> iterator = body.iterator();
        int snakeNextPos = -1;
        while (iterator.hasNext()) {
            SnakeBody snakebody = iterator.next();
            int i = body.indexOf(snakebody);
            if (snakebody.getPosition().equals(playerPos)) {
                snakeNextPos = i + 1;
                map.destroyEntity(snakebody);
                iterator.remove();
            }
        }

        if (snakeNextPos >= 0 && snakeNextPos < length) {
            List<SnakeBody> secondBody = new ArrayList<>(body.subList(snakeNextPos, length - 1));

            SnakeHead newHead = new SnakeHead(body.get(snakeNextPos - 1).getPosition(), battleStatistics.getHealth(),
                    battleStatistics.getAttack(), snakeHealthTreasureBuff, snakeHealthKeyBuff, snakeAttackArrowBuff,
                    secondBody, this.flag);
            newHead.setLength(secondBody.size());
            map.addEntity(newHead);
            game.register(() -> newHead.move(game), Game.AI_MOVEMENT, newHead.getId());
            length = snakeNextPos - 1;
        }

    }

    public void cutBody(Game game, BattleStatistics battleStatistics) {
        GameMap map = game.getMap();
        Position playerPos = map.getPlayer().getPosition();

        Iterator<SnakeBody> iterator = body.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            SnakeBody snakebody = iterator.next();

            if (snakebody.getPosition().equals(playerPos)) {
                // Determine the index of the snakebody to be cut
                int snakeIndex = index;
                int newLength = snakeIndex;

                // Remove snake bodies starting from the found position
                while (iterator.hasNext()) {
                    SnakeBody currentBody = iterator.next();
                    map.destroyEntity(currentBody);
                    iterator.remove();
                }

                // Adjust the length of the snake
                length = newLength;
                break; // Exit the loop after the cut
            }
            index++;
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (getInvisiblePotion()) {
            return (entity instanceof Player) || (entity instanceof Wall) || (entity instanceof SnakeHead)
                    || (entity instanceof SnakeBody && !body.contains(entity));
        } else {
            return entity instanceof Player;
        }

    }

    @Override
    public void move(Game game) {
        GameMap map = game.getMap();
        Entity closesEntity = findClosestEntity(map.getEntities());
        //checkHeadWithPlayerPositon(map, this.getPosition());
        if (this.flag == 1) {
            cutBodyInvinPotion(game, getBattleStatistics());
        } else {
            cutBody(game, getBattleStatistics());
        }

        if (closesEntity == null) {
            // hibernation, wont move
            return;
        } else {
            Position nextPos = map.dijkstraPathFind(this.getPosition(), closesEntity.getPosition(), this);
            Position oldPos = this.getPosition();

            // find snake tail pos
            Position tailPos;
            if (length == 0) {
                tailPos = oldPos;
            } else {
                tailPos = body.get(length - 1).getPosition();
            }

            map.moveTo(this, nextPos);
            Position dest = oldPos;
            for (int i = 0; i < length; i++) {
                SnakeBody src = body.get(i);
                Position prevPos = src.getPosition();
                map.moveTo(src, dest);
                dest = prevPos;
            }

            checkHeadWithPlayerPositon(map, nextPos);

            // eat food
            if (nextPos.equals(closesEntity.getPosition())) {
                BattleStatistics battleStatistics = this.getBattleStatistics();
                SnakeBody newBody = new SnakeBody(tailPos, battleStatistics.getHealth(), battleStatistics.getAttack());
                body.add(length, newBody);
                map.addEntity(newBody);
                this.length++;
                if (closesEntity instanceof Treasure) {
                    battleStatistics.setHealth(battleStatistics.getHealth() + snakeHealthTreasureBuff);
                } else if (closesEntity instanceof Key) {
                    battleStatistics.setHealth(battleStatistics.getHealth() * snakeHealthKeyBuff);
                } else if (closesEntity instanceof Arrow) {
                    battleStatistics.setAttack(battleStatistics.getAttack() + snakeAttackArrowBuff);
                } else if (closesEntity instanceof InvisibilityPotion) {
                    setInvisiblePotion(true);
                } else if (closesEntity instanceof InvincibilityPotion) {
                    this.flag = 1;
                }

                map.destroyEntity(closesEntity);
            }

        }
    }
}
