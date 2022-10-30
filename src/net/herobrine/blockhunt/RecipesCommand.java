package net.herobrine.blockhunt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Games;
import net.herobrine.gamecore.Manager;

public class RecipesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Manager.isPlaying(player)) {

				if (Manager.getArena(player).getGame(Manager.getArena(player).getID()).equals(Games.BLOCK_HUNT)
						&& Manager.getArena(player).getState().equals(GameState.LIVE)) {

					if (Manager.getArena(player).hasModifier(ModifiedTypes.SPECIAL_ITEMS)) {

						Menus.applyRecipesMenu(player);

					} else {
						player.sendMessage(ChatColor.RED + "You cannot use this command right now!");
					}

				} else {
					player.sendMessage(ChatColor.RED + "You cannot use this command right now!");
				}

			} else {
				player.sendMessage(ChatColor.RED + "You cannot use this command right now!");
			}

		}

		return false;
	}

}
