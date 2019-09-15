# Changelog

## v0.1.2 (27/07/2019

### Added
- Material names ([found here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)) can now be placed in the `blacklist.txt` file (one material per line). Any materials in this file will be blacklisted meaning players cannot place them inside barrels.

## v0.1.1 (27/07/2019)

This version is a quick release which fixes an issue regarding hoppers and barrels that was reported [here](https://github.com/lukecarr/TABBA/issues/1). 

### Added
- The 'simplified' barrel HUD text (the default HUD) now displays the number of stacks inside the barrel and the remainder.

### Changed
- When placing an item into an empty barrel that has a hopper output attached, a null pointer exception no longer occurs.
- Fixed an issue with incorrect white-space formatting on a barrel's HUD text.

## v0.1.0 (23/07/2019)

This is the initial release for TABBA!

Please check out the [wiki](https://github.com/lukecarr/TABBA/wiki) where you can find guides on getting started with the plugin!

In this version there are 5 barrel tiers, however the barrel upgrade items have not been implemented yet. This will be implemented in v0.1.1; in the meantime, you can only create barrels with the lowest tier (they hold 64 stacks).

*You will also need to grab a copy of [Anvil](https://www.spigotmc.org/resources/anvil.69610/) which TABBA requires to function.*
