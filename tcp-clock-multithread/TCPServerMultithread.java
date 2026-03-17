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
 * @author [O Teu Nome]
 * @date 17/03/2026
 * @summary Servidor TCP Concorrente (Multithread) que atende múltiplos clientes em simultâneo.
 */
public class TCPServerMultithread {
    public static void main(String[] args) {
        int porta = 9876;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("✅ Servidor TCP Multithread a aguardar conexões na porta " + porta + "...");

            while (true) {
                // 1. Aceita a conexão do cliente
                Socket clientSocket = serverSocket.accept();
                
                // 2. Dispara uma nova Thread para processar esta requisição específica
                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Classe interna para lidar com cada cliente numa Thread separada
class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        // Obtém informações para os logs
        String clientIP = clientSocket.getInetAddress().getHostAddress();
        int clientPort = clientSocket.getPort();
        String threadName = Thread.currentThread().getName();

        // Log exigido: Imprime qual Thread está a cuidar de qual cliente
        System.out.println("🧵 [" + threadName + "] A atender o cliente " + clientIP + ":" + clientPort);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String region = in.readLine();
            
            if (region != null) {
                region = region.trim();
                System.out.println("📡 [" + threadName + "] Região solicitada: " + region);

                String response;
                try {
                    ZonedDateTime now = ZonedDateTime.now(ZoneId.of(region));
                    response = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                } catch (Exception e) {
                    response = "Erro: Região '" + region + "' inválida!";
                }

                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("⚠️ [" + threadName + "] Erro na comunicação: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("🔌 [" + threadName + "] Conexão encerrada com " + clientIP + ":" + clientPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}