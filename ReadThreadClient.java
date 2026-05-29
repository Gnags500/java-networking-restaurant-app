//package tcpdiff;


import util.SocketWrapper;

import java.io.IOException;
import java.util.HashMap;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    HashMap<String, SocketWrapper> clientMap;

    public ReadThreadClient(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
        // this.clientMap=clientMap;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                // String s = (String) socketWrapper.read();
                //  System.out.println(s);
                Object o = (Object) socketWrapper.read();
                if (o instanceof RestaurantData) {
                    RestaurantData data = (RestaurantData) o;
                    //SocketWrapper socketWrapper1=clientMap.get(data.getRestaurantName());
                    //socketWrapper1.write(data);
                    System.out.println("NEW Order-------->");
                    for (FoodItems v : data.getFlist()) {
                        v.ShowFood();
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
//        } finally {
//            try {
//                socketWrapper.closeConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        }
    }
}



