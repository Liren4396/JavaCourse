package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SceptreTest {
    @Test
    @DisplayName("Test building a sceptre using a key and wood")
    public void buildSceptre() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptre_keyAndWoodSunstone", "c_BuildablesTest_BuildSceptre");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up key x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Pick up sun_stone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
    }

    @Test
    @DisplayName("Test building a sceptre using a treasure and 2 arrows")
    public void buildSceptreTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptre_arrowsAndTreasureAndSunstone", "c_BuildablesTest_BuildSceptre");

        // using 1 treasure 2 arrows and a sun_stone
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up arrow
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Pick up sun_stone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up treasure x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("18-3")
    @DisplayName("Test building a sceptre using a 2 sunstones and 2 arrows")
    public void buildSceptreSunstone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptre_twoSunstones", "c_BuildablesTest_BuildSceptre");

        // using 2 arrows and a 2 sunstones
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up arrow
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Pick up sun_stone x1
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory except sun_stone
        // because it has been used as a treasure
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("18-4")
    @DisplayName("Testing a mercenary can be bribed using sceptre with no radius limit")
    public void bribeRadius() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptre_mercenaryBribe", "c_mercenaryTest_sceptreBasic");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        buildSceptre(res, dmc);

        // bribe - need to check that it lasts for the said time && check that is allied
        // to keep it blackboxed, cannot directly check if it is allied
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());

    }

    @Test
    @Tag("18-5")
    @DisplayName("Testing a mercenary can be bribed with insufficient sceptre/gold")
    public void noMoney() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptre_mercenaryBribe", "c_mercenaryTest_sceptreBasic");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());

        // no sceptre no bribery
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
    }

    private void buildSceptre(DungeonResponse res, DungeonManiaController dmc) {
        // using 1key, 1 wood and 1 sun_stone
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up key x1
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up sun_stone x1
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @DisplayName("Testing the tick range of 2 mercenaries being mind-controlled")
    public void mindControlDuration() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_sceptre_twoMercenaries", "c_mercenaryTest_sceptreBasic");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        buildSceptre(res, dmc);
        buildSceptre(res, dmc);

        // bribe - need to check that it lasts for the said time && check that is allied
        // to keep it blackboxed, cannot directly check if it is allied
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());

    }
}
