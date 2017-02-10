package com.github.lyokofirelyte.VariableTriggers;

import com.github.lyokofirelyte.VariableTriggers.Identifiers.VTMap;
import com.github.lyokofirelyte.VariableTriggers.Utils.VTUtils;

public class VTVars extends VTMap<Object, Object> {

    private VariableTriggers main;

    public VTVars(VariableTriggers i) {
        main = i;
        makePath(VTUtils.getDataFolder().getPath() + "/system", "vars.yml");
        load();
    }

    public void loadAll() {
        load();
    }

    public void saveAll() {
        save();
    }
}