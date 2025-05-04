import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter =null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(1234);

        while(true){
            try{
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while(true){
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println("Client: "+ messageFromClient);
                    bufferedWriter.write("Message received!");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(messageFromClient.equalsIgnoreCase("BYE"))
                        break;
                }

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedWriter.close();
                bufferedReader.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
