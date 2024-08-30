package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public abstract class PlayerState {
    private boolean isInvincible = false;
    private boolean isInvisible = false;

    PlayerState(Player player, boolean isInvincible, boolean isInvisible) {
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    };

    public boolean isInvisible() {
        return isInvisible;
    };
    public void toInvisible(Player player) {
        player.changeState(new InvisibleState(player));
    }
    public void toInvincible(Player player) {
        player.changeState(new InvincibleState(player));
    }
    public void tobase(Player player) {
        player.changeState(new BaseState(player));
    }
}
