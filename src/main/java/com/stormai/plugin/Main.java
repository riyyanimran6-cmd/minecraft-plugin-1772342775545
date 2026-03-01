package com.stormai.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JavaPlugin implements Listener {

    private List<Gem> gems = new ArrayList<>();
    private Random random = new Random();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        // Initialize gems
        gems.add(new Gem("Ruby", Material.REDSTONE, 10, 0.1f));
        gems.add(new Gem("Sapphire", Material.LAPIS_LAZULI, 15, 0.15f));
        gems.add(new Gem("Emerald", Material.EMERALD, 20, 0.2f));
        gems.add(new Gem("Diamond", Material.DIAMOND, 25, 0.25f));
        gems.add(new Gem("Netherite Gem", Material.NETHERITE_INGOT, 30, 0.3f));

        getLogger().info("Gem plugin enabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Gem randomGem = gems.get(random.nextInt(gems.size()));

        ItemStack gemItem = new ItemStack(randomGem.getMaterial());
        ItemMeta meta = gemItem.getItemMeta();
        meta.setDisplayName("§6" + randomGem.getName() + " Gem");
        List<String> lore = new ArrayList<>();
        lore.add("§7A mystical gem that grants special abilities");
        lore.add("§7Type: " + randomGem.getName());
        lore.add("§7Power: +" + randomGem.getHealthBoost() + " HP");
        lore.add("§7Speed: +" + (randomGem.getSpeedBoost() * 100) + "%");
        meta.setLore(lore);
        gemItem.setItemMeta(meta);

        // Store gem data in item NBT
        NamespacedKey key = new NamespacedKey(this, "gem_data");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, randomGem.getName());

        player.getInventory().addItem(gemItem);
        player.sendMessage("§aYou have received a " + randomGem.getName() + " Gem!");

        // Apply gem effects
        new BukkitRunnable() {
            @Override
            public void run() {
                applyGemEffects(player, randomGem);
            }
        }.runTaskLater(this, 1L);
    }

    private void applyGemEffects(Player player, Gem gem) {
        // Apply health boost
        double currentHealth = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
        player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).setBaseValue(currentHealth + gem.getHealthBoost());
        player.setHealth(player.getHealth() + gem.getHealthBoost());

        // Apply speed boost
        float currentSpeed = player.getWalkSpeed();
        player.setWalkSpeed(Math.min(currentSpeed + gem.getSpeedBoost(), 1.0f));

        // Send effect message
        player.sendMessage("§e" + gem.getName() + " Gem effects applied!");
    }

    private static class Gem {
        private final String name;
        private final Material material;
        private final double healthBoost;
        private final float speedBoost;

        public Gem(String name, Material material, double healthBoost, float speedBoost) {
            this.name = name;
            this.material = material;
            this.healthBoost = healthBoost;
            this.speedBoost = speedBoost;
        }

        public String getName() {
            return name;
        }

        public Material getMaterial() {
            return material;
        }

        public double getHealthBoost() {
            return healthBoost;
        }

        public float getSpeedBoost() {
            return speedBoost;
        }
    }
}