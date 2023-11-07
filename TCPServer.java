import java.io.*;
import java.net.*;

class TCPServer {

    public static void main(String argv[]) {
        try {
            ServerSocket welcomeSocket = new ServerSocket(6789);
            
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                new ClientHandler(connectionSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

                String clientSentence = inFromClient.readLine();
                String capitalizedSentence = clientSentence.toUpperCase() + '\n';

                outToClient.writeBytes(capitalizedSentence);

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
