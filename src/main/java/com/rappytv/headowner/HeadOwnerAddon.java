package com.rappytv.headowner;

import com.rappytv.headowner.listeners.MessageSendListener;
import com.rappytv.headowner.modules.HeadModule;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;

import java.util.List;

public class HeadOwnerAddon extends LabyModAddon {

    public static String prefix = "\u00A7d\u00A7lHeadOwner \u00A78\u00bb \u00A7r";
    public static boolean enabled = true;
    public static int length = 10;
    public static String copyCmd = "copy";

    public static HeadOwnerAddon instance;

    @Override
    public void onEnable() {
        instance = this;
        getApi().registerModule(new HeadModule());
        getApi().getEventService().registerListener(new MessageSendListener());
    }

    @Override
    public void loadConfig() {
        enabled = getConfig().has("enabled") ? getConfig().get("enabled").getAsBoolean() : enabled;
        length = getConfig().has("length") ? getConfig().get("length").getAsInt() : length;
        copyCmd = getConfig().has("copyCmd") ? getConfig().get("copyCmd").getAsString() : copyCmd;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        BooleanElement enabledEl = new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), new Consumer<Boolean>() {

            @Override
            public void accept(Boolean value) {
                enabled = value;

                getConfig().addProperty("enabled", enabled);
                saveConfig();
            }
        }, enabled);

        NumberElement lengthEl = new NumberElement("Block reach distance", new ControlElement.IconData(Material.MAP), length)
                .addCallback((integer) -> {
                    length = integer;

                    getConfig().addProperty("length", length);
                    saveConfig();
                });

        StringElement copyCmdEl = new StringElement("Command to copy head data to clipboard", new ControlElement.IconData(Material.COMMAND), copyCmd, value -> {
            copyCmd = value;

            getConfig().addProperty("copyCmd", copyCmd);
            saveConfig();
        });

        list.add(enabledEl);
        list.add(lengthEl);
        list.add(copyCmdEl);
    }
}
