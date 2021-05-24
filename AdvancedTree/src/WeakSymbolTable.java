public class WeakSymbolTable {


    private static class MySymbolGraph<K extends Comparable<? super K>,V>{
        public MyAVL<KV<K,V>> avl;

        public MySymbolGraph(){
            avl=new MyAVL<>(null);
        }

        public V get(K key){
            return  get(key,avl.root);
        }

        public V get(K key,AVLNode<KV<K,V>> node){
            if (node==null)
                return null;
            if (node.val.key.compareTo(key)==0)
                return node.val.value;
            else if (node.val.key.compareTo(key)>0)
                return get(key,node.left);
            else
                return  get(key,node.right);
        }

        public boolean contains(K key){
            boolean answer=false;
            AVLNode<KV<K,V>> node=avl.root;
            while(node!=null){
                if (node.val.key.compareTo(key)==0)
                    answer=true;
                else if (node.val.key.compareTo(key)>0)
                    node=node.left;
                else
                    node=node.right;
                if (answer)
                    break;
            }
            return answer;
        }

        public void delete(K key){
            avl.remove(new KV<>(key,null));
        }

        public void put(K key,V value){
            boolean answer=false;
            AVLNode<KV<K,V>> node=avl.root;
            while(node!=null){
                if (node.val.key.compareTo(key)==0){
                    answer=true;
                    node.val.value=value;
                }
                else if (node.val.key.compareTo(key)>0)
                    node=node.left;
                else
                    node=node.right;
                if (answer)
                    break;
            }
            if (!answer){
                avl.insert(new KV<>(key,value));
            }
        }


        public static void main(String[] args) {
            MySymbolGraph<String,Integer> st=new MySymbolGraph<>();
            st.put("碧蓝航线",1);
            st.put("明日方舟",2);
            st.put("BanGDream",3);
            st.put("原神",4);
            st.put("崩坏3",5);
            System.out.println(st.contains("碧蓝航线"));
            System.out.println(st.get("碧蓝航线"));
            System.out.println(st.contains("BanGDream"));
            st.delete("BanGDream");
            st.delete("dd");
            System.out.println(st.contains("BanGDream"));
        }
    }


    private static class KV<K extends Comparable<? super K>,V> implements Comparable<KV> {
        public K key;
        public V value;
        public KV(K k,V v){
            key=k;
            value=v;
        }
        @Override
        public int compareTo(KV o) {
            return key.compareTo((K)o.key);
        }

    }

    private static class AVLNode<T extends Comparable<? super T>>{
        int height;
        int size;
        T val;
        AVLNode<T> right;
        AVLNode<T> left;
        AVLNode<T> pare;
        public AVLNode(T t){
            val=t;
            height=0;
            pare=null;
        }

    }

    private static class MyAVL<T extends Comparable<? super T>>{
        public AVLNode<T> root;
        public MyAVL(T t){
            if (t==null){

            }
            else{
                root=new AVLNode<>(t);
            }
        }

        public int height(AVLNode<T> node){
            if (node==null)
                return -1;
            else
                return node.height;
        }

        public int size(AVLNode<T> node){
            if (node==null)
                return 0;
            else
                return node.size;
        }
        public void insert(T t){
            root=insert(t,root);
        }
        public AVLNode<T> insert(T t,AVLNode<T> node){
            if (node==null){
                return balance(new AVLNode<>(t));
            }
            else{
                int cmp=t.compareTo(node.val);
                if (cmp<0){
                    node.left=insert(t,node.left);
                    node.left.pare=node;
                }
                else if (cmp>0){
                    node.right=insert(t,node.right);
                    node.right.pare=node;
                }
                else{
                }
            }
            return balance(node);
        }

        public AVLNode<T> balance(AVLNode<T> node){
            if (node==null){
                return node;
            }
            else{
                if (height(node.left)-height(node.right)>1) {
                    if (height(node.left.left) >= height(node.left.right)) {
                        node=rotateWithLeft(node);
                    }
                    else{
                        node=doubleWithLeft(node);
                    }
                }
                else if (height(node.right)-height(node.left)>1){
                    if (height(node.right.right) >= height(node.right.left)){
                        node=rotateWithRight(node);
                    }
                    else{
                        node=doubleWithRight(node);
                    }
                }
                node.height=Math.max(height(node.right),height(node.left))+1;
                node.size=size(node.left)+size(node.right)+1;
                return node;
            }
        }

        public AVLNode<T> rotateWithLeft(AVLNode<T> n2){
            AVLNode<T> n1=n2.left;
            n2.left=n1.right;
            n1.right=n2;
            n1.pare=n2.pare;
            if (n2.left!=null)
                n2.left.pare=n2;
            n2.pare=n1;
            n2.size=1+size(n2.right)+size(n2.left);
            n1.size=1+size(n2)+size(n1.left);
            n2.height=Math.max(height(n2.left),height(n2.right))+1;
            n1.height=Math.max(height(n1.left),height(n1.right))+1;
            return n1;
        }

        public AVLNode<T> rotateWithRight(AVLNode<T> n2){
            AVLNode<T> n1=n2.right;
            n2.right=n1.left;
            n1.left=n2;
            n1.pare=n2.pare;
            if (n2.right!=null)
                n2.right.pare=n2;
            n2.pare=n1;
            n2.size=1+size(n2.right)+size(n2.left);
            n1.size=1+size(n2)+size(n1.right);
            n2.height=Math.max(height(n2.left),height(n2.right))+1;
            n1.height=Math.max(height(n1.left),height(n1.right))+1;
            return n1;
        }

        public AVLNode<T> doubleWithLeft(AVLNode<T> n3){
            n3.left=rotateWithRight(n3.left);
            return rotateWithLeft(n3);
        }

        public AVLNode<T> doubleWithRight(AVLNode<T> n3){
            n3.right=rotateWithLeft(n3.right);
            return rotateWithRight(n3);
        }


        public T findMin(AVLNode<T> node){
            if (node==null)
                return null;
            while(node.left!=null)
                node=node.left;
            return node.val;
        }

        public T findK(AVLNode<T> node,int k){
            if (node==null || k>size(node) || k<0){
                return null;
            }
            if (size(node.left)+1==k)
                return node.val;
            else{
                T t=null;
                int nowin= size(node.left)+1;
                if (k>nowin){
                    t=findK(node.right,k-nowin);
                }
                else{
                    t=findK(node.left,k);
                }
                return t;
            }
        }

        public AVLNode<T> remove(T t,AVLNode<T> node){
            if (node==null)
                return null;
            int cmp=t.compareTo(node.val);
            if (cmp>0){
                node.right=remove(t,node.right);
                if (node.right!=null)
                node.right.pare=node;
            }
            else if (cmp<0){
                node.left=remove(t,node.left);
                if (node.left!=null)
                node.left.pare=node;
            }
            else if (node.left!=null && node.right!=null){
                node.val=findMin(node.right);
                node.right=remove(node.val,node.right);
                if (node.right!=null)
                node.right.pare=node;
            }
            else{
                node=node.left!=null?node.left:node.right;
            }
            return balance(node);
        }
        public void remove(T t){
            root=remove(t,root);
        }


        public void print(AVLNode<T> node){
            if (node==null)
                return;
            else{
                print(node.left);
                System.out.println(node.val+" "+node.pare.val);
                print(node.right);
            }
        }

    }
}
