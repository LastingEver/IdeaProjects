import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatSocket extends Thread {

    Socket socket;

    public ChatSocket(Socket socket) {
        this.socket = socket;
    }

    public void out(String out) {
        try {
            socket.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader bufferedReader= null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                ChatManager.getChatManager().publish(this,line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
