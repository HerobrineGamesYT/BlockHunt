# BlockHunt
Block Hunt is a game about teamwork and Minecraft skills. Work with your team to find or craft your assigned block and stand on it within 5 minutes to win the round! Best of 3 rounds wins! Also supports modifiers.

This project was developed in 2020, and is the first minigame I ever developed to completion.

Dependencies:

- [Mine] HerobrinePVP-CORE (Main core. It's the backbone for all my custom plugins within this setup.)
- [Mine] GameCore (Lots of minigame-related functions, configuration settings, and arena management. It's the backbone for all my minigame plugins within this setup.)
- [3rd Party] NoteBlockAPI (HBPVP-Core Dependency for playing custom NoteBlock themes - i.e win/draw/lose jingles)


![image](https://user-images.githubusercontent.com/74119793/198857740-dfc894d9-f8f7-478a-a981-6c8ca03b0687.png)

**Here's how the game works**:


  - There are a set of blocks, each with an assigned difficulty number. At the start of the game, both teams are assigned two different blocks with the same difficulty.
  - They are then teleported to a random plains biome in the world, where they should have all the resources they need to get started. The timer also starts counting down.
  - A round ends when a player stands on their team's assigned block, or the timer ends.
  - When a round ends, the player's inventory is cleared, they get teleported to another random location, and each team gets assigned another block of the same difficulty. If the round ended because the timer ran out, the block difficulty is decreased.
  - A new world is generated after each game ends, so each game is unique.
 
  
**Modifier Mode**:
- Before the game starts, players can vote on whether or not the game will be a MODIFIER game.
- In a modifier game, 3 random modifiers are selected and applied to the game. They could make the game easier, they could make the game harder, or change the way you play the game entirley. (See the Special Items section for more info)
![image](https://user-images.githubusercontent.com/74119793/198857880-7f530820-1fdd-460e-affd-36ddd6f7b3e9.png)

![image](https://user-images.githubusercontent.com/74119793/198857818-02a9b841-f922-44a5-8e9d-99341c61c6c7.png)


A full list of modifiers is below:
- Speed
- Haste
- Tool Enchants (Axe and Pickaxe)
- Zombie & Skeleton Armor
- Mob Damage Enabled
- Fall Damage Enabled
- Automatic Smelting
- Special Items Enabled


**Commands**:
Two commands are available for use in-game:

- /teamtp: Teleport to a teammate!
- /top: Instantly teleport to the surface! Also useful if you're stuck.

**Special Items**:
When the Special Items modifier is enabled, things can get a little crazy.

Players will be able to craft 8 different special items, 3 of which are special blocks that can be potentially assigned to a team when this modifier is active.
These special blocks will require you to craft different special items to obtain them. From custom drills that you can use to obtain special blocks from mining, to custom swords that can make mobs have a chance to drop a special item. The two swords themselves also have their own special abilies. These include the Aspect of the Nether, and the Aspect of the End from Hypixel SkyBlock!
The recipes for all of the items are available in the Recipe Book menu that is accessible to players at all times via the "Recipe Book" item in their hotbar.

![image](https://user-images.githubusercontent.com/74119793/198857952-2fbb62b6-c051-4555-8382-bafdde1a18e6.png)
![image](https://user-images.githubusercontent.com/74119793/198857969-9b1026b9-c87f-43dd-a8b2-b89271bfa162.png)
![image](https://user-images.githubusercontent.com/74119793/198858052-e2498ed9-9ab9-4eff-93c7-40fe93522d83.png)
![image](https://user-images.githubusercontent.com/74119793/198858080-953ddd18-bc62-4814-ac6f-811767c8d23c.png)
