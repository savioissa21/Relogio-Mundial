import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Sávio Issa de Sousa
 * @date 17/03/2026
 * @summary Servidor UDP que recebe uma região e devolve a data/hora atual utilizando ZonedDateTime.
 */
public class UDPServer {
    public static void main(String[] args){
        int porta = 9876;

        try(DatagramSocket serverSocket = new DatagramSocket(porta)) {
            System.out.println("Servidor UDP a correr na porta "+ porta);

            byte[] receiveData = new byte[1024];
            while (true) {
                // 1. Prepara o pacote
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                
                // 2. Extrai a região
                String region = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Região solicitada: " + region);

                // 3. Processa a hora
                String response;
                try {
                    ZonedDateTime now = ZonedDateTime.now(ZoneId.of(region));
                    response = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
                } catch (Exception e){
                    response = "Erro: Região '" + region + "' inválida!";
                }

                // 4. Prepara a resposta para enviar de volta
                byte[] sendData = response.getBytes();
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}