package communication;

public interface ConnectionInterface {
    void connect(String host, int port) throws Exception;
    void disconnect() throws Exception;
    void startListening(int port) throws Exception;
    void sendMessage(String message) throws Exception;
    void addMessageListener(MessageListener listener);
}
