package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerDropItem extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerDropItem(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/events/player", "PlayerDropItem.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onDrop(PlayerDropItemEvent e) {

        if (getList("Worlds").contains(e.getPlayer().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setCancelled(true);
                    }
                    new VTParser(main, "PlayerDropItem.yml", "main", getList("main"), e.getPlayer().getLocation(), getCustoms(e), e.getPlayer().getType().name().toLowerCase()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(PlayerDropItemEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();
        ItemStack i = e.getItemDrop().getItemStack();
        int x = 0;

        map.put("<droppeditem:name>", i.getType().name().toLowerCase());
        map.put("<droppeditem:displayname>", i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : "none");
        map.put("<droppeditem:amount>", i.getAmount() + "");

        if (i.hasItemMeta() && i.getItemMeta().hasLore()) {
            map.put("<droppeditem:lore:amount>", i.getItemMeta().getLore().size() + "");
            for (String lore : i.getItemMeta().getLore()) {
                map.put("<droppeditem:lore:" + x + ">", lore);
                x++;
            }
        } else {
            map.put("<droppeditem:lore:amount>", "0");
        }

        x = 0;

        if (i.hasItemMeta() && i.getItemMeta().hasEnchants()) {
            map.put("<droppeditem:enchant:amount>", i.getItemMeta().getEnchants().size() + "");
            for (Enchantment ench : i.getItemMeta().getEnchants().keySet()) {
                map.put("<droppeditem:enchant:" + x + ">", ench.getName().toLowerCase() + "_" + i.getItemMeta().getEnchants().get(ench));
                x++;
            }
        } else {
            map.put("<droppeditem:enchant:amount>", "0");
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