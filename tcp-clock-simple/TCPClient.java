import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Sávio Issa de Sousa
 * @date 17/03/2026
 * @summary Cliente TCP que conecta ao servidor, envia a região e recebe a hora formatada.
 */
public class TCPClient {
    public static void main(String[] args) {
        String host = "localhost";
        int porta = 9876;

        try (Scanner scanner = new Scanner(System.in);
             // 1. Abre a conexão TCP com o servidor
             Socket socket = new Socket(host, porta);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.print("🌍 Digite a região (ex: Europe/London, Asia/Tokyo): ");
            String region = scanner.nextLine();

            // 2. Envia a região pelo fluxo de saída
            out.println(region);

            // 3. Aguarda e lê a resposta do servidor
            String resposta = in.readLine();
            System.out.println("🕒 Resposta do Servidor TCP: " + resposta);

        } catch (UnknownHostException e) {
            System.out.println("⚠️ Erro: Servidor não encontrado.");
        } catch (IOException e) {
            System.out.println("⚠️ Erro de I/O: O servidor pode estar offline ou recusou a conexão.");
        }
    }
}