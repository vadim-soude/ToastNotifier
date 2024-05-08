package fr.vadimsoude.toastnotifier.notification;

import fr.vadimsoude.toastnotifier.ToastNotifier;
import fr.vadimsoude.toastnotifier.tools.Config;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class Notification {

    private final Delays delays;
    private final Player target;
    private final String imageName;
    private final String title;
    private final String firstRow;
    private final String secondRow;
    private Title result;

    public Notification(Player target, String imageName, String title, String firstRow, String secondRow) {
        this.target = target;
        this.delays = Config.getDefaultDelays();
        this.imageName = imageName;
        this.title = title;
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.build();
    }

    public Notification(Player target, String imageName, String title, String firstRow, String secondRow, Delays customDelays) {
        this.target = target;
        this.delays = customDelays;
        this.imageName = imageName;
        this.title = title;
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.build();
    }

    private void build() {

        TextComponent cardComponent = Component.text()
                .content("\u00000\u0001")
                .font(Key.key(Config.getCardFont()))
                .build();

        TextComponent imgComponent = Component.text()
                .content(Config.getImageChar(imageName))
                .font(Key.key(Config.getImageFont(imageName)))
                .append(
                        Component.text()
                                .content(Config.getImageChar(imageName))
                                .font(Key.key(Config.getImageFont(imageName) + "-offset"))
                )
                .build();

        TextComponent titleComponent = Component.text()
                .content(title)
                .font(Key.key(Config.getTitleFont()))
                .append(
                        Component.text()
                                .content(title)
                                .font(Key.key(Config.getTitleFont() + "-offset"))
                )
                .build();

        TextComponent firstRowComponent = Component.text()
                .content(firstRow)
                .font(Key.key(Config.getFirstRowFont()))
                .append(
                        Component.text()
                                .content(firstRow)
                                .font(Key.key(Config.getFirstRowFont() + "-offset"))
                )
                .build();

        TextComponent secondRowComponent = Component.text()
                .content(secondRow)
                .font(Key.key(Config.getSecondRowFont()))
                .append(
                        Component.text()
                                .content(secondRow)
                                .font(Key.key(Config.getSecondRowFont() + "-offset"))
                )
                .build();

        TextComponent resultComponent = Component.text()
                .append(
                        cardComponent.append(
                                imgComponent.append(
                                        titleComponent.append(
                                                firstRowComponent.append(
                                                        secondRowComponent
                                                )
                                        )
                                )
                        )
                )
                .color(TextColor.color(0x6604F9))
                .build();

        Title.Times times = Title.Times.times(Duration.ofMillis(delays.fadeIn()), Duration.ofMillis(delays.stay()), Duration.ofMillis(delays.fadeOut()));

        this.result = Title.title(Component.empty(), resultComponent, times);
    }

    public void send() {
        ToastNotifier.queue.sendNotificationToQueue(this);
    }

    public Player getTarget() {
        return target;
    }

    public Title getResult() {
        return result;
    }

    public Delays getDelays() {
        return delays;
    }


}
