package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.HashMap;

public class PlayerSprint extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerSprint(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers-Backport/events/player", "PlayerSprint.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onSneak(PlayerToggleSprintEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setCancelled(true);
                    }
                    new VTParser(main, "PlayerSprint.yml", "main", getList("main"), e.getPlayer().getLocation(), getCustoms(e), e.getPlayer().getName()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(PlayerToggleSprintEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("<sprinting>", e.isSprinting() + "");

        return map;
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}