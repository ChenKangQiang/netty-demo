package netty.demo.bio;

import com.alibaba.fastjson.JSON;
import netty.demo.dto.User;

import java.io.*;
import java.net.Socket;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/25
 */
public class PlainOioClient {

    private final String host;
    private final int port;

    public PlainOioClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 5; i++) {
            PlainOioClient client = new PlainOioClient("localhost", 8080);
            client.request();
        }
    }


    public void request() throws IOException {
        Socket socket = new Socket(host, port);
        try {
            //创建一个客户端socket
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new User("Tom", 20));
            out.flush();
            //获取服务器端返回的数据
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            User user = (User) in.readObject();
            System.out.println("Data received from server:" + JSON.toJSONString(user));
            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            socket.close();
        }
    }


}
