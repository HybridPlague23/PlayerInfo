package me.hybridplague.playerinfo.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.hybridplague.playerinfo.PlayerInfo;
import me.hybridplague.playerinfo.Utils;

public class HostileMenu {

	private PlayerInfo main;
	
	public HostileMenu(PlayerInfo main) {
		this.main = main;
	}
	
	public Inventory hostileMenu;
	
	public void open(Player p, OfflinePlayer t) {
		
		hostileMenu = Bukkit.createInventory(null, 54, Utils.format(main.getConfig().getString("Menus.Hostile.header").replace("%target%", t.getName())));
		main.invs.add(hostileMenu);
		p.openInventory(hostileMenu);
		
		for (int i = 0; i < hostileMenu.getSize(); i++) {
			hostileMenu.setItem(i, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, null, " "));
		}
		main.getConfig().getConfigurationSection("Menus.Hostile.Items").getKeys(false).forEach(key -> {
			
			List<String> lore = new ArrayList<String>();
			
			for (String list : main.getConfig().getStringList("Menus.Hostile.Items." + key + ".lore")) {
				lore.add(Utils.format(list
						.replace("%kills%", String.valueOf(t.getStatistic(Statistic.KILL_ENTITY, EntityType.valueOf(key.toUpperCase()))))
						.replace("%killedby%", String.valueOf(t.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.valueOf(key.toUpperCase()))))));
			}
			hostileMenu.setItem(main.getConfig().getInt("Menus.Hostile.Items." + key + ".slot"),
					Utils.createItem(Material.matchMaterial(main.getConfig().getString("Menus.Hostile.Items." + key + ".type")),
							lore,
							main.getConfig().getString("Menus.Hostile.Items." + key + ".name")));
		});

		hostileMenu.setItem(48, Utils.createItem(Material.ARROW, null, "&4Back"));
		hostileMenu.setItem(50, Utils.createItem(Material.BARRIER, null, "&4Close"));
	}
	
}
