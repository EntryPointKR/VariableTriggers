package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.HashMap;

public class PlayerKick extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerKick(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/events/player", "PlayerKick.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onJoin(PlayerKickEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setLeaveMessage(null);
                    }
                    new VTParser(main, "PlayerKick.yml", "main", getList("main"), e.getPlayer().getLocation(), new HashMap<String, String>(), e.getPlayer().getName()).start();
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