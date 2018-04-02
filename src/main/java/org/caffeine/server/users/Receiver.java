package org.caffeine.server.users;

import org.caffeine.server.manager.XMPPManager;

public class Receiver {
    private static String serverHost = "mukthara-a01.local";
    //private static String serverHost = "10.5.87.97";
    private static int serverPort = 5222;


    public static void main(String[] args) {
        XMPPManager xmppManager = new XMPPManager(serverHost, serverPort);
        try {
            xmppManager.init();

            String userTo = "testuser1" + "@" + serverHost;
            String user = "caffeine" + "@" + serverHost;
            String pass = "Mahmed!234";
            xmppManager.login(user, pass);
            xmppManager.setStatus(true, "Yo! I'm available");

            //xmppManager.createEntry(user, user);
            //xmppManager.printRoster();
            //xmppManager.logout();


            for (int i = 0; i < 10; i++) {
                xmppManager.sendMessage("Iteration # " + i + " => " + userTo + ", you there!", userTo);
                Thread.sleep(1000);
            }


            xmppManager.printRoster();
            xmppManager.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
