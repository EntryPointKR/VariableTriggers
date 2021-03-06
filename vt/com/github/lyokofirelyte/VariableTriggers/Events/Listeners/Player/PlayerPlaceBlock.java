package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerPlaceBlock extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerPlaceBlock(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/events/player", "PlayerPlaceBlock.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onPlace(BlockPlaceEvent e) {

        if (getList("Worlds").contains(e.getBlock().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getBool("Cancelled")) {
                    e.setCancelled(true);
                }
                if (getList("main").size() > 0) {
                    new VTParser(main, "PlayerPlaceBlock.yml", "main", getList("main"), e.getBlock().getLocation(), getCustoms(e), e.getPlayer().getName()).start();
                    cooldown();
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private HashMap<String, String> getCustoms(BlockPlaceEvent e) {

        Vector loc = e.getBlock().getLocation().toVector();
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("<blockid>", e.getBlock().getType().getId() + "");
        map.put("<blockdata>", e.getBlock().getData() + "");
        map.put("<blocktype>", e.getBlock().getType().getId() + ":" + e.getBlock().getData());
        map.put("<blockmaterial>", e.getBlock().getType().name());
        map.put("<blocklocation>", e.getBlock().getLocation().getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());

        return map;
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}