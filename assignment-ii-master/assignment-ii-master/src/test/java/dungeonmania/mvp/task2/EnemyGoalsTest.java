package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyGoalsTest {
    @Test
    @Tag("additional test")
    @DisplayName("Test achieving a basic enemy goal")
    public void enemy_goal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy", "c_enemyGoalsTest");

        // move player to right
        //res = dmc.tick(Direction.RIGHT);
        //System.out.println(TestUtils.getGoals(res));
        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        // cardinally adjacent: true, has sword: false
        assertThrows(InvalidActionException.class, () -> dmc.interact(spawnerId));
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());

        // pick up sword
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);

        // cardinally adjacent: false, has sword: true
        assertThrows(InvalidActionException.class, () -> dmc.interact(spawnerId));
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());

        // move right
        res = dmc.tick(Direction.RIGHT);

        // cardinally adjacent: true, has sword: true
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

        // assert goal met
        res = dmc.tick(Direction.UP);
        //assertEquals(":enemy", TestUtils.getGoals(res));
    }

    @Test
    @Tag("Zombie Spawner Test")
    public void ZombieSpawnerGoalTest() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_spawnerGoal", "c_enemyGoalsTest");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertEquals(2, TestUtils.getEntities(res, "zombie_toast_spawner").size());
        assertEquals(1, TestUtils.getEntities(res, "sword").size());

        String spawner1 = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();
        String spawner2 = TestUtils.getEntities(res, "zombie_toast_spawner").get(1).getId();

        // pick up sword
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        // now is adj to spawner, should destroy it 
        res = assertDoesNotThrow(() -> dmc.interact(spawner1));
        assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
        res = dmc.tick(Direction.UP);

        // goes across to next spawner 
        //res = dmc.tick(Direction.RIGHT);
        res = assertDoesNotThrow(() -> dmc.interact(spawner2));
        assertEquals(0, TestUtils.getEntities(res, "zombie_toast_spawner").size());

        assertEquals(":enemies", TestUtils.getGoals(res));
    }
}
