
public class WeakRBTree {
    private static final boolean RED =true;
    private static final boolean BLACK =false;

    private static class MyRBTree<V extends Comparable<V>>{
        RBNode<V> root;

        public void insert(V v){
            root=insert(root,v);
            root.col=BLACK;
        }

        public RBNode<V> insert(RBNode<V> h,V v){
            if (h==null)
                return new RBNode<>(v,1,RED);

            int cmp=v.compareTo(h.val);
            if (cmp<0) h.left=insert(h.left,v);
            else if(cmp>0) h.right=insert(h.right,v);
            else h.val=v;

            if (isRed(h.right) && !isRed(h.left)) h=rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h=rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);

            h.N=size(h.left)+size(h.right)+1;

            return h;
        }

        public void delete(V v){
            V vtodele=findRightMin(v);
            if (vtodele==null)
                return;
            else{
                deletexia(vtodele);
                RBNode<V> node=root;
                int cmp=0;
                if (vtodele.compareTo(v)!=0){
                    while(node.val.compareTo(v)!=0){
                        cmp=v.compareTo(node.val);
                        if (cmp>0){
                            node=node.right;
                        }
                        else if (cmp<0){
                            node=node.left;
                        }
                        else{
                            break;
                        }
                    }
                    node.val=vtodele;
                }
            }
        }

        public V findRightMin(V v){
            RBNode<V> node=root;
            int cmp=0;
            while(node!=null&&node.val!=v){
                cmp=v.compareTo(node.val);
                if (cmp>0){
                    node=node.right;
                }
                else if (cmp<0){
                    node=node.left;
                }
                else{
                    break;
                }
            }
            if (node==null){
                return null;
            }
            else{
                if (node.right==null){
                    return node.val;
                }

                else{
                    node=node.right;
                    while(node.left!=null)
                        node=node.left;
                    return node.val;
                }
            }

        }

        public void deletexia(V v){
            root=deletexia(root,v);
            if (root!=null)
            root.col=BLACK;
        }

        public RBNode<V> deletexia(RBNode<V> node,V v){
            int cmp=v.compareTo(node.val);
            if (cmp>0){
                if (isRed(node.right)){
                    node.right=deletexia(node.right,v);
                }

                else if (isRed(node.right.left)){
                    node.right=deletexia(node.right,v);
                }

                else{
                    if (isRed(node.left)){
                        if (!isRed(node.left.right.left)){
                            RBNode<V> a=node;
                            node=node.left;
                            node.col=BLACK;
                            node.right.col=RED;
                            a.left=node.right;
                            node.right=a;
                            a.right.col=RED;
                            a.col=BLACK;//
                            a.N=size(a.right)+size(a.left)+1;
                            node.N=size(node.left)+size(node.right)+1;
                            node.right=deletexia(node.right,v); //1
                        }
                        else{
                            RBNode<V> a=node;
                            node=node.left.right;
                            RBNode<V> I =node.right;
                            RBNode<V> J=a.right.left;
                            a.left.right=node.left;
                            node.left.col=BLACK;
                            node.left=a.left;
                            node.right=a.right;
                            node.col=a.col;
                            a.right.left=a;
                            a.col=RED;
                            a.left=I;
                            a.right=J;
                            node.left.N=size(node.left.left)+size(node.left.right)+1;
                            a.N=size(I)+size(J)+1;
                            node.right.N=size(a)+size(node.right.right)+1;
                            node.N=size(node.left)+size(node.right)+1;
                            node.right=deletexia(node.right,v); //2
                        }
                    }

                    else{
                        if (isRed(node.left.left)){
                            RBNode<V> F=node.left.right;
                            RBNode<V> G=node.right.left;
                            RBNode<V> a=node;
                            node=node.left;
                            node.left.col=BLACK;
                            node.right=a.right;
                            node.col=a.col;
                            a.right.left=a;
                            a.col=RED;
                            a.left=F;
                            a.right=G;
                            a.N=size(F)+size(G)+1;
                            node.right.N=size(a)+size(node.right.right)+1;
                            node.N=size(node.right)+size(node.left)+1;
                            node.right=deletexia(node.right,v); //3
                        }

                        else{
                            flipColors(node);
                            node.right=deletexia(node.right,v); //4
                        }
                    }
                }
            }
            else if (cmp<0){
                if (isRed(node.left.left)){
                    node.left=deletexia(node.left,v);
                }
                else{
                    if (isRed(node.left)){
                        node.left=deletexia(node.left,v);//5
                    }
                    else if (!isRed(node.right.left)){
                        flipColors(node);
                        node.left=deletexia(node.left,v);//6
                    }
                    else {
                        RBNode<V> a=node;
                        node=node.right.left;
                        RBNode<V> H=node.right;
                        RBNode<V> G=node.left;
                        node.col=a.col;
                        node.right=a.right;
                        node.left=a;
                        a.col=BLACK;
                        a.left.col=RED;
                        a.right=G;
                        node.right.left=H;
                        node.right.N=size(node.right.left)+size(node.right.right)+1;
                        a.N=size(a.left)+size(a.right)+1;
                        node.N=size(node.left)+size(node.right)+1;
                        node.left=deletexia(node.left,v);//7
                    }
                }
            }
            else{
                if (node.left==null && node.right==null){
                    return null;
                }
                else{
                    node.left.col=node.col;
                    node=node.left;
                }
            }

            if (isRed(node.right) && !isRed(node.left)) node=rotateLeft(node);
            if (isRed(node.left) && isRed(node.left.left)) node=rotateRight(node);
            if (isRed(node.left) && isRed(node.right)) flipColors(node);
            node.N=size(node.left)+size(node.right)+1;
            return node;
        }

