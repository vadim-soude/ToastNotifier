package fr.vadimsoude.toastnotifier;

import fr.vadimsoude.toastnotifier.command.NotifCommand;
import fr.vadimsoude.toastnotifier.notification.Queue;
import fr.vadimsoude.toastnotifier.tools.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class ToastNotifier extends JavaPlugin {

    public static Queue queue = new Queue();
    public static ToastNotifier plugin;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        new Config();
        new NotifCommand();

        queue.run();
    }
}
