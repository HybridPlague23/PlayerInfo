package me.hybridplague.playerinfo;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Utils {

	public Utils(PlayerInfo main) {
	}
	
	public static ItemStack createItem(Material material, @Nullable List<String> lore, String displayName) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		if (lore != null) {
			List<String> l = lore;
			meta.setLore(l);
		}
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createHead(String player, @Nullable List<String> lore, String displayName) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		meta.setOwner(player);
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static String format(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	
}
