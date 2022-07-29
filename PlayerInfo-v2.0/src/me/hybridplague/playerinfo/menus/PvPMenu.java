package me.hybridplague.playerinfo.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.hybridplague.playerinfo.PlayerInfo;
import me.hybridplague.playerinfo.Utils;

public class PvPMenu {
	
	private PlayerInfo main;
	
	public PvPMenu(PlayerInfo main) {
		this.main = main;
	}
	
	public Inventory pvpMenu;
	
	public void open(Player p, OfflinePlayer t) {
		
		pvpMenu = Bukkit.createInventory(null, 36, Utils.format(main.getConfig().getString("Menus.PvP.header").replace("%target%", t.getName())));
		main.invs.add(pvpMenu);
		p.openInventory(pvpMenu);
		
		for (int i = 0; i < pvpMenu.getSize(); i++) {
			pvpMenu.setItem(i, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, null, " "));
		}
		
		main.getConfig().getConfigurationSection("Menus.PvP.Items").getKeys(false).forEach(key -> {
			List<String> lore = new ArrayList<String>();
			
			for (String list : main.getConfig().getStringList("Menus.PvP.Items." + key + ".lore")) {
				
				double kills = t.getStatistic(Statistic.PLAYER_KILLS);
				double deaths = t.getStatistic(Statistic.DEATHS);
				double kdr = (Math.round((kills / deaths) * 100.00) / 100.00);
				
				lore.add(Utils.format(list
						.replace("%kills%", String.valueOf(kills))
						.replace("%deaths%", String.valueOf(deaths))
						.replace("%kdr%", String.valueOf(kdr))));
				
				
			}
			
			pvpMenu.setItem(main.getConfig().getInt("Menus.PvP.Items." + key + ".slot"),
					Utils.createItem(Material.matchMaterial(main.getConfig().getString("Menus.PvP.Items." + key + ".type")),
							lore,
							main.getConfig().getString("Menus.PvP.Items." + key + ".name")));
			
		});

		pvpMenu.setItem(30, Utils.createItem(Material.ARROW, null, "&4Back"));
		pvpMenu.setItem(32, Utils.createItem(Material.BARRIER, null, "&4Close"));
	}

}
