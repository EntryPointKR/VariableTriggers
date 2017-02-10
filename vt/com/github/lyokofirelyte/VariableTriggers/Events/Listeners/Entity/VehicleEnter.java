package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Entity;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.HashMap;

public class VehicleEnter extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public VehicleEnter(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers-Backport/events/entity", "VehicleEnter.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onDamage(VehicleEnterEvent e) {

        if (getList("Worlds").contains(e.getEntered().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getBool("Cancelled")) {
                    e.setCancelled(true);
                }
                if (getList("main").size() > 0) {
                    new VTParser(main, "VehicleEnter.yml", "main", getList("main"), e.getVehicle().getLocation(), getCustoms(e), e.getEntered().getType().name().toLowerCase()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(VehicleEnterEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();
        String type = e.getEntered().getType().name();

        map.put("<entitytype>", type);

        if (e.getEntered() instanceof Player) {
            map.put("<entityname>", ((Player) e.getEntered()).getName());
        } else {
            map.put("<entityname>", type.substring(0, 1) + type.substring(1).toLowerCase());
        }

        return map;
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}