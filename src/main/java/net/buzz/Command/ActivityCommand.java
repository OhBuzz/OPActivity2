package net.buzz.Command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import net.buzz.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;

public class ActivityCommand extends Command {
    public ActivityCommand() {
        super("activity");
    }

    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof net.md_5.bungee.api.connection.ProxiedPlayer)) {
            if (args.length == 0) {
                s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("no-args-console")));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Main.config.load();
                    s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("config-reloaded")));
                } else {
                    String x = args[0];
                    if (x != null) {
                        if (Main.data.getConfig().get(x.toLowerCase().toString()) != null) {
                            Date d = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            int t = 0;
                            for (int i = d.getDate(); i >= Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".day-the-week-starts"); i--) {
                                String modDate = formatter.format(d);
                                StringBuilder strBld = new StringBuilder(modDate);
                                if (i <= 9) {
                                    strBld.setCharAt(0, '0');
                                    strBld.setCharAt(1, String.valueOf(i).charAt(0));
                                } else {
                                    strBld.setCharAt(0, String.valueOf(i).charAt(0));
                                    strBld.setCharAt(1, String.valueOf(i).charAt(1));
                                }
                                t += Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + strBld.toString());
                            }
                            int m = 0;
                            for (int j = d.getDate(); j >= Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".day-the-month-starts"); j--) {
                                String modDate = formatter.format(d);
                                StringBuilder strBld = new StringBuilder(modDate);
                                if (j <= 9) {
                                    strBld.setCharAt(0, '0');
                                    strBld.setCharAt(1, String.valueOf(j).charAt(0));
                                } else {
                                    strBld.setCharAt(0, String.valueOf(j).charAt(0));
                                    strBld.setCharAt(1, String.valueOf(j).charAt(1));
                                }
                                m += Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + strBld.toString());
                            }
                            for (String str : Main.config.getConfig().getStringList("activity-see-others"))
                                s.sendMessage((BaseComponent)Main.toColor(str.replaceAll("%player%", x)
                                        .replaceAll("%day%", timeConvert(Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + formatter.format(d))))
                                        .replaceAll("%week%", timeConvert(t))
                                        .replaceAll("%month%", timeConvert(m))));
                        }
                    } else {
                        s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("invalid-player")));
                    }
                }
            }
        } else if (args.length == 0) {
            if (s.hasPermission("activity.use")) {
                String x = s.getName();
                if (x != null) {
                    if (Main.data.getConfig().get(x.toLowerCase().toString()) != null) {
                        Date d = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        int t = 0;
                        for (int i = d.getDate(); i >= Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".day-the-week-starts"); i--) {
                            String modDate = formatter.format(d);
                            StringBuilder strBld = new StringBuilder(modDate);
                            if (i <= 9) {
                                strBld.setCharAt(0, '0');
                                strBld.setCharAt(1, String.valueOf(i).charAt(0));
                            } else {
                                strBld.setCharAt(0, String.valueOf(i).charAt(0));
                                strBld.setCharAt(1, String.valueOf(i).charAt(1));
                            }
                            t += Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + strBld.toString());
                        }
                        int m = 0;
                        for (int j = d.getDate(); j >= Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".day-the-month-starts"); j--) {
                            String modDate = formatter.format(d);
                            StringBuilder strBld = new StringBuilder(modDate);
                            if (j <= 9) {
                                strBld.setCharAt(0, '0');
                                strBld.setCharAt(1, String.valueOf(j).charAt(0));
                            } else {
                                strBld.setCharAt(0, String.valueOf(j).charAt(0));
                                strBld.setCharAt(1, String.valueOf(j).charAt(1));
                            }
                            m += Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + strBld.toString());
                        }
                        for (String str : Main.config.getConfig().getStringList("activity-see-others"))
                            s.sendMessage((BaseComponent)Main.toColor(str.replaceAll("%player%", x)
                                    .replaceAll("%day%", timeConvert(Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + formatter.format(d))))
                                    .replaceAll("%week%", timeConvert(t))
                                    .replaceAll("%month%", timeConvert(m))));
                    } else {
                        s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("invalid-player")));
                    }
                } else {
                    s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("invalid-player")));
                }
            } else {
                s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("no-perm")));
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (s.hasPermission("activity.reload")) {
                    Main.config.load();
                    s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("config-reloaded")));
                } else {
                    s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("no-perm")));
                }
            } else if (s.hasPermission("activity.admin")) {
                String x = args[0];
                if (x != null) {
                    if (Main.data.getConfig().get(x.toLowerCase().toString()) != null) {
                        Date d = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        int t = 0;
                        for (int i = d.getDate(); i >= Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".day-the-week-starts"); i--) {
                            String modDate = formatter.format(d);
                            StringBuilder strBld = new StringBuilder(modDate);
                            if (i <= 9) {
                                strBld.setCharAt(0, '0');
                                strBld.setCharAt(1, String.valueOf(i).charAt(0));
                            } else {
                                strBld.setCharAt(0, String.valueOf(i).charAt(0));
                                strBld.setCharAt(1, String.valueOf(i).charAt(1));
                            }
                            t += Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + strBld.toString());
                        }
                        int m = 0;
                        for (int j = d.getDate(); j >= Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".day-the-month-starts"); j--) {
                            String modDate = formatter.format(d);
                            StringBuilder strBld = new StringBuilder(modDate);
                            if (j <= 9) {
                                strBld.setCharAt(0, '0');
                                strBld.setCharAt(1, String.valueOf(j).charAt(0));
                            } else {
                                strBld.setCharAt(0, String.valueOf(j).charAt(0));
                                strBld.setCharAt(1, String.valueOf(j).charAt(1));
                            }
                            m += Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + strBld.toString());
                        }
                        for (String str : Main.config.getConfig().getStringList("activity-see-others"))
                            s.sendMessage((BaseComponent)Main.toColor(str.replaceAll("%player%", x)
                                    .replaceAll("%day%", timeConvert(Main.data.getConfig().getInt(String.valueOf(x.toLowerCase().toString()) + ".timeplayed." + formatter.format(d))))
                                    .replaceAll("%week%", timeConvert(t))
                                    .replaceAll("%month%", timeConvert(m))));
                    } else {
                        s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("invalid-player")));
                    }
                } else {
                    s.sendMessage((BaseComponent)Main.toColor(Main.config.getConfig().getString("invalid-player")));
                }
            } else {
                s.sendMessage((BaseComponent) Main.toColor(Main.config.getConfig().getString("no-perm")));
            }
        }
    }

    public String timeConvert(int seconds) {
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        String finalReturn = "";
        if (day > 0)
            finalReturn = String.valueOf(finalReturn) + day + "d ";
        if (hours > 0L)
            finalReturn = String.valueOf(finalReturn) + hours + "h ";
        if (minute > 0L)
            finalReturn = String.valueOf(finalReturn) + minute + "m ";
        if (second > 0L)
            finalReturn = String.valueOf(finalReturn) + second + "s";
        return finalReturn;
    }
}