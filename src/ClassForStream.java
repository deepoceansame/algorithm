import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClassForStream {
    public static void main(String[] args) {
        List<Integer> a= Arrays.asList(1,2,3,4);
        List<int[]> pairs=a.stream().flatMap(i->
                a.stream().filter(j->a.indexOf(j)>a.indexOf(i)).map(j->new int[]{i,j})).collect(toList());
        for (int[] b:pairs){
            for (int c:b){
                System.out.print(c+" ");
            }
            System.out.println();
        }
    }

}
