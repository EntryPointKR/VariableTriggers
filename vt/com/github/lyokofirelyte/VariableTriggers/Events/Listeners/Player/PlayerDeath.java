package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class PlayerDeath extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerDeath(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers-Backport/events/player", "PlayerDeath.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onDeath(PlayerDeathEvent e) {

        if (getList("Worlds").contains(e.getEntity().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    new VTParser(main, "PlayerDeath.yml", "main", getList("main"), e.getEntity().getLocation(), getCustoms(e), e.getEntity().getName()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(PlayerDeathEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("<killedbyplayer>", (e.getEntity().getKiller() instanceof Player) + "");
        map.put("<whodied>", e.getEntity().getName());
        map.put("<deathcause>", e.getEntity().getLastDamageCause().getCause().name());

        if (e.getEntity().getKiller() != null) {
            map.put("<killername>", e.getEntity().getKiller().getName());
            map.put("<killerentitytype>", e.getEntity().getKiller().getType().name());
            map.put("<isprojectile>", (e.getEntity().getKiller() instanceof Projectile) + "");
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