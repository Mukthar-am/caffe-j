package org.caffeine.server.manager;


import org.caffeine.server.listeners.CaffeineChatListener;
import org.caffeine.server.listeners.CaffeineMessageListener;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class XMPPManager {
    private static final int packetReplyTimeout = 500; // millis
    private String server;
    private int port;

    private ConnectionConfiguration config;
    private XMPPConnection connection;
    private CaffeineMessageListener messageListener;
    private ChatManager chatManager;
    private CaffeineChatListener chatManagerListener;


    public XMPPManager(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public XMPPConnection getConnection() {
        return this.connection;
    }

    public XMPPManager disconnect() {
        this.chatManagerListener = null;
        this.messageListener = null;
        this.connection = null;

        return this;
    }

    public void init() throws XMPPException {
        System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));

        SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);

        config = new ConnectionConfiguration(server, port);
        config.setReconnectionAllowed(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setDebuggerEnabled(false);       /** If "true", it sustains a demon thread. */
        config.setSelfSignedCertificateEnabled(false);
        config.setSASLAuthenticationEnabled(true);

        connection = new XMPPConnection(config);
        connection.connect();
        System.out.println("Connected: " + connection.isConnected());


        messageListener = new CaffeineMessageListener();
        chatManagerListener = new CaffeineChatListener(messageListener);

        chatManager = connection.getChatManager();
        chatManager.addChatListener(chatManagerListener);
    }

    public void login(String username, String password) throws XMPPException {
        System.out.println("Logged in as - \"" + username + "\"");
        if (connection != null && connection.isConnected()) {
            connection.login(username, password);
        }
    }

    public void setStatus(boolean available, String status) {
        Presence.Type type = available ? Type.available : Type.unavailable;
        Presence presence = new Presence(type);

        presence.setStatus(status);
        connection.sendPacket(presence);

    }

    public void logout() {
        if (connection != null && connection.isConnected()) {
            connection.disconnect();
        }
    }

    public void sendMessage(String message, String buddyJID) throws XMPPException {
        System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));
        Chat chat = chatManager.createChat(buddyJID, messageListener);
        chat.sendMessage(message);
    }

    public void createEntry(String user, String name) throws Exception {
        System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", user, name));
        Roster roster = connection.getRoster();
        roster.createEntry(user, name, null);
    }

    public void printRoster() {
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        for (RosterEntry entry : entries) {
            System.out.println(String.format("Buddy:%1$s - Status:%2$s",
                    entry.getName(), entry.getStatus()));
        }
    }


}

