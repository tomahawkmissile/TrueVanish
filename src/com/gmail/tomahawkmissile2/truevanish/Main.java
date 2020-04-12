package com.gmail.tomahawkmissile2.truevanish;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

	private List<Player> pl = new ArrayList<Player>();
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("tv").setExecutor(this);
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tv")) {
			Player p = (Player) sender;
			if(p.hasPermission("tv.use")) {
				if(pl.contains(p)) {
					for(Player s : this.getServer().getOnlinePlayers()) {
						if(s!=p) {
							s.showPlayer(this, p);
							pl.remove(p);
						}
					}
					this.getServer().broadcastMessage(ChatColor.YELLOW+p.getName()+" has joined the game.");
					p.sendMessage(ChatColor.GREEN+"You have unvanished.");
				} else {
					for(Player s : this.getServer().getOnlinePlayers()) {
						if(s!=p) {
							s.hidePlayer(this, p);
							pl.add(p);
						}
					}
					this.getServer().broadcastMessage(ChatColor.YELLOW+p.getName()+" has left the game.");
					p.sendMessage(ChatColor.GREEN+"You have vanished.");
				}
			} else {
				p.sendMessage(ChatColor.RED+"Error: "+ChatColor.DARK_RED+"You do not have permission.");
			}
			return true;
		}
		return false;
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(pl.contains(p)) {
			pl.remove(p);
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for(Player q : pl) {
			p.hidePlayer(this, q);
		}
	}
}
