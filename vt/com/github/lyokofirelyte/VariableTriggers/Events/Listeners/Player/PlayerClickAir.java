package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerClickAir extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerClickAir(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers/events/player", "PlayerClickAir.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onClick(PlayerInteractEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getBool("Cancelled")) {
                    e.setCancelled(true);
                }
                if (getList("main").size() > 0 && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR)) {
                    new VTParser(main, "PlayerClickAir.yml", "main", getList("main"), e.getPlayer().getLocation(), getCustoms(e), e.getPlayer().getName()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(PlayerInteractEvent e) {

        Vector loc = e.getPlayer().getLocation().toVector();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("<clicktype>", e.getAction().name());

        return map;
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}