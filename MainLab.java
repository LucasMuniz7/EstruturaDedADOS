import java.util.*;

public class MainLab {
    public static void main(String[] args) {

        System.out.println("\n===== BLOCO A =====");
        tarefaA1();
        tarefaA2();

        System.out.println("\n===== BLOCO B =====");
        tarefaB1();
        tarefaB2();

        System.out.println("\n===== BLOCO C =====");
        tarefaC1();
        tarefaC2();

        System.out.println("\n===== BLOCO D =====");
        tarefaD1();
        tarefaD2();

        System.out.println("\n===== BLOCO E =====");
        tarefaE1();
        tarefaE2();
    }

    public static void tarefaA1() {
        System.out.println("\n--- Tarefa A1 ---");
        List<Integer> list = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));
        Collections.sort(list);
        System.out.println("Ordenada: " + list);
        int pos = Collections.binarySearch(list, 8);
        System.out.println("Posição do 8: " + pos);
        Collections.reverse(list);
        System.out.println("Reversa: " + list);
        Collections.shuffle(list);
        System.out.println("Embaralhada: " + list);
    }

    public static void tarefaA2() {
        System.out.println("\n--- Tarefa A2 ---");
        List<String> l1 = new ArrayList<>(Arrays.asList("Java", "Python", "C++"));
        List<String> l2 = new ArrayList<>(Arrays.asList("C#", "Java", "Go"));
        Collections.addAll(l1, "Kotlin", "Swift");
        System.out.println("Lista l1 após addAll: " + l1);
        System.out.println("Frequência de 'Java' em l1: " + Collections.frequency(l1, "Java"));
        System.out.println("São disjuntas? " + Collections.disjoint(l1, l2));
        List<Integer> nums = Arrays.asList(10, 2, 30, 5);
        System.out.println("Min: " + Collections.min(nums));
        System.out.println("Max: " + Collections.max(nums));
    }

    public static void tarefaB1() {
        System.out.println("\n--- Tarefa B1 (Parenteses Balanceados) ---");
        String[] tests = {"((2+3)*(4-1))", ")(2+3)(", "(1+(2*3)"};
        for (String exp : tests) {
            System.out.println(exp + " → " + (balanceada(exp) ? "Balanceada" : "Desbalanceada"));
        }
    }

    public static boolean balanceada(String exp) {
        Stack<Character> pilha = new Stack<>();
        for (char c : exp.toCharArray()) {
            if (c == '(') pilha.push(c);
            else if (c == ')') {
                if (pilha.isEmpty()) return false;
                pilha.pop();
            }
        }
        return pilha.isEmpty();
    }

    public static void tarefaB2() {
        System.out.println("\n--- Tarefa B2 (Inverter palavra) ---");
        String palavra = "ALGORITMO";
        Stack<Character> stack = new Stack<>();
        for (char c : palavra.toCharArray()) stack.push(c);
        String invertida = "";
        while (!stack.isEmpty()) invertida += stack.pop();
        System.out.println("Invertida: " + invertida);
    }

    public static void tarefaC1() {
        System.out.println("\n--- Tarefa C1 (Fila de impressão) ---");
        Queue<String> fila = new LinkedList<>();
        fila.offer("Doc1");
        fila.offer("Doc2");
        fila.offer("Doc3");
        System.out.println("Fila após inserir 3: " + fila);
        fila.poll();
        fila.poll();
        System.out.println("Após remover 2: " + fila);
        fila.offer("Doc4");
        System.out.println("Após inserir Doc4: " + fila);
    }

    public static void tarefaC2() {
        System.out.println("\n--- Tarefa C2 (PriorityQueue) ---");
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(30);
        pq.offer(10);
        pq.offer(20);
        pq.offer(40);
        System.out.println("Menor elemento (peek): " + pq.peek());
        System.out.print("Removendo em ordem: ");
        while (!pq.isEmpty()) System.out.print(pq.poll() + " ");
        System.out.println();
    }

    public static void tarefaD1() {
        System.out.println("\n--- Tarefa D1 ---");
        HashSet<String> h = new HashSet<>();
        h.add("Java");
        h.add("Python");
        h.add("C++");
        h.add("Java");
        System.out.println("HashSet: " + h);
        TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(10, 5, 8, 1));
        System.out.println("TreeSet ordenado: " + ts);
        System.out.println("headSet(8): " + ts.headSet(8));
        System.out.println("tailSet(8): " + ts.tailSet(8));
    }

    public static void tarefaD2() {
        System.out.println("\n--- Tarefa D2 ---");
        Map<String, Integer> map = new HashMap<>();
        map.put("Ana", 90);
        map.put("Carlos", 80);
        map.put("Bianca", 85);
        System.out.println("Nota da Ana: " + map.get("Ana"));
        System.out.println("Chaves: " + map.keySet());
        System.out.println("Tamanho: " + map.size());
        TreeMap<String, Integer> tm = new TreeMap<>(map);
        System.out.println("TreeMap ordenado: " + tm);
    }

    static class Node {
        int data;
        Node next;
        Node(int d) { data = d; }
    }

    static Node head = null;

    public static void insertAtFront(int v) {
        Node n = new Node(v);
        n.next = head;
        head = n;
    }

    public static void insertAtBack(int v) {
        Node n = new Node(v);
        if (head == null) { head = n; return; }
        Node aux = head;
        while (aux.next != null) aux = aux.next;
        aux.next = n;
    }

    public static void removeFromFront() {
        if (head != null) head = head.next;
    }

    public static void removeFromBack() {
        if (head == null) return;
        if (head.next == null) { head = null; return; }
        Node aux = head;
        while (aux.next.next != null) aux = aux.next;
        aux.next = null;
    }

    public static void printList() {
        Node aux = head;
        while (aux != null) {
            System.out.print(aux.data + " ");
            aux = aux.next;
        }
        System.out.println();
    }

    public static void tarefaE1() {
        System.out.println("\n--- Tarefa E1 (Lista Encadeada) ---");
        head = null;
        insertAtFront(3);
        insertAtFront(2);
        insertAtBack(4);
        insertAtBack(5);
        insertAtFront(1);
        System.out.print("Lista: ");
        printList();
        removeFromFront();
        System.out.print("Após remover da frente: ");
        printList();
        removeFromBack();
        System.out.print("Após remover do fim: ");
        printList();
    }

    static class BST {
        int value;
        BST left, right;
        BST(int v) { value = v; }
    }

    static BST root = null;

    public static BST insertBST(BST r, int v) {
        if (r == null) return new BST(v);
        if (v < r.value) r.left = insertBST(r.left, v);
        else r.right = insertBST(r.right, v);
        return r;
    }

    public static void inOrder(BST r) {
        if (r != null) {
            inOrder(r.left);
            System.out.print(r.value + " ");
            inOrder(r.right);
        }
    }

    public static void preOrder(BST r) {
        if (r != null) {
            System.out.print(r.value + " ");
            preOrder(r.left);
            preOrder(r.right);
        }
    }

    public static void postOrder(BST r) {
        if (r != null) {
            postOrder(r.left);
            postOrder(r.right);
            System.out.print(r.value + " ");
        }
    }

    public static void tarefaE2() {
        System.out.println("\n--- Tarefa E2 (Árvore BST) ---");
        root = null;
        int[] valores = {8,3,10,1,6,14,4,7,13};
        for (int v : valores) root = insertBST(root, v);
        System.out.print("In-order: ");
        inOrder(root);
        System.out.println();
        System.out.print("Pre-order: ");
        preOrder(root);
        System.out.println();
        System.out.print("Post-order: ");
        postOrder(root);
        System.out.println();
    }
}