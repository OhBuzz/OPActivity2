package net.buzz.Util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabComplete implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String[] args = event.getCursor().toLowerCase().split(" ");
        if (args.length >= 1 && args[0].startsWith("/") &&
                args.length > 1 && args[0].equalsIgnoreCase("/activity")) {
            String check = args[args.length - 1];
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(check))
                    event.getSuggestions().add(player.getName());
            }
        }
    }
}
