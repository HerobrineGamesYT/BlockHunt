package net.herobrine.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.herobrine.gamecore.Arena;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Games;
import net.herobrine.gamecore.Manager;

public class TeamTPCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (Manager.isPlaying(player)) {
				Arena arena = Manager.getArena(player);
				if (Manager.getArena(player).getState().equals(GameState.LIVE)
						&& arena.getGame(arena.getID()).isTeamGame()
						&& arena.getGame(arena.getID()).equals(Games.BLOCK_HUNT)) {

					if (args.length == 0) {
						Menus.applyTeamTPMenu(player);
					}

					if (args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);

						if (target != null) {
							if (target.isOnline()) {
								if (Manager.isPlaying(target)) {
									if (Manager.getArena(target).getID() == Manager.getArena(player).getID()) {
										if (arena.getTeam(target).equals(arena.getTeam(player))) {

											if (target.getUniqueId() != player.getUniqueId()) {
												player.teleport(target.getLocation());
												player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);

												player.sendMessage(ChatColor.GREEN + "Successfully teleported to "
														+ arena.getTeam(player).getColor() + target.getName()
														+ ChatColor.GREEN + "!");
											}
										} else {
											player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
											player.sendMessage(ChatColor.RED + "That player is not your teammate!");
										}

									} else {
										player.sendMessage(ChatColor.RED + "That player isn't in your game!");
									}

								} else {
									player.sendMessage(ChatColor.RED + "That player isn't playing a game!");
								}
							} else {
								player.sendMessage(ChatColor.RED + "That player is not online!");
							}
						} else {
							player.sendMessage(ChatColor.RED + args[0] + " is not a valid player!");
						}
					}

					if (args.length > 1) {
						player.sendMessage(ChatColor.RED + "Invalid usage! Usage: /teamtp <teammate> OR /teamtp");
					}

				} else {
					player.sendMessage(ChatColor.RED + "You can only use this command in a live Block Hunt game!");
				}

			} else {
				player.sendMessage(ChatColor.RED + "You can only use this command in a live game!");
			}

		} else {
			sender.sendMessage(ChatColor.RED + "Only a player can use this command!");
		}
		return false;
	}

}
