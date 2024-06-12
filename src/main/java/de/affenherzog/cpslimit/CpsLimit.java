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
  private final HashMap<Player, PlayerClickCooldown> cpsLimitPlayers = new HashMap<>();

  @Override
  public void onEnable() {
    instance = this;

    this.protocolManager = ProtocolLibrary.getProtocolManager();

    this.registerConfig();
    this.registerPacketListener();
    this.registerListener();
    this.registerOnlinePlayer();
  }

  private void registerConfig() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }

  private void registerOnlinePlayer() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      cpsLimitPlayers.put(player, new PlayerClickCooldown(player));
    }
  }

  @Override
  public void onDisable() {
  }

  private void registerPacketListener() {
    this.registerArmAnimationListener();
    this.registerUseEntityListener();
  }

  private void registerArmAnimationListener() {
    this.protocolManager.addPacketListener(new PacketAdapter(this, Client.ARM_ANIMATION) {
      @Override
      public void onPacketReceiving(PacketEvent event) {
        final Player player = event.getPlayer();
        final PlayerClickCooldown playerClickCooldown = cpsLimitPlayers.get(player);

        if (playerClickCooldown == null) {
          return;
        }

        if (!playerClickCooldown.clickAllowed()) {
          event.setCancelled(true);
        }
      }
    });
  }

  private void registerUseEntityListener() {
    this.protocolManager.addPacketListener(new PacketAdapter(this, Client.USE_ENTITY) {
      @Override
      public void onPacketReceiving(PacketEvent event) {
        final Player player = event.getPlayer();
        final PlayerClickCooldown playerClickCooldown = cpsLimitPlayers.get(player);

        if (playerClickCooldown == null) {
          return;
        }

        if (playerClickCooldown.clickAllowed()) {
          playerClickCooldown.onClick();
        } else {
          event.setCancelled(true);
        }
      }
    });
  }

  private void registerListener() {
    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new PlayerJoinListener(), this);
  }
}
