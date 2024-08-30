package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SunstoneTest {
    @Test
    @Tag("Sunstone-Test")
    @DisplayName("Test sunstone on map and player pick up")
    public void sunstonePickup() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunstoneTest", "c_enemyGoalsTest");

        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.LEFT);
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);
    }

    @Test
    @DisplayName("Test sunstone opens multiple doors and is retained")
    public void sunstoneDoorTest() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunstoneTest_doors", "c_enemyGoalsTest");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        res = dmc.tick(Direction.LEFT);
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);
        res = dmc.tick(Direction.DOWN); // in front of door now 
        res = dmc.tick(Direction.DOWN); // shouldve walked through door 
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);
        res = dmc.tick(Direction.DOWN); // walked through next door  
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);

    }

    @Test
    @DisplayName("Test key taking priority over sunstone when opening door")
    public void sunstoneKeyDoorTest() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunstoneTest_keyVsunstone", "c_enemyGoalsTest");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getEntities(res, "key").size());

        res = dmc.tick(Direction.LEFT);
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getInventory(res, "key").size() == 1);
        // has one sunstone and one key corresponding to the door 
        // in front of door now 
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        // walked thru door 
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);
        assertTrue(TestUtils.getInventory(res, "key").size() == 0);

    }

    @Test
    @DisplayName("Test sunstone counts towards treasure goal")
    public void sunstoneTreasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunstoneTest_treasureGoal", "c_sunstoneTest_treasureGoal");

        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(2, TestUtils.getEntities(res, "treasure").size());
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getInventory(res, "sun_stone").size() == 1);
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        // ASSERT GOAL NOT MET  
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getInventory(res, "treasure").size() == 1);
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getInventory(res, "treasure").size() == 2);

        // should have 3 treasures now 
        assertEquals("", TestUtils.getGoals(res));
    }
}
