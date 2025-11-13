
import java.util.*;

// Projeto simples: Pizzaria Java Delícia (tudo em um arquivo - opção A)
public class MainPizzaria {

    // Classe interna Pedido
    static class Pedido {
        int numero;
        String cliente;
        String sabor;
        char tamanho; // P, M, G
        double valor;
        int tempoEstimado; // em minutos, para prioridade

        public Pedido(int numero, String cliente, String sabor, char tamanho, double valor, int tempoEstimado) {
            this.numero = numero;
            this.cliente = cliente;
            this.sabor = sabor;
            this.tamanho = tamanho;
            this.valor = valor;
            this.tempoEstimado = tempoEstimado;
        }

        @Override
        public String toString() {
            return String.format("#%d - %s | %s (%c) R$ %.2f | tempo=%dmin",
                    numero, cliente, sabor, tamanho, valor, tempoEstimado);
        }
    }

    // Coleções do sistema
    static Set<String> sabores = new HashSet<>();
    static List<Pedido> pedidosAbertos = new ArrayList<>();
    static Queue<Pedido> filaEntregas = new LinkedList<>();
    // PriorityQueue por tempoEstimado (menor tempo = maior prioridade)
    static PriorityQueue<Pedido> pedidosPrioritarios = new PriorityQueue<>(Comparator.comparingInt(p -> p.tempoEstimado));
    static Stack<Pedido> historicoCancelados = new Stack<>();
    static Map<String, Integer> vendasPorSabor = new HashMap<>();

    static int contadorPedidos = 1;
    static Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("=== Pizzaria Java Delícia - Sistema (versão simplificada) ===\n");

        // Demonstração: popular sabores
        adicionarSabor("Calabresa");
        adicionarSabor("Mussarela");
        adicionarSabor("Portuguesa");
        adicionarSabor("Frango com Catupiry");
        adicionarSabor("Quatro Queijos");
        adicionarSabor("Calabresa"); // duplicado (será ignorado)

        listarSabores();

        // Registrar alguns pedidos
        Pedido p1 = criarPedido("Lucas", "Calabresa", 'M', 32.50, 25);
        Pedido p2 = criarPedido("Ana", "Mussarela", 'G', 45.00, 35);
        Pedido p3 = criarPedido("Carlos", "Quatro Queijos", 'P', 28.00, 20);
        Pedido p4 = criarPedido("Sofia", "Portuguesa", 'G', 47.50, 15); // rápido - pode ser prioritário
        Pedido p5 = criarPedido("Marina", "Frango com Catupiry", 'M', 39.00, 30);

        // Mostrar pedidos abertos
        System.out.println("\n--- Pedidos Abertos (ArrayList) ---");
        listarPedidos();

        // Buscar pedido pelo número
        System.out.println("\nBusca pelo número 3:");
        Pedido busc = buscarPedidoPorNumero(3);
        System.out.println(busc != null ? busc : "Pedido não encontrado");

        // Ordenar pedidos por valor (decrescente) e por cliente (alfabético)
        System.out.println("\nOrdenar por valor (decrescente):");
        Collections.sort(pedidosAbertos, Comparator.comparingDouble((Pedido p) -> p.valor).reversed());
        listarPedidos();

        System.out.println("\nOrdenar por nome do cliente (crescente):");
        Collections.sort(pedidosAbertos, Comparator.comparing(p -> p.cliente));
        listarPedidos();

        // Simular fila de entregas (Queue)
        System.out.println("\n--- Fila de Entregas (Queue) ---");
        filaEntregas.offer(p1);
        filaEntregas.offer(p2);
        filaEntregas.offer(p3);
        System.out.println("Fila atual: " + filaToString());
        System.out.println("Próximo (peek): " + filaEntregas.peek());
        filaEntregas.poll(); // entregar o primeiro
        System.out.println("Após entregar 1: " + filaToString());

        // Pedidos prioritários (PriorityQueue)
        System.out.println("\n--- Pedidos Prioritários (PriorityQueue) ---");
        pedidosPrioritarios.offer(p4); // tempo 15
        pedidosPrioritarios.offer(p2); // tempo 35
        pedidosPrioritarios.offer(p3); // tempo 20
        pedidosPrioritarios.offer(p5); // tempo 30
        System.out.println("Peek (menor tempo): " + pedidosPrioritarios.peek());
        System.out.print("Removendo todos por prioridade (do menor tempo ao maior): ");
        while (!pedidosPrioritarios.isEmpty()) {
            System.out.print(pedidosPrioritarios.poll().numero + " ");
        }
        System.out.println();

        // Cancelamentos (Stack)
        System.out.println("\n--- Cancelamentos (Stack) ---");
        historicoCancelados.push(p3);
        historicoCancelados.push(p5);
        System.out.println("Último cancelado (peek): " + historicoCancelados.peek());
        System.out.println("Recuperando último cancelado (pop): " + historicoCancelados.pop());
        System.out.println("Agora último cancelado: " + (historicoCancelados.isEmpty() ? "nenhum" : historicoCancelados.peek()));

        // Registrar vendas por sabor (HashMap)
        registrarVenda("Calabresa");
        registrarVenda("Mussarela");
        registrarVenda("Calabresa");
        registrarVenda("Quatro Queijos");
        registrarVenda("Calabresa");
        System.out.println("\n--- Vendas por sabor (HashMap) ---");
        System.out.println(vendasPorSabor);

        System.out.println("\nRanking de sabores (do mais vendido para o menos vendido):");
        listarRankingSabores();

