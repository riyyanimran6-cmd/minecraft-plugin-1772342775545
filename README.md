# Gem Plugin for Minecraft 1.21

A Paper plugin that gives players a random gem with special abilities when they join.

## Features
- 5 unique gems with different abilities
- Health boost from gems
- Speed boost from gems
- Custom item lore and NBT data

## Gems
1. **Ruby** - Redstone material, +10 HP, +10% speed
2. **Sapphire** - Lapis Lazuli material, +15 HP, +15% speed  
3. **Emerald** - Emerald material, +20 HP, +20% speed
4. **Diamond** - Diamond material, +25 HP, +25% speed
5. **Netherite Gem** - Netherite Ingot material, +30 HP, +30% speed

## Installation
1. Build the plugin using Maven: `mvn clean package`
2. Copy the generated JAR from `target/` to your server's `plugins/` folder
3. Restart your server

## Commands
- `/geminfo` - Show gem information

## Notes
- Gems are given randomly on player join
- Effects are applied immediately after joining
- Gem data is stored in item NBT for persistence