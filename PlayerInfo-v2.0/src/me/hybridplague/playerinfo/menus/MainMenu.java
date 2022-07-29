package me.hybridplague.playerinfo.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.myzelyam.api.vanish.VanishAPI;
import me.hybridplague.playerinfo.PlayerInfo;
import me.hybridplague.playerinfo.Utils;

public class MainMenu {

	private PlayerInfo main;
	
	public MainMenu(PlayerInfo main) {
		this.main = main;
	}
	
	public Inventory mainMenu;
	
	public void open(Player p, OfflinePlayer t) {
		
		mainMenu = Bukkit.createInventory(null, 54, Utils.format(main.getConfig().getString("Menus.Main.header").replace("%target%", t.getName())));
		main.invs.add(mainMenu);
		p.openInventory(mainMenu);
		
		for (int i = 0; i < mainMenu.getSize(); i++) {
			mainMenu.setItem(i, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, null, " "));
		}
		mainMenu.setItem(13, Utils.createHead(t.getName(), null, Utils.format("&7" + t.getName())));
		
		mainMenu.setItem(49, Utils.createItem(Material.BARRIER, null, "&4Close"));
		
		main.getConfig().getConfigurationSection("Menus.Main.Items").getKeys(false).forEach(key -> {
			List<String> lore = new ArrayList<String>();
			for (String list : main.getConfig().getStringList("Menus.Main.Items." + key + ".lore")) {
				
				int health;
				int food;
				
				list = list.replace("%target%", t.getName())
						.replace("%balance%", String.valueOf(Math.round(main.eco.getBalance(t)*100.00)/100.00));
				
				if (t.isOnline() && !VanishAPI.isInvisible((Player) t)) {
					health = (int) ((Player) t).getHealth();
					food = ((Player) t).getFoodLevel();
					list = list.replace("%health%", health + "/" + (int) ((Player) t).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())
							.replace("%food%", food + "/20");
				} else {
					list = list.replace("%health%", "&cData unobtainable").replace("%food%", "&cData unobtainable");
				}
				
				lore.add(Utils.format(list));
				
			}
			
			mainMenu.setItem(main.getConfig().getInt("Menus.Main.Items." + key + ".slot"),
					Utils.createItem(Material.matchMaterial(main.getConfig().getString("Menus.Main.Items." + key + ".type")),
							lore,
							main.getConfig().getString("Menus.Main.Items." + key + ".name")));
			lore.clear();
		});
		
		
	}
	
	
	
}
