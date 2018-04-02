package org.caffeine.server.manager;

import org.caffeine.server.threads.User;
import org.caffeine.server.users.ServerUser;
import org.jivesoftware.smack.XMPPException;

public class Manage {
    private static String serverHost = "mukthara-a01.local";
    private static int serverPort = 5222;


    public static void main(String[] args) {
        String user = "testuser1" + "@" + serverHost;
        String userPass = "testuser1";

        String userTo = "caffeine" + "@" + serverHost;
        String caffeinePass = "Mahmed!234";

//        XMPPManager xmppManager = new XMPPManager(serverHost, serverPort);
//        try {
//            xmppManager.init();
//            xmppManager.login(user, pass);
//            xmppManager.setStatus(true, "Yo! I'm available");
//
//            //xmppManager.createEntry(user, user);
//            //xmppManager.printRoster();
//            //xmppManager.logout();
//
//
//            for (int i = 0; i < 10; i++) {
//                xmppManager.sendMessage("Iteration # " + i + " => " + userTo + ", you there!", userTo);
//                Thread.sleep(1000);
//            }
//
//
//            xmppManager.printRoster();
//            xmppManager.logout();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        User user1 = new User(user, userPass);
//        user1.signIn(serverHost, serverPort);
//        user1.setStatus(true, "Yo! I'm available");
//
//        new Thread(user1).start();
//
//        for (int i = 0; i < 10; i++) {
//            user1.sendMessage("User1-Bot, Iteration # " + i + " => " + userTo + ", you there!", userTo);
//
//            try {
//
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }



        ServerUser user1 = new ServerUser(user, userPass);
        user1.signIn(serverHost, serverPort);
        user1.setStatus(true, "Yo! I'm available");

        for (int i = 1; i <= 10; i++) {
            try {
                user1.sendMessage("Itr-" + i + ", Yo! I'm yours", userTo);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }

        user1.signOut();
        user1.disconnect();
    }
}
