package cliServer.src;

//IMPLEMENT GENERICS

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class SocketIdentification{
    private final Socket socket;
    private final Long socketId;
    private final String username;

    public SocketIdentification(Long socketId, Socket clientSocket, String username){
        this.socket = clientSocket;
        this.socketId = socketId;
        this.username = username;
    }

    public List<String, Socket> getSocket() {
        return new List<String, Socket>(username, Socket);
    }

    public Long getSocketId() {
        return socketId;
    }

    public String getUsername(){
        return username;
    }
}


public class Server {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        HashMap < Long, Socket > activeClients = new HashMap <> ();

        try {
            ClientSocketHandler task = new ClientSocketHandler();
            pool.submit(task);

            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening...👂");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Long socketId = System.currentTimeMillis();
                    BufferedReader bufferedReaderName = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String username = bufferedReaderName.readLine();
                    SocketIdentification clientConnection = new SocketIdentification(socketId, socket, username);
                    bufferedReaderName.close();



                    activeClients.put(clientConnection.getSocketId(),clientConnection.getSocket());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


                    while (true) {
                        String messageFromClient = bufferedReader.readLine();
                        System.out.println("Client: " + messageFromClient);
                        bufferedWriter.write("Message received!😄");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        switch (messageFromClient.toLowerCase()) {

                            case "1" -> {
                                for (Long clientSocketId: activeClients.keySet()) {
                                    bufferedWriter.write(String.valueOf(clientSocketId));
                                    bufferedWriter.newLine();

                                }

                                bufferedWriter.write("END");
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                            }
                            case "3" -> {
                                    System.out.println("Closing port in Server🥲");
                                socket.close();
                                bufferedWriter.close();
                                bufferedReader.close();

                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("IOException in server!💥");
                }
            }
        } catch (IOException e) {
            System.out.println("IOException has occurred in server 💥" + e);
        }
    }
}

class ClientSocketHandler implements Runnable {

    @Override
    public void run() {

    }
}