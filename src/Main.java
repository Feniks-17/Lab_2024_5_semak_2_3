public class Main {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(1, "один");
        map.put(2, "два");
        map.put(3, "три");
        map.put(4, "четыре");

        System.out.println(map.get(2));

        System.out.println(map.put(2, "не два"));

        System.out.println(map.containsKey(3));

        System.out.println(map.remove(1));

        System.out.println(map.size());

        map.clear();

        System.out.println(map.isEmpty());

        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        System.out.println("Элементы в map: ");
        for (Node<Integer, String> node : map) {
            System.out.println("Ключ: " + node.key + ", значение: " + node.value);
        }

    }
}