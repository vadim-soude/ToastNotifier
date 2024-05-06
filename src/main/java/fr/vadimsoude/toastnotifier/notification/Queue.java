package fr.vadimsoude.toastnotifier.notification;

import fr.vadimsoude.toastnotifier.ToastNotifier;
import fr.vadimsoude.toastnotifier.tools.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Queue {

    private final ConcurrentHashMap<Player, List<Notification>> map;

    public Queue() {
        this.map = new ConcurrentHashMap<>();
    }

    public void run() {
        Delays configDelay = Config.getDefaultDelays();
        int tickDelay = Math.round((configDelay.fadeIn() + configDelay.stay() + configDelay.fadeOut()) / 50f);
        Bukkit.getScheduler().runTaskTimerAsynchronously(ToastNotifier.plugin, () -> {
            map.forEach( (key,value) -> {
                if(value.isEmpty()){
                    map.remove(key);
                }else{
                    key.showTitle(value.get(0).getResult());
                    value.remove(0);
                    map.put(key,value);
                }
            } );
        },0,tickDelay);
    }

    public void sendNotificationInQueue(Notification notification){
        List<Notification> list = new ArrayList<>();
        Player target = notification.getTarget();
        if(map.containsKey(target)){
            list = map.get(target);
        }
        list.add(notification);
        map.put(target, list);
    }

}
