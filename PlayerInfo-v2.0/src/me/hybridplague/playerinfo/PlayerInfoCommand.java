package me.hybridplague.playerinfo;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerInfoCommand implements CommandExecutor {

	public Inventory mainMenu;
	
	public PlayerInfo main;
	
	public PlayerInfoCommand(PlayerInfo main) {
		this.main = main;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		
		if (!isNum(main.data.getConfig().getString("Count"))) {
			main.data.getConfig().set("Count", 0);
		}
		int count = main.data.getConfig().getInt("Count")+1;
		main.data.getConfig().set("Count", count);
		main.data.saveConfig();
		
		if (args.length == 0) {
			main.mainMenu.open(p, p);
			main.targeting.put(p, p);
			return true;
		} else if (args.length > 0) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (!p.hasPermission("playerinfo.reload")) {
					return false;
				} else {
					main.reloadConfig();
					p.sendMessage(Utils.format("&aPlayerInfo config reloaded!"));
					return true;
				}
			}
			if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore() || Bukkit.getOfflinePlayer(args[0]).isOnline()) {
				OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
				main.mainMenu.open(p, t);
				main.targeting.put(p, t);
				return true;
			} else {
				p.sendMessage(Utils.format("&cPlayer not found"));
				return true;
			}
		}
		return true;
	}
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
}
