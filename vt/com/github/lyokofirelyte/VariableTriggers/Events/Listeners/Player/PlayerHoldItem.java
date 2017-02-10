package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.HashMap;

public class PlayerHoldItem extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerHoldItem(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers/events/player", "PlayerHoldItem.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onHold(PlayerItemHeldEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setCancelled(true);
                    }
                    new VTParser(main, "PlayerHoldItem.yml", "main", getList("main"), e.getPlayer().getLocation(), getCustoms(e), e.getPlayer().getName()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(PlayerItemHeldEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("<oldslot>", e.getPreviousSlot() + "");
        map.put("<newSlot>", e.getNewSlot() + "");

        return map;
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}