package de.affenherzog.cpslimit;

import lombok.Getter;
import org.bukkit.entity.Player;

public class CpsLimitPlayer {

  @Getter
  private Player player;

  private long lastClick = System.currentTimeMillis();

  public CpsLimitPlayer(Player player) {
    this.player = player;
  }

  public boolean clickAllowed() {
    final long currentTimeMillis = System.currentTimeMillis();

    if (currentTimeMillis - CpsLimit.getInstance().getCooldownMillis()  >= lastClick) {
      this.lastClick = currentTimeMillis;
      return true;
    }

    return false;
  }

}
