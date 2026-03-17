import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author [O Teu Nome]
 * @date 17/03/2026
 * @summary Cliente TCP que conecta ao servidor, envia a região e recebe a hora formatada.
 */
public class TCPClient {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 9876;

        try (Scanner scanner = new Scanner(System.in);
             Socket socket = new Socket(host, porta);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.print("🌍 Digita a região (ex: Europe/London, Asia/Tokyo): ");
            String region = scanner.nextLine();

            out.println(region);

            String resposta = in.readLine();
            System.out.println("🕒 Resposta do Servidor TCP: " + resposta);

        } catch (UnknownHostException e) {
            System.out.println("⚠️ Erro: Servidor não encontrado.");
        } catch (IOException e) {
            System.out.println("⚠️ Erro de I/O: O servidor pode estar offline ou recusou a conexão.");
        }
    }
}