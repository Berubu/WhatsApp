package isa.whatsapp.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 2121;
    //host de la casa: 192.168.1.13
    //host del tec: 
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clientHandlers.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler aClient : clientHandlers) {
            if (aClient != excludeUser) {
                aClient.sendMessage(message);
            }
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                userName = in.readLine();
                System.out.println(userName + " se ha conectado.");
                broadcast(userName + " se ha unido al chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + message);
                    broadcast(message, this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                socket.close();
                clientHandlers.remove(this);
                System.out.println(userName + " se ha desconectado.");
                broadcast(userName + " ha dejado el chat.", this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        void sendMessage(String message) {
            out.println(message);
        }
    }
}
