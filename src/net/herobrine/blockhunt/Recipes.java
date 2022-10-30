package net.herobrine.blockhunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Recipes {

	// this is called when a game starts with the special items modifier
	public void addRecipies() {
		addStoneDrill();
		addIronDrill();
		addRedstoneDrill();
		addAspectOfTheEnd();
		addAspectOfTheNether();
		addEnderChest();
		addEnderPortal();
		addNetherQuartz();
		// addDiamondDrill();
	}

	// this is called when a game ends
	public void removeRecipies() {
		Bukkit.getServer().resetRecipes();
	}

	public void addStoneDrill() {
		ItemStack stack = new ItemStack(Items.STONE_DRILL.getMaterial());

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "This item will help you mine faster than usual!");
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.STONE_DRILL.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 8, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);

		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape("CSC", "SIS", "CSC");
		recipe.setIngredient('S', Material.STONE);
		recipe.setIngredient('C', Material.COBBLESTONE);
		recipe.setIngredient('I', Material.IRON_INGOT);

		Bukkit.getServer().addRecipe(recipe);

	}

	public void addIronDrill() {
		ItemStack stack = new ItemStack(Items.IRON_DRILL.getMaterial());
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.WHITE + "You can convert Stone to End Stone with this!");

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.IRON_DRILL.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 9, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);

		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape("SIS", "IRI", "SIS");
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('S', Material.COBBLESTONE);
		recipe.setIngredient('R', Material.REDSTONE);

		Bukkit.getServer().addRecipe(recipe);
	}

	public void addRedstoneDrill() {
		ItemStack stack = new ItemStack(Items.REDSTONE_DRILL.getMaterial());

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.RED + "You can convert Stone to Netherrrack/Nether Brick with this!");
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.REDSTONE_DRILL.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 9, true);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape("SRS", "RGR", "SRS");
		recipe.setIngredient('S', Material.COBBLESTONE);
		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('G', Material.GOLD_INGOT);

		Bukkit.getServer().addRecipe(recipe);

	}

	public void addEnderChest() {
		ItemStack stack = new ItemStack(Items.ENDER_CHEST.getMaterial());

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.ENDER_CHEST.getDisplay());
		stack.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape("EEE", "ECE", "EEE");

		recipe.setIngredient('E', Material.EYE_OF_ENDER);
		recipe.setIngredient('C', Material.CHEST);
		Bukkit.getServer().addRecipe(recipe);
	}

	public void addEnderPortal() {
		ItemStack stack = new ItemStack(Items.ENDER_PORTAL.getMaterial());

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.ENDER_PORTAL.getDisplay());

		stack.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape("SGS", "GEG", "SGS");

		recipe.setIngredient('S', Material.ENDER_STONE);
		recipe.setIngredient('E', Material.EYE_OF_ENDER);
		recipe.setIngredient('G', Material.GLASS);
		Bukkit.getServer().addRecipe(recipe);
	}

	public void addNetherQuartz() {
		ItemStack stack = new ItemStack(Items.NETHER_QUARTZ.getMaterial());

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.NETHER_QUARTZ.getDisplay());

		stack.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape("QNQ", "NNN", "QNQ");

		recipe.setIngredient('N', Material.NETHERRACK);
		recipe.setIngredient('Q', Material.QUARTZ);
		Bukkit.getServer().addRecipe(recipe);
	}

	public void addAspectOfTheEnd() {
		ItemStack stack = new ItemStack(Items.ASPECT_OF_THE_END.getMaterial());

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD + "Item Ability: Instant Transmission "
				+ ChatColor.translateAlternateColorCodes('&', "&e&lRIGHT CLICK"));
		lore.add(ChatColor.GRAY + "Teleport 8 blocks ahead of you");
		lore.add(ChatColor.GRAY + "and gain a small " + ChatColor.GREEN + "speed" + ChatColor.GRAY + " boost");
		lore.add(ChatColor.GRAY + "for " + ChatColor.GREEN + "3" + ChatColor.GRAY + " seconds. (Cooldown 5s)");
		lore.add(ChatColor.GOLD + "Passive Ability: Ender Infusion");
		lore.add(ChatColor.GRAY + "Killed mobs have a" + ChatColor.GREEN + " 2% " + ChatColor.GRAY
				+ "chance to drop 5 Eyes of Ender.");
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.ASPECT_OF_THE_END.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape(" E ", " E ", " S ");

		recipe.setIngredient('E', Material.ENDER_PEARL);
		recipe.setIngredient('S', Material.STICK);

		Bukkit.getServer().addRecipe(recipe);
	}

	public void addAspectOfTheNether() {
		ItemStack stack = new ItemStack(Items.ASPECT_OF_THE_NETHER.getMaterial());

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD + "Item Ability: Fireshot "
				+ ChatColor.translateAlternateColorCodes('&', "&e&lRIGHT CLICK"));
		lore.add(ChatColor.GRAY + "Shoot a fireball which deals damage to mobs (Cooldown 4s)");
		lore.add(ChatColor.GOLD + "Passive Ability: Nether Infusion");
		lore.add(ChatColor.GRAY + "Killed mobs have a" + ChatColor.GREEN + " 1% " + ChatColor.GRAY
				+ "chance to drop 4 Nether Quartz.");
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.ASPECT_OF_THE_NETHER.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DAMAGE_ALL, 9, true);
		meta.addEnchant(Enchantment.DURABILITY, 9, true);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(stack);

		recipe.shape(" R ", " R ", " S ");

		recipe.setIngredient('R', Material.REDSTONE);
		recipe.setIngredient('S', Material.STICK);

		Bukkit.getServer().addRecipe(recipe);

	}

	// Scrapped Drill.
	// public void addDiamondDrill() {
	//// ItemStack stack = new ItemStack(Items.DIAMOND_DRILL.getMaterial());
//
	// List<String> lore = new ArrayList<>();
	// lore.add(ChatColor.AQUA + "Use this to mine SUPER fast!");
	// ItemMeta meta = stack.getItemMeta();
	// meta.setDisplayName(Items.DIAMOND_DRILL.getDisplay());
//		meta.setLore(lore);
	// meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
	// meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//		stack.setItemMeta(meta);
	// ShapedRecipe recipe = new ShapedRecipe(stack);
//
//		recipe.shape("SSS", " G ", " G ");
//		recipe.setIngredient('S', Material.DIAMOND);
//		recipe.setIngredient('G', Material.STICK);
//
//		Bukkit.getServer().addRecipe(recipe);
//	}

}
