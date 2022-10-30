package net.herobrine.blockhunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Items {
// Items we're considering:
	// Ore Compass: Points towards the nearest ore of it's type (ex. Iron Compass)
	//

	STONE_DRILL(Material.STONE_PICKAXE, ChatColor.DARK_GRAY + "Stone Drill"),
	IRON_DRILL(Material.IRON_PICKAXE, ChatColor.GRAY + "Iron Drill"),
	GOLD_DRILL(Material.GOLD_PICKAXE, ChatColor.GOLD + "Gold Drill"),
	REDSTONE_DRILL(Material.GOLD_PICKAXE, ChatColor.RED + "Redstone Drill"),
	ASPECT_OF_THE_END(Material.DIAMOND_SWORD, ChatColor.BLUE + "Aspect of the End"),
	ASPECT_OF_THE_NETHER(Material.GOLD_SWORD, ChatColor.RED + "Aspect Of The Nether"),
	ENDER_CHEST(Material.ENDER_CHEST, ChatColor.GREEN + "Ender Chest"),
	ENDER_PORTAL(Material.ENDER_PORTAL_FRAME, ChatColor.GREEN + "Ender Portal"),
	NETHER_QUARTZ(Material.QUARTZ_ORE, ChatColor.GOLD + "Nether Quartz");

	private Material material;
	private String display;

	private Items(Material material, String display) {
		this.material = material;
		this.display = display;
	}

	public Material getMaterial() {
		return material;
	}

	public String getDisplay() {

		return display;
	}

}
