package de.affenherzog.cpslimit;

public class PlayerClickCooldown {

  private final static int COOLDOWN_MILLIS = getCooldownFromConfig();

  private long lastClick = System.currentTimeMillis();

  private static int getCooldownFromConfig() {
    return CpsLimit.getInstance().getConfig().getInt("cooldown-between-hits-millis");
  }

  public boolean clickAllowed() {
    final long currentTimeMillis = System.currentTimeMillis();

    if (currentTimeMillis - COOLDOWN_MILLIS >= lastClick) {
      this.lastClick = currentTimeMillis;
      return true;
    }

    return false;
  }

}
