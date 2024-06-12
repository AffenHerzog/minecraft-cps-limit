package de.affenherzog.cpslimit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

public class PlayerClickCooldown {

  private final static int COOLDOWN_MILLIS = getCooldownFromConfig();

  private final Player player;
  private long lastClick = System.currentTimeMillis();

  public PlayerClickCooldown(Player player) {
    this.player = player;
  }

  private static int getCooldownFromConfig() {
    return CpsLimit.getInstance().getConfig().getInt("cooldown-between-hits-millis");
  }

  public boolean clickAllowed() {
    final long currentTimeMillis = System.currentTimeMillis();
    return currentTimeMillis - COOLDOWN_MILLIS >= lastClick;
  }

  public void onClick() {
    this.lastClick = System.currentTimeMillis();
    sendArmAnimation();
  }

  public void sendArmAnimation() {
    final ProtocolManager protocolManager = CpsLimit.getInstance().getProtocolManager();
    try {
      PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ANIMATION);
      packet.getIntegers().write(0, player.getEntityId());
      packet.getIntegers().write(1, 0);
      protocolManager.broadcastServerPacket(packet);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
