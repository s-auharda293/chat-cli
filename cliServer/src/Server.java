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

    public Socket getSocket() {
        return socket;
    }

    public String getUsername(){
        return username;
    }
}


public class Server {
    public static void main(String[] args) {
        HashMap < String, SocketIdentification > activeClients = new HashMap <> ();

        try(ServerSocket serverSocket = new ServerSocket(1234)) {

           System.out.println("Server is listening on port 1234...👂");


            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected!🥳");

                ClientHandler clientHandler = new ClientHandler(clientSocket, activeClients);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

    }catch (IOException e){
            System.out.println("IOException occurred in server");
        }
    }
}




class ClientHandler implements Runnable {
    private final Socket socket;
    private final HashMap<String, SocketIdentification > activeClients;
    private String socketId;

    public ClientHandler(Socket socket, HashMap<String, SocketIdentification> activeClients){
        this.socket = socket;
        this.activeClients = activeClients;
    }

    @Override
    public void run() {
        try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
            this.socketId = UUID.randomUUID().toString();
            SocketIdentification clientConnection = new SocketIdentification(this.socketId, this.socket, bufferedReader.readLine());
            this.activeClients.put(this.socketId, clientConnection);

            bufferedWriter.write("Connection established with client🥳");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            while(true){
                String input = bufferedReader.readLine();
                if(input == null || input.equalsIgnoreCase("3")){
                    break;
                }

                switch (input){
                    case "1"->{
                        for(SocketIdentification id: this.activeClients.values()){
                            bufferedWriter.write("Socket: " + id.getSocketId() +" Username: " + id.getUsername());
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.write("END");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }

                    case "2" -> {
                        bufferedWriter.write("Connection logic for peer chat not implemented yet.");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }

                    case "3" -> {
                        SocketIdentification id = this.activeClients.get(this.socketId);
                        if(id!=null){
                            bufferedWriter.write("Disconnecting " + id.getUsername() + "from server...");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                            this.activeClients.remove(this.socketId);
                        }
                    }
                }
            }

        }catch (IOException e){
            System.out.println("IOException has occurred in Server");
        } finally {
            try {
                if (this.socketId != null) this.activeClients.remove(socketId);
                if (this.socket != null) this.socket.close();
                System.out.println("Client " + this.socketId + " disconnected.");
            } catch (IOException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}