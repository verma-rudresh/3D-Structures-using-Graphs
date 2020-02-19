//import java.util.Arrays;

public class ArrayList1<T extends Comparable<T>>  {

    Object [] myStore;
    public int actual_Size = 0;

    public ArrayList1(){
        myStore =  new Object [1000];
    }

    public T get(int index){
        if(index < actual_Size){
            return (T)myStore[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void add(T obj){
        if(myStore.length-actual_Size <= 4){
            increaseListSize();
        }
        myStore[actual_Size++] = obj;
    }
    //    public void add_point(T obj1){
//        if(myStore.length-actual_Size <= 4){
//            increaseListSize();
//        }
//        Point obj =(Point) obj1;
//        int pos=-1;
//        for(int i=0; i<actual_Size; i++){
//            Point point = (Point) myStore[i];
////            System.out.println(obj1 + " is being compared to already existing " + myStore[i]);
//            if(obj.compareTo(point) < 0){
//                pos=i;
////                System.out.println("will go before that");
//                break;
//            }
//        }
//        if(pos==-1){
//            myStore[actual_Size++] = obj1;
//            return;
//        }
//        T temp = obj1;
//        while(pos < actual_Size){
//            T temp1 = (T)myStore[pos];
//            set(pos,temp);
//            temp = temp1;
//            pos++;
//        }
//        actual_Size++;
//        pos=actual_Size-1;
//        set(pos,temp);
//    }
    public void add_sorted(T obj1){
        if(myStore.length-actual_Size <= 4){
            increaseListSize();
        }
        T obj = obj1;
        int pos=-1;
        for(int i=0; i<actual_Size; i++){
            T obj2 = (T)myStore[i];
            if(obj.compareTo(obj2) < 0){
                pos=i;
                break;
            }
        }
        if(pos==-1){
            myStore[actual_Size++] = obj1;
            return;
        }
        T temp = obj1;
        while(pos < actual_Size){
            T temp1 = (T)myStore[pos];
            set(pos,temp);
            temp = temp1;
            pos++;
        }
        actual_Size++;
        pos=actual_Size-1;
        set(pos,temp);
    }
//    public void add_edge(T obj1){
//        if(myStore.length-actual_Size <= 4){
//            increaseListSize();
//        }
//        Edge obj =(Edge) obj1;
//        int pos=-1;
//        for(int i=0; i<actual_Size; i++){
//            Edge edge = (Edge) myStore[i];
//            if(obj.compareTo(edge) < 0){
//                pos=i;
//                break;
//            }
//        }
//        if(pos==-1){
//            myStore[actual_Size++] = obj1;
//            return;
//        }
//        T temp = obj1;
//        while(pos < actual_Size){
//            T temp1 = (T)myStore[pos];
//            set(pos,temp);
//            temp = temp1;
//            pos++;
//        }
//        actual_Size++;
//        pos=actual_Size-1;
//        set(pos,temp);
//    }
//    public void add_triangle(T obj1){
//
//        if(myStore.length-actual_Size <= 4){
//            increaseListSize();
//        }
//        Triangle obj =(Triangle) obj1;
//        int pos=-1;
//        for(int i=0; i<actual_Size; i++){
//            Triangle triangle = (Triangle) myStore[i];
//            if(obj.compareTo(triangle) < 0){
//                pos=i;
//                break;
//            }
//        }
//        if(pos==-1){
//            myStore[actual_Size++] = obj1;
//            return;
//        }
//        T temp = obj1;
//        while(pos < actual_Size){
//            T temp1 = (T)myStore[pos];
//            set(pos,temp);
//            temp = temp1;
//            pos++;
//        }
//        actual_Size++;
//        pos=actual_Size-1;
//        set(pos,temp);
//    }

    public void add_triangle_uniquely(T obj1){
        if(search(obj1))
            return;
        add_sorted(obj1);
    }

    public void add_uniquely(T obj){
        if(search(obj))
            return;
        if(myStore.length-actual_Size <= 4){
            increaseListSize();
        }
        myStore[actual_Size++] = obj;
    }
    public void set(int i,T obj ){
        if(i>=actual_Size)
            throw new ArrayIndexOutOfBoundsException();
        myStore[i] = obj;
    }
//    public T remove(int index){
//        if(index < actual_Size){
//            T obj = myStore[index];
//            myStore[index] = null;
//            int tmp = index;
//            while(tmp < actual_Size){
//                myStore[tmp] = myStore[tmp+1];
//                myStore[tmp+1] = null;
//                tmp++;
//            }
//            actual_Size--;
//            return obj;
//        } else {
//            throw new ArrayIndexOutOfBoundsException();
//        }
//
//    }

    public int size(){
        return actual_Size;
    }

    public boolean search(T obj){
        int size= this.actual_Size;
        for(int i=0; i<size; i++){
            if(myStore[i].equals(obj))
                return true;
        }
        return false;
    }

    private void increaseListSize(){
        Object[] temp =  new Object[myStore.length*2];
        int size = myStore.length;
        for(int i=0; i< size; i++){
            temp[i] = myStore[i];
        }
        myStore = temp;
        //myStore = Arrays.copyOf(myStore, myStore.length*2);
//        System.out.println("\nNew length: "+myStore.length);
    }
    public void display(){
        for(int i=0; i<actual_Size; i++){
            System.out.println(myStore[i].toString());
        }
    }

//    public static void main(String a[]){
//        ArrayList list = new ArrayList();
//        list.add_sorted(2);
//        list.add_sorted(5);
//        list.add_sorted(1);
//        list.add_sorted(23);
//        list.add_sorted(14);
//        for(int i=0;i<list.size();i++){
//            System.out.print(list.get(i)+" ");
//        }
//        list.add_sorted(29);
//        System.out.println("Element at Index 5:"+list.get(5));
//        System.out.println("List size: "+list.size());
//        list.add_sorted(12);
//        System.out.println("List size: "+list.size());
////        System.out.println("Removing element at index 2: "+list.remove(2));
//        System.out.println("List size: "+list.size());
//        for(int i=0;i<list.size();i++){
//            System.out.print(list.get(i)+" ");
//        }
//    }
}