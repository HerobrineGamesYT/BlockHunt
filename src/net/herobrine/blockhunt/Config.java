package net.herobrine.blockhunt;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;

public class Config {
	private static BlockHuntMain BlockHuntMain;

	public Config(BlockHuntMain BlockHuntMain) {
		Config.BlockHuntMain = BlockHuntMain;
		BlockHuntMain.getConfig().options().copyDefaults();
		BlockHuntMain.saveDefaultConfig();
	}

	public static int getRequiredPlayers() {
		return BlockHuntMain.getConfig().getInt("required-players");
	}

	public static int getMaxPlayers() {
		return BlockHuntMain.getConfig().getInt("max-players");
	}

	public static int getCountdownSeconds() {
		return BlockHuntMain.getConfig().getInt("countdown-seconds");
	}

	public static Location getLobbySpawn() {
		return new Location(Bukkit.getWorld(BlockHuntMain.getConfig().getString("lobby-spawn.world")),
				BlockHuntMain.getConfig().getDouble("lobby-spawn.x"),
				BlockHuntMain.getConfig().getDouble("lobby-spawn.y"),
				BlockHuntMain.getConfig().getDouble("lobby-spawn.z"),
				BlockHuntMain.getConfig().getInt("lobby-spawn.yaw"),
				BlockHuntMain.getConfig().getInt("lobby-spawn.pitch"));

	}

	public static Location getLobbyNPCSpawn() {
		return new Location(Bukkit.getWorld(BlockHuntMain.getConfig().getString("lobby-npc-spawn.world")),
				BlockHuntMain.getConfig().getDouble("lobby-npc-spawn.x"),
				BlockHuntMain.getConfig().getDouble("lobby-npc-spawn.y"),
				BlockHuntMain.getConfig().getDouble("lobby-npc-spawn.z"),
				BlockHuntMain.getConfig().getInt("lobby-npc-spawn.yaw"),
				BlockHuntMain.getConfig().getInt("lobby-npc-spawn.pitch"));
	}

	public static Location getArenaSpawn(int id) {
		World world = Bukkit
				.createWorld(new WorldCreator(BlockHuntMain.getConfig().getString("arenas." + id + ".world")));
		world.setAutoSave(false);
		return new Location(world, BlockHuntMain.getConfig().getDouble("arenas." + id + ".x"),
				BlockHuntMain.getConfig().getDouble("arenas." + id + ".y"),
				BlockHuntMain.getConfig().getDouble("arenas." + id + ".z"),
				BlockHuntMain.getConfig().getInt("arenas." + id + ".yaw"),
				BlockHuntMain.getConfig().getInt("arenas." + id + ".pitch"));

	}

	public static World getArenaWorld(int id) {
		return (World) Bukkit.getWorld(BlockHuntMain.getConfig().getString("arenas." + id + ".world"));
	}

	public static int getArenaAmount() {
		return BlockHuntMain.getConfig().getConfigurationSection("arenas.").getKeys(false).size();
	}

	public static Location getPlayerSpawn(int id) {
		int i = 0;
		int xMin = 50;
		int xMax = 5000;
		int zMin = 50;
		int zMax = 5000;
		Random r = new Random();
		World w = Bukkit.getWorld("bhMap" + id);

		do {
			int redx = r.nextInt(xMax - xMin + 1) + xMin;
			int redz = r.nextInt(zMax - zMin + 1) + zMin;
			int redy = w.getHighestBlockYAt(redx, redz) + 3;
			Location redLoc = new Location(w, redx, redy, redz).add(0, 2, 0);

			if (redLoc.getWorld().getBiome(redx, redz).equals(Biome.PLAINS)) {
				i = 1;
				return redLoc;
			}

		} while (i == 0);

		return null;
	}

	public static Location getRedTeamSpawn(int id) {
		return getPlayerSpawn(id);

	}

	public static Location getBlueTeamSpawn(int id) {
		return getPlayerSpawn(id);
	}

}