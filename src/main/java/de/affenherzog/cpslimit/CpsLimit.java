package de.affenherzog.cpslimit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CpsLimit extends JavaPlugin {

    @Getter
    private static CpsLimit instance;

    @Getter
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        instance = this;

        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onDisable() {
    }
}
