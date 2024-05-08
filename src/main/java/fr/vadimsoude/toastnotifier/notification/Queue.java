package fr.vadimsoude.toastnotifier.notification;

import fr.vadimsoude.toastnotifier.ToastNotifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Queue {

    private final ConcurrentHashMap<Player, List<Notification>> map;
    private final ConcurrentHashMap<Player, Long> delay;

    public Queue() {
        this.map = new ConcurrentHashMap<>();
        this.delay = new ConcurrentHashMap<>();
    }

    public void run() {
        long currentTimestamp = System.currentTimeMillis();

        Bukkit.getScheduler().runTaskTimerAsynchronously(ToastNotifier.plugin, () -> {
            map.forEach((key, value) -> {
                if (value.isEmpty()) {
                    map.remove(key);
                } else {
                    if (!delay.containsKey(key) || currentTimestamp > delay.get(key)) {
                        Notification notif = value.get(0);
                        key.showTitle(notif.getResult());
                        value.remove(0);
                        map.put(key, value);
                        delay.put(key, currentTimestamp + notif.getDelays().getTotal());
                    }
                }
            });
        }, 0, 10);
    }

    public void sendNotificationToQueue(Notification notification) {
        List<Notification> list = new ArrayList<>();
        Player target = notification.getTarget();
        if (map.containsKey(target)) {
            list = map.get(target);
        }
        list.add(notification);
        map.put(target, list);
    }

}
