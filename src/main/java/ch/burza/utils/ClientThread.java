package ch.burza.utils;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.Socket;

public class ClientThread implements Runnable {
    @Override
    public void run() {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        for (int port = 10000; port < 10002; port++) {
            System.out.println("Connecting to localhost:" + port);
            try {
                //Create a client SSL socket
                SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(Inet6Address.getLocalHost(), port);
                System.out.println("Client's starting handshake...");
                socket.startHandshake();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Received: " + bufferedReader.readLine());
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}