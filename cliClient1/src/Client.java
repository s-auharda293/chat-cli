package cliClient1.src;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket("localhost", 1234);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println(bufferedReader.readLine());

            System.out.println("********** WELCOME TO CHAT CLI **********");

            while(true){
                System.out.println("For checking availability of all clients press 1");
                System.out.println("For establishing connection to an available client press 2");

                System.out.print("Enter Option: ");
                String messageFromClient = scanner.nextLine();

                bufferedWriter.write(messageFromClient);
                bufferedWriter.newLine();
                bufferedWriter.flush();


                switch (messageFromClient) {
                    case "1" -> {
                            String line;
                            while((line = bufferedReader.readLine())!=null){
                                if (line.trim().equalsIgnoreCase("end")) {
                                    break;
                            }
                        System.out.println("Available Sockets: \n" + line);
                        }
                    }

                    case "2" -> {

                    }

                }

            }
            /*
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

             */
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception has occurred in client 💥" + e);
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Exception has occurred in client 💥" + e);
            }
        }
    }
}