package net.herobrine.blockhunt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.herobrine.gamecore.Arena;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Manager;

public class Menus {

	public static void applyTeamTPMenu(Player player) {

		if (Manager.isPlaying(player)) {
			Arena arena = Manager.getArena(player);
			if (arena.getState().equals(GameState.LIVE)) {
				if (arena.getTeamCount(arena.getTeam(player)) == 1) {
					player.sendMessage(ChatColor.RED + "You are on a team by yourself!");
				} else {

					Inventory menu = Bukkit.createInventory(null, 9,
							ChatColor.translateAlternateColorCodes('&', "&aChoose a teammate to teleport to!"));
					int i = 0;
					for (UUID uuid : arena.getPlayers()) {

						Player target = Bukkit.getPlayer(uuid);
						if (arena.getTeam(target).equals(arena.getTeam(player))
								&& target.getUniqueId() != player.getUniqueId()) {
							ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
							SkullMeta meta = (SkullMeta) skull.getItemMeta();

							meta.setOwner(target.getName());
							meta.setDisplayName(arena.getTeam(player).getColor() + target.getName());
							skull.setItemMeta(meta);
							menu.setItem(i, skull);
							i++;
						}
					}
					player.openInventory(menu);

				}
			} else {
				player.sendMessage(ChatColor.RED + "You can only use this command in a live game!");
			}
		}

	}

