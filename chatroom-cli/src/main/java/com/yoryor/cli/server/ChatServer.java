package com.yoryor.cli.server;


import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;
import com.yoryor.core.context.ServerContext;
import com.yoryor.core.thread.MessageSender;
import com.yoryor.core.thread.ServerChatThread;
import com.yoryor.core.user.OnlineUser;

import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {

  public interface CommandLineOptions {

    @Option
    int maxUser();

    @Option
    int port();

  }
  private final int serverPort;
  private final int maxOnlineUser;
  private final ServerContext context;
  private final Thread messageSender;

  public ChatServer(int port, int maxOnlineUser) {
    this.serverPort = port;
    this.maxOnlineUser = maxOnlineUser;
    this.context = new ServerContext(maxOnlineUser);
    this.messageSender = new Thread(new MessageSender(context));
  }
  private static CommandLineOptions commandLineOptions;
  public static void main(String[] args) {
    try {
      commandLineOptions = CliFactory.parseArguments(CommandLineOptions.class, args);
      ChatServer server = new ChatServer(commandLineOptions.port(), commandLineOptions.maxUser());
      server.start();
    }catch (Exception e) {
      System.out.println(e);
    }
  }

  public void start() throws Exception{
    ServerSocket server = new ServerSocket(serverPort);
    System.out.println("server start");
    messageSender.start();
    while (true) {
      try {
        Socket socket = server.accept();
        OnlineUser user = new OnlineUser(socket);
        context.addUser(user);
        new Thread(new ServerChatThread(user, context)).start();
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }


}
