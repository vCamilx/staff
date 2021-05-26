package com.github.vcamilx.staff.manager.mode;

import com.github.vcamilx.staff.core.player.StaffPlayer;
import com.github.vcamilx.staff.manager.client.ClientManager;
import com.github.vcamilx.staff.manager.database.JedisProvider;
import com.github.vcamilx.staff.manager.database.implementation.JedisManager;
import com.github.vcamilx.staff.util.chat.ChatUtil;
import com.google.gson.Gson;
import me.yushust.inject.InjectAll;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

@InjectAll
public class StaffChatManager {

    private ClientManager clientManager;
    private JedisProvider jedisProvider;
    private JedisManager jedisManager;
    private Gson gson;


    public void setStaffChatMode(Player player) {
        if (!isStaffChat(player)) {
            setStaffChat(player, true);
            player.sendMessage(ChatUtil.color("&bNow you're in staff-chat!"));
            return;
        }

        setStaffChat(player, false);
        player.sendMessage(ChatUtil.color("&cYou aren't in staff chat now!"));
    }

    public void setStaffChat(Player player, boolean mode) {
        String key = player.getUniqueId().toString();

        try (Jedis resource = jedisProvider.getJedisPool().getResource()) {
            clientManager.createPlayer(player);

            StaffPlayer deserialized = jedisManager.deserialized(key);
            deserialized.setStaffChat(mode);

            String serialized = gson.toJson(deserialized);
            resource.hset("staff", key, serialized);
        }
    }

    public boolean isStaffChat(Player player) {
        String key = player.getUniqueId().toString();

        return jedisManager.deserialized(key).isStaffChat();
    }
}
