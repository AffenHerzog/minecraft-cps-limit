package de.affenherzog.cpslimit;

import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.affenherzog.cpslimit.listener.PlayerJoinListener;
import java.util.HashMap;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CpsLimit extends JavaPlugin {

  @Getter
  private static CpsLimit instance;

  @Getter
  private ProtocolManager protocolManager;

  @Getter
  private final HashMap<Player, CpsLimitPlayer> cpsLimitPlayers = new HashMap<>();

  @Getter
  private final int cooldownMillis = 100;

  @Override
  public void onEnable() {
    instance = this;

    this.protocolManager = ProtocolLibrary.getProtocolManager();

    this.registerClickPacketListener();
    this.registerListener();
    this.registerOnlinePlayer();
  }

  private void registerOnlinePlayer() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      cpsLimitPlayers.put(player, new CpsLimitPlayer(player));
    }

  }

  @Override
  public void onDisable() {
  }

  private void registerClickPacketListener() {
    this.protocolManager.addPacketListener(new PacketAdapter(this, Client.USE_ENTITY) {
      @Override
      public void onPacketReceiving(PacketEvent event) {
        final Player player = event.getPlayer();
        final CpsLimitPlayer cpsLimitPlayer = cpsLimitPlayers.get(player);

        if (cpsLimitPlayer == null) {
          return;
        }

        if (!cpsLimitPlayer.clickAllowed()) {
          event.setCancelled(true);
        }
      }

      @Override
      public void onPacketSending(PacketEvent event) {
        //empty on purpose
      }
    });
  }

  private void registerListener() {
    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new PlayerJoinListener(), this);
  }
}
