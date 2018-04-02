package org.caffeine.server.listeners;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;

public class CaffeineChatListener implements ChatManagerListener {
    private CaffeineMessageListener messageListener;

    public CaffeineChatListener(CaffeineMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void chatCreated(Chat chat, boolean b) {
        chat.addMessageListener(messageListener);
    }
}
