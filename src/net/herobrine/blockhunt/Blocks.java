package net.herobrine.blockhunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum Blocks {

	GLASS(1, Material.GLASS, ChatColor.GRAY + "Glass", false),
	STONE_BRICK(1, Material.SMOOTH_BRICK, ChatColor.GRAY + "Stone Brick", false),
	COAL_BLOCK(1, Material.COAL_BLOCK, ChatColor.GRAY + "Block of Coal", false),
	IRON_ORE(1, Material.IRON_ORE, ChatColor.GRAY + "Iron Ore", false),
	LAPIS_ORE(2, Material.LAPIS_ORE, ChatColor.BLUE + "Lapis Ore", false),
	GOLD_ORE(2, Material.GOLD_ORE, ChatColor.BLUE + "Gold Ore", false),
	IRON_BLOCK(2, Material.IRON_BLOCK, ChatColor.BLUE + "Iron Block", false),
	PISTON(2, Material.PISTON_BASE, ChatColor.BLUE + "Piston", false),
	BRICK(2, Material.BRICK, ChatColor.BLUE + "Brick", false),
	LAPIS_BLOCK(3, Material.LAPIS_BLOCK, ChatColor.GREEN + "Lapis Block", false),
	REDSTONE_BLOCK(3, Material.REDSTONE_BLOCK, ChatColor.GREEN + "Redstone Block", false),
	OBSIDIAN(3, Material.OBSIDIAN, ChatColor.GREEN + "Obisidan", false),
	BOOKSHELF(3, Material.BOOKSHELF, ChatColor.GREEN + "Bookshelf", false),
	DISPENSER(3, Material.DISPENSER, ChatColor.GREEN + "Dispenser", false),
	DIAMOND_ORE(4, Material.DIAMOND_ORE, ChatColor.GOLD + "Diamond Ore", false),
	TNT(4, Material.TNT, ChatColor.GOLD + "TNT", false),
	GOLD_BLOCK(4, Material.GOLD_BLOCK, ChatColor.GOLD + "Gold Block", false),
	JUKEBOX(4, Material.JUKEBOX, ChatColor.GOLD + "Jukebox", false),
	DIAMOND_BLOCK(5, Material.DIAMOND_BLOCK, ChatColor.RED + "Diamond Block", false),
	EMERALD_ORE(5, Material.EMERALD_ORE, ChatColor.RED + "Emerald Ore", false),
	NETHER_BRICK(4, Material.NETHER_BRICK, ChatColor.GREEN + "Nether Brick", true),
	NETHERRACK(3, Material.NETHERRACK, ChatColor.BLUE + "Netherrack", true),
	END_PORTAL(3, Material.ENDER_PORTAL_FRAME, ChatColor.GOLD + "End Portal", true),
	ENDER_CHEST(3, Material.ENDER_CHEST, ChatColor.GREEN + "Ender Chest", true),
	END_STONE(3, Material.ENDER_STONE, ChatColor.BLUE + "End Stone", true),
	NETHER_QUARTZ(4, Material.QUARTZ_ORE, ChatColor.GOLD + "Quartz Ore", true);

	private int difficulty;
	private Material material;
	private String display;
	private boolean isSpecial;

	private Blocks(int difficulty, Material material, String display, boolean isSpecial) {
		this.difficulty = difficulty;
		this.material = material;
		this.display = display;
		this.isSpecial = isSpecial;

	}

	public int getDifficulty() {
		return difficulty;

	}

	public Material getMaterial() {
		return material;
	}

	public String getDisplay() {
		return display;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

}
