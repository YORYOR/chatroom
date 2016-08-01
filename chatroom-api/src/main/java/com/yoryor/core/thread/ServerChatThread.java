package com.yoryor.core.thread;

import com.yoryor.core.context.ServerContext;
import com.yoryor.core.user.OnlineUser;

public class ServerChatThread implements Runnable{

  private final OnlineUser user;
  private final ServerContext context;

  public ServerChatThread(OnlineUser onlineUser, ServerContext context) {
    this.user = onlineUser;
    this.context = context;
  }


  public void run() {
    while (true) {
      try {
        String msg = user.readMsg();
        if (msg == null || msg.equals("")) {
          continue;
        }
        if (msg.equals("/onLineUser")) {
          user.sendMsgToUser(String.valueOf(context.getOnlineUsers()));
          continue;
        } else if (msg.equals("/exit")) {
          user.logOut();
          context.removeUser(user);
          break;
        }
        context.addMessage(msg, user.getUserName());
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

}
