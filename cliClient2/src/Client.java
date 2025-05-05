package cliClient2.src;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;

public class Client {

        public static void main(String[] args){
            Socket socket = null;
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;

            try{
                socket = new Socket("localhost",1234);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.println("********** WELCOME TO CHAT CLI **********");

                while(true){
                    System.out.println("For checking availability of all clients press 1");
                    System.out.println("For establishing connection to an available client press 2");
                    System.out.println("For exiting press 3");

                    System.out.print("Enter Option: ");
                    Scanner scanner = new Scanner(System.in);
                    String messageFromClient = scanner.nextLine();

                    bufferedWriter.write(messageFromClient);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    System.out.println(bufferedReader.readLine());

                    System.out.println("Available Sockets: "+ bufferedReader.readLine());
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
