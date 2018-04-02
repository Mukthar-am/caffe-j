package org.caffeine.server.listeners;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class CaffeineMessageListener implements MessageListener {
    public void processMessage(Chat chat, Message message) {
        String from = message.getFrom();
        String body = message.getBody();
        System.out.println(String.format("MsgFrom('%1$s'): [%2$s]", from, body));
    }
}
