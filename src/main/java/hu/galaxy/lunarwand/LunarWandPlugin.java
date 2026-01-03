package hu.galaxy.lunarwand;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class LunarWandPlugin extends JavaPlugin implements Listener, TabExecutor {

    private NamespacedKey wandKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        wandKey = new NamespacedKey(this, "lunar_wand");
        Objects.requireNonNull(getCommand("lunarwand")).setExecutor(this);
        Objects.requireNonNull(getCommand("lunarwand")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Player only.");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
            p.getInventory().addItem(createWand());
            p.sendMessage("§aMegkaptad a pálcát.");
            return true;
        }
        p.sendMessage("§7Használat: §f/lunarwand give");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return List.of("give");
        return List.of();
    }

    private ItemStack createWand() {
        ItemStack it = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = it.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§d☾ Lunár Kígyó Pálca");
            meta.setLore(List.of(
                    "§7Sebzés: §c150",
                    "§6BAL KATTINTÁS §7– §aKígyó lövedék",
                    "§6JOBB KATTINTÁS §7– §dMimik-Klón (max 2)",
                    "§8• Nincs mana, csak cooldown"
            ));
            meta.getPersistentDataContainer().set(wandKey, PersistentDataType.BYTE, (byte) 1);
            it.setItemMeta(meta);
        }
        return it;
    }
}
