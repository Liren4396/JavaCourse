package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal implements GoalStrategy {
    private int enemyTarget;

    public EnemyGoal(int enemyTarget) {
        this.enemyTarget = enemyTarget;
    }

    @Override
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;
        int numOfEnemy = game.getPlayer().getDefeatedEnemy();
        int numOfSpawner = game.getSpawners();
        int defeatedSpawners = game.getPlayer().getDefeatedSpawners();
        return (numOfEnemy >= enemyTarget) && (defeatedSpawners >= numOfSpawner);
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return ":enemies";
    }

}
