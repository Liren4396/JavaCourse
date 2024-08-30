package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    @Test
    @Tag("additional test")
    @DisplayName("Test achieving a basic enemy goal")
    public void enemy_goal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest", "c_snakeTest");

        // move player to right
        //res = dmc.tick(Direction.RIGHT);
        //System.out.println(TestUtils.getGoals(res));
        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // assert only one head
        assertEquals(1, TestUtils.getEntities(res, "snake_head").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // eat food
        assertEquals(new Position(6, 6, 0), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // eat food
        assertEquals(new Position(5, 5), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        // eat food
        assertEquals(new Position(3, 3), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        // broken snake's body

        // broken snake's head
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("additional test1")
    @DisplayName("Test potion cut body")
    public void snake_collision_and_body_break() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest1", "c_snakeTest");

        // move player to right
        //res = dmc.tick(Direction.RIGHT);
        //System.out.println(TestUtils.getGoals(res));
        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // assert only one head
        assertEquals(1, TestUtils.getEntities(res, "snake_head").size());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // eat food
        assertEquals(new Position(6, 6, 0), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        // eat food
        assertEquals(new Position(5, 5), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        // eat food
        assertEquals(new Position(3, 3), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        // broken snake's body

        // broken snake's head
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("additional test2")
    @DisplayName("Test destroy head")
    public void snake_head_break() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeTest2", "c_snakeTest");

        // move player to right
        //res = dmc.tick(Direction.RIGHT);
        //System.out.println(TestUtils.getGoals(res));
        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // assert only one head
        assertEquals(1, TestUtils.getEntities(res, "snake_head").size());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

}
