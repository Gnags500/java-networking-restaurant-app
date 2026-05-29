//package tcpdiff;


import util.SocketWrapper;

import java.io.IOException;
import java.util.HashMap;

public class ReadThread implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    HashMap<String,SocketWrapper> clientMap;

    public ReadThread(SocketWrapper socketWrapper,HashMap<String,SocketWrapper> clientMap) {
        this.socketWrapper = socketWrapper;
        this.clientMap=clientMap;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
               // String s = (String) socketWrapper.read();
              //  System.out.println(s);
                Object o=(Object)socketWrapper.read();
                if(o instanceof RestaurantData)
                {
                  //  System.out.println("inside the read thread 18th septmber");
                    RestaurantData data=(RestaurantData) o;
                    SocketWrapper socketWrapper1=clientMap.get(data.getRestaurantName());
                    socketWrapper1.write(data);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        /* finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}



