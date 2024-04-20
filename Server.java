import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static int numberOfOnline; // инициализация счетчика
    public static void main(String[] args) throws IOException {
        new Server().run();
    }
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    Server() throws IOException {
        serverSocket = new ServerSocket(1234); //создаем серверный сокет на порту 1234
    }
    public void sendAll(String message) { // рассылка сообщений всем клиентам по циклу
        for (Client client : clients) {
            client.receive(message); //вызов данного сообщения
        }
    }
    public void run() {
        // Сервер ждет подключения клиентов в бесконечном цикле и каждому подключившемуся клиенту создает
        // свой поток, что позволяет подключаться к серверу более чем 1му потоку одновременно

        while (true) {
            try {
                System.out.println("waiting..");
                Socket socket = serverSocket.accept(); //Ожидает подключение клиента
                clients.add(new Client(socket, this));

                numberOfOnline++; // // Увеличивается счетчик активных клиентов
                System.out.println("client connected " + Server.numberOfOnline); // выводит счетчик

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
