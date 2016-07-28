package com.yoryor.core.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import com.yoryor.core.msg.Message;
import com.yoryor.core.user.OnlineUser;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class ServerContext {
  private static final int MAX_QUEUE_SIZE = 100;
  private final Set<String> onlineUserSet;
  private final List<OnlineUser> onlineUserList;
  private final int maxOnlineUsers;
  private final ArrayBlockingQueue<Message> messages;

  public ServerContext(int maxOnlineUsers) {
    onlineUserSet = Sets.newHashSet();
    this.maxOnlineUsers = maxOnlineUsers;
    this.messages = Queues.newArrayBlockingQueue(MAX_QUEUE_SIZE);
    this.onlineUserList = Lists.newLinkedList();
  }

  public void addUser(OnlineUser user) {
    if (onlineUserSet.contains(user.getUserName())) {
      System.out.println("user already in");
      return;
    }
    onlineUserList.add(user);
    onlineUserSet.add(user.getUserName());
  }

  public void removeUser(OnlineUser user) {
    onlineUserList.remove(user);
    onlineUserSet.remove(user.getUserName());
  }

  public String getUnSendMessage() throws Exception{
    return messages.take().formatString();
  }

  public void addMessage(String msg, String producer) {
    System.out.println("new msg arrived:" + msg);
    messages.add(new Message(msg, producer));
  }

  public int getOnlineUsers() {
    return onlineUserSet.size();
  }

  public List<OnlineUser> getOnlineUserList() {
    return this.onlineUserList;
  }

}
