package dungeonmania.entities.buildables;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.enemies.Mercenary;

public class Sceptre extends Buildable {
    private int duration; // total duration

    public Sceptre(int duration) {
        super(null);
        this.duration = duration;
    }

    @Override
    public void use(Game game) {
        duration--;
        if (duration <= 0) {
            // remove sceptre once
            game.getPlayer().remove(this);
            List<Mercenary> mercenaries = game.getMap().getEntities(Mercenary.class);
            mercenaries.forEach(e -> {
                if (e.isControlledBySceptre(this.getId())) {
                    e.setAlly(false);
                }
            });
        }

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1));
    }

    @Override
    public int getDurability() {
        return duration;
    }

}