        private boolean isRed(RBNode<V> node){
            if (node==null)
                return false;
            else
                return node.col==RED;
        }

        private RBNode<V> rotateLeft(RBNode<V> h){
            RBNode<V> x=h.right;
            h.right=x.left;
            x.left=h;
            x.col=h.col;
            h.col=RED;
            x.N=h.N;
            h.N=1+size(h.left)+size(h.right);
            return x;
        }

        private RBNode<V> rotateRight(RBNode<V> h){
            RBNode<V> x=h.left;
            h.left=x.right;
            x.right=h;
            x.col=h.col;
            h.col=RED;
            x.N=h.N;
            h.N=1+size(h.left)+size(h.right);
            return x;
        }

        private void flipColors(RBNode<V> h){
            h.col=!h.col;
            h.left.col=!h.left.col;
            h.right.col=!h.right.col;
        }

        private int size(RBNode<V> h){
            if (h==null)
                return 0;
            else
                return h.N;
        }
        public void print(RBNode<V> node){
            if (node==null)
                return;
            else{
                print(node.left);
                System.out.print(node.val+" ");
                print(node.right);
            }
        }
    }

    private static class RBNode<V extends Comparable<V>>{
        V val;
        boolean col;
        RBNode<V> left;
        RBNode<V> right;
        int N;

        public RBNode(V v,int N,boolean col){
            val=v;
            this.N=N;
            this.col=col;
        }
    }

    public static void main(String[] args) {
        /*MyRBTree<Integer> arb=new MyRBTree<>();
        arb.insert(1);
        arb.insert(2);
        arb.insert(3);
        arb.insert(4);
        arb.insert(5);
        arb.insert(6);
        arb.insert(7);
        arb.insert(7);
        arb.insert(8);
        arb.insert(9);
        arb.insert(10);
        arb.insert(11);
        arb.insert(12);
        arb.insert(13);
        arb.insert(14);
        arb.insert(15);
        arb.insert(16);
        arb.insert(17);
        arb.insert(18);
        arb.insert(19);
        arb.insert(20);
        arb.insert(21);
        arb.delete(1);
        arb.print(arb.root);System.out.println();
        arb.delete(2);
        arb.print(arb.root);System.out.println();
        arb.delete(3);
        arb.print(arb.root);System.out.println();
        arb.delete(4);
        arb.print(arb.root);System.out.println();
        arb.delete(7);
        arb.print(arb.root);System.out.println();
        arb.delete(8);
        arb.print(arb.root);System.out.println();
        arb.delete(9);
        arb.print(arb.root);System.out.println();
        arb.delete(12);
        arb.print(arb.root);System.out.println();
        arb.delete(21);
        arb.print(arb.root);System.out.println();*/

        MyRBTree<Integer> brb=new MyRBTree<>();
        brb.root=new RBNode<>(5,8,false);
        brb.root.left=new RBNode<>(3,4,false);
        brb.root.right=new RBNode<>(7,3,false);
        brb.root.left.left=new RBNode<>(2,2,false);
        brb.root.left.right=new RBNode<>(4,1,false);
        brb.root.left.left.left=new RBNode<>(1,1,true);
        brb.root.right.right=new RBNode<>(8,1,false);
        brb.root.right.left=new RBNode<>(6,1,false);
        brb.delete(4);
        brb.print(brb.root);
    }
}
