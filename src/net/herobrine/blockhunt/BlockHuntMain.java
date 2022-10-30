package net.herobrine.blockhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.herobrine.core.HerobrinePVPCore;
import net.herobrine.gamecore.GameCoreMain;
import net.herobrine.gamecore.GameState;
import net.herobrine.gamecore.Manager;
import net.herobrine.gamecore.Teams;

public class BlockHuntMain extends JavaPlugin {
	private static BlockHuntMain instance;

//2 players get assigned 2 blocks of the same difficulty, they have 5 minutes to find it somewhere in the world and stand on it. If they don't stand on the block within 5 minutes, they lose.
// if there is a draw, players get assigned new blocks.
// whoever is standing on their block wins.
	@Override
	public void onEnable() {
		BlockHuntMain.instance = this;

		if (getCustomAPI() == null) {
			System.out.println(
					"[BLOCK HUNT] The Herobrine PVP Core was not found. HBPVP-Core is required for the plugin to function, disabling.");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			System.out.println("[BLOCK HUNT] Successfully hooked into Herobrine PVP Core Plugin!");
		}

		new Config(this);

		getCommand("top").setExecutor(new TopCommand());
		getCommand("teamtp").setExecutor(new TeamTPCommand());
		getCommand("stonedrillrecipe").setExecutor(new StoneDrillCMD());
		getCommand("irondrillrecipe").setExecutor(new IronDrillCMD());
		getCommand("redstonedrillrecipe").setExecutor(new RedstoneDrillCMD());
		getCommand("aoterecipe").setExecutor(new AOTECMD());
		getCommand("recipes").setExecutor(new RecipesCommand());

		Bukkit.getPluginManager().registerEvents(new GameListener(), this);

	}

	public static BlockHuntMain getInstance() {
		return instance;
	}

	@Override
	public void onDisable() {
		System.out.println("[BLOCK GAME] Successfully disabled!");

	}

	public void isPlaying(Player player) {
		Manager.isPlaying(player);
	}

	public GameState getState(Player player) {
		if (Manager.isPlaying(player)) {
			return Manager.getArena(player).getState();
		} else {
			return GameState.UNKNOWN;
		}

	}

	public Teams getTeam(Player player) {
		if (Manager.isPlaying(player)) {
			if (Manager.getArena(player).getState().equals(GameState.LIVE)) {
				return Manager.getArena(player).getTeam(player);

			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public HerobrinePVPCore getCustomAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("HBPVP-Core");
		if (plugin instanceof HerobrinePVPCore) {
			return (HerobrinePVPCore) plugin;
		} else {
			return null;
		}
	}

	public GameCoreMain getCoreAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GameCore");

		if (plugin instanceof GameCoreMain) {
			return (GameCoreMain) plugin;
		} else {
			return null;
		}
	}

}
