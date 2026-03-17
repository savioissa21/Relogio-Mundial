import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author Sávio Issa de Sousa
 * @date 17/03/2026
 * @summary Cliente UDP com timeout de 5 segundos para consulta de fuso horário.
 */
public class UDPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String hostname = "localhost";
        int porta = 9876;

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            // Requisito: Timeout de 5 segundos
            clientSocket.setSoTimeout(5000);

            System.out.print("Digita a região (ex: America/Sao_Paulo): ");
            String region = scanner.nextLine();

            InetAddress IPAddress = InetAddress.getByName(hostname);
            byte[] sendData = region.getBytes();

            // Envia o pacote
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
            clientSocket.send(sendPacket);

            // Tenta receber a resposta
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                clientSocket.receive(receivePacket);
                String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("🕒 Resposta do Servidor: " + modifiedSentence);
            } catch (java.net.SocketTimeoutException e) {
                // Requisito: Mensagem de erro caso o servidor não responda
                System.out.println("⚠️ Erro: Servidor ocupado ou offline.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}