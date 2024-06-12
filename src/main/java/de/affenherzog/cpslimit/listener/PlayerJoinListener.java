package de.affenherzog.cpslimit.listener;

import de.affenherzog.cpslimit.CpsLimit;
import de.affenherzog.cpslimit.PlayerClickCooldown;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final PlayerClickCooldown playerClickCooldown = new PlayerClickCooldown(player);

    CpsLimit.getInstance().getCpsLimitPlayers().put(player, playerClickCooldown);
  }

}
