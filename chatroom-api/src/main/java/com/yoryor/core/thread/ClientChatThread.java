package com.yoryor.core.thread;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientChatThread implements Runnable{
  private final Scanner in;
  private final PrintStream outPut;
  public ClientChatThread(Scanner in, PrintStream outPut)
  {
    this.in = in;
    this.outPut = outPut;
  }
  public void run() {
    while(in.hasNext())
    {
      outPut.println(in.nextLine());
    }
  }

}
