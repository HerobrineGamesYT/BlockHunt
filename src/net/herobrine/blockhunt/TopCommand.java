package net.herobrine.blockhunt;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Games;
import net.herobrine.gamecore.Manager;

public class TopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (Manager.isPlaying(player)) {

				if (Manager.getArena(player).getState().equals(GameState.LIVE) && Manager.getArena(player)
						.getGame(Manager.getArena(player).getID()).equals(Games.BLOCK_HUNT)) {

					player.teleport(
							player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0));
					player.sendMessage(ChatColor.GREEN + "Teleported!");
					player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
				} else {
					player.sendMessage(ChatColor.RED + "You can only use this command in a live Block Hunt game!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You can only use this command in a live Block Hunt game!");
			}

		} else {
			sender.sendMessage(ChatColor.RED + "Only a player can use this command.");
		}

		return false;
	}

}
