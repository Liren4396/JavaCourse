package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        switch (jsonGoal.getString("goal")) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            AndGoal andGoal = new AndGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
            return new Goal(andGoal);
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            OrGoal orGoal = new OrGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
            return new Goal(orGoal);
        case "exit":
            return new Goal(new ExitGoal());
        case "boulders":
            return new Goal(new BouldersGoal());
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new Goal(new TreasureGoal(treasureGoal));
        case "enemies":
            int enemyTarget = config.getInt("enemy_goal");
            return new Goal(new EnemyGoal(enemyTarget));
        default:
            return null;
        }
    }
}
