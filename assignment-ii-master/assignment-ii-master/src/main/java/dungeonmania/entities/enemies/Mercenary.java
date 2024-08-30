package dungeonmania.entities.enemies;

import dungeonmania.entities.enemies.moveStrategy.InvincibilityPotionMove;
import dungeonmania.entities.enemies.moveStrategy.InvisibilityPotionMove;
import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;
    private String mindControl;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        double distance = getManhattanDistance(player.getPosition(), getPosition());
        if (player.countEntityOfType(Sceptre.class) >= 1) {
            return true;
        } else {
            return bribeRadius >= distance && player.countEntityOfType(Treasure.class) >= bribeAmount;
        }
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    public void checkAdjacentToplayer(Position pos1, Position pos2) {
        if (!isAdjacentToPlayer && Position.isAdjacent(pos1, pos2))
            isAdjacentToPlayer = true;
    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        if (player.countEntityOfType(Sceptre.class) >= 1) {
            Sceptre sceptre = player.getInventory().hasSceptre();
            this.mindControl = sceptre.getId();
            sceptre.use(game);
        } else {
            bribe(player);
        }

        checkAdjacentToplayer(player.getPosition(), getPosition());
    }

    @Override
    public void move(Game game) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        if (allied) {
            nextPos = isAdjacentToPlayer ? player.getPreviousDistinctPosition()
                    : map.dijkstraPathFind(getPosition(), player.getPosition(), this);
            checkAdjacentToplayer(player.getPosition(), nextPos);
            map.moveTo(this, nextPos);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            // Move random

            setMoveStrategy(new InvisibilityPotionMove());

            //nextPos = moveWithInvincibilityPosion(map);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            setMoveStrategy(new InvincibilityPotionMove());
            //nextPos = moveWithInvincibilityPosion(map);

        } else {
            // Follow hostile
            nextPos = map.dijkstraPathFind(getPosition(), player.getPosition(), this);
            map.moveTo(this, nextPos);
            //setMoveStrategy(new FollowHostileMove(player));
        }
        this.strategyMove(map);

    }

    @Override
    public boolean isInteractable(Player player) {
        int sceptre = player.countEntityOfType(Sceptre.class);
        return ((!allied && canBeBribed(player)) || !allied && sceptre >= 1) ? true : false;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }

    public void setAlly(boolean ally) {
        this.allied = ally;
    }

    public boolean isControlledBySceptre(String sceptreId) {
        return mindControl == sceptreId;
    }

    private static int getManhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
    }
}
