package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Entity;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class EntityDeath extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public EntityDeath(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/events/entity", "EntityDeath.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onDeath(EntityDeathEvent e) {

        if (getList("Worlds").contains(e.getEntity().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    new VTParser(main, "EntityDeath.yml", "main", getList("main"), e.getEntity().getLocation(), getCustoms(e), e.getEntity().getType().name().toLowerCase()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(EntityDeathEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();
        String type = e.getEntity().getType().name();

        map.put("<whodied>", type.substring(0, 1) + type.substring(1).toLowerCase());
        map.put("<killedbyplayer>", (e.getEntity().getKiller() != null) + "");
        map.put("<killername>", e.getEntity().getKiller() != null ? e.getEntity().getKiller().getName() : "null");
        map.put("<droppedexp>", e.getDroppedExp() + "");

        for (int i = 0; i < e.getDrops().size(); i++) {
            map.put("<drops:" + i + ">", e.getDrops().get(i).getType().name().toLowerCase());
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