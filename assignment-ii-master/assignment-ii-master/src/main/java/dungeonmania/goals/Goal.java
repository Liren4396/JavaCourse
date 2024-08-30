package dungeonmania.goals;

import dungeonmania.Game;

public class Goal {
    private GoalStrategy goalStrategy;

    public Goal(GoalStrategy goalStrategy) {
        this.goalStrategy = goalStrategy;
    }

    public Goal(String string, String string2) {
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        return goalStrategy.achieved(game);
    }

    public String toString(Game game) {
        return goalStrategy.toString(game);
    }

}
