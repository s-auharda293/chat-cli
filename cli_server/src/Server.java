import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args){
        Socket socket = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter =null;
        ServerSocket serverSocket = null;

        try{
        serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening...👂");
        while(true){
            try{
                socket = serverSocket.accept();
               bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


                while(true){
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println("Client: "+ messageFromClient);
                    bufferedWriter.write("Message received!😄");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(messageFromClient.equalsIgnoreCase("BYE")){

                        System.out.println("Closing port in Server🥲");
                        break;
                    }
                }

                socket.close();
                bufferedWriter.close();
                bufferedReader.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }} catch (IOException e) {
            System.out.println("IOException has occurred in server 💥"+e);
        }
    }
}