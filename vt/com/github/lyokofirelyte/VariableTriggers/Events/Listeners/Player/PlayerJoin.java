package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerJoin extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerJoin(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/events/player", "PlayerJoin.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onJoin(PlayerJoinEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setJoinMessage(null);
                    }
                    new VTParser(main, "PlayerJoin.yml", "main", getList("main"), e.getPlayer().getLocation(), new HashMap<String, String>(), e.getPlayer().getName()).start();
                    cooldown();
                }
            }
        }
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}