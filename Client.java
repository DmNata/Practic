import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {
    Socket socket;
    Scanner in;
    PrintStream out;
    Server server;

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        new Thread(this).start(); //запускаем поток
    }
    void receive(String message) { // принимаем сообщение с консоли
        out.println(message);
    }
    public void run() {
        try {
            // Входной и выходной потоки для приёма и передачи данных
            InputStream is = socket.getInputStream(); //поток ввода
            OutputStream os = socket.getOutputStream(); // поток вывода

            // Для приёма и отправки данных
            in = new Scanner(is); // ввод-пишем
            out = new PrintStream(os); // вывод-отправляем

            out.println("Welcome to Server");
            String text = in.nextLine();
            while (!text.equals("Bye")) { //выход из сервера

                server.sendAll(text); //Читаем данные с клавиатуры и отправляем серверу
                text = in.nextLine();
            }
            socket.close(); //Закрываем соединение
            Server.numberOfOnline--; // уменьшает число клиентов онлайн при отсоединении клиента
           System.out.println("There are " + Server.numberOfOnline + " clients online");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

