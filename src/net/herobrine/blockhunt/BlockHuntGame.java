
package net.herobrine.blockhunt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.herobrine.blockhunt.ModifiedTypes;
import net.herobrine.core.HerobrinePVPCore;
import net.herobrine.core.SongPlayer;
import net.herobrine.core.Songs;
import net.herobrine.gamecore.Arena;
import net.herobrine.gamecore.GameCoreMain;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.GameType;
import net.herobrine.gamecore.Teams;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class BlockHuntGame extends Recipes {
	private Arena arena;

	private int seconds;
	BukkitRunnable runnable;
	private List<Teams> finishedTeams = new ArrayList<>();
	private boolean preGame;
	private HashMap<Teams, Blocks> targetBlock = new HashMap<>();
	private List<Blocks> redWins = new ArrayList<>();
	private List<Blocks> blueWins = new ArrayList<>();
	private int roundsPlayed = 0;

	public BlockHuntGame(Arena arena) {
		this.arena = arena;
		this.seconds = 300;

	}

	public int getID() {
		return arena.getID();
	}

	public boolean hasModifier(ModifiedTypes type) {

		if (arena.getBHModifiers().contains(type)) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<ModifiedTypes> getList() {

		return arena.getBHModifiers();
	}

	public Blocks getBlock(Teams team) {

		return targetBlock.get(team);

	}

	public void setBlock(Teams team, Blocks block) {

		targetBlock.put(team, block);

	}

	public void isGameOver(Teams roundWinner) {
		preGame = true;
		roundsPlayed = roundsPlayed + 1;
		// arena.sendMessage(ChatColor.GOLD + "[DEBUG] Seconds:" + seconds);
		// arena.sendMessage(ChatColor.GOLD + "[DEBUG] Finished Teams Size: " +
		// finishedTeams.size());
		if (arena.getTeamCount(Teams.RED) == 0 && arena.getTeamCount(Teams.BLUE) == 0) {
			finishedTeams.clear();
			arena.getBHModifiers().clear();
			redWins.clear();
			blueWins.clear();
			arena.reset();
			removeRecipies();
			GameListener.aoteCooldown.clear();
			GameListener.aotnCooldown.clear();
			preGame = false;
		}

		if (arena.getTeamCount(Teams.RED) == 0) {
			Teams winningTeams = Teams.BLUE;

			arena.sendMessage(ChatColor.GREEN + "The winner is: " + winningTeams.getDisplay());

			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				player.setWalkSpeed(.2F);
				if (arena.getTeam(player).equals(Teams.BLUE)) {
					GameCoreMain.getInstance().sendTitle(player, "&6VICTORY",
							"&7There are no players left on the other team!", 0, 15, 1);
					SongPlayer.playSong(player, Songs.WIN);
					giveTrophy(player);
				}
			}
			finishedTeams.clear();
			arena.getBHModifiers().clear();
			redWins.clear();
			blueWins.clear();
			arena.reset();
			removeRecipies();
			GameListener.aoteCooldown.clear();
			GameListener.aotnCooldown.clear();
			preGame = false;
		}
		if (arena.getTeamCount(Teams.BLUE) == 0) {
			Teams winningTeams = Teams.RED;
			arena.playEndSounds(winningTeams, Sound.LEVEL_UP, Sound.VILLAGER_NO);
			arena.sendMessage(ChatColor.GREEN + "The winner is: " + winningTeams.getDisplay());
			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				player.setWalkSpeed(.2F);
				if (arena.getTeam(player).equals(Teams.RED)) {
					GameCoreMain.getInstance().sendTitle(player, "&6VICTORY",
							"&7There are no players left on the other team!", 0, 15, 1);
					SongPlayer.playSong(player, Songs.WIN);
					giveTrophy(player);
				}
			}
			finishedTeams.clear();
			arena.getBHModifiers().clear();
			redWins.clear();
			blueWins.clear();
			arena.reset();
			removeRecipies();
			GameListener.aoteCooldown.clear();
			GameListener.aotnCooldown.clear();
			preGame = false;

		}

		if (redWins.size() == 2) {
			Teams winningTeams = Teams.RED;
			arena.playEndSounds(winningTeams, Sound.LEVEL_UP, Sound.VILLAGER_NO);
			arena.sendMessage(ChatColor.GREEN + "The winner is: " + winningTeams.getDisplay());
			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				player.setWalkSpeed(.2F);
				if (arena.getTeam(player).equals(Teams.RED)) {
					GameCoreMain.getInstance().sendTitle(player, "&6VICTORY",
							"&7Your team won " + redWins.size() + " &7out of 3 rounds!", 0, 15, 1);
					SongPlayer.playSong(player, Songs.WIN);
					giveTrophy(player);
				} else {
					GameCoreMain.getInstance().sendTitle(player, "&cGAME OVER", "&7You didn't win this time!", 0, 15,
							1);
					SongPlayer.playSong(player, Songs.LOSE);
				}
			}
			finishedTeams.clear();
			arena.getBHModifiers().clear();
			redWins.clear();
			blueWins.clear();
			arena.reset();
			removeRecipies();
			GameListener.aoteCooldown.clear();
			GameListener.aotnCooldown.clear();
			preGame = false;
		}

		if (blueWins.size() == 2) {
			Teams winningTeams = Teams.BLUE;
			arena.playEndSounds(winningTeams, Sound.LEVEL_UP, Sound.VILLAGER_NO);
			arena.sendMessage(ChatColor.GREEN + "The winner is: " + winningTeams.getDisplay());
			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				player.setWalkSpeed(.2F);
				if (arena.getTeam(player).equals(Teams.BLUE)) {
					GameCoreMain.getInstance().sendTitle(player, "&6VICTORY",
							"&7Your team won " + blueWins.size() + " &7out of 3 rounds!", 0, 15, 1);
					SongPlayer.playSong(player, Songs.WIN);
					giveTrophy(player);
				} else {
					GameCoreMain.getInstance().sendTitle(player, "&cGAME OVER", "&7You didn't win this time!", 0, 15,
							1);
					SongPlayer.playSong(player, Songs.LOSE);
				}
			}
			finishedTeams.clear();
			arena.getBHModifiers().clear();
			redWins.clear();
			blueWins.clear();
			arena.reset();
			removeRecipies();
			GameListener.aoteCooldown.clear();
			GameListener.aotnCooldown.clear();
			preGame = false;
		}

		if (roundsPlayed >= 4) {
			if (redWins.size() > blueWins.size()) {
				Teams winningTeams = Teams.RED;

				arena.sendMessage(ChatColor.GREEN + "The winner is: " + winningTeams.getDisplay());
				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					player.setWalkSpeed(.2F);
					if (arena.getTeam(player).equals(Teams.RED)) {
						GameCoreMain.getInstance().sendTitle(player, "&6VICTORY",
								"&7Your team won " + redWins.size() + " &7out of 3 rounds!", 0, 15, 1);
						SongPlayer.playSong(player, Songs.WIN);
						giveTrophy(player);
					} else {
						GameCoreMain.getInstance().sendTitle(player, "&cGAME OVER", "&7You didn't win this time!", 0,
								15, 1);
						SongPlayer.playSong(player, Songs.LOSE);
					}
				}

			} else if (blueWins.size() > redWins.size()) {
				Teams winningTeams = Teams.BLUE;

				arena.sendMessage(ChatColor.GREEN + "The winner is: " + winningTeams.getDisplay());
				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					player.setWalkSpeed(.2F);
					if (arena.getTeam(player).equals(Teams.BLUE)) {
						GameCoreMain.getInstance().sendTitle(player, "&6VICTORY",
								"&7Your team won " + blueWins.size() + " &7out of 3 rounds!", 0, 15, 1);
						SongPlayer.playSong(player, Songs.WIN);
						giveTrophy(player);
					} else {
						GameCoreMain.getInstance().sendTitle(player, "&cGAME OVER", "&7You didn't win this time!", 0,
								15, 1);
						SongPlayer.playSong(player, Songs.LOSE);
					}
				}
			} else {
				arena.sendMessage(ChatColor.YELLOW + "This game ended in a "
						+ ChatColor.translateAlternateColorCodes('&', "&e&lDRAW!"));
				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					player.setWalkSpeed(.2F);
					GameCoreMain.getInstance().sendTitle(player, "&e&lDRAW",
							"&7The game ended in a draw!" + "&c" + redWins.size() + "&7-" + "&9" + blueWins.size(), 0,
							15, 1);
					SongPlayer.playSong(player, Songs.DRAW);
				}
			}

			finishedTeams.clear();
			arena.getBHModifiers().clear();
			redWins.clear();
			blueWins.clear();
			arena.reset();
			removeRecipies();
			GameListener.aoteCooldown.clear();
			GameListener.aotnCooldown.clear();
			preGame = false;
		}


		if (finishedTeams.size() != 2 && finishedTeams.size() > 0 && preGame) {
			Blocks newBlock = randomBlock();
			setBlock(Teams.RED, newBlock);
			Blocks newBlock2 = randomBlock(getBlock(Teams.RED).getDifficulty());

			setBlock(Teams.BLUE, newBlock2);

			finishedTeams.clear();
			seconds = 300;
			preGame = false;

			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				arena.removeSpectator(player);
				String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
				String roundsPlayedString = roundsPlayed + "/" + "3";
				player.sendMessage(
						ChatColor.GREEN + "Your new block is " + getBlock(arena.getTeam(player)).getDisplay());
				player.getScoreboard().getTeam("bhtimer").setSuffix(ChatColor.GREEN + time);
				player.getScoreboard().getTeam("rounds").setSuffix(ChatColor.GREEN + roundsPlayedString);
				if (arena.getTeam(player).equals(Teams.RED)) {
					player.getScoreboard().getTeam("block").setSuffix(newBlock.getDisplay());
				} else if (arena.getTeam(player).equals(Teams.BLUE)) {
					player.getScoreboard().getTeam("block").setSuffix(newBlock2.getDisplay());
				}

			}

			ItemStack boat = new ItemStack(Material.BOAT);
			ItemMeta boatMeta = boat.getItemMeta();
			boatMeta.setDisplayName(ChatColor.AQUA + "SpeedBoat 3000");
			boat.setItemMeta(boatMeta);

			Location redLoc = Config.getPlayerSpawn(arena.getID());
			Location blueLoc = Config.getPlayerSpawn(arena.getID());
			double redx = redLoc.getX();
			double redy = redLoc.getY();
			double redz = redLoc.getZ();
			double bluex = blueLoc.getX();
			double bluey = blueLoc.getY();
			double bluez = blueLoc.getZ();
			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				player.getInventory().clear();
				ItemStack crafter = new ItemStack(Material.WORKBENCH);
				ItemMeta crafterMeta = crafter.getItemMeta();
				crafterMeta.setDisplayName(ChatColor.GREEN + "Crafting Table" + ChatColor.GRAY + " (Right Click)");
				crafter.setItemMeta(crafterMeta);
				player.getInventory().addItem(crafter);
				player.getInventory().addItem(boat);
				if (arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {
					ItemStack recipeBook = new ItemStack(Material.ENCHANTED_BOOK);
					ItemMeta recipeBookMeta = recipeBook.getItemMeta();
					recipeBookMeta.setDisplayName(ChatColor.GREEN + "Recipe Book");
					recipeBook.setItemMeta(recipeBookMeta);
					player.getInventory().addItem(recipeBook);
				}

				if (arena.getTeam(player).equals(Teams.RED)) {
					player.teleport(redLoc);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&a&lRANDOM! &eYour team has been teleported to: ") + ChatColor.GOLD + redx + ", " + redy
							+ ", " + redz);
					player.sendMessage(ChatColor.RED + "The terrain may be generating... Please be patient...");
					if (hasModifier(ModifiedTypes.MOB_DAMAGE) || hasModifier(ModifiedTypes.FALL_DAMAGE)) {
						player.sendMessage(ChatColor.RED
								+ "If you die, your inventory will be kept, but you'll be sent back to a random spawnpoint.");

					}

				}

				else if (arena.getTeam(player).equals(Teams.BLUE)) {
					player.teleport(blueLoc);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&a&lRANDOM! &eYour team has been teleported to: ") + ChatColor.GOLD + bluex + ", " + bluey
							+ ", " + bluez);
					if (hasModifier(ModifiedTypes.MOB_DAMAGE) || hasModifier(ModifiedTypes.FALL_DAMAGE)) {
						player.sendMessage(ChatColor.RED
								+ "If you die, your inventory will be kept, but you'll be sent back to a random spawnpoint.");

					}
				}

			}

			if (getBlock(Teams.RED).isSpecial()) {
				if (getBlock(Teams.RED).equals(Blocks.NETHERRACK) || getBlock(Teams.RED).equals(Blocks.NETHER_BRICK)
						|| getBlock(Teams.RED).equals(Blocks.NETHER_QUARTZ)) {

					if (getBlock(Teams.RED).equals(Blocks.NETHER_BRICK)
							|| getBlock(Teams.RED).equals(Blocks.NETHERRACK)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.RED
								+ "Redstone Drill " + ChatColor.GREEN + "to get this block.", Teams.RED);
						TextComponent recipe = new TextComponent("Click here to see the recipe!");
						recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
						recipe.setUnderlined(true);
						recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/redstonedrillrecipe"));

						arena.sendSpigotMessage(recipe, Teams.RED);
					}

					else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.RED
								+ "Aspect Of The Nether " + ChatColor.GREEN + "and " + ChatColor.RED + "Redstone Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					}

				}

				else if (getBlock(Teams.RED).equals(Blocks.END_STONE)) {
					arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.WHITE + "Iron Drill "
							+ ChatColor.GREEN + " to get this block.", Teams.RED);

					TextComponent recipe = new TextComponent("Click here to see the recipe!");
					recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					recipe.setUnderlined(true);
					recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/irondrillrecipe"));

					arena.sendSpigotMessage(recipe, Teams.RED);
				} else if (getBlock(Teams.RED).equals(Blocks.END_PORTAL)
						|| getBlock(Teams.RED).equals(Blocks.ENDER_CHEST)) {

					if (getBlock(Teams.RED).equals(Blocks.END_PORTAL)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "and " + ChatColor.WHITE + "Iron Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					} else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					}

				}

			}

			if (getBlock(Teams.BLUE).isSpecial()) {
				if (getBlock(Teams.BLUE).equals(Blocks.NETHERRACK) || getBlock(Teams.BLUE).equals(Blocks.NETHER_BRICK)
						|| getBlock(Teams.BLUE).equals(Blocks.NETHER_QUARTZ)) {

					if (getBlock(Teams.BLUE).equals(Blocks.NETHER_BRICK)
							|| getBlock(Teams.BLUE).equals(Blocks.NETHERRACK)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.RED
								+ "Redstone Drill " + ChatColor.GREEN + "to get this block.", Teams.BLUE);
						TextComponent recipe = new TextComponent("Click here to see the recipe!");
						recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
						recipe.setUnderlined(true);
						recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/redstonedrillrecipe"));

						arena.sendSpigotMessage(recipe, Teams.RED);
					}

					else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.RED
								+ "Aspect Of The Nether " + ChatColor.GREEN + "and " + ChatColor.RED + "Redstone Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					}
				}

				else if (getBlock(Teams.BLUE).equals(Blocks.END_STONE)) {
					arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.WHITE + "Iron Drill "
							+ ChatColor.GREEN + " to get this block.", Teams.BLUE);

					TextComponent recipe = new TextComponent("Click here to see the recipe!");
					recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					recipe.setUnderlined(true);
					recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/irondrillrecipe"));
					arena.sendSpigotMessage(recipe, Teams.BLUE);
				} else if (getBlock(Teams.BLUE).equals(Blocks.END_PORTAL)
						|| getBlock(Teams.BLUE).equals(Blocks.ENDER_CHEST)) {

					if (getBlock(Teams.BLUE).equals(Blocks.END_PORTAL)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "and " + ChatColor.WHITE + "Iron Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.BLUE);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					} else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					}
					// send clickable text component

				}
			}

		}

		if (finishedTeams.size() == 0 && preGame) {
			arena.playSound(Sound.VILLAGER_NO);
			arena.sendMessage(ChatColor.RED
					+ "Time is up! Neither Team finished, so you'll be assigned a new block of a lower difficulty. Your inventory will be cleared and you'll be teleported to a random location.");
			if (getBlock(Teams.RED).getDifficulty() == 1) {
				arena.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&e&lDRAW! You guys can't even do a 1 difficulty block?"));
				finishedTeams.clear();
				arena.reset();
				removeRecipies();
				preGame = false;
			} else {
				Blocks newBlock = randomBlock(getBlock(Teams.RED).getDifficulty() - 1);
				Blocks newBlock2 = randomBlock(getBlock(Teams.BLUE).getDifficulty() - 1);
				setBlock(Teams.RED, newBlock);
				setBlock(Teams.BLUE, newBlock2);

				finishedTeams.clear();
				seconds = 300;
				preGame = false;

				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					arena.removeSpectator(player);
					String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
					String roundsPlayedString = roundsPlayed + "/" + "3";
					player.sendMessage(
							ChatColor.GREEN + "Your new block is " + getBlock(arena.getTeam(player)).getDisplay());
					player.getScoreboard().getTeam("bhtimer").setSuffix(ChatColor.GREEN + time);
					player.getScoreboard().getTeam("rounds").setSuffix(ChatColor.GREEN + roundsPlayedString);
					if (arena.getTeam(player).equals(Teams.RED)) {
						player.getScoreboard().getTeam("block").setSuffix(newBlock.getDisplay());
					} else if (arena.getTeam(player).equals(Teams.BLUE)) {
						player.getScoreboard().getTeam("block").setSuffix(newBlock2.getDisplay());
					}
				}

			}
			ItemStack boat = new ItemStack(Material.BOAT);
			ItemMeta boatMeta = boat.getItemMeta();
			boatMeta.setDisplayName(ChatColor.AQUA + "SpeedBoat 3000");
			boat.setItemMeta(boatMeta);
			Location redLoc = Config.getPlayerSpawn(arena.getID());
			Location blueLoc = Config.getPlayerSpawn(arena.getID());
			double redx = redLoc.getX();
			double redy = redLoc.getY();
			double redz = redLoc.getZ();
			double bluex = blueLoc.getX();
			double bluey = blueLoc.getY();
			double bluez = blueLoc.getZ();

			for (UUID uuid : arena.getPlayers()) {
				Player player = Bukkit.getPlayer(uuid);
				player.getInventory().clear();
				ItemStack crafter = new ItemStack(Material.WORKBENCH);
				ItemMeta crafterMeta = crafter.getItemMeta();
				crafterMeta.setDisplayName(ChatColor.GREEN + "Crafting Table" + ChatColor.GRAY + " (Right Click)");
				crafter.setItemMeta(crafterMeta);
				player.getInventory().addItem(crafter);
				player.getInventory().addItem(boat);

				if (arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {
					ItemStack recipeBook = new ItemStack(Material.ENCHANTED_BOOK);
					ItemMeta recipeBookMeta = recipeBook.getItemMeta();
					recipeBookMeta.setDisplayName(ChatColor.GREEN + "Recipe Book");
					recipeBook.setItemMeta(recipeBookMeta);
					player.getInventory().addItem(recipeBook);
				}

				if (arena.getTeam(player).equals(Teams.RED)) {
					player.teleport(redLoc);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&a&lRANDOM! &eYour team has been teleported to: ") + ChatColor.GOLD + redx + ", " + redy
							+ ", " + redz);
					player.sendMessage(ChatColor.RED + "The terrain may be generating... Please be patient...");
					if (hasModifier(ModifiedTypes.MOB_DAMAGE) || hasModifier(ModifiedTypes.FALL_DAMAGE)) {
						player.sendMessage(ChatColor.RED
								+ "If you die, your inventory will be kept, but you'll be sent back to a random location.");

					}

				}

				else if (arena.getTeam(player).equals(Teams.BLUE)) {
					player.teleport(blueLoc);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&a&lRANDOM! &eYour team has been teleported to: ") + ChatColor.GOLD + bluex + ", " + bluey
							+ ", " + bluez);
					if (hasModifier(ModifiedTypes.MOB_DAMAGE) || hasModifier(ModifiedTypes.FALL_DAMAGE)) {
						player.sendMessage(ChatColor.RED
								+ "If you die, your inventory will be kept, but you'll be sent back to a random location.");

					}
				}

			}
			if (getBlock(Teams.RED).isSpecial()) {
				if (getBlock(Teams.RED).equals(Blocks.NETHERRACK) || getBlock(Teams.RED).equals(Blocks.NETHER_BRICK)
						|| getBlock(Teams.RED).equals(Blocks.NETHER_QUARTZ)) {

					if (getBlock(Teams.RED).equals(Blocks.NETHER_BRICK)
							|| getBlock(Teams.RED).equals(Blocks.NETHERRACK)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.RED
								+ "Redstone Drill " + ChatColor.GREEN + "to get this block.", Teams.RED);
						TextComponent recipe = new TextComponent("Click here to see the recipe!");
						recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
						recipe.setUnderlined(true);
						recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/redstonedrillrecipe"));

						arena.sendSpigotMessage(recipe, Teams.RED);
					}

					else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.RED
								+ "Aspect Of The Nether " + ChatColor.GREEN + "and " + ChatColor.RED + "Redstone Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					}

				}

				else if (getBlock(Teams.RED).equals(Blocks.END_STONE)) {
					arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.WHITE + "Iron Drill "
							+ ChatColor.GREEN + " to get this block.", Teams.RED);

					TextComponent recipe = new TextComponent("Click here to see the recipe!");
					recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					recipe.setUnderlined(true);
					recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/irondrillrecipe"));

					arena.sendSpigotMessage(recipe, Teams.RED);
				} else if (getBlock(Teams.RED).equals(Blocks.END_PORTAL)
						|| getBlock(Teams.RED).equals(Blocks.ENDER_CHEST)) {

					if (getBlock(Teams.RED).equals(Blocks.END_PORTAL)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "and " + ChatColor.WHITE + "Iron Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					} else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					}

				}

			}

			if (getBlock(Teams.BLUE).isSpecial()) {
				if (getBlock(Teams.BLUE).equals(Blocks.NETHERRACK) || getBlock(Teams.BLUE).equals(Blocks.NETHER_BRICK)
						|| getBlock(Teams.BLUE).equals(Blocks.NETHER_QUARTZ)) {

					if (getBlock(Teams.BLUE).equals(Blocks.NETHER_BRICK)
							|| getBlock(Teams.BLUE).equals(Blocks.NETHERRACK)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.RED
								+ "Redstone Drill " + ChatColor.GREEN + "to get this block.", Teams.BLUE);
						TextComponent recipe = new TextComponent("Click here to see the recipe!");
						recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
						recipe.setUnderlined(true);
						recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/redstonedrillrecipe"));

						arena.sendSpigotMessage(recipe, Teams.RED);
					}

					else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.RED
								+ "Aspect Of The Nether " + ChatColor.GREEN + "and " + ChatColor.RED + "Redstone Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					}
				}

				else if (getBlock(Teams.BLUE).equals(Blocks.END_STONE)) {
					arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.WHITE + "Iron Drill "
							+ ChatColor.GREEN + " to get this block.", Teams.BLUE);

					TextComponent recipe = new TextComponent("Click here to see the recipe!");
					recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					recipe.setUnderlined(true);
					recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/irondrillrecipe"));
					arena.sendSpigotMessage(recipe, Teams.BLUE);
				} else if (getBlock(Teams.BLUE).equals(Blocks.END_PORTAL)
						|| getBlock(Teams.BLUE).equals(Blocks.ENDER_CHEST)) {

					if (getBlock(Teams.BLUE).equals(Blocks.END_PORTAL)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "and " + ChatColor.WHITE + "Iron Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.BLUE);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					} else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					}
					// send clickable text component

				}
			}

		}

	}

	public Blocks randomBlock() {
		int pick = new Random().nextInt(Blocks.values().length);
		if (!arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {
			int i = 0;
			do {
				int pick2 = new Random().nextInt(Blocks.values().length);
				if (!Blocks.values()[pick2].isSpecial()) {
					i = 1;
					return Blocks.values()[pick2];
				}
			} while (i != 1);

		}
		return Blocks.values()[pick];
	}

	// ADD RECIPE BOOK
	// ADD CUSTOM ITEMS
	// ADD CUSTOM RECIPIES
	public Blocks randomBlock(int difficulty) {

		if (!arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {

			int i = 0;
			do {
				int pick = new Random().nextInt(Blocks.values().length);
				if (Blocks.values()[pick].getDifficulty() == difficulty && !Blocks.values()[pick].isSpecial()) {
					i = 1;
					return Blocks.values()[pick];
				}
			} while (i != 1);

		} else {
			int i = 0;
			do {
				int pick = new Random().nextInt(Blocks.values().length);
				if (Blocks.values()[pick].getDifficulty() == difficulty) {
					i = 1;
					return Blocks.values()[pick];
				}
			} while (i != 1);
		}

		return null;
	}


	public void giveCoins(Player player) {
		HerobrinePVPCore.getFileManager().addCoins(player, 100);
		player.sendMessage(ChatColor.YELLOW + "+100 Coins! (Team Finished)");
	}

	public void giveTrophy(Player player) {
		HerobrinePVPCore.getFileManager().addTrophies(player, 1);
		player.sendMessage(ChatColor.GOLD + "+1 Trophy! (Win)");
	}

	public List<ModifiedTypes> chooseModifiers() {
		int pick = new Random().nextInt(ModifiedTypes.values().length);
		int pick1 = new Random().nextInt(ModifiedTypes.values().length);
		int pick2 = new Random().nextInt(ModifiedTypes.values().length);

		arena.getBHModifiers().add(ModifiedTypes.values()[pick]);
		if (!arena.getBHModifiers().contains(ModifiedTypes.values()[pick1])) {
			arena.getBHModifiers().add(ModifiedTypes.values()[pick1]);
		} else {
			int i = 0;
			do {
				int pick1Backup = new Random().nextInt(ModifiedTypes.values().length);
				if (!arena.getBHModifiers().contains(ModifiedTypes.values()[pick1Backup])) {
					i = 1;
					arena.getBHModifiers().add(ModifiedTypes.values()[pick1Backup]);
				}
			} while (i != 1);
		}

		if (!arena.getBHModifiers().contains(ModifiedTypes.values()[pick2])) {
			arena.getBHModifiers().add(ModifiedTypes.values()[pick2]);

			return arena.getBHModifiers();
		} else {
			int i = 0;
			do {
				int pick2Backup = new Random().nextInt(ModifiedTypes.values().length);
				if (!arena.getBHModifiers().contains(ModifiedTypes.values()[pick2Backup])) {
					i = 1;
					arena.getBHModifiers().add(ModifiedTypes.values()[pick2Backup]);
				}
			} while (i != 1);
		}

		return arena.getBHModifiers();

	}

	public void startBlockHunt(GameType type) {
		System.gc();
		roundsPlayed = 1;
		if (type.equals(GameType.MODIFIER)) {

			chooseModifiers();
		}

		if (arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {
			addRecipies();
		}
		Location spawn = Config.getPlayerSpawn(arena.getID());

		for (UUID uuid : arena.getPlayers()) {
			Player player = Bukkit.getPlayer(uuid);
			player.teleport(spawn);
		}
		ItemStack crafter = new ItemStack(Material.WORKBENCH);
		ItemMeta crafterMeta = crafter.getItemMeta();
		crafterMeta.setDisplayName(ChatColor.GREEN + "Crafting Table" + ChatColor.GRAY + " (Right Click)");
		crafter.setItemMeta(crafterMeta);

		ItemStack boat = new ItemStack(Material.BOAT);
		ItemMeta boatMeta = boat.getItemMeta();
		boatMeta.setDisplayName(ChatColor.AQUA + "SpeedBoat 3000");
		boat.setItemMeta(boatMeta);

		ItemStack recipeBook = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta recipeBookMeta = recipeBook.getItemMeta();
		recipeBookMeta.setDisplayName(ChatColor.GREEN + "Recipe Book");
		recipeBook.setItemMeta(recipeBookMeta);

		arena.setState(GameState.LIVE);

		arena.sendMessage(ChatColor.GRAY + "Picking blocks...");
		Blocks block1 = randomBlock();

		Blocks block2 = randomBlock();

		do {
			block2 = randomBlock();

		} while (block1.getDifficulty() != block2.getDifficulty());
		setBlock(Teams.RED, block1);
		setBlock(Teams.BLUE, block2);
		arena.playSound(Sound.SUCCESSFUL_HIT);
		System.out.println("Block Hunt Game Starting In Arena " + arena.getID() + "!");
		for (UUID uuid : arena.getPlayers()) {
			System.out.println("WORKING ON UUID:" + uuid);
			Player player = Bukkit.getPlayer(uuid);
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("game", "dummy");
			obj.setDisplayName(ChatColor.GREEN + "Block Hunt");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			Team rank = board.registerNewTeam("bharenaID");

			rank.addEntry(ChatColor.BLUE.toString());
			rank.setPrefix(ChatColor.AQUA + "Game ID: ");
			rank.setSuffix(ChatColor.GRAY + "bh" + arena.getID());
			obj.getScore(ChatColor.BLUE.toString()).setScore(9);
			Score blank1 = obj.getScore(" ");
			blank1.setScore(8);
			Team team = board.registerNewTeam("bhteam");
			team.addEntry(ChatColor.RED.toString());
			team.setPrefix(ChatColor.AQUA + "Team: ");
			team.setSuffix(arena.getTeam(player).getDisplay());
			obj.getScore(ChatColor.RED.toString()).setScore(7);

			Team block = board.registerNewTeam("block");
			block.addEntry(ChatColor.GOLD.toString());
			block.setPrefix(ChatColor.AQUA + "Your Block: ");
			block.setSuffix(getBlock(arena.getTeam(player)).getDisplay());
			obj.getScore(ChatColor.GOLD.toString()).setScore(6);

			Score blank2 = obj.getScore("  ");
			blank2.setScore(5);

			Team timer = board.registerNewTeam("bhtimer");
			timer.addEntry(ChatColor.LIGHT_PURPLE.toString());
			timer.setPrefix(ChatColor.AQUA + "Time Left: ");
			String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
			timer.setSuffix(ChatColor.GREEN + time);
			obj.getScore(ChatColor.LIGHT_PURPLE.toString()).setScore(4);

			Team rounds = board.registerNewTeam("rounds");
			rounds.addEntry(ChatColor.RED.toString());
			rounds.setPrefix(ChatColor.AQUA + "Rounds: ");
			String roundsPlayedString = roundsPlayed + "/" + "3";
			rounds.setSuffix(ChatColor.GREEN + roundsPlayedString);

			obj.getScore(ChatColor.RED.toString()).setScore(3);

			Score blank3 = obj.getScore("   ");
			blank3.setScore(2);

			Score ip = obj.getScore(ChatColor.translateAlternateColorCodes('&', "&cherobrinepvp.beastmc.com"));
			ip.setScore(1);

			player.setScoreboard(board);
			player.getInventory().clear();
			player.getInventory().addItem(crafter);
			player.getInventory().addItem(boat);
			if (arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {
				player.getInventory().addItem(recipeBook);

			}

			if (arena.getTeam(player).equals(Teams.RED)) {
				ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET);
				LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
				leatherHelmetMeta.setColor(Color.RED);
				leatherHelmetMeta.setDisplayName(ChatColor.RED + "RED TEAM");
				leatherHelmetMeta.spigot().setUnbreakable(true);
				leatherHelmet.setItemMeta(leatherHelmetMeta);

				ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
				LeatherArmorMeta leatherChestplateMeta = (LeatherArmorMeta) leatherChestplate.getItemMeta();
				leatherChestplateMeta.setColor(Color.RED);
				leatherChestplateMeta.setDisplayName(ChatColor.RED + "RED TEAM");
				leatherChestplateMeta.spigot().setUnbreakable(true);
				leatherChestplate.setItemMeta(leatherChestplateMeta);

				ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
				LeatherArmorMeta leatherLeggingsMeta = (LeatherArmorMeta) leatherLeggings.getItemMeta();
				leatherLeggingsMeta.setColor(Color.RED);
				leatherLeggingsMeta.setDisplayName(ChatColor.RED + "RED TEAM");
				leatherLeggingsMeta.spigot().setUnbreakable(true);
				leatherLeggings.setItemMeta(leatherLeggingsMeta);

				ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS);
				LeatherArmorMeta leatherBootsMeta = (LeatherArmorMeta) leatherBoots.getItemMeta();
				leatherBootsMeta.setColor(Color.RED);
				leatherBootsMeta.setDisplayName(ChatColor.RED + "RED TEAM");
				leatherBootsMeta.spigot().setUnbreakable(true);
				leatherBoots.setItemMeta(leatherBootsMeta);

				player.getEquipment().setHelmet(leatherHelmet);
				player.getEquipment().setChestplate(leatherChestplate);
				player.getEquipment().setLeggings(leatherLeggings);
				player.getEquipment().setBoots(leatherBoots);

			} else if (arena.getTeam(player).equals(Teams.BLUE)) {
				ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET);
				LeatherArmorMeta leatherHelmetMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
				leatherHelmetMeta.setColor(Color.BLUE);
				leatherHelmetMeta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
				leatherHelmetMeta.spigot().setUnbreakable(true);
				leatherHelmet.setItemMeta(leatherHelmetMeta);

				ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
				LeatherArmorMeta leatherChestplateMeta = (LeatherArmorMeta) leatherChestplate.getItemMeta();
				leatherChestplateMeta.setColor(Color.BLUE);
				leatherChestplateMeta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
				leatherChestplateMeta.spigot().setUnbreakable(true);
				leatherChestplate.setItemMeta(leatherChestplateMeta);

				ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
				LeatherArmorMeta leatherLeggingsMeta = (LeatherArmorMeta) leatherLeggings.getItemMeta();
				leatherLeggingsMeta.setColor(Color.BLUE);
				leatherLeggingsMeta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
				leatherLeggingsMeta.spigot().setUnbreakable(true);
				leatherLeggings.setItemMeta(leatherLeggingsMeta);

				ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS);
				LeatherArmorMeta leatherBootsMeta = (LeatherArmorMeta) leatherBoots.getItemMeta();
				leatherBootsMeta.setColor(Color.BLUE);
				leatherBootsMeta.setDisplayName(ChatColor.BLUE + "BLUE TEAM");
				leatherBootsMeta.spigot().setUnbreakable(true);
				leatherBoots.setItemMeta(leatherBootsMeta);

				player.getEquipment().setHelmet(leatherHelmet);
				player.getEquipment().setChestplate(leatherChestplate);
				player.getEquipment().setLeggings(leatherLeggings);
				player.getEquipment().setBoots(leatherBoots);

			} else {
				player.sendMessage(ChatColor.RED
						+ "Couldn't send you to your Team's spawn point! Reason: You are not on a Team/your Team is invalid. Please report this to staff, as you shouldn't be getting this error.");

			}
			if (arena.getBHModifiers().contains(ModifiedTypes.SPEED)) {
				// player.sendMessage(ChatColor.GOLD + "[DEBUG]: You have the speed modifier!");
				player.addPotionEffect(PotionEffectType.SPEED.createEffect(99999999, 1));
			}

			if (arena.getBHModifiers().contains(ModifiedTypes.HASTE)) {
				// player.sendMessage(ChatColor.GOLD + "[DEBUG]: You have the haste modifier!");
				player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(99999999, 1));
			}
		}
		arena.sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&a&m&l----------------------------------------"));
		arena.sendMessage(ChatColor.translateAlternateColorCodes('&', "                   &f&lBlock Hunt"));
		arena.sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&e&lExplore the world, and find or craft your assigned block and stand on it within 5 minutes.\nWe will check whether you're standing on the block every 5 seconds, and at the end of the game."));
		arena.sendMessage(
				ChatColor.translateAlternateColorCodes('&', "&a&m&l----------------------------------------"));

		if (type.equals(GameType.MODIFIER)) {
			arena.sendMessage(ChatColor.GREEN + "This is a modifier game! The following modifiers were selected:");
			arena.sendMessage(ChatColor.GOLD + "- " + arena.getBHModifiers().get(0).getName());
			arena.sendMessage(ChatColor.GOLD + "- " + arena.getBHModifiers().get(1).getName());
			arena.sendMessage(ChatColor.GOLD + "- " + arena.getBHModifiers().get(2).getName());
		}

		arena.sendMessage(ChatColor.GREEN + "Your assigned block is: " + getBlock(Teams.RED).getDisplay(), Teams.RED);
		arena.sendMessage(ChatColor.GREEN + "Your assigned block is: " + getBlock(Teams.BLUE).getDisplay(), Teams.BLUE);
		if (arena.getBHModifiers().contains(ModifiedTypes.SPECIAL_ITEMS)) {

			if (getBlock(Teams.RED).isSpecial()) {
				if (getBlock(Teams.RED).equals(Blocks.NETHERRACK) || getBlock(Teams.RED).equals(Blocks.NETHER_BRICK)
						|| getBlock(Teams.RED).equals(Blocks.NETHER_QUARTZ)) {

					if (getBlock(Teams.RED).equals(Blocks.NETHER_BRICK)
							|| getBlock(Teams.RED).equals(Blocks.NETHERRACK)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.RED
								+ "Redstone Drill " + ChatColor.GREEN + "to get this block.", Teams.RED);
						TextComponent recipe = new TextComponent("Click here to see the recipe!");
						recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
						recipe.setUnderlined(true);
						recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/redstonedrillrecipe"));

						arena.sendSpigotMessage(recipe, Teams.RED);
					}

					else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.RED
								+ "Aspect Of The Nether " + ChatColor.GREEN + "and " + ChatColor.RED + "Redstone Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					}

				}

				else if (getBlock(Teams.RED).equals(Blocks.END_STONE)) {
					arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.WHITE + "Iron Drill "
							+ ChatColor.GREEN + " to get this block.", Teams.RED);

					TextComponent recipe = new TextComponent("Click here to see the recipe!");
					recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					recipe.setUnderlined(true);
					recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/irondrillrecipe"));

					arena.sendSpigotMessage(recipe, Teams.RED);
				} else if (getBlock(Teams.RED).equals(Blocks.END_PORTAL)
						|| getBlock(Teams.RED).equals(Blocks.ENDER_CHEST)) {

					if (getBlock(Teams.RED).equals(Blocks.END_PORTAL)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "and " + ChatColor.WHITE + "Iron Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					} else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.RED);
					}

				}

			}

			if (getBlock(Teams.BLUE).isSpecial()) {
				if (getBlock(Teams.BLUE).equals(Blocks.NETHERRACK) || getBlock(Teams.BLUE).equals(Blocks.NETHER_BRICK)
						|| getBlock(Teams.BLUE).equals(Blocks.NETHER_QUARTZ)) {

					if (getBlock(Teams.BLUE).equals(Blocks.NETHER_BRICK)
							|| getBlock(Teams.BLUE).equals(Blocks.NETHERRACK)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.RED
								+ "Redstone Drill " + ChatColor.GREEN + "to get this block.", Teams.BLUE);
						TextComponent recipe = new TextComponent("Click here to see the recipe!");
						recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
						recipe.setUnderlined(true);
						recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/redstonedrillrecipe"));

						arena.sendSpigotMessage(recipe, Teams.RED);
					}

					else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.RED
								+ "Aspect Of The Nether " + ChatColor.GREEN + "and " + ChatColor.RED + "Redstone Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					}
				}

				else if (getBlock(Teams.BLUE).equals(Blocks.END_STONE)) {
					arena.sendMessage(ChatColor.GREEN + "You're going to need a " + ChatColor.WHITE + "Iron Drill "
							+ ChatColor.GREEN + " to get this block.", Teams.BLUE);

					TextComponent recipe = new TextComponent("Click here to see the recipe!");
					recipe.setColor(net.md_5.bungee.api.ChatColor.GREEN);
					recipe.setUnderlined(true);
					recipe.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/irondrillrecipe"));
					arena.sendSpigotMessage(recipe, Teams.BLUE);
				} else if (getBlock(Teams.BLUE).equals(Blocks.END_PORTAL)
						|| getBlock(Teams.BLUE).equals(Blocks.ENDER_CHEST)) {

					if (getBlock(Teams.BLUE).equals(Blocks.END_PORTAL)) {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "and " + ChatColor.WHITE + "Iron Drill "
								+ ChatColor.GREEN + "to get this block.", Teams.BLUE);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					} else {
						arena.sendMessage(ChatColor.GREEN + "You're going to need an " + ChatColor.BLUE
								+ "Aspect Of The End " + ChatColor.GREEN + "to get this block.", Teams.RED);
						arena.sendMessage(ChatColor.AQUA
								+ "This block also has a custom recipe. Refer to your recipe book (or use /recipes) and coordinate with your team to find out how to make everything you need!",
								Teams.BLUE);
					}
					// send clickable text component

				}
			}

		}

		System.gc();
		startTimer();
	}

	public void startTimer() {
		seconds = 300;
		runnable = new BukkitRunnable() {
			@Override
			public void run() {

				if (arena.getState().equals(GameState.RECRUITING) || arena.getState().equals(GameState.COUNTDOWN)) {
					runnable.cancel();
					removeRecipies();
					runnable = null;
				}
				for (UUID uuid : arena.getPlayers()) {
					Player player = Bukkit.getPlayer(uuid);
					String time = String.format("%02d:%02d", seconds / 60, seconds % 60);

					player.getScoreboard().getTeam("bhtimer").setSuffix(ChatColor.GREEN + time);
				}
				if (seconds == 0) {

					for (UUID uuid : arena.getPlayers()) {
						Player player = Bukkit.getPlayer(uuid);
						player.getEnderChest().clear();
					}
					isGameOver(null);

				}
				// number just after % is the check frequency (example seconds % 5 == 0 it will
				// check every 5 seconds)
				if (seconds % 1 == 0 && seconds != 0) {
					for (UUID uuid : arena.getPlayers()) {
						Player player = Bukkit.getPlayer(uuid);
						player.getEnderChest().clear();
						if (GameListener.aoteCooldown.containsKey(uuid)) {

							if (System.currentTimeMillis() - GameListener.aoteCooldown.get(uuid) >= 3000) {
								player.setWalkSpeed(0.2F);
							}
						}
						// player.sendMessage(ChatColor.GOLD + "[DEBUG] I got inside the loop!");
						// player.sendMessage(ChatColor.GOLD + "[DEBUG] Finished Teams Size: " +
						// finishedTeams.size());
						// player.sendMessage(ChatColor.GOLD + "[DEBUG] Seconds Left: " + seconds);
						String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
						// player.sendMessage(ChatColor.GOLD + "[DEBUG] Timer Should Say: " + time);
						if (!finishedTeams.contains(arena.getTeam(player))) {
							// player.sendMessage(ChatColor.GOLD + "[DEBUG] Your Team is not finished");
							if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()
									.equals(getBlock(arena.getTeam(player)).getMaterial())) {
								// player.sendMessage(ChatColor.GOLD + "[DEBUG] You are on the target block!");

								arena.sendMessage(
										arena.getTeam(player).getDisplay() + ChatColor.GREEN + " Team has finished!");
								arena.playSound(Sound.LEVEL_UP);
								finishedTeams.add(arena.getTeam(player));
								if (arena.getTeam(player).equals(Teams.RED)) {
									redWins.add(getBlock(Teams.RED));
									arena.sendMessage(ChatColor.RED + "RED" + ChatColor.GREEN + " team won the round!"
											+ " They have won a total of " + redWins.size() + " rounds.");
								} else {
									blueWins.add(getBlock(Teams.BLUE));
									arena.sendMessage(ChatColor.BLUE + "BLUE" + ChatColor.GREEN + " team won the round!"
											+ " They have won a total of " + blueWins.size() + " rounds.");

								}

								giveCoins(player);

								for (UUID uuid2 : arena.getPlayers()) {
									Player player2 = Bukkit.getPlayer(uuid2);

									if (arena.getTeam(player2).equals(arena.getTeam(player))
											&& player2.getUniqueId() != player.getUniqueId()) {
										giveCoins(player2);

									}

								}
								isGameOver(arena.getTeam(player));

								if (finishedTeams.size() == 2) {

									arena.sendMessage(ChatColor.RED
								1			+ "FATAL ERROR! STOPPING GAME BEFORE EVERYTHING BREAKS... (ERR CODE: TWO_FINISHED_TEAMS)");
									arena.reset();
									removeRecipies();
									runnable.cancel();

								}

							} else {
								// player.sendMessage(ChatColor.GOLD + "[DEBUG]: You are not on the target
								// block!");
								// player.sendMessage(ChatColor.GOLD + "[DEBUG]: The game thinks you should
								// stand on: "
								// + getBlock(arena.getTeam(player)).getDisplay());
								// player.sendMessage(ChatColor.GOLD + "[DEBUG]: You are standing on: "
								// + player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType());
							}
						}

					}
				}

				if (seconds == 180)

				{
					arena.playSound(Sound.CLICK);
					arena.sendMessage(ChatColor.YELLOW + "The game" + " will end in " + ChatColor.GREEN + "3"
							+ ChatColor.YELLOW + " minutes!");
				}

				if (seconds == 120) {
					arena.playSound(Sound.CLICK);
					arena.sendMessage(ChatColor.YELLOW + "The game" + " will end in " + ChatColor.GREEN + "2"
							+ ChatColor.YELLOW + " minutes!");
				}
				if (seconds == 30) {
					arena.playSound(Sound.CLICK);
					arena.sendMessage(ChatColor.YELLOW + "The game" + " will end in " + ChatColor.GOLD + "30"
							+ ChatColor.YELLOW + " seconds!");
				}
				if (seconds <= 10) {

					if (seconds > 1) {
						arena.playSound(Sound.CLICK);
						arena.sendMessage(ChatColor.YELLOW + "The game" + " will end in " + ChatColor.RED + seconds
								+ ChatColor.YELLOW + " seconds!");
					}
					if (seconds == 1) {
						arena.playSound(Sound.CLICK);
						arena.sendMessage(ChatColor.YELLOW + "The game" + " will end in " + ChatColor.RED + "1"
								+ ChatColor.YELLOW + " second!");
					}

				}

				seconds--;
			}
		};
		runnable.runTaskTimer(BlockHuntMain.getInstance(), 0, 20);
	}

}
