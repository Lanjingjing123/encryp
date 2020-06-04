import java.util.*;

public class TreeMapOrderTest {
    public static void main(String[] args) {
        Map<String,Object> map = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        map.put("a","1");

        map.put("c","2");

        map.put("b","3");

        map.put("d","4");

        Set<String> keys = map.keySet();
        for (String key : keys
             ) {
            System.out.println(key+":"+map.get(key));
        }

//        System.out.println(map);
    }
}