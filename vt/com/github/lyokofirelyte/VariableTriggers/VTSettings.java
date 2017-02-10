package com.github.lyokofirelyte.VariableTriggers;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;

public class VTSettings extends VTMap<Object, Object> {

    private VariableTriggers main;

    public VTSettings(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/system", "settings.yml");
        load();
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}