package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shrralis on 2/19/17.
 */
public class ServerTCP implements Runnable {
    private int iServerPort = 6777;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private Thread runningThread = null;

    public static void main(String args[]) {
        new Thread(new ServerTCP(6777)).start();
    }

    public ServerTCP(final int port) {
        iServerPort = port;
    }
    @Override
    public void run() {
        synchronized (this) {
            runningThread = Thread.currentThread();
        }
        openServerSocket();

        while (!isStopped) {
            Socket clientSocket;

            try {
                clientSocket = serverSocket.accept();

                System.out.println("Connecting from IP " + clientSocket.getInetAddress().getHostAddress()
                        + " established!");
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server stopped!");
                    return;
                }
                throw new RuntimeException("Error accepting client connection!", e);
            }
            new Thread(new WorkerRunnable(clientSocket)).start();
        }
        System.out.println("Server stopped!");
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(iServerPort);

            System.out.println("Server successfully started!");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + iServerPort, e);
        }
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }

    public synchronized void stop() {
        isStopped = true;

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing socket!", e);
        }
    }
}
