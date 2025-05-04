import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class Client{
    public static void main(String[] args){
        Socket socket = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try{
            socket = new Socket("localhost",1234);
           bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

             Scanner scanner = new Scanner(System.in);

            while(true){

                System.out.print("Enter message to send: ");
                String messageToSend = scanner.nextLine();

                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println(bufferedReader.readLine());

                if(messageToSend.equalsIgnoreCase("BYE")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception has occurred in client 💥"+ e);
        } finally {
            try{
                if(socket!=null)
                    socket.close();
                if(bufferedReader!=null)
                    bufferedReader.close();
                if(bufferedWriter!=null)
                    bufferedWriter.close();
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("IO Exception has occurred in client 💥" + e);
            }
        }
    }
}