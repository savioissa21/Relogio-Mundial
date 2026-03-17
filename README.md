# Sistema Distribuído de Relógio Mundial (TCP/UDP)

## Resumo do Projeto
Atividade prática desenvolvida para aprofundar os conhecimentos em Sockets Java, explorando as diferenças entre os protocolos UDP e TCP. O sistema permite que um cliente consulte a data e hora exatas de diferentes regiões geográficas.

## Estrutura do Repositório
- `/udp-clock`: Versão 1 - Comunicação via datagramas (UDP) com timeout no cliente.
- `/tcp-clock-simple`: Versão 2 - Comunicação orientada à conexão (TCP Single-Thread).
- `/tcp-clock-multithread`: Versão 3 - Servidor TCP Concorrente (Multithread).

## Análise Técnica (Versão 2 vs Versão 3)
A grande diferença de performance entre a Versão 2 (Single-Thread) e a Versão 3 (Multithread) evidencia-se quando múltiplos clientes se tentam ligar ao mesmo tempo. Na Versão 2, o servidor atende os clientes de forma sequencial; se a ligação de um cliente for lenta, todos os outros ficam bloqueados numa fila de espera do Sistema Operativo. Na Versão 3, ao receber um `accept()`, o servidor cria uma nova `Thread` dedicada a esse cliente. Isto liberta imediatamente o ciclo principal para aceitar a próxima ligação, permitindo o processamento concorrente e tornando o sistema infinitamente mais rápido e escalável sob carga.

## Instruções de Execução (Versão 1 - UDP)
1. Abre o terminal e navega até à pasta `/udp-clock`.
2. Compila os ficheiros Java: `javac UDPServer.java UDPClient.java`
3. Inicia o servidor num terminal: `java UDPServer`
4. Abre outro terminal e inicia o cliente: `java UDPClient`
5. Insere uma região válida (ex: `Europe/Lisbon` ou `America/Sao_Paulo`).

## Instruções de Execução (Versão 2 - TCP Single-Thread)
1. Abre o terminal e navega até à pasta `/tcp-clock-simple`.
2. Compila os ficheiros Java: `javac TCPServer.java TCPClient.java`
3. Inicia o servidor num terminal: `java TCPServer`
4. Abre outro terminal e inicia o cliente: `java TCPClient`

## Instruções de Execução (Versão 3 - TCP Multithread)
1. Abre o terminal e navega até à pasta `/tcp-clock-multithread`.
2. Compila os ficheiros Java: `javac TCPServerMultithread.java TCPClient.java`
3. Inicia o servidor num terminal: `java TCPServerMultithread`
4. Abre vários terminais e inicia o cliente em cada um: `java TCPClient`
5. Observa os registos (logs) no terminal do servidor a mostrar as diferentes Threads em ação.