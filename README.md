# ToastNotifier !

## Description

*This plugin has been made in about 4 days as a test for Rivrs.*

ToastNotifier is a plugin that allow to send [toast like notification](https://en.wikipedia.org/wiki/Pop-up_notification) to a player screen. This notification can have a title and 2 rows of text, as well as an image !

Example : 

![image](https://github.com/vadim-soude/ToastNotifier/assets/94833069/4922b757-e5e4-4b1e-a699-67ed4a61ba6b)

## Using it

You can send these notifications [using the custom command provided by the plugin](https://github.com/vadim-soude/ToastNotifier?tab=readme-ov-file#The-command-approche), or [using your own plugin with this one as a dependancy](https://github.com/vadim-soude/ToastNotifier?tab=readme-ov-file#The-plugin-approche).

The plugin include a queue system for the notifications, allowing you to send notification to the player and being sure that each of the notifications are display as intended and in the order in which you sent them on the player screen !

This plugin is based around vanilla features, mainly fonts and core shaders, so you will need the resource pack provided with the plugin [See the releases](https://github.com/vadim-soude/ToastNotifier/releases/).

## The command approach

You can send a notification from the built-in command using this simple syntax :
```
/notif <target> <image> {"title":"Your title","first-row":"Your first line","second-row":"Your second line"}
```
The ``<target>`` being the player that will receive the notification and the ``<image>`` being one of the images specified in the config (both of these parameters have auto-completion and suggestion).

## The plugin approach
*This part while be dedicated to Gradle only*

Once your Gradle project is set up, you are going to edit a couple of file :

### gradle.build

Add the repository :
```diff
repositories {
    ...
+    maven {
+        url = uri("https://maven.pkg.github.com/vadim-soude/toastnotifier")
+        credentials {
+            username = findProperty("github.username")
+            password = findProperty("github.token")
+        }
+    }
    ...
}
```
Add the dependency  :
```diff
dependencies {
    ...
+    implementation 'fr.vadimsoude:toastnotifier:1.3'
    ...
}
```
### gradle.properties

Add your GitHub credentials :
```diff
+ github.username=your-github-username-in-lower-case
+ github.token=xxxxxxxxxxxxxxx
```
Get a token here : https://github.com/settings/tokens (You don't need to add any particular permission to it, only for authentication with GitHub Packages) 

**DO NOT SHARE YOUR TOKEN AND DON'T PUSH IT TO ANY GIT REPO, BE CAREFUL !**

### plugin.yml
Add the depend mention :
```diff
+ depend: [ToastNotifier]
```

Add the .jar of the corresponding version of ToastNotifier in the plugin directory of your server (You can found the .jar in the packages section of this repo)

And now everything should work (open an issue if you encounter any problem) you can now use the API to send a notification.

## Plugin Example 

To send a notification you need to provide the target, the image name as defined in the config.yml of ToastNotifier, the title text, the first row text and the second row text.

Then, you need to use the ``.send()`` method to send the notification to the player (the notification will end-up in the queue if another notification is currently displayed).

#### Example of a notification sent to players when they join the server :

```Java
public class ListenerExample implements Listener {
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Notification notification = new Notification(event.getPlayer(), "purple", "Welcome !", "have fun in this", "server .");
        notification.send();
    }
}
```

#### Example of a notification, with custom delays, sent to players when they level up :

```Java
    @EventHandler
    public void onLevelUp(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() > event.getOldLevel()) {
            Delays delay = new Delays(100, 1800, 100);
            Notification notification = new Notification(event.getPlayer(), "light", "Level UP !", "you just gained", "a level.", delay);
            notification.send();
        }
    }
```

# Have fun !

*Readme updated for version 1.3*
