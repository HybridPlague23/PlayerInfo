package me.hybridplague.playerinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.myzelyam.api.vanish.VanishAPI;
import me.hybridplague.playerinfo.menus.HostileMenu;
import me.hybridplague.playerinfo.menus.MainMenu;
import me.hybridplague.playerinfo.menus.MobMenu;
import me.hybridplague.playerinfo.menus.PassiveMenu;
import me.hybridplague.playerinfo.menus.PvPMenu;
import net.milkbowl.vault.economy.Economy;

public class PlayerInfo extends JavaPlugin {

	public PlayerInfoCommand picmd;
	public PlayerInfoEvents pievents;
	public Utils utils;
	
	public Economy eco;
	public DataTracker data;
	public VanishAPI sv;
	
	public MainMenu mainMenu;
	public MobMenu mobMenu;
	public PvPMenu pvpMenu;
	public PassiveMenu passiveMenu;
	public HostileMenu hostileMenu;
	
	public List<Inventory> invs = new ArrayList<Inventory>();
	
	public HashMap<Player, String> viewing = new HashMap<Player, String>();
	public HashMap<Player, OfflinePlayer> targeting = new HashMap<Player, OfflinePlayer>();
	
	@Override
	public void onEnable() {
		
		this.saveDefaultConfig();
		
		this.picmd = new PlayerInfoCommand(this);
		this.pievents = new PlayerInfoEvents(this);
		this.utils = new Utils(this);
		this.data = new DataTracker(this);
		
		this.mainMenu = new MainMenu(this);
		this.mobMenu = new MobMenu(this);
		this.pvpMenu = new PvPMenu(this);
		this.passiveMenu = new PassiveMenu(this);
		this.hostileMenu = new HostileMenu(this);
		
		this.getCommand("playerinfo").setExecutor(new PlayerInfoCommand(this));
		this.getCommand("pi").setExecutor(new PlayerInfoCommand(this));
		this.getServer().getPluginManager().registerEvents(new PlayerInfoEvents(this), this);
		
		if (!setupEconomy()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
	}
	
	@Override
	public void onDisable() {
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economy = getServer().
				getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economy != null)
			eco = economy.getProvider();
		return (eco != null);
	}
	
}
