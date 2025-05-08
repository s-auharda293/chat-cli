package cliServer.src;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;



class SocketIdentification{
    private final Socket socket;
    private final String socketId;
    private final String username;

    public SocketIdentification(String socketId, Socket clientSocket, String username){
        this.socket = clientSocket;
        this.socketId = socketId;
        this.username = username;
    }

    public String getSocketId() {
        return socketId;
    }

    public Socket getSocket(){
        return socket;
    }

    public String getUsername(){
        return username;
    }
}


public class Server {
    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(10);

        HashMap < String, Socket > activeClients = new HashMap <> ();

        try {
//            ClientSocketHandler task = new ClientSocketHandler();
//            pool.submit(task);

            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening...👂");


            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    String socketId = UUID.randomUUID().toString();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    SocketIdentification clientConnection = new SocketIdentification(socketId, socket, bufferedReader.readLine());

                    bufferedWriter.write("Connection has been successfully established with server!🥳");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    activeClients.put(clientConnection.getSocketId()+" Username: "+clientConnection.getUsername(),clientConnection.getSocket());


                    while (true) {
                        String messageFromClient = bufferedReader.readLine();

                        switch (messageFromClient.toLowerCase()) {

                            case "1" -> {
                                if(activeClients.isEmpty()){
                                    bufferedWriter.write("No active clients!🥺 ");
                                    bufferedWriter.newLine();
                                }else{
                                    for (String clientSocketId: activeClients.keySet()) {
                                        bufferedWriter.write(" SocketId: "+ String.valueOf(clientSocketId));
                                        bufferedWriter.newLine();
                                    }
                                }
                                bufferedWriter.write("END");
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                            }

                            case "2"->{
                               String username = bufferedReader.readLine();
//                                clientConnection = new SocketIdentification(socketId, socket, username);
//
//
//                                activeClients.put(clientConnection.getSocketId(),clientConnection.getSocket());

                                bufferedWriter.write("Connection has been successully registered with Server!");
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