        // Extensão: opção de menu simples via Scanner (opcional)
        System.out.println("\n--- Menu interativo simples (digite 0 para sair) ---");
        menuInterativo();
        System.out.println("\nEncerrando sistema. Obrigado!");
    }

    // ---------- SABORES (HashSet) ----------
    static void adicionarSabor(String s) {
        if (sabores.add(s)) {
            System.out.println("Sabor adicionado: " + s);
        } else {
            System.out.println("Sabor já existe (ignorando): " + s);
        }
    }

    static void removerSabor(String s) {
        if (sabores.remove(s)) System.out.println("Sabor removido: " + s);
        else System.out.println("Sabor não encontrado: " + s);
    }

    static void listarSabores() {
        System.out.println("\nSabores disponíveis: " + sabores);
    }

    static boolean existeSabor(String s) {
        return sabores.contains(s);
    }

    // ---------- PEDIDOS (ArrayList) ----------
    static Pedido criarPedido(String cliente, String sabor, char tamanho, double valor, int tempoEstimado) {
        Pedido p = new Pedido(contadorPedidos++, cliente, sabor, tamanho, valor, tempoEstimado);
        pedidosAbertos.add(p);
        System.out.println("Pedido criado: " + p);
        return p;
    }

    static void listarPedidos() {
        if (pedidosAbertos.isEmpty()) {
            System.out.println("Nenhum pedido aberto.");
            return;
        }
        for (Pedido p : pedidosAbertos) {
            System.out.println(p);
        }
    }

    static Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido p : pedidosAbertos) {
            if (p.numero == numero) return p;
        }
        return null;
    }

    static void ordenarPedidosPorValorDesc() {
        Collections.sort(pedidosAbertos, Comparator.comparingDouble((Pedido p) -> p.valor).reversed());
    }

    // ---------- FILA (Queue) ----------
    static String filaToString() {
        StringBuilder sb = new StringBuilder();
        for (Pedido p : filaEntregas) {
            sb.append("#").append(p.numero).append(" ");
        }
        return sb.toString();
    }

    // ---------- PRIORIDADE ----------
    static void adicionarPedidoPrioritario(Pedido p) {
        pedidosPrioritarios.offer(p);
    }

    // ---------- CANCELAMENTOS (Stack) ----------
    static void cancelarPedido(Pedido p) {
        pedidosAbertos.remove(p);
        historicoCancelados.push(p);
        System.out.println("Pedido cancelado: " + p);
    }

    // ---------- VENDAS (HashMap) ----------
    static void registrarVenda(String sabor) {
        vendasPorSabor.put(sabor, vendasPorSabor.getOrDefault(sabor, 0) + 1);
    }

    static void listarRankingSabores() {
        List<Map.Entry<String,Integer>> lista = new ArrayList<>(vendasPorSabor.entrySet());
        lista.sort((a,b) -> b.getValue().compareTo(a.getValue()));
        for (int i = 0; i < lista.size(); i++) {
            Map.Entry<String,Integer> e = lista.get(i);
            System.out.println((i+1) + " - " + e.getKey() + ": " + e.getValue());
        }
    }

    // ---------- MENU INTERATIVO SIMPLES ----------
    static void menuInterativo() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1) Adicionar sabor  2) Criar pedido  3) Listar pedidos  4) Enfileirar entrega");
            System.out.println("5) Entregar (poll)  6) Cancelar pedido  7) Registrar venda  8) Mostrar ranking");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
            String opt = sc.nextLine().trim();
            if (opt.equals("0")) break;
            try {
                switch (opt) {
                    case "1":
                        System.out.print("Nome do sabor: ");
                        String s = sc.nextLine().trim();
                        adicionarSabor(s);
                        break;
                    case "2":
                        System.out.print("Cliente: ");
                        String cliente = sc.nextLine().trim();
                        System.out.print("Sabor: ");
                        String sabor = sc.nextLine().trim();
                        System.out.print("Tamanho (P/M/G): ");
                        char t = sc.nextLine().trim().toUpperCase().charAt(0);
                        System.out.print("Valor: ");
                        double v = Double.parseDouble(sc.nextLine().trim().replace(',','.'));
                        int tempo = 10 + rand.nextInt(41); // 10-50 min
                        criarPedido(cliente, sabor, t, v, tempo);
                        break;
                    case "3":
                        listarPedidos();
                        break;
                    case "4":
                        System.out.print("Número do pedido para enfileirar: ");
                        int num = Integer.parseInt(sc.nextLine().trim());
                        Pedido p = buscarPedidoPorNumero(num);
                        if (p != null) {
                            filaEntregas.offer(p);
                            System.out.println("Pedido enfileirado: " + p);
                        } else System.out.println("Pedido não encontrado.");
                        break;
                    case "5":
                        Pedido entregue = filaEntregas.poll();
                        if (entregue != null) {
                            System.out.println("Entregue: " + entregue);
                            // registrar venda automaticamente ao entregar
                            registrarVenda(entregue.sabor);
                            pedidosAbertos.remove(entregue);
                        } else System.out.println("Fila vazia.");
                        break;
                    case "6":
                        System.out.print("Número do pedido para cancelar: ");
                        int nc = Integer.parseInt(sc.nextLine().trim());
                        Pedido pc = buscarPedidoPorNumero(nc);
                        if (pc != null) cancelarPedido(pc);
                        else System.out.println("Pedido não encontrado.");
                        break;
                    case "7":
                        System.out.print("Sabor vendido: ");
                        String sv = sc.nextLine().trim();
                        registrarVenda(sv);
                        System.out.println("Venda registrada.");
                        break;
                    case "8":
                        listarRankingSabores();
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
        sc.close();
    }
}
