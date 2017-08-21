package ch.burza.utils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.ArrayList;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Collection<SSLServerSocket> serverSockets = new ArrayList<>();

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        for (int port = 10000; port < 10010; port++) {
            serverSockets.add((SSLServerSocket) sslServerSocketFactory.createServerSocket(port));
        }


        Runnable clientThread = new ClientThread();
        new Thread(clientThread).start();

        for (SSLServerSocket serverSocket : serverSockets) {
            Socket socket = serverSocket.accept();
            System.out.println("Accepted");
            socket.getOutputStream().write(7);
            socket.getOutputStream().flush();
        }

        try {
            

Thread.sleep(3 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {

        }

        for (ServerSocket serverSocket : serverSockets) {
            serverSocket.close();
        }
    }
}
