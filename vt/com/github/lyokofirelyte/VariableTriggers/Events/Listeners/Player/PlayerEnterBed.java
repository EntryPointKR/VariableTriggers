package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.HashMap;

public class PlayerEnterBed extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerEnterBed(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers-Backport/events/player", "PlayerEnterBed.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onBed(PlayerBedEnterEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setCancelled(true);
                    }
                    new VTParser(main, "PlayerEnterBed.yml", "main", getList("main"), e.getBed().getLocation(), new HashMap<String, String>(), e.getPlayer().getName()).start();
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