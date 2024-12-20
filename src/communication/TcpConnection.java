package communication;

import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TcpConnection implements ConnectionInterface {
    private Socket socket;
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final CopyOnWriteArrayList<MessageListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        setupStreams();
    }

    @Override
    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    @Override
    public void startListening(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(() -> {
            try {
                socket = serverSocket.accept();
                setupStreams();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void sendMessage(String message) throws IOException {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        listeners.add(listener);
    }

    private void setupStreams() throws IOException {
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    for (MessageListener listener : listeners) {
                        listener.onMessageReceived(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
