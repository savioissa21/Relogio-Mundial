import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Sávio Issa de Sousa
 * @date 17/03/2026
 * @summary Servidor TCP Single-Thread que responde com a hora da região solicitada.
 */
public class TCPServer {
    public static void main(String[] args) {
        int porta = 9876;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("✅ Servidor TCP Single-Thread aguardando conexões na porta " + porta + "...");

            while (true) {
                // 1. Aceita a conexão do cliente (Bloqueia até alguém conectar)
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // 2. Lê a requisição do cliente
                    String region = in.readLine();
                    
                    if (region != null) {
                        region = region.trim();
                        System.out.println("📡 Cliente conectado! Região solicitada: " + region);

                        // 3. Processa a data e hora
                        String response;
                        try {
                            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(region));
                            response = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                        } catch (Exception e) {
                            response = "Erro: Região '" + region + "' inválida!";
                        }

                        // 4. Envia a resposta de volta e o socket fecha automaticamente ao fim do bloco try
                        out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("⚠️ Erro na comunicação com o cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}