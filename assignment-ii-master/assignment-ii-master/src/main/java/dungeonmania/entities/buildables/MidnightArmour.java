package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    private int attack;
    private int defence;

    public MidnightArmour(int attack, int defence) {
        super(null);
        this.attack = attack;
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        return;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, attack, defence, 1, 1));
    }

    @Override
    public int getDurability() {
        return defence;
    }
}
