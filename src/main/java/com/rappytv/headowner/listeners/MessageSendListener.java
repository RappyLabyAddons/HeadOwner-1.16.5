package com.rappytv.headowner.listeners;

import com.rappytv.headowner.HeadOwnerAddon;
import com.rappytv.headowner.util.Util;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.events.client.chat.MessageSendEvent;
import net.labymod.main.LabyMod;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;

public class MessageSendListener {

    @Subscribe
    public void onMessage(final MessageSendEvent event) {
        event.setCancelled(onSend(event.getMessage()));
    }

    public boolean onSend(String s) {
        String[] args = s.split(" ");
        if (HeadOwnerAddon.enabled && args[0].equalsIgnoreCase("/" + HeadOwnerAddon.copyCmd)) {
            Util.Skull skull = Util.getSkullLooking();
            String name = skull.getCopy();
            StringSelection stringSelection = new StringSelection(name);

            if(skull.getDisplay().equalsIgnoreCase("Skull")) {
                LabyMod.getInstance().getLabyModAPI().displayMessageInChat(HeadOwnerAddon.prefix + "\u00A7cNo head found!");
                return true;
            }

            try {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, (ClipboardOwner) null);
                LabyMod.getInstance().getLabyModAPI().displayMessageInChat(HeadOwnerAddon.prefix + "\u00A7eHead Info copied!");
            } catch (IllegalStateException e) {
                e.printStackTrace();
                LabyMod.getInstance().getLabyModAPI().displayMessageInChat(HeadOwnerAddon.prefix + "\u00A7cCan't modify clipboard :/");
            }
            return true;
        }
        return false;
    }
}
