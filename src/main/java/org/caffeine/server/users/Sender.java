package org.caffeine.server.users;

import org.caffeine.server.manager.XMPPManager;

public class Sender {
    private static String serverHost = "mukthara-a01.local";
    //private static String serverHost = "10.5.87.97";
    private static int serverPort = 5222;


    public static void main(String[] args) {
        XMPPManager xmppManager = new XMPPManager(serverHost, serverPort);
        try {
            xmppManager.init();

            String userTo = "caffeine" + "@" + serverHost;
            String user = "testuser1" + "@" + serverHost;
            String pass = "testuser1";
            xmppManager.login(user, pass);
            xmppManager.setStatus(true, "Yo! I'm available");

            //xmppManager.createEntry(user, user);

            for (int i = 0; i < 10; i++) {
                xmppManager.sendMessage("Iteration # " + i + " Caffeine, How are you!", userTo);
                Thread.sleep(5000);
            }


            xmppManager.printRoster();
            xmppManager.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
