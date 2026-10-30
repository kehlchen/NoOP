package info.plugin.minecraft.noop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import java.util.ArrayList;
import java.util.Set;

public final class NoOP extends JavaPlugin implements Listener {
    private static final Set<String> ALLOWED_OPS = Set.of("kehlchen");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        // Alle 2 Sekunden alle Online-Spieler prüfen (40 Ticks)
        getServer().getScheduler().runTaskTimer(this, () -> {
            // Kopie, da während des Kicks die Originalliste geändert werden kann
            for (Player p : new ArrayList<>(Bukkit.getOnlinePlayers())) {
                checkOp(p);
            }
        }, 40L, 40L);
    }

    private void checkOp(Player player) {
        if (!player.isOp() || ALLOWED_OPS.contains(player.getName().toLowerCase())) {
            return;
        }

        player.setOp(false);

        String message = player.getName().equalsIgnoreCase("Legendary_Salmon")
                ? player.getName() + " is trying to admin abuse, like last Season xD"
                : player.getName() + " is trying to admin abuse";

        Bukkit.broadcast(Component.text(message));
        player.kick(Component.text("Kicked because isOp() returned true"));
        getLogger().warning("OP von " + player.getName() + " entfernt und gekickt");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        checkOp(event.getPlayer());
    }
}