package ch.burza.utils;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.io.PrintWriter;
import java.net.Inet6Address;
import java.net.Socket;

public class ClientThread implements Runnable {
    @Override
    public void run() {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        for (int port = 10000; port < 10010; port++) {
            System.out.println("creating a socket for " + port);
            try {
                Thread.sleep(1000);
                SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(Inet6Address.getLocalHost(), port);
                socket.startHandshake();;
                System.out.println("isConnected " + socket.isConnected());
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                
                
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}