	public static void applyStoneDrillMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&8Stone Drill Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);
		ItemStack stack = new ItemStack(Items.STONE_DRILL.getMaterial());

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "This item will help you mine faster than usual!");
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.STONE_DRILL.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);

		// Recipe Slots
		menu.setItem(19, new ItemStack(Material.COBBLESTONE));
		menu.setItem(20, new ItemStack(Material.STONE));
		menu.setItem(21, new ItemStack(Material.COBBLESTONE));
		menu.setItem(28, new ItemStack(Material.STONE));
		menu.setItem(29, new ItemStack(Material.IRON_INGOT));
		menu.setItem(30, new ItemStack(Material.STONE));
		menu.setItem(37, new ItemStack(Material.COBBLESTONE));
		menu.setItem(38, new ItemStack(Material.STONE));
		menu.setItem(39, new ItemStack(Material.COBBLESTONE));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);
	}

	public static void applyIronDrillMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&fIron Drill Recipe"));

		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);
		ItemStack stack = new ItemStack(Items.IRON_DRILL.getMaterial());
		List<String> lore = new ArrayList<>();

		lore.add(ChatColor.WHITE + "You can convert Stone to End Stone with this!");

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.IRON_DRILL.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 8, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
		// Recipe Slots
		menu.setItem(19, new ItemStack(Material.COBBLESTONE));
		menu.setItem(20, new ItemStack(Material.IRON_INGOT));
		menu.setItem(21, new ItemStack(Material.COBBLESTONE));
		menu.setItem(28, new ItemStack(Material.IRON_INGOT));
		menu.setItem(29, new ItemStack(Material.REDSTONE));
		menu.setItem(30, new ItemStack(Material.IRON_INGOT));
		menu.setItem(37, new ItemStack(Material.COBBLESTONE));
		menu.setItem(38, new ItemStack(Material.IRON_INGOT));
		menu.setItem(39, new ItemStack(Material.COBBLESTONE));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);
	}

	public static void applyRedstoneDrillMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&cRedstone Drill Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

		ItemStack stack = new ItemStack(Items.REDSTONE_DRILL.getMaterial());

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.RED + "You can convert Stone to Netherrrack/Nether Brick with this!");
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.REDSTONE_DRILL.getDisplay());
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 8, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);

		// Recipe Slots
		menu.setItem(19, new ItemStack(Material.COBBLESTONE));
		menu.setItem(20, new ItemStack(Material.REDSTONE));
		menu.setItem(21, new ItemStack(Material.COBBLESTONE));
		menu.setItem(28, new ItemStack(Material.REDSTONE));
		menu.setItem(29, new ItemStack(Material.GOLD_INGOT));
		menu.setItem(30, new ItemStack(Material.REDSTONE));
		menu.setItem(37, new ItemStack(Material.COBBLESTONE));
		menu.setItem(38, new ItemStack(Material.REDSTONE));
		menu.setItem(39, new ItemStack(Material.COBBLESTONE));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);
	}

	public static void applyAOTEMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&9Aspect Of The End Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

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
		meta.addEnchant(Enchantment.DAMAGE_ALL, 7, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);

		// Recipe Slots

		menu.setItem(20, new ItemStack(Material.ENDER_PEARL));
		menu.setItem(29, new ItemStack(Material.ENDER_PEARL));
		menu.setItem(38, new ItemStack(Material.STICK));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);

	}

	public static void applyRecipesMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Block Hunt Recipe Guide");

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.YELLOW + "Click to view recipe!");

		ItemStack stoneDrill = new ItemStack(Items.STONE_DRILL.getMaterial());
		ItemMeta stoneDrillMeta = stoneDrill.getItemMeta();
		stoneDrillMeta.setDisplayName(Items.STONE_DRILL.getDisplay());
		stoneDrillMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		stoneDrillMeta.setLore(lore);
		stoneDrill.setItemMeta(stoneDrillMeta);

		ItemStack ironDrill = new ItemStack(Items.IRON_DRILL.getMaterial());
		ItemMeta ironDrillMeta = ironDrill.getItemMeta();
		ironDrillMeta.setDisplayName(Items.IRON_DRILL.getDisplay());
		ironDrillMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		ironDrillMeta.setLore(lore);
		ironDrill.setItemMeta(ironDrillMeta);

		ItemStack redstoneDrill = new ItemStack(Items.REDSTONE_DRILL.getMaterial());
		ItemMeta redstoneDrillMeta = redstoneDrill.getItemMeta();
		redstoneDrillMeta.setDisplayName(Items.REDSTONE_DRILL.getDisplay());
		redstoneDrillMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		redstoneDrillMeta.setLore(lore);
		redstoneDrill.setItemMeta(redstoneDrillMeta);

		ItemStack aote = new ItemStack(Items.ASPECT_OF_THE_END.getMaterial());
		ItemMeta aoteMeta = aote.getItemMeta();
		aoteMeta.setDisplayName(Items.ASPECT_OF_THE_END.getDisplay());
		aoteMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		aoteMeta.setLore(lore);
		aote.setItemMeta(aoteMeta);

		ItemStack aotn = new ItemStack(Items.ASPECT_OF_THE_NETHER.getMaterial());
		ItemMeta aotnMeta = aotn.getItemMeta();
		aotnMeta.setDisplayName(Items.ASPECT_OF_THE_NETHER.getDisplay());
		aotnMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		aotnMeta.setLore(lore);
		aotn.setItemMeta(aotnMeta);

		ItemStack enderChest = new ItemStack(Items.ENDER_CHEST.getMaterial());
		ItemMeta enderChestMeta = enderChest.getItemMeta();
		enderChestMeta.setDisplayName(Items.ENDER_CHEST.getDisplay());
		enderChestMeta.setLore(lore);
		enderChest.setItemMeta(enderChestMeta);

		ItemStack enderPortal = new ItemStack(Items.ENDER_PORTAL.getMaterial());
		ItemMeta enderPortalMeta = enderPortal.getItemMeta();
		enderPortalMeta.setDisplayName(Items.ENDER_PORTAL.getDisplay());
		enderPortalMeta.setLore(lore);
		enderPortal.setItemMeta(enderPortalMeta);

		ItemStack netherQuartz = new ItemStack(Items.NETHER_QUARTZ.getMaterial());
		ItemMeta netherQuartzMeta = netherQuartz.getItemMeta();
		netherQuartzMeta.setDisplayName(Items.NETHER_QUARTZ.getDisplay());
		netherQuartzMeta.setLore(lore);
		netherQuartz.setItemMeta(netherQuartzMeta);

		ItemStack comingSoon = new ItemStack(Material.BARRIER);
		ItemMeta comingSoonMeta = comingSoon.getItemMeta();
		comingSoonMeta.setDisplayName(ChatColor.RED + "Coming Soon...");
		List<String> comingSoonLore = new ArrayList<>();
		comingSoonLore.add(ChatColor.RED + "This item will be added soon!");
		comingSoonMeta.setLore(comingSoonLore);
		comingSoon.setItemMeta(comingSoonMeta);

		// WIP Item
		// ItemStack stoneDrill = new ItemStack(Items.STONE_DRILL.getMaterial());
		// ItemMeta stoneDrillMeta = stoneDrill.getItemMeta();
		// stoneDrillMeta.setDisplayName(Items.STONE_DRILL.getDisplay());
		// stoneDrill.setItemMeta(stoneDrillMeta);

		// it's ideal to start at 9, 11 is only temporary to make the GUI line up. Once
		// more items are added, the first slot will be 9.

		menu.setItem(9, stoneDrill);
		menu.setItem(10, ironDrill);
		menu.setItem(11, redstoneDrill);
		menu.setItem(12, aote);
		menu.setItem(13, aotn);
		menu.setItem(14, enderChest);
		menu.setItem(15, enderPortal);
		menu.setItem(16, netherQuartz);
		menu.setItem(17, comingSoon);

		player.openInventory(menu);

	}

	public static void applyAOTNMenu(Player player) {

		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&cAspect Of The Nether Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

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
		meta.addEnchant(Enchantment.DAMAGE_ALL, 8, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);

		// Recipe Slots

		menu.setItem(20, new ItemStack(Material.REDSTONE));
		menu.setItem(29, new ItemStack(Material.REDSTONE));
		menu.setItem(38, new ItemStack(Material.STICK));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);

	}

	public static void applyEnderChestMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&aEnder Chest Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

		ItemStack stack = new ItemStack(Items.ENDER_CHEST.getMaterial());

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.ENDER_CHEST.getDisplay());

		stack.setItemMeta(meta);

		// Recipe Slots
		menu.setItem(19, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(20, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(21, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(28, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(29, new ItemStack(Material.CHEST));
		menu.setItem(30, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(37, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(38, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(39, new ItemStack(Material.EYE_OF_ENDER));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);
	}

	public static void applyNetherQuartzMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&6Nether Quartz Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

		ItemStack stack = new ItemStack(Items.NETHER_QUARTZ.getMaterial());

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.NETHER_QUARTZ.getDisplay());
		stack.setItemMeta(meta);

		// Recipe Slots
		menu.setItem(19, new ItemStack(Material.QUARTZ));
		menu.setItem(20, new ItemStack(Material.NETHERRACK));
		menu.setItem(21, new ItemStack(Material.QUARTZ));
		menu.setItem(28, new ItemStack(Material.NETHERRACK));
		menu.setItem(29, new ItemStack(Material.NETHERRACK));
		menu.setItem(30, new ItemStack(Material.NETHERRACK));
		menu.setItem(37, new ItemStack(Material.QUARTZ));
		menu.setItem(38, new ItemStack(Material.NETHERRACK));
		menu.setItem(39, new ItemStack(Material.QUARTZ));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);
	}

	public static void applyEnderPortalMenu(Player player) {
		Inventory menu = Bukkit.createInventory(null, 54,
				ChatColor.translateAlternateColorCodes('&', "&aEnder Portal Recipe"));
		ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
		ItemMeta fillerMeta = filler.getItemMeta();
		fillerMeta.setDisplayName(" ");
		filler.setItemMeta(fillerMeta);

		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = goBack.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.RED + "Go Back");
		List<String> backLore = new ArrayList<>();
		backLore.add(ChatColor.GRAY + "Return to Recipe Guide");
		arrowMeta.setLore(backLore);
		goBack.setItemMeta(arrowMeta);

		menu.setItem(45, goBack);

		ItemStack stack = new ItemStack(Items.ENDER_PORTAL.getMaterial());

		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(Items.ENDER_PORTAL.getDisplay());

		stack.setItemMeta(meta);

		// Recipe Slots
		menu.setItem(19, new ItemStack(Material.ENDER_STONE));
		menu.setItem(20, new ItemStack(Material.GLASS));
		menu.setItem(21, new ItemStack(Material.ENDER_STONE));
		menu.setItem(28, new ItemStack(Material.GLASS));
		menu.setItem(29, new ItemStack(Material.EYE_OF_ENDER));
		menu.setItem(30, new ItemStack(Material.GLASS));
		menu.setItem(37, new ItemStack(Material.ENDER_STONE));
		menu.setItem(38, new ItemStack(Material.GLASS));
		menu.setItem(39, new ItemStack(Material.ENDER_STONE));

		// Result Slot
		menu.setItem(32, stack);

		// Filler Slots
		menu.setItem(0, filler);
		menu.setItem(1, filler);
		menu.setItem(2, filler);
		menu.setItem(3, filler);
		menu.setItem(4, filler);
		menu.setItem(5, filler);
		menu.setItem(6, filler);
		menu.setItem(7, filler);
		menu.setItem(8, filler);
		menu.setItem(9, filler);
		menu.setItem(10, filler);
		menu.setItem(11, filler);
		menu.setItem(12, filler);
		menu.setItem(13, filler);
		menu.setItem(14, filler);
		menu.setItem(15, filler);
		menu.setItem(16, filler);
		menu.setItem(17, filler);
		menu.setItem(18, filler);
		menu.setItem(22, filler);
		menu.setItem(23, filler);
		menu.setItem(24, filler);
		menu.setItem(25, filler);
		menu.setItem(26, filler);
		menu.setItem(27, filler);
		menu.setItem(31, filler);
		menu.setItem(33, filler);
		menu.setItem(34, filler);
		menu.setItem(35, filler);
		menu.setItem(36, filler);
		menu.setItem(40, filler);
		menu.setItem(41, filler);
		menu.setItem(42, filler);
		menu.setItem(43, filler);
		menu.setItem(44, filler);

		menu.setItem(46, filler);
		menu.setItem(47, filler);
		menu.setItem(48, filler);
		menu.setItem(49, filler);
		menu.setItem(50, filler);
		menu.setItem(51, filler);
		menu.setItem(52, filler);
		menu.setItem(53, filler);

		player.openInventory(menu);
	}

	public static void applyVotingMenu(Player player) {

		if (Manager.isPlaying(player)) {
			Arena arena = Manager.getArena(player);

			if (arena.getState().equals(GameState.LIVE) || arena.getState().equals(GameState.RECRUITING)) {
				player.sendMessage(ChatColor.RED + "You cannot open this menu while there isn't a voting event!");
			} else {
				Inventory menu = Bukkit.createInventory(null, 27,
						ChatColor.translateAlternateColorCodes('&', "&bVote for a game type!"));

				ItemStack vanillaGame = new ItemStack(Material.GRASS);
				ItemMeta vanillaGameMeta = vanillaGame.getItemMeta();
				vanillaGameMeta.setDisplayName(ChatColor.YELLOW + "Vanilla");
				vanillaGame.setItemMeta(vanillaGameMeta);

				ItemStack modifiedGame = new ItemStack(Material.NETHERRACK);
				ItemMeta modifiedGameMeta = modifiedGame.getItemMeta();
				modifiedGameMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Modifier");
				modifiedGame.setItemMeta(modifiedGameMeta);
				menu.setItem(12, vanillaGame);
				menu.setItem(14, modifiedGame);

				player.openInventory(menu);
			}

		} else {
			player.sendMessage(ChatColor.RED + "You must be in a game to do this!");
		}
	}
}
