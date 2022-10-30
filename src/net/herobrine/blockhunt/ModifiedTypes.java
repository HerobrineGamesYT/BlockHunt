package net.herobrine.blockhunt;

public enum ModifiedTypes {

	SPEED("Speed"), HASTE("Haste"), TOOLS("Tool Enchants (Axe and Pickaxe)"), MOB_ARMOR("Zombie & Skeleton Armor"),
	MOB_DAMAGE("Mob Damage Enabled"), FALL_DAMAGE("Fall Damage Enabled"), AUTO_SMELT("Automatic Smelting"),
	SPECIAL_ITEMS("Special Items Enabled");

	private String name;

	private ModifiedTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
