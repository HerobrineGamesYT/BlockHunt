package net.herobrine.wallsg;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.herobrine.core.Emotes;
import net.herobrine.core.HerobrinePVPCore;
import net.herobrine.core.Ranks;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Manager;

public class Shout implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (Manager.isPlaying(player)) {

				if (Manager.getArena(player).getState().equals(GameState.LIVE)
						&& Manager.getGame(Manager.getArena(player)).isTeamGame()) {

					if (args.length > 0) {
						int al = args.length;
						StringBuilder sb = new StringBuilder(args[0]);
						for (int i = 1; i < al; i++) {
							sb.append(' ').append(args[i]);
							// message is sb.toString();

						}
						Ranks rank = HerobrinePVPCore.getFileManager().getRank(player);
						String message = sb.toString();
						if (rank.getPermLevel() >= 5) {
							for (Emotes emote : Emotes.values()) {
								if (message.contains(emote.getKey())) {
									message = message.replaceAll(emote.getKey(), emote.getDisplay());
								}
							}

						}

						for (UUID uuid : Manager.getArena(player).getPlayers()) {

							Player onlinePlayers = Bukkit.getPlayer(uuid);

							if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.MEMBER)) {
								onlinePlayers.sendMessage(ChatColor.GOLD + "[SHOUT] " + rank.getColor() + rank.getName()
										+ " " + player.getName() + ": " + ChatColor.GRAY + message);
							} else if (HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.OWNER)
									|| HerobrinePVPCore.getFileManager().getRank(player).equals(Ranks.ADMIN)) {

								onlinePlayers.sendMessage(ChatColor.GOLD + "[SHOUT] " + rank.getColor() + rank.getName()
										+ " " + player.getName() + ChatColor.RESET + ": "
										+ ChatColor.translateAlternateColorCodes('&', message));

							} else {
								if (HerobrinePVPCore.getFileManager().getRank(player).hasPlusColor()) {
									onlinePlayers
											.sendMessage(ChatColor.GOLD + "[SHOUT] " + rank.getColor() + rank.getName()
													+ HerobrinePVPCore.translateString(HerobrinePVPCore.getFileManager()
															.getPlusColor(player.getUniqueId()) + "+")
													+ HerobrinePVPCore.getFileManager().getRank(player).getColor() + " "
													+ player.getName() + ": " + ChatColor.RESET + message);
								}

								else {
									onlinePlayers
											.sendMessage(ChatColor.GOLD + "[SHOUT] " + rank.getColor() + rank.getName()
													+ " " + player.getName() + ": " + ChatColor.RESET + message);
								}

							}
						}
					}

					else {
						player.sendMessage(ChatColor.RED + "Invalid usage! Usage: /shout <message>");
					}

				} else {
					player.sendMessage(ChatColor.RED + "You must be in a live team game to use this command!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You must be in a game to use this command!");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
		}

		return false;
	}

}