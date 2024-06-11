package de.affenherzog.cpslimit.listener;

import de.affenherzog.cpslimit.CpsLimit;
import de.affenherzog.cpslimit.CpsLimitPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final CpsLimitPlayer cpsLimitPlayer = new CpsLimitPlayer(player);

    CpsLimit.getInstance().getCpsLimitPlayers().put(player, cpsLimitPlayer);
  }

}