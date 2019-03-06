package com.zcloud.reactor.example;

import com.zcloud.reactor.SystemConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MthreadReactor implements Runnable
{

    //subReactors集合, 一个selector代表一个subReactor
    Selector[] selectors=new Selector[2];
    int next = 0;
    final ServerSocketChannel serverSocket;

    MthreadReactor(int port) throws IOException
    { //Reactor初始化
        selectors[0]=Selector.open();
        selectors[1]= Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);


        //分步处理,第一步,接收accept事件
        SelectionKey sk =
                serverSocket.register( selectors[0], SelectionKey.OP_ACCEPT);
        //attach callback object, Acceptor
        sk.attach(new Acceptor());
    }

    public void run()
    {
        try
        {
            while (!Thread.interrupted())
            {
                for (int i = 0; i <2 ; i++)
                {
                    selectors[i].select();
                    Set selected =  selectors[i].selectedKeys();
                    Iterator it = selected.iterator();
                    while (it.hasNext())
                    {
                        //Reactor负责dispatch收到的事件
                        dispatch((SelectionKey) (it.next()));
                    }
                    selected.clear();
                }

            }
        } catch (IOException ex)
        { /* ... */ }
    }

    void dispatch(SelectionKey k)
    {
        Runnable r = (Runnable) (k.attachment());
        //调用之前注册的callback对象
        if (r != null)
        {
            r.run();
        }
    }


    class Acceptor implements Runnable {
        public void run()
        {
            SocketChannel connection =
                    null; //主selector负责accept
            try {
                connection = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null)
            {
                try {
                    new MthreadHandler(selectors[next], connection); //选个subReactor去负责接收到的connection
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (++next == selectors.length) next = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new MthreadReactor(SystemConfig.SOCKET_SERVER_PORT)).start();
    }
}