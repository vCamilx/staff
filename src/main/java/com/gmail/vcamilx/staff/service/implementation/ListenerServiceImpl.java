package com.gmail.vcamilx.staff.service.implementation;

import com.gmail.vcamilx.staff.Staff;
import com.gmail.vcamilx.staff.listener.StaffChatListener;
import com.gmail.vcamilx.staff.listener.StaffJoinListener;
import com.gmail.vcamilx.staff.service.IService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerServiceImpl implements IService {
    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new StaffChatListener(), Staff.getPlugin());
        pluginManager.registerEvents(new StaffJoinListener(), Staff.getPlugin());
    }

    @Override
    public void start() {
        registerListener();
    }
}
