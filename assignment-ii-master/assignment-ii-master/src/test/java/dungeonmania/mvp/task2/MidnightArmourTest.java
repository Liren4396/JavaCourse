package dungeonmania.mvp.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    @DisplayName("Test building midnightArmour - no zombies")
    public void buildMidnightArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmour_noZombies", "c_midnightArmour_basic");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up sun_stone x1
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build midnightArmour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @DisplayName("Test cant build armour wiht zombies present")
    public void buildMidnightArmourException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmour_ThreeZombies", "c_midnightArmour_basic");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up sun_stone x3
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build midnightArmour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @DisplayName("Test midnight Armour increases attack damage")
    public void testMidnightArmourIncreasesAttackDamage() {
        DungeonManiaController controller = new DungeonManiaController();
        String config = "c_midnightArmour_basicAttack";
        controller.newGame("d_midnightArmour_attackDamage", config);
        // Pick up sword
        DungeonResponse res = controller.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // pick up sun_stone
        res = controller.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> controller.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size()); // Battle the zombie
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.UP);

        List<BattleResponse> battles = res.getBattles();
        BattleResponse battle = battles.get(0);

        // This is the attack without the sword
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double midnightArmourAttack = Double
                .parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        RoundResponse firstRound = battle.getRounds().get(0);

        assertEquals((playerBaseAttack + midnightArmourAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }

}
