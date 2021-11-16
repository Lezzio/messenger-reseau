package fr.insalyon.messenger.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HermesServer {

    public static final int SYSTEM_CORES = Runtime.getRuntime().availableProcessors();

    private final Map<String, Socket> connections;
    private ServerSocket serverSocket;
    private boolean running;
    private ConnectionHandler connectionHandler;
    private final ExecutorService executorService = Executors.newFixedThreadPool(SYSTEM_CORES);

    public HermesServer() {
        connections = new HashMap<>();
    }

    public void init(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        running = true;
        while (running) {
            Socket socket = serverSocket.accept();
            executorService.submit(() -> connectionHandler.handleConnection(socket));
        }
    }

    public Map<String, Socket> getConnections() {
        return connections;
    }

    public static void main(String ... args) {
        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        HermesServer listenSocket = new HermesServer(
        ); //port
        try {
            listenSocket.init(Integer.parseInt(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
