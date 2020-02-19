
public class Trie<T>  {
//    public int global_count = 0;
    TrieNode<T> root;
    public Trie(){
        this.root = new TrieNode();
    }

    public boolean insert(String word, Object value) {
        TrieNode<T> check_Node = root;
        int Size = word.length();
        for(int i =0; i<Size;i++){
            char c= word.charAt(i);
            //TrieNode<T> new_Node ;
            int index = find_Index(c);
            if(check_Node.children[index]==null){
                TrieNode<T> new_Node = new TrieNode();//create a new node
                new_Node.ch=c;
                check_Node.children[index]=new_Node;    //insert this node into the corresponding place in the array
                check_Node = new_Node;                  //now, shift the checking node to this node for further check...
            }else{
                check_Node=check_Node.children[index];
                //if already present, then shift the check node to the child in concern.
            }
        }
        check_Node.flag = true;
        check_Node.value = (T) value;
        return true;
    }

    public int find_Index(char c){
//        int index;
//        if(c=='-')
//            index = 0;
//        else index = c - '0'+1;
        int index= c-' ';
        return index;
    }


    public boolean delete(String word) {
        if(word ==null || word.length()==0)
            return false;
        TrieNode deleteBelow = null;
        char deleteChar = '\0';
        // Search to ensure word is present
        TrieNode parent = root;
        for (int i = 0; i < word.length(); i++) {
            char cur = word.charAt(i);
            int index1= find_Index(cur);
            TrieNode child = parent.children[index1]; // Check if having a TrieNode associated with 'cur'
            if (child == null) { // null if 'word' is way too long or its prefix doesn't appear in the Trie
                System.out.println("ERROR DELETING");
                return false;
            }
            int count_non_empty=0;
            for(int j =0;j<95;j++){
                if(parent.children[j]!=null)
                    count_non_empty++;
            }
            if (count_non_empty > 1 || parent.flag) { // Update 'deleteBelow' and 'deleteChar'
                deleteBelow = parent;
                deleteChar = cur;
            }

            parent = child;
        }
        if (!parent.flag) { // word isn't in trie
            System.out.println("ERROR DELETING");
            return false;
        }
        int count_empty=0;
        for(int i =0;i<95;i++){
            if(parent.children[i]==null)
                count_empty++;
        }
        if (count_empty==95) {
            int index = find_Index(deleteChar);
            //System.out.println("Starting of delete is "+ deleteChar);
//            assert deleteBelow != null;
            if(deleteBelow!=null)
            deleteBelow.children[index]= null;
            else
                root=null;
        } else {
            parent.flag = false; // Delete word by mark it as not the end of a word
        }
        System.out.println("DELETED");
        return true;

    }


    public TrieNode search(String word) {
        TrieNode check_node = startsWith(word);
        if(check_node==null){
            return null;
        }else{
            if(check_node.flag)
                return check_node;
        }

        return null;
    }


    public TrieNode startsWith(String prefix) {
        TrieNode check_Node = root;
        for(int i=0; i<prefix.length(); i++){
            char c= prefix.charAt(i);
            int index = find_Index(c);
            if(check_Node.children[index]!=null){
                check_Node = check_Node.children[index];
            }else{
                return null;
            }
        }

        if(check_Node==root)
            return null;

        return check_Node;
    }



    }
