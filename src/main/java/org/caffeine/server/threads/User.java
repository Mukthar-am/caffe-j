package org.caffeine.server.threads;

import org.caffeine.server.manager.XMPPManager;
import org.jivesoftware.smack.XMPPException;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class User implements Runnable {
    private String USER_NAME;
    private String PASSWORD;
    private boolean IS_ONLINE = false;

    private String HOST = "mukthara-a01.local";
    private int PORT = 5222;

    private XMPPManager XMPP_MANAGER;

    private String MESSAGE = "";
    private String BUDDY_JID = null;

    public User(String userName, String password) {
        this.USER_NAME = userName;
        this.PASSWORD = password;
    }


    /**
     * sign-out logic
     */
    public void signOut() {
        this.IS_ONLINE = false;
        this.XMPP_MANAGER.logout();  // logout
    }

    public void disconnect() {
        this.XMPP_MANAGER.disconnect();
    }

    /**
     * sign-in logic
     */
    public void signIn(String server, int port) {
        this.IS_ONLINE = true;
        this.HOST = server;
        this.PORT = port;

        loginNow();     // login
    }

    private void loginNow() {
        this.XMPP_MANAGER = new XMPPManager(this.HOST, this.PORT);
        try {
            this.XMPP_MANAGER.init();
            this.XMPP_MANAGER.login(this.USER_NAME, this.PASSWORD);

            //XMPP_MANAGER.createEntry(this.USER_NAME, this.USER_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatus(boolean available, String statusMsg) {
        this.XMPP_MANAGER.setStatus(available, statusMsg);
        this.XMPP_MANAGER.printRoster();
    }



    public synchronized void sendMessage(String message, String buddyJid) {
        this.MESSAGE = message;
        this.BUDDY_JID = buddyJid;
    }


    public void run() {
        while (this.IS_ONLINE) {

            if (!this.MESSAGE.isEmpty()) {
                System.out.println("Sending:- " + this.MESSAGE + " To " + this.BUDDY_JID);

                try {
                    this.XMPP_MANAGER.sendMessage(this.MESSAGE, this.BUDDY_JID);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }

                this.MESSAGE = "";
            } else {

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
