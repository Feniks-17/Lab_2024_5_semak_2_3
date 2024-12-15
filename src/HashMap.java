import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements Iterable<Node<K, V>> {
    private static final int INITIAL_CAPACITY = 10; //размер по умолчанию
    private static final double LOAD_FACTOR = 0.75; //коэффициент загрузки

    private Node<K, V>[] buckets;
    private int size;

    //конструктор по умолчанию
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    //конструктор для задания размера и количества пар элементов
    public HashMap(int capacity) {
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    //вычисление индекса бакета на основе хеш-кода ключа
    private int bucketIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % buckets.length;
    }

    //добавление пары ключ-значение в таблицу
    public V put(K key, V value) {
        int index = bucketIndex(key);
        Node<K, V> node = buckets[index];

        while (node != null) {
            if (node.key.equals(key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;

        if (((double) size / buckets.length) >= LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    //изменение размера таблицы
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[buckets.length * 2];
        size = 0;

        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    //возвращает значение по ключу
    public V get(K key) {
        int index = bucketIndex(key);
        Node<K, V> node = buckets[index];

        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    //удаляет пару по ключу и возвращает значение
    public V remove(K key) {
        int index = bucketIndex(key);
        Node<K, V> node = buckets[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }

        return null;
    }

    //проверяет наличие ключа в таблице
    public boolean containsKey(K key) {
        int index = bucketIndex(key);
        Node<K, V> node = buckets[index];

        while (node != null) {
            if (node.key.equals(key)) {
                return true;
            }
            node = node.next;
        }

        return false;
    }

    //количество пар элементов в таблице
    public int size() {
        return size;
    }

    //проверка на пустоту таблицы
    public boolean isEmpty() {
        return size == 0;
    }

    //очистка таблицы
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    //создание экземпляра класса HashMapIterator
    public Iterator<Node<K, V>> iterator() {
        return new HashMapIterator();
    }

    public class HashMapIterator implements Iterator<Node<K, V>> {
        private int currentBucket = 0;
        private Node<K, V> currentNode = null;

        //первый узел для итерации
        public HashMapIterator() {
            findNextNode();
        }

        //поиск следующего узла для итерации
        private void findNextNode() {
            if (currentNode != null) {
                currentNode = currentNode.next; //переход к следующему узлу в текущем бакете
            }
            while (currentNode == null && currentBucket < buckets.length) {
                currentNode = buckets[currentBucket]; //ссылка на первый узел в текущем бакете
                currentBucket++;
            }
        }

        //проверка на наличие следующей пары элементов бакете
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        //возвращает текущую пару элементов в таблице
        @Override
        public Node<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("В бакете больше нет элементов");
            }
            Node<K, V> result = currentNode;
            findNextNode();
            return result;
        }
    }
}