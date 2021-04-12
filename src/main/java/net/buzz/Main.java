package net.buzz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import net.buzz.Command.ActivityCommand;
import net.buzz.Util.Config;
import net.buzz.Util.TabComplete;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Main extends Plugin {
    public static Config config = null;

    public static Config data = null;

    public static Main instance = null;

    public void onEnable() {
        instance = this;
        config = new Config(instance, "config.yml");
        data = new Config(instance, "data.yml");
        ProxyServer.getInstance().getConsole().sendMessage((BaseComponent)toColor("&aBungeeActivity enabled!"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ActivityCommand());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new TabComplete());
        config.load();
        data.load();
        ProxyServer.getInstance().getScheduler().schedule(this, new Runnable() {
            public void run() {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.set(5, c.getActualMaximum(5));
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    if (p.getServer() != null && Main.config.getConfig().get("enable-tracker." + p.getServer().getInfo().getName().toLowerCase()) != null && Main.config.getConfig()
                            .getBoolean("enable-tracker." + p.getServer().getInfo().getName().toLowerCase())) {
                        if (Main.config.getConfig().get(p.getName().toString().toLowerCase()) == null) {
                            Main.data.getConfig().set(String.valueOf(p.getName().toString().toLowerCase()) + ".day-the-week-starts", Integer.valueOf(date.getDate()));
                            Main.data.getConfig().set(String.valueOf(p.getName().toString().toLowerCase()) + ".day-the-month-starts", Integer.valueOf(date.getDate()));
                            Main.data.save();
                        }
                        if ((new SimpleDateFormat("EEEE HH:mm")).format(date).equals("Saturday 23:59")) {
                            Main.data.getConfig().set(String.valueOf(p.getName().toString().toLowerCase()) + ".day-the-week-starts", Integer.valueOf(date.getDate()));
                            Main.data.save();
                        }
                        if (date.equals(c.getTime()) && (new SimpleDateFormat("HH:mm")).format(date).equals("23:59")) {
                            Main.data.getConfig().set(String.valueOf(p.getName().toString().toLowerCase()) + ".day-the-month-starts", Integer.valueOf(date.getDate()));
                            Main.data.save();
                        }
                        Main.data.getConfig().set(String.valueOf(p.getName().toString().toLowerCase()) + ".timeplayed." + formatter.format(date),
                                Main.data.getConfig()
                                        .getInt(String.valueOf(p.getName().toString().toLowerCase()) + ".timeplayed." + formatter.format(date)) + 1);
                        Main.data.save();
                    }
                }
            }
        }, 1L, TimeUnit.SECONDS);
    }

    public static TextComponent toColor(String b) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', b));
    }
}
