package random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * A simple game, where a hero engages in battles. The hero has an equally
 * likely chance of succeeding as of failing.
 *
 * @author Nick Patrikeos + @your name
 */
public class Game {
    private Random random;

    public Game(long seed) {
        random = new Random(seed);
    }

    public Game() {
        this(System.currentTimeMillis());
    }

    public boolean battle() {
        // TODO
        return random.nextInt(100) < 50;
    }
    public String battle1() {
        // TODO
        double ret = random.nextInt(100);
        if (ret < 50) {
            return ret + " true"; 
        } else {
            return ret + " false"; 
        }
    }
    public static void main(String[] args) {
        Game g = new Game();
        int count = 0;
        for (int i = 0; i < 100; i++) {
            if (g.battle()) {
                count++;
                System.out.println("We won!! You are awesome!!");
            } else {
                System.out.println("Lost :(");
            }
        }
        System.out.println(count);
        //testBattleWithSeed4();
        //testBattleWithSeedMinus4();
    }
    @Test
    static
    void testBattleWithSeed4() {
        Game game = new Game(4);
        Random random = new Random(4);

        for (int i = 0; i < 8; i++) {
            System.out.println(game.battle1());
            //boolean battleResult = game.battle();
            //assertTrue(battleResult == (random.nextInt(100) < 50));
        }
    }

    @Test
    static
    void testBattleWithSeedMinus4() {
        System.out.println();
        Game game = new Game(-4);
        Random random = new Random(-4);
        for (int i = 0; i < 8; i++) {
            System.out.println(game.battle1());
            //assertEquals(random.nextInt(100), game.battle() ? 39 : 13);
        }
    }
}
