package fr.vadimsoude.toastnotifier.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import fr.vadimsoude.toastnotifier.ToastNotifier;
import fr.vadimsoude.toastnotifier.notification.Notification;
import fr.vadimsoude.toastnotifier.tools.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

public class NotifCommand {
    private final ToastNotifier plugin;

    public NotifCommand(ToastNotifier plugin) {
        this.plugin = plugin;
        this.registerNotifCommand();
    }

    private void registerNotifCommand() {

        new CommandAPICommand("notification")
                .withArguments(new PlayerArgument("target"))
                .withArguments(new ListArgumentBuilder<String>("image")
                        .allowDuplicates(false)
                        .withList(Config.getImageList())
                        .withMapper(key -> key)
                        .buildText()
                )
                .withArguments(JsonArgument("json"))
                .withAliases("notif", "toastnotif")
                .withShortDescription("Send a toast notification to a player.")
                .withPermission(CommandPermission.fromString(Config.getPermission()))
                .executes((sender, args) -> {
                    Player target = (Player) args.get("target");
                    List<String> imageNameArg = args.getUnchecked("image");
                    assert imageNameArg != null;
                    String imageName = imageNameArg.get(0);

                    JSONObject json = (JSONObject) args.get("json");

                    List<String> jsonElements = decodeJson(json);

                    if (jsonElements.size() == 3) {
                        Notification notif = new Notification(plugin, target, imageName, jsonElements.get(0), jsonElements.get(1), jsonElements.get(2));
                        notif.send();
                    } else {
                        if (sender instanceof Player) {
                            TextComponent error = Component.text("Json format is invalid ! try again")
                                    .color(TextColor.color(0xF00000));
                            sender.sendMessage(error);
                        }
                    }
                })
                .register();
    }

    public Argument<JSONObject> JsonArgument(String nodeName) {
        return new CustomArgument<JSONObject, String>(new GreedyStringArgument(nodeName), info -> {
            return (JSONObject) JSONValue.parse(info.input());
        });
    }

    public List<String> decodeJson(JSONObject json) {
        List<String> result = new ArrayList<>();
        try {
            result.add(json.get("title").toString());
            result.add(json.get("first-row").toString());
            result.add(json.get("second-row").toString());
        } catch (Exception ignored) {
        }
        return result;
    }
}
