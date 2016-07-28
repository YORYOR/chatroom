package com.yoryor.cli.client;

import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.yoryor.core.thread.ClientChatThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient {

  public interface CommandLineOptions {

    @Option
    String serverIp();

    @Option
    int port();

  }
  private final String serverIp;
  private final int port;
  private Socket socket;

  private final BufferedReader reader;
  private final PrintWriter writer;

  public ChatClient(String ip, int port) throws Exception{
    this.serverIp = ip;
    this.port = port;
    this.socket = new Socket(ip, port);
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream());
  }


  private static CommandLineOptions commandLineOptions;
  public static void main(String[] args) {
    commandLineOptions = CliFactory.parseArguments(CommandLineOptions.class, args);
    try {
      ChatClient client = new ChatClient(commandLineOptions.serverIp(), commandLineOptions.port());
      client.start();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  public void start() throws Exception{
    Scanner getServer = new Scanner(socket.getInputStream());
    //控制台输入流
    Scanner sin = new Scanner(System.in);
    PrintStream sout = new PrintStream(System.out);
    new Thread(new ClientChatThread(sin, new PrintStream(socket.getOutputStream()))).start();
    new Thread(new ClientChatThread(getServer, sout)).start();
  }

}
