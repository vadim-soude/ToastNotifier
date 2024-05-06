package fr.vadimsoude.toastnotifier.tools;

import fr.vadimsoude.toastnotifier.ToastNotifier;
import fr.vadimsoude.toastnotifier.notification.Delays;
import org.bukkit.configuration.ConfigurationSection;
import org.intellij.lang.annotations.Subst;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static List<String> getImageList() {
        ConfigurationSection sec = ToastNotifier.plugin.getConfig().getConfigurationSection("images");
        assert sec != null;
        return new ArrayList<>(sec.getKeys(false));
    }

    public static String getPermission() {
        return ToastNotifier.plugin.getConfig().getString("permission");
    }

    public static Delays getDefaultDelays() {
        return new Delays(
                ToastNotifier.plugin.getConfig().getInt("notifications.fadeIn"),
                ToastNotifier.plugin.getConfig().getInt("notifications.stay"),
                ToastNotifier.plugin.getConfig().getInt("notifications.fadeOut")
        );
    }

    @Subst("minecraft:default")
    public static String getCardFont() {
        return ToastNotifier.plugin.getConfig().getString("fonts.card");
    }

    @Subst("minecraft:default")
    public static String getTitleFont() {
        return ToastNotifier.plugin.getConfig().getString("fonts.title");
    }

    @Subst("minecraft:default")
    public static String getFirstRowFont() {
        return ToastNotifier.plugin.getConfig().getString("fonts.first-row");
    }

    @Subst("minecraft:default")
    public static String getSecondRowFont() {
        return ToastNotifier.plugin.getConfig().getString("fonts.second-row");
    }

    @Subst("minecraft:default")
    public static String getImageFont(String imageName) {
        return ToastNotifier.plugin.getConfig().getString("images." + imageName + ".font");
    }

    public static String getImageChar(String imageName) {
        String stringChar = ToastNotifier.plugin.getConfig().getString("images." + imageName + ".unicode");
        assert stringChar != null;
        int codePoint = Integer.parseInt(stringChar, 16);
        return Character.toString(codePoint);
    }

}
