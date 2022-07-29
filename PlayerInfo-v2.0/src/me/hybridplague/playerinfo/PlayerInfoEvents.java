package me.hybridplague.playerinfo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInfoEvents implements Listener {

	public PlayerInfo main;
	
	public PlayerInfoEvents(PlayerInfo main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (main.invs.contains(e.getInventory())) {
			e.setCancelled(true);
			
			Player p = (Player) e.getWhoClicked();
			
			Material item = e.getCurrentItem().getType();
			ItemMeta meta = e.getCurrentItem().getItemMeta();
			
			if (item == Material.BARRIER && meta.getDisplayName().equals(Utils.format("&4Close"))) {
				p.closeInventory();
			}
			
			if (item == Material.ARROW && meta.getDisplayName().equals(Utils.format("&4Back"))) {
				if (main.viewing.get(p).equals("pvp") || main.viewing.get(p).equals("mob")) {
					main.mainMenu.open(p, main.targeting.get(p));
					main.viewing.put(p, "main");
					return;
				}
				if (main.viewing.get(p).equals("passive") || main.viewing.get(p).equals("hostile")) {
					main.mobMenu.open(p, main.targeting.get(p));
					main.viewing.put(p, "mob");
					return;
				}
			}
			
			if (item == Material.matchMaterial(main.getConfig().getString("Menus.Main.Items.Mob.type"))
					&& meta.getDisplayName().equals(Utils.format(main.getConfig().getString("Menus.Main.Items.Mob.name"))))	{
				main.mobMenu.open(p, main.targeting.get(p));
				main.viewing.put(p, "mob");
			}
			
			if (item == Material.matchMaterial(main.getConfig().getString("Menus.Main.Items.PvP.type"))
					&& meta.getDisplayName().equals(Utils.format(main.getConfig().getString("Menus.Main.Items.PvP.name"))))	{
				main.pvpMenu.open(p, main.targeting.get(p));
				main.viewing.put(p, "pvp");
			}
			
			if (item == Material.matchMaterial(main.getConfig().getString("Menus.Mob.Items.Passive.type"))
					&& meta.getDisplayName().equals(Utils.format(main.getConfig().getString("Menus.Mob.Items.Passive.name")))) {
				main.passiveMenu.open(p, main.targeting.get(p));
				main.viewing.put(p, "passive");
			}
			
			if (item == Material.matchMaterial(main.getConfig().getString("Menus.Mob.Items.Hostile.type"))
					&& meta.getDisplayName().equals(Utils.format(main.getConfig().getString("Menus.Mob.Items.Hostile.name")))) {
				main.hostileMenu.open(p, main.targeting.get(p));
				main.viewing.put(p, "hostile");
			}
			
		}
		return;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		main.viewing.remove(p);
	}
	
}
