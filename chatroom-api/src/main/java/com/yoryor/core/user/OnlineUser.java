package com.yoryor.core.user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OnlineUser {
  private final String userName;
  private final BufferedReader reader;
  private final PrintWriter writer;
  private final Socket socket;

  public OnlineUser(Socket socket) throws Exception{
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.writer = new PrintWriter(socket.getOutputStream());
    sendMsgToUser("please input your name first!");
    this.userName = reader.readLine();
    this.socket = socket;
  }


  public void sendMsgToUser(String msg) {
    try {
      if (msg == null || msg.equals(" ")) {
      return;
    }
    writer.println(msg);
    writer.flush();
    } catch (Exception e) {
      System.out.println("send msg error, " + msg + "|" + e);
    }

  }

  public String readMsg() throws Exception{
    return reader.readLine();
  }

  public String getUserName() {
    return userName;
  }

  public void logOut() throws Exception{
    socket.close();
  }

}
