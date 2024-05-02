package fr.vadimsoude.toastnotifier.tools;

import fr.vadimsoude.toastnotifier.ToastNotifier;
import fr.vadimsoude.toastnotifier.notification.Delays;
import org.bukkit.configuration.ConfigurationSection;
import org.intellij.lang.annotations.Subst;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static ToastNotifier plugin;

    public Config(ToastNotifier plugin) {
        Config.plugin = plugin;
    }

    public static List<String> getImageList() {
        ConfigurationSection sec = plugin.getConfig().getConfigurationSection("images");
        assert sec != null;
        return new ArrayList<>(sec.getKeys(false));
    }

    public static String getPermission() {
        return plugin.getConfig().getString("permission");
    }

    public static Delays getDefaultDelays() {
        return new Delays(
                plugin.getConfig().getInt("notifications.fadeIn"),
                plugin.getConfig().getInt("notifications.stay"),
                plugin.getConfig().getInt("notifications.fadeOut")
        );
    }

    @Subst("minecraft:default")
    public static String getCardFont() {
        return plugin.getConfig().getString("fonts.card");
    }

    @Subst("minecraft:default")
    public static String getTitleFont() {
        return plugin.getConfig().getString("fonts.title");
    }

    @Subst("minecraft:default")
    public static String getFirstRowFont() {
        return plugin.getConfig().getString("fonts.first-row");
    }

    @Subst("minecraft:default")
    public static String getSecondRowFont() {
        return plugin.getConfig().getString("fonts.second-row");
    }

    @Subst("minecraft:default")
    public static String getImageFont(String imageName) {
        return plugin.getConfig().getString("images." + imageName + ".font");
    }

    public static String getImageChar(String imageName) {
        String stringChar = plugin.getConfig().getString("images." + imageName + ".unicode");
        assert stringChar != null;
        int codePoint = Integer.parseInt(stringChar, 16);
        return Character.toString(codePoint);
    }

}
