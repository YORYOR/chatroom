package com.yoryor.core.thread;

import com.yoryor.core.context.ServerContext;
import com.yoryor.core.user.OnlineUser;

import java.util.Iterator;
import java.util.List;

public class MessageSender implements Runnable{
  private final List<OnlineUser> onlineUsers;
  private final ServerContext serverContext;

  public MessageSender (ServerContext context) {
    this.serverContext = context;
    this.onlineUsers = context.getOnlineUserList();
  }

  @Override
  public void run() {
    while (true) {
      try {
        String msg = serverContext.getUnSendMessage();
        System.out.println("ready to send message:" + msg);
        Iterator<OnlineUser> users = onlineUsers.iterator();
        OnlineUser user;
        while (users.hasNext()) {
          user = users.next();
          user.sendMsgToUser(msg);
          System.out.println("send msg to user success|" + user.getUserName());
        }
      } catch (Exception e) {
        System.out.println(e);
      }

    }
  }
}
