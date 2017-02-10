package com.github.lyokofirelyte.VariableTriggers.Events.Listeners.System;

import com.github.lyokofirelyte.VariableTriggers.Commands.VTCommandMain;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.AR;
import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.VTParser;
import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;

public class SystemAutoSave extends VTMap<Object, Object> implements AR, Runnable {

    private VariableTriggers main;

    public SystemAutoSave(VariableTriggers i) {
        main = i;
        makePath("./plugins/VariableTriggers-Backport/events/system", "SystemAutoSave.yml");
        load();
    }

    public void run() {
        if (getLong("ActiveCooldown") <= System.currentTimeMillis()) {
            if (getList("main").size() > 0) {
                new VTParser(main, "SystemAutoSave.yml", "main", getList("main"), new Location(Bukkit.getWorlds().get(0), 0, 0, 0), new HashMap<String, String>(), "VTSystem").start();
                VTCommandMain cmd = (VTCommandMain) main.getInstance(VTCommandMain.class);
                cmd.onVT(Bukkit.getConsoleSender(), new String[]{"sv"}, "VTMAIN");
                cmd.onVT(Bukkit.getConsoleSender(), new String[]{"ss"}, "VTMAIN");
                cmd.onVT(Bukkit.getConsoleSender(), new String[]{"st"}, "VTMAIN");
                cooldown();
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