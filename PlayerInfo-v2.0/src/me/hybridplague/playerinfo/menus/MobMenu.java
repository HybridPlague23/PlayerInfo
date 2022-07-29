package me.hybridplague.playerinfo.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.hybridplague.playerinfo.PlayerInfo;
import me.hybridplague.playerinfo.Utils;

public class MobMenu {

	private PlayerInfo main;
	
	public MobMenu(PlayerInfo main) {
		this.main = main;
	}
	
	public Inventory mobMenu;
	
	public void open(Player p, OfflinePlayer t) {
		
		mobMenu = Bukkit.createInventory(null, 36, Utils.format(main.getConfig().getString("Menus.Mob.header").replace("%target%", t.getName())));
		main.invs.add(mobMenu);
		p.openInventory(mobMenu);
		
		for (int i = 0; i < mobMenu.getSize(); i++) {
			mobMenu.setItem(i, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, null, " "));
		}
		
		main.getConfig().getConfigurationSection("Menus.Mob.Items").getKeys(false).forEach(key -> {
			mobMenu.setItem(main.getConfig().getInt("Menus.Mob.Items." + key + ".slot"),
					Utils.createItem(Material.matchMaterial(main.getConfig().getString("Menus.Mob.Items." + key + ".type")),
							null,
							main.getConfig().getString("Menus.Mob.Items." + key + ".name")));
			
		});

		mobMenu.setItem(30, Utils.createItem(Material.ARROW, null, "&4Back"));
		mobMenu.setItem(32, Utils.createItem(Material.BARRIER, null, "&4Close"));
	}
	
}
