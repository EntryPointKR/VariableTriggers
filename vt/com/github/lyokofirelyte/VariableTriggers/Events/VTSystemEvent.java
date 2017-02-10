package com.github.lyokofirelyte.VariableTriggers.Events;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VTSystemEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private VTData type;

    public VTSystemEvent(VTData type) {
        this.type = type;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getSender() {
        return "VTSystem";
    }

    public String getWorld() {
        return "VTSystem";
    }

    public VTData getType() {
        return type;
    }

    public void setType(VTData t) {
        type = t;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
    }
}