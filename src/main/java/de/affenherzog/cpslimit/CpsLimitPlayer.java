package de.affenherzog.cpslimit;

import lombok.Getter;
import org.bukkit.entity.Player;

public class CpsLimitPlayer {

  @Getter
  private Player player;

  private int clicks = 0;

  public CpsLimitPlayer(Player player) {
    this.player = player;
  }

  public boolean clickAllowed() {
    if (clicks >= CpsLimit.getInstance().getMaxCps()) {
      return false;
    }

    this.clicks++;

    return true;
  }

  public void resetClicks() {
    this.clicks = 0;
  }

}
