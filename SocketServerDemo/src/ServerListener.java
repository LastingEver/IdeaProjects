import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(54321);
            while (true) {
                //block
                Socket socket = serverSocket.accept();
                //��������
                JOptionPane.showMessageDialog(null, "54321");
                //��socket�����µ��߳�
                ChatSocket chatSocket=new ChatSocket(socket);
                chatSocket.start();
                ChatManager.getChatManager().add(chatSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
