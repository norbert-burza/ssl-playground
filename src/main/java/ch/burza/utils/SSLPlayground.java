package ch.burza.utils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.ArrayList;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.PrintWriter;

/**
 * Playground for SSL connections.
 *
 */
public class SSLPlayground {
    public static void main(String[] args) throws Exception {
        // A list of sockets
        Collection<SSLServerSocket> serverSockets = new ArrayList<>();

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        // Creating SSL server sockets
        for (int port = 10000; port < 10002; port++) {
            serverSockets.add((SSLServerSocket) sslServerSocketFactory.createServerSocket(port));
        }

        // Start a thread where client sockets will be created
        Runnable clientThread = new ClientThread();
        new Thread(clientThread).start();

        // Accepts connections from clients and sending them a message
        for (SSLServerSocket serverSocket : serverSockets) {
            SSLSocket socket = (SSLSocket) serverSocket.accept();
            System.out.println("Connection for port=" + socket.getLocalPort() + " accepted.");
            socket.setUseClientMode(false);
            System.out.println("Server starts handshake...");
            socket.startHandshake();
            // Send a sample message to the client
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("A simple message from localPort=" + socket.getLocalPort());
            writer.flush();
        }

        // Wait 3 seconds
        try {
            Thread.sleep(3 * 1000);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Close all sockets
        for (ServerSocket serverSocket : serverSockets) {
            serverSocket.close();
        }
    }
}
