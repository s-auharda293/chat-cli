package cliClient2.src;

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

            final BufferedReader finalBufferedReader = bufferedReader;

            // Start a background thread that listens for messages from the server
            new Thread(() -> {
                try {
                    String line;
                    while (true) {
                        if (finalBufferedReader.ready()) {
                            line = finalBufferedReader.readLine();
                            if (line != null) {
                                if(line.equalsIgnoreCase("end")){
                                    break;
                                }
                                System.out.println("Message from server: " + line);

                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Read the server's welcome message
            System.out.println(bufferedReader.readLine());

            System.out.println("********** WELCOME TO CHAT CLI **********");

            while (true) {
                System.out.println("For checking availability of all clients press 1");
                System.out.println("For establishing connection to an available client press 2");
                System.out.println("For closing current client connection press 3");

                System.out.print("Enter Option: ");
                String messageFromClient = scanner.nextLine();

                bufferedWriter.write(messageFromClient);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                switch (messageFromClient) {
                    case "1" -> {
                        System.out.println("Available Clients: ");
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.trim().equalsIgnoreCase("end")) {
                                break;
                            }
                            System.out.println(line);
                        }
                    }
                    case "2" -> {
                        System.out.println("Enter message in this format <socket_id> <message>: ");
                        String input = scanner.nextLine();

                        bufferedWriter.write(input);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        String line;
                        if(bufferedReader.ready()){
                            System.out.println(bufferedReader.readLine());
                        }
                    }
                    case "3" -> {
                        System.out.println("Closing connection to server...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }

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
