package fr.vadimsoude.toastnotifier;

import fr.vadimsoude.toastnotifier.command.NotifCommand;
import fr.vadimsoude.toastnotifier.notification.Queue;
import fr.vadimsoude.toastnotifier.tools.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class ToastNotifier extends JavaPlugin {

    public static Queue queue = new Queue();

    @Override
    public void onEnable() {

        saveDefaultConfig();

        new Config(this);
        new NotifCommand(this);

        queue.run(this);
    }
}
