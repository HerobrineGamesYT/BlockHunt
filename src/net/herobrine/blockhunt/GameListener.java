package net.herobrine.blockhunt;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.herobrine.core.BuildCommand;
import net.herobrine.core.HerobrinePVPCore;
import net.herobrine.core.SongPlayer;
import net.herobrine.core.Songs;
import net.herobrine.gamecore.Arena;
import net.herobrine.gamecore.Countdown;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.GameType;
import net.herobrine.gamecore.Games;
import net.herobrine.gamecore.Manager;
import net.herobrine.gamecore.Teams;

public class GameListener implements Listener {

	public static HashMap<UUID, Long> aoteCooldown = new HashMap<>();
	public static HashMap<UUID, Long> aotnCooldown = new HashMap<>();

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Player player = e.getPlayer();

		if (Manager.isPlaying(player)) {

			int id = Manager.getArena(player).getID();

			Arena arena = Manager.getArena(player);

			if (arena.getGame(id).equals(Games.WALLS_SG)) {
				return;
			}

			else {
				Manager.getArena(player).sendMessage(HerobrinePVPCore.getFileManager().getRank(player).getColor()
						+ player.getName() + ChatColor.YELLOW + " has left!");
				Manager.getArena(player).removePlayer(player);
			}

			if (Manager.getArena(id).getPlayers().size() == 0) {
				Manager.getArena(id).reset();

			}

		}

	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (e.getEntityType() != EntityType.PLAYER) {
			if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
				if (e.getEntity().getKiller().getItemInHand() != null && e.getEntity().getKiller().getItemInHand()
						.getItemMeta().getDisplayName().equals(Items.ASPECT_OF_THE_END.getDisplay())) {
					int randNum = new Random().nextInt(100 - 1 + 1) + 1;

					if (randNum == 1 || randNum == 2) {
						Player player = (Player) e.getEntity().getKiller();
						player.getInventory().addItem(new ItemStack(Material.EYE_OF_ENDER, 6));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lRARE DROP!")
								+ ChatColor.GREEN + " 6x Eye Of Ender" + ChatColor.AQUA + " (2% Chance)");
						SongPlayer.playSong(player, Songs.FIND);

					}
				} else if (e.getEntity().getKiller().getItemInHand() != null
						&& e.getEntity().getKiller().getItemInHand().getItemMeta().getDisplayName()
								.equals(Items.ASPECT_OF_THE_NETHER.getDisplay())) {
					int randNum = new Random().nextInt(100 - 1 + 1) + 1;

					if (randNum == 1) {
						Player player = (Player) e.getEntity().getKiller();
						player.getInventory().addItem(new ItemStack(Material.QUARTZ, 4));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&lCRAZY RARE DROP!")
								+ ChatColor.RED + " 4x Nether Quartz" + ChatColor.AQUA + " (1% Chance)");
						SongPlayer.playSong(player, Songs.FIND_RARE);

					}
				}

			}

		} else {
			Player player = (Player) e.getEntity();

			if (Manager.getArena(player).getGame(Manager.getArena(player).getID()).equals(Games.BLOCK_HUNT)) {
				player.spigot().respawn();
			}

		}

	}

	@EventHandler

	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (e.getSlotType().equals(SlotType.ARMOR) && Manager.isPlaying(player)
				&& !Manager.getArena(player).getGame(Manager.getArena(player).getID()).equals(Games.WALLS_SG)) {
			e.setCancelled(true);
		}

		if (e.getSlotType().equals(SlotType.ARMOR) && Manager.isPlaying(player) && Manager.getArena(player).getGame(Manager.getArena(player).getID()).equals(Games.WALLS_SG)) {
			e.setCancelled(false);
		}

		if (e.getClickedInventory() != null && e.getClickedInventory().getTitle() != null
				&& e.getClickedInventory().getTitle().contains("Recipe")) {

			if (e.getCurrentItem().getType().equals(Material.ARROW)) {
				Menus.applyRecipesMenu(player);
			}

			if (e.getClickedInventory() != null && e.getClickedInventory().getTitle() != null
					&& ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle())
							.equals(ChatColor.GREEN + "Block Hunt Recipe Guide")) {

				if (e.getCurrentItem() != null) {
					switch (e.getCurrentItem().getType()) {

					case DIAMOND_SWORD:
						Menus.applyAOTEMenu(player);
						break;

					case STONE_PICKAXE:
						Menus.applyStoneDrillMenu(player);
						break;

					case IRON_PICKAXE:
						Menus.applyIronDrillMenu(player);

						break;

					case GOLD_PICKAXE:
						Menus.applyRedstoneDrillMenu(player);
						break;

					case GOLD_SWORD:
						Menus.applyAOTNMenu(player);
						break;

					case ENDER_PORTAL_FRAME:
						Menus.applyEnderPortalMenu(player);
						break;
					case ENDER_CHEST:
						Menus.applyEnderChestMenu(player);
						break;

					case QUARTZ_ORE:
						Menus.applyNetherQuartzMenu(player);

						break;
					default:
						break;

					}
				}

			}

			e.setCancelled(true);
		}

		if (e.getClickedInventory() != null && e.getClickedInventory().getTitle() != null
				&& ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle())
						.equals(ChatColor.translateAlternateColorCodes('&', "&bVote for a game type!"))) {

			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
				if (e.getCurrentItem().getType().equals(Material.GRASS)) {
					if (Manager.getArena(player).getState().equals(GameState.COUNTDOWN)) {
						Countdown.registerVote(player, GameType.VANILLA);
						player.closeInventory();

					}
				} else if (e.getCurrentItem().getType().equals(Material.NETHERRACK)) {
					if (Manager.getArena(player).getState().equals(GameState.COUNTDOWN)) {
						Countdown.registerVote(player, GameType.MODIFIER);
						player.closeInventory();
					}
				}

			}

		}

		if (e.getClickedInventory() != null && e.getClickedInventory().getTitle() != null
				&& ChatColor.translateAlternateColorCodes('&', e.getClickedInventory().getTitle())
						.equals(ChatColor.translateAlternateColorCodes('&', "&aChoose a teammate to teleport to!"))) {
			e.setCancelled(true);

			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()) {
				Arena arena = Manager.getArena(player);
				Player target = Bukkit
						.getPlayerExact(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
				player.closeInventory();
				player.teleport(target.getLocation());
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);

				player.sendMessage(ChatColor.GREEN + "Successfully teleported to " + arena.getTeam(player).getColor()
						+ target.getName() + ChatColor.GREEN + "!");
			}

		}
	}

	@EventHandler

	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().hasItemMeta()) {
			if (e.getPlayer().getItemInHand().getType().equals(Material.REDSTONE)
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Leave")
					&& e.getPlayer().getItemInHand().getItemMeta().getEnchants().isEmpty()) {
				if (Manager.isPlaying(e.getPlayer())) {
					Manager.getArena(e.getPlayer())
							.sendMessage(HerobrinePVPCore.getFileManager().getRank(e.getPlayer()).getColor()
									+ e.getPlayer().getName() + ChatColor.YELLOW + " has left!");

					Manager.getArena(e.getPlayer()).removePlayer(e.getPlayer());

				} else {

					e.getPlayer().sendMessage(ChatColor.RED + "You are not in a game!");
				}
			} else if (e.getPlayer().getItemInHand() != null
					&& e.getPlayer().getItemInHand().getType().equals(Material.WRITTEN_BOOK)) {

				return;

			}

			else if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getItemMeta() != null
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.GREEN + "Crafting Table" + ChatColor.GRAY + " (Right Click)")) {

				e.getPlayer().openWorkbench(null, true);

			} else if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getItemMeta() != null
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.AQUA + "Vote for the game type!")) {
				Menus.applyVotingMenu(e.getPlayer());
			}

			if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().hasItemMeta()
					&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
				if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.contains(Items.ASPECT_OF_THE_END.getDisplay())) {

					if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
						if (aoteCooldown.containsKey(e.getPlayer().getUniqueId())) {

							if (System.currentTimeMillis() - aoteCooldown.get(e.getPlayer().getUniqueId()) >= 5000) {
								aoteCooldown.remove(e.getPlayer().getUniqueId());
								Location loc = e.getPlayer().getLocation();
								Vector dir = loc.getDirection();
								dir.normalize();
								dir.multiply(8); // 8 blocks away
								loc.add(dir);
								e.getPlayer().teleport(loc);
								e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
								e.getPlayer().setWalkSpeed(0.4F);
								aoteCooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());

							} else {
								e.getPlayer().sendMessage(ChatColor.RED + "That ability is on cooldown!");
								e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1f, 1f);

							}

						} else {
							Location loc = e.getPlayer().getLocation();
							Vector dir = loc.getDirection();
							dir.normalize();
							dir.multiply(8); // 8 blocks away
							loc.add(dir);
							e.getPlayer().teleport(loc);
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
							e.getPlayer().setWalkSpeed(0.4F);
							aoteCooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
						}

					} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						e.getPlayer().sendMessage(ChatColor.RED + "There is a block in the way!");
					}
				}

				else if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
						.contains(Items.ASPECT_OF_THE_NETHER.getDisplay())) {
					Player player = e.getPlayer();
					if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
							|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

						if (aotnCooldown.containsKey(player.getUniqueId())) {

							if (System.currentTimeMillis() - aotnCooldown.get(player.getUniqueId()) >= 4000) {
								aotnCooldown.remove(player.getUniqueId());
								Fireball fireball = player.launchProjectile(Fireball.class);
								fireball.setShooter(player);
								fireball.setYield(0F);
								fireball.setCustomName(player.getName());
								fireball.setCustomNameVisible(false);
								fireball.setFireTicks(0);
								fireball.setIsIncendiary(false);
								fireball.playEffect(EntityEffect.WITCH_MAGIC);
								player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1f, 1f);
								player.sendMessage(ChatColor.GREEN + "Used " + ChatColor.GOLD + "Fireshot!");
								aotnCooldown.put(player.getUniqueId(), System.currentTimeMillis());
							} else {
								player.sendMessage(ChatColor.RED + "This ability is still on cooldown!");
								player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
							}

						} else {
							Fireball fireball = player.launchProjectile(Fireball.class);
							fireball.setShooter(player);
							fireball.setCustomName(player.getName());
							fireball.setCustomNameVisible(false);
							fireball.setYield(0F);
							fireball.setFireTicks(0);
							fireball.setIsIncendiary(false);
							fireball.playEffect(EntityEffect.WITCH_MAGIC);
							player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1f, 1f);
							player.sendMessage(ChatColor.GREEN + "Used " + ChatColor.GOLD + "Fireshot!");
							aotnCooldown.put(player.getUniqueId(), System.currentTimeMillis());
						}

					}

				}

				if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Recipe Book")) {

					if (e.getAction().equals(Action.RIGHT_CLICK_AIR)
							|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						Menus.applyRecipesMenu(e.getPlayer());
					}

					e.setCancelled(true);

				}

			} else {
				return;
			}

		} else {
			return;
		}

		if (e.getPlayer().getItemInHand() != null
				&& e.getPlayer().getItemInHand().getType().equals(Material.EYE_OF_ENDER)) {
			e.setCancelled(true);
		}

		else {
			return;
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();

		if (Manager.isPlaying(player)) {

			Arena arena = Manager.getArena(player);

			if (arena.getState().equals(GameState.COUNTDOWN) || arena.getState().equals(GameState.RECRUITING)) {
				if (!BuildCommand.buildEnabledPlayers.contains(player)) {
					e.setCancelled(true);
				} else {
					if (!arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {
						e.setCancelled(true);
					}
				}
			} else {
				if (!arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)
						&& !arena.getGame(arena.getID()).equals(Games.WALLS_SG)) {
					e.setCancelled(true);
					return;
				}

				if (arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {

					if (arena.hasModifier(ModifiedTypes.AUTO_SMELT)) {
						if (e.getBlock().getType().equals(Material.IRON_ORE)) {

							e.setCancelled(true);
							e.getBlock().setType(Material.AIR);

							player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));

						} else if (e.getBlock().getType().equals(Material.GOLD_ORE)) {

							e.setCancelled(true);
							e.getBlock().setType(Material.AIR);
							player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
						}
					}

					if (arena.hasModifier(ModifiedTypes.SPECIAL_ITEMS)) {
						if (e.getPlayer().getItemInHand().hasItemMeta()) {

							if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
									.equalsIgnoreCase(ChatColor.RED + "Redstone Drill")) {

								if (e.getBlock().getType().equals(Material.STONE)) {
									int randNum = new Random().nextInt(100 - 1 + 1) + 1;

									if (randNum <= 24 && randNum > 22) {

										e.getBlock().setType(Material.NETHERRACK);
										e.setCancelled(true);
									}

									else if (randNum <= 15 && randNum > 14) {
										e.getBlock().setType(Material.NETHER_BRICK);
										e.setCancelled(true);
									}
								}
							} else if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
									.equalsIgnoreCase(ChatColor.GRAY + "Iron Drill")) {

								if (e.getBlock().getType().equals(Material.STONE)) {
									int randNum = new Random().nextInt(100 - 1 + 1) + 1;

									if (randNum <= 20 && randNum > 19) {

										e.getBlock().setType(Material.ENDER_STONE);
										e.setCancelled(true);
									}

								}

							}

						}

					}

				}
			}

		} else {
			if (!BuildCommand.buildEnabledPlayers.contains(player)) {
				e.setCancelled(true);
			} else {
				return;
			}
		}

	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (Manager.isPlaying(player)) {

			Arena arena = Manager.getArena(player);
			if (!arena.getState().equals(GameState.LIVE) || !arena.getGame(arena.getID()).hasCrafting()) {
				e.setCancelled(true);
				return;
			}

			if(arena.getGame(arena.getID()).hasCrafting() && !arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) return;



			if (e.getInventory().getType().equals(InventoryType.CRAFTING)
					&& !e.getClickedInventory().getTitle().contains("Crafting")) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED + "Right-click the crafter to craft items!");
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
			}

			if (e.getRecipe().getResult().getType().equals(Material.WORKBENCH)) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You already have a crafting table, silly!");
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
			}
			if (Manager.getArena(player).getState().equals(GameState.LIVE)) {
				if (arena.hasModifier(ModifiedTypes.TOOLS)) {
					switch (e.getRecipe().getResult().getType()) {
					case WOOD_PICKAXE:
						ItemStack result = e.getRecipe().getResult().clone();
						ItemMeta meta = result.getItemMeta();
						meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta.addEnchant(Enchantment.DURABILITY, 2, true);
						result.setItemMeta(meta);
						e.getInventory().setResult(result);

						break;
					case WOOD_AXE:
						ItemStack result1 = e.getRecipe().getResult().clone();
						ItemMeta meta1 = result1.getItemMeta();
						meta1.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta1.addEnchant(Enchantment.DURABILITY, 2, true);
						result1.setItemMeta(meta1);
						e.getInventory().setResult(result1);
						break;

					case STONE_PICKAXE:
						ItemStack result11 = e.getRecipe().getResult().clone();
						ItemMeta meta11 = result11.getItemMeta();
						meta11.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta11.addEnchant(Enchantment.DURABILITY, 2, true);
						result11.setItemMeta(meta11);
						e.getInventory().setResult(result11);
						break;

					case STONE_AXE:
						ItemStack result2 = e.getRecipe().getResult().clone();
						ItemMeta meta2 = result2.getItemMeta();
						meta2.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta2.addEnchant(Enchantment.DURABILITY, 2, true);
						result2.setItemMeta(meta2);
						e.getInventory().setResult(result2);
						break;

					case IRON_PICKAXE:
						ItemStack result21 = e.getRecipe().getResult().clone();
						ItemMeta meta21 = result21.getItemMeta();
						meta21.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta21.addEnchant(Enchantment.DURABILITY, 2, true);
						result21.setItemMeta(meta21);
						e.getInventory().setResult(result21);
						break;

					case IRON_AXE:
						ItemStack result211 = e.getRecipe().getResult().clone();
						ItemMeta meta211 = result211.getItemMeta();
						meta211.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta211.addEnchant(Enchantment.DURABILITY, 2, true);
						result211.setItemMeta(meta211);
						e.getInventory().setResult(result211);
						break;

					case GOLD_AXE:
						ItemStack result2111 = e.getRecipe().getResult().clone();
						ItemMeta meta2111 = result2111.getItemMeta();
						meta2111.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta2111.addEnchant(Enchantment.DURABILITY, 2, true);
						result2111.setItemMeta(meta2111);
						e.getInventory().setResult(result2111);
						break;

					case GOLD_PICKAXE:
						ItemStack result3 = e.getRecipe().getResult().clone();
						ItemMeta meta3 = result3.getItemMeta();
						meta3.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta3.addEnchant(Enchantment.DURABILITY, 2, true);
						result3.setItemMeta(meta3);
						e.getInventory().setResult(result3);
						break;

					case DIAMOND_PICKAXE:
						ItemStack result31 = e.getRecipe().getResult().clone();
						ItemMeta meta31 = result31.getItemMeta();
						meta31.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta31.addEnchant(Enchantment.DURABILITY, 2, true);
						result31.setItemMeta(meta31);
						e.getInventory().setResult(result31);
						break;

					case DIAMOND_AXE:
						ItemStack result311 = e.getRecipe().getResult().clone();
						ItemMeta meta311 = result311.getItemMeta();
						meta311.addEnchant(Enchantment.DIG_SPEED, 2, true);
						meta311.addEnchant(Enchantment.DURABILITY, 2, true);
						result311.setItemMeta(meta311);
						e.getInventory().setResult(result311);
						break;

					default:
						return;
					}
				}
			}

		} else {
			if (BuildCommand.buildEnabledPlayers.contains(player)) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();

		if (Manager.isPlaying(player)) {

			Arena arena = Manager.getArena(player);

			if (arena.getState().equals(GameState.COUNTDOWN) || arena.getState().equals(GameState.RECRUITING)) {
				if (!BuildCommand.buildEnabledPlayers.contains(player)) {
					e.setCancelled(true);
				}
			} else {
				if (!arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {
					e.setCancelled(true);
				}

				if (e.getBlock().getType().equals(Material.WORKBENCH)) {
					e.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Right-click the crafter to craft items!");
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);

				}

				else if (e.getItemInHand().hasItemMeta()) {

					if (e.getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.AQUA + "Vote for the game type!")) {

						e.setCancelled(true);
					}
				}

				else {
					e.setCancelled(false);
				}
			}
		} else {
			if (!BuildCommand.buildEnabledPlayers.contains(player)) {
				e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
		}

	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();

		if (Manager.isPlaying(player)) {
			Arena arena = Manager.getArena(player);

			if (e.getItemDrop() != null && e.getItemDrop().getItemStack().getType().equals(Material.WRITTEN_BOOK)) {
				e.setCancelled(true);
			} else if (e.getItemDrop() != null && e.getItemDrop().getItemStack().hasItemMeta()
					&& e.getItemDrop().getItemStack().getItemMeta().getDisplayName() != null
					&& e.getItemDrop().getItemStack().getItemMeta().getDisplayName()
							.equals(ChatColor.GREEN + "Crafting Table" + ChatColor.GRAY + " (Right Click)")) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot drop the crafting tool!");
			}

			else if (e.getItemDrop() != null && e.getItemDrop().getItemStack().hasItemMeta()
					&& e.getItemDrop().getItemStack().getItemMeta().getDisplayName() != null
					&& ChatColor.stripColor(e.getItemDrop().getItemStack().getItemMeta().getDisplayName())
							.contains("TEAM")) {
				e.setCancelled(true);
			}

			else if (e.getItemDrop() != null && e.getItemDrop().getItemStack().hasItemMeta()
					&& e.getItemDrop().getItemStack().getItemMeta().getDisplayName() != null && e.getItemDrop()
							.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Recipe Book")) {
				e.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot drop the recipe book!");
			}
			if (arena.getState().equals(GameState.COUNTDOWN) || arena.getState().equals(GameState.RECRUITING)) {
				if (!BuildCommand.buildEnabledPlayers.contains(player)) {
					e.setCancelled(true);
				}
			}
		} else {
			if (!BuildCommand.buildEnabledPlayers.contains(player)) {
				e.setCancelled(true);
			} else {
				return;
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {

			return;

		}

		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();

			if (Manager.isPlaying(player)) {
				Arena arena = Manager.getArena(player);

				if (arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {
					if (arena.hasModifier(ModifiedTypes.FALL_DAMAGE) && e.getCause().equals(DamageCause.FALL)) {

						e.setCancelled(false);

					} else if (!arena.hasModifier(ModifiedTypes.FALL_DAMAGE) && e.getCause().equals(DamageCause.FALL)) {

						e.setCancelled(true);
					}

					if (!e.getCause().equals(DamageCause.ENTITY_ATTACK) && !e.getCause().equals(DamageCause.FALL)) {
						e.setCancelled(true);
					}
				}

			}

			else {
				e.setCancelled(true);
			}

		}
	}

	@EventHandler

	public void onExplosion(EntityExplodeEvent e) {
		if (e.getEntity() instanceof Fireball) {
			e.setCancelled(true);

			Fireball fireball = (Fireball) e.getEntity();

			Player player = Bukkit.getPlayer(fireball.getCustomName());
			if (Manager.isPlaying(player)) {
				Arena arena = Manager.getArena(player);
				if (arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)
						&& !arena.getGame(arena.getID()).equals(Games.CLASH_ROYALE)) {
					e.getLocation().getWorld().createExplosion(e.getLocation().getX(), e.getLocation().getY(),
							e.getLocation().getZ(), 4.0F, false, false);
					player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1f, .5f);
				}
			}

		} else {
			return;
		}

	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (!e.getEntity().getType().equals(EntityType.PLAYER)) {

			if (e.getDamager() instanceof Fireball) {
				e.setDamage(20.0);
			}

			else {
				return;
			}

		}
		if (e.getDamager() instanceof Player && e.getEntity().getType().equals(EntityType.PLAYER)) {
			if (Manager.getArena((Player) e.getDamager()) != null) {
				Arena arena = Manager.getArena((Player) e.getDamager());
				if (arena.getGame(arena.getID()).isPVPGame()) {

					if (arena.getState().equals(GameState.LIVE)) {
						if (arena.getGame(arena.getID()).isTeamGame()) {
							if (arena.getTeam((Player) e.getDamager()).equals(arena.getTeam((Player) e.getEntity()))
									|| arena.getSpectators().contains(e.getDamager().getUniqueId())) {
								e.setCancelled(true);
							} else {
								Player victim = (Player) e.getEntity();

								if (Manager.isPlaying(victim)) {
									e.setCancelled(false);
								} else {
									e.setCancelled(true);
								}

							}

						} else {

							Player victim = (Player) e.getEntity();

							if (Manager.isPlaying(victim)) {
								e.setCancelled(false);
							} else {
								e.setCancelled(true);
							}

						}
					} else {
						e.setCancelled(true);
					}

				} else {
					e.setCancelled(true);
				}

			}

		} else if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();

			if (Manager.isPlaying(player)) {
				Arena arena = Manager.getArena(player);

				if (arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {
					if (arena.hasModifier(ModifiedTypes.MOB_DAMAGE)) {

						if (e.getDamager() instanceof Fireball && e.getEntity() instanceof Player) {
							e.setCancelled(true);
						} else {
							e.setCancelled(false);
						}

					} else {
						e.setCancelled(true);

					}
				}

			} else {
				e.setCancelled(true);
			}

		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();

		if (Manager.isPlaying(player)) {

			if (Manager.getArena(player).getState().equals(GameState.LIVE)
					&& Manager.getArena(player).getGame(Manager.getArena(player).getID()).equals(Games.BLOCK_HUNT)) {
				Arena arena = Manager.getArena(player);

				if (arena.getTeam(player).equals(Teams.RED)) {

					e.setRespawnLocation(Config.getRedTeamSpawn(arena.getID()));

				} else {

					e.setRespawnLocation(Config.getBlueTeamSpawn(arena.getID()));
				}

			}

		}
	}

	@EventHandler

	public void onRide(VehicleEnterEvent e) {
		if (e.getEntered() instanceof Player && e.getVehicle() instanceof Boat) {
			Boat boat = (Boat) e.getVehicle();
			Player player = (Player) e.getEntered();
			Vector vec = boat.getVelocity();
			boat.setMaxSpeed(10.0);
			boat.setWorkOnLand(true);
			vec.multiply(new Vector(10.0F, 1.0F, 10.0F));

		}

		else if (e.getEntered() instanceof Player && e.getVehicle() instanceof Minecart) {

			Minecart minecart = (Minecart) e.getVehicle();
			Player player = (Player) e.getEntered();
			Vector vec = minecart.getDerailedVelocityMod();

			minecart.setMaxSpeed(15.0);
			player.sendMessage(ChatColor.GOLD + "[DEBUG]: Derailed Minecart Velocity Modifier: " + vec);
			vec.multiply(new Vector(-2F, -2F, -2F));
			player.sendMessage(
					ChatColor.GOLD + "[DEBUG]: Derailed Minecart Velocity Modifier (After Modification): " + vec);

		}

	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (Manager.isGameWorld(e.getEntity().getWorld())) {
			Arena arena = Manager.getArena(e.getEntity().getWorld());

			if (!arena.getState().equals(GameState.LIVE)) {
				e.setCancelled(true);
			}

			else if (!arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {
				if (!arena.getGame(arena.getID()).equals(Games.CLASH_ROYALE)
						&& !arena.getGame(arena.getID()).equals(Games.WALLS_SG)) {
					e.setCancelled(true);
				}

			}

			else {

				if (arena.hasModifier(ModifiedTypes.MOB_ARMOR)) {

					if (e.getEntityType().equals(EntityType.ZOMBIE)) {
						ItemStack iHelmet = new ItemStack(Material.IRON_HELMET);
						ItemStack iChestplate = new ItemStack(Material.IRON_CHESTPLATE);
						ItemStack iLeggings = new ItemStack(Material.IRON_LEGGINGS);
						ItemStack iBoots = new ItemStack(Material.IRON_BOOTS);

						ItemStack iSword = new ItemStack(Material.IRON_SWORD);
						ItemMeta iSwordMeta = iSword.getItemMeta();
						iSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
						iSword.setItemMeta(iSwordMeta);

						e.getEntity().getEquipment().setHelmet(iHelmet);
						e.getEntity().getEquipment().setHelmetDropChance(0.0F);
						e.getEntity().getEquipment().setChestplate(iChestplate);
						e.getEntity().getEquipment().setChestplateDropChance(0.0F);
						e.getEntity().getEquipment().setLeggings(iLeggings);
						e.getEntity().getEquipment().setLeggingsDropChance(0.0F);
						e.getEntity().getEquipment().setBoots(iBoots);
						e.getEntity().getEquipment().setBootsDropChance(0.0F);
						e.getEntity().getEquipment().setItemInHand(iSword);
						e.getEntity().getEquipment().setItemInHandDropChance(0.0F);

					} else if (e.getEntityType().equals(EntityType.SKELETON)) {
						ItemStack iHelmet = new ItemStack(Material.IRON_HELMET);
						ItemStack iChestplate = new ItemStack(Material.IRON_CHESTPLATE);
						ItemStack iLeggings = new ItemStack(Material.IRON_LEGGINGS);
						ItemStack iBoots = new ItemStack(Material.IRON_BOOTS);

						ItemStack iSword = new ItemStack(Material.BOW);
						ItemMeta iSwordMeta = iSword.getItemMeta();
						iSwordMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
						iSwordMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
						iSword.setItemMeta(iSwordMeta);

						e.getEntity().getEquipment().setHelmet(iHelmet);
						e.getEntity().getEquipment().setHelmetDropChance(0.0F);
						e.getEntity().getEquipment().setChestplate(iChestplate);
						e.getEntity().getEquipment().setChestplateDropChance(0.0F);
						e.getEntity().getEquipment().setLeggings(iLeggings);
						e.getEntity().getEquipment().setLeggingsDropChance(0.0F);
						e.getEntity().getEquipment().setBoots(iBoots);
						e.getEntity().getEquipment().setBootsDropChance(0.0F);
						e.getEntity().getEquipment().setItemInHand(iSword);
						e.getEntity().getEquipment().setItemInHandDropChance(0.0F);

					} else {
						return;
					}
				}
			}

		}

	}

	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		if (Manager.isArenaWorld(e.getWorld())) {
			Manager.getArena(e.getWorld()).setJoinState(true);
			Manager.getArena(e.getWorld()).getSpawn().getWorld().setGameRuleValue("keepInventory", "true");
		}
	}
}
