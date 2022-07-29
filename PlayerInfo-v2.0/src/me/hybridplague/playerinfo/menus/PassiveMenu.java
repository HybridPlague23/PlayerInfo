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

public class PassiveMenu {

	private PlayerInfo main;
	
	public PassiveMenu(PlayerInfo main) {
		this.main = main;
	}
	
	public Inventory passiveMenu;
	
	public void open(Player p, OfflinePlayer t) {
		
		passiveMenu = Bukkit.createInventory(null, 54, Utils.format(main.getConfig().getString("Menus.Passive.header").replace("%target%", t.getName())));
		main.invs.add(passiveMenu);
		p.openInventory(passiveMenu);
		
		for (int i = 0; i < passiveMenu.getSize(); i++) {
			passiveMenu.setItem(i, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, null, " "));
		}
		
		main.getConfig().getConfigurationSection("Menus.Passive.Items").getKeys(false).forEach(key -> {
			
			List<String> lore = new ArrayList<String>();
			
			for (String list : main.getConfig().getStringList("Menus.Passive.Items." + key + ".lore")) {
				lore.add(Utils.format(list
						.replace("%kills%", String.valueOf(t.getStatistic(Statistic.KILL_ENTITY, EntityType.valueOf(key.toUpperCase()))))
						.replace("%killedby%", String.valueOf(t.getStatistic(Statistic.ENTITY_KILLED_BY, EntityType.valueOf(key.toUpperCase()))))));
			}
			passiveMenu.setItem(main.getConfig().getInt("Menus.Passive.Items." + key + ".slot"),
					Utils.createItem(Material.matchMaterial(main.getConfig().getString("Menus.Passive.Items." + key + ".type")),
							lore,
							main.getConfig().getString("Menus.Passive.Items." + key + ".name")));
		});

		passiveMenu.setItem(48, Utils.createItem(Material.ARROW, null, "&4Back"));
		passiveMenu.setItem(50, Utils.createItem(Material.BARRIER, null, "&4Close"));
	}
	
}
