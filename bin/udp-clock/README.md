# Sistema Distribuído de Relógio Mundial (TCP/UDP)

## Resumo do Projeto
Atividade prática desenvolvida para aprofundar os conhecimentos em Sockets Java, explorando as diferenças entre os protocolos UDP e TCP. O sistema permite que um cliente consulte a data e hora exatas de diferentes regiões geográficas.

## Estrutura do Repositório
- `/udp-clock`: Versão 1 - Comunicação via datagramas (UDP) com timeout no cliente.
- `/tcp-clock-simple`: Versão 2 - (A desenvolver).
- `/tcp-clock-multithread`: Versão 3 - (A desenvolver).

## Instruções de Execução (Versão 1 - UDP)
1. Abre o terminal e navega até à pasta `/udp-clock`.
2. Compila os ficheiros Java: `javac UDPServer.java UDPClient.java`
3. Inicia o servidor num terminal: `java UDPServer`
4. Abre outro terminal e inicia o cliente: `java UDPClient`
5. Insere uma região válida (ex: `Europe/Lisbon` ou `America/Sao_Paulo`).