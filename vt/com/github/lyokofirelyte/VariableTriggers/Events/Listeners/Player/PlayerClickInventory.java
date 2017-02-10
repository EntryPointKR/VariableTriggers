package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.Player;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerClickInventory extends VTMap<Object, Object> implements AR {

    private VariableTriggers main;

    public PlayerClickInventory(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/events/player", "PlayerClickInventory.yml");
        load();
    }

    @EventHandler(ignoreCancelled = false)
    public void onClick(InventoryClickEvent e) {

        if (getList("Worlds").contains(e.getWhoClicked().getWorld().getName())) {
            if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
                if (getList("main").size() > 0) {
                    if (getBool("Cancelled")) {
                        e.setCancelled(true);
                    }
                    new VTParser(main, "PlayerClickInventory.yml", "main", getList("main"), e.getWhoClicked().getLocation(), getCustoms(e), e.getWhoClicked().getName()).start();
                    cooldown();
                }
            }
        }
    }

    private HashMap<String, String> getCustoms(InventoryClickEvent e) {

        HashMap<String, String> map = new HashMap<String, String>();
        ItemStack i = e.getCurrentItem();

        map.put("<clickedslot>", e.getSlot() + "");
        map.put("<clickeditem>", i != null ? i.getType().toString() : "null");
        map.put("<clickeditemname>", i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : "null");
        map.put("<inventorytitle>", e.getInventory().getTitle());
        map.put("<inventorysize>", e.getInventory().getSize() + "");

        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
            for (int x = 0; x < i.getItemMeta().getLore().size(); x++) {
                map.put("<clickeditemlore:" + x + ">", i.getItemMeta().getLore().get(x));
            }
        }

        int x = 0;

        if (i != null && i.hasItemMeta() && i.getItemMeta().hasEnchants()) {
            for (Enchantment ench : i.getItemMeta().getEnchants().keySet()) {
                map.put("<clickeditemenchant:" + x + ">", ench.getName() + "_" + i.getItemMeta().getEnchants().get(ench));
                x++;
            }
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