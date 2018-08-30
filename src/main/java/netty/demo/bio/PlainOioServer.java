package netty.demo.bio;

import com.alibaba.fastjson.JSON;
import io.netty.util.CharsetUtil;
import netty.demo.dto.User;
import sun.reflect.generics.scope.Scope;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 未使用Netty的阻塞网络编程
 * @Author: chenkangqiang
 * @Date: 2018/7/16
 */
public class PlainOioServer {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        PlainOioServer plainOioServer = new PlainOioServer();
        try {
            plainOioServer.serve(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serve(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket);
                executorService.execute(new Thread(() -> {
                    try {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        User user = (User) in.readObject();
                        user.setName("Mary");
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(user);
                        out.flush();
                        socket.close();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
        }

    }

}
