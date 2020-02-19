

public class TrieNode<T>  {
    TrieNode<T> [] children;
    public T value;
    char ch;
    boolean flag =false;
    public TrieNode() {
//        this.children = new TrieNode[11];
        this.children = new TrieNode[95];
    }
    public T getValue() {
        return  value;
    }
}