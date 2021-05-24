public class NoWayToAssignDataField<T> {
    int value;
    T t;

    public <T> int hashcode() {
        return value * 5;
    }

    public static void main(String[] args) {
        Integer[] arr1 = new Integer[10];
        String astring = new String("dd");
        Object[] arr3 = arr1;
        arr3[0]=new String("00");
        System.out.println(arr3[0].toString());
    }
}
