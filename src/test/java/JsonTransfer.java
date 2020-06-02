import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

public class JsonTransfer {
    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<String,Object>();

        List<Technology> technology = new ArrayList<>();
        Technology one = new Technology();
        one.setPerson("张三");
        one.setAddress("江苏");
        one.setSubject("aaa");
        one.setTime(new Date().toString());

        Technology two = new Technology();
        two.setPerson("张三");
        two.setAddress("江苏");
        two.setSubject("aaa");
        two.setTime(new Date().toString());
        technology.add(one);
//        technology.add(two);

        List<Map> ttmaps=new ArrayList<>();
        for(Technology tt:technology) {
            //json.put(String.valueOf(tt.getT_id()), tt);
            Map<String,Object> tts=new HashMap<String,Object>();
            tts.put("subject", tt.getSubject());
            tts.put("person", tt.getPerson());
            tts.put("address", tt.getAddress());
            tts.put("time", tt.getTime());
            //System.out.println(tt.getSubject()+" ");
            ttmaps.add(tts);
            //tts.clear();
        }
        map.put("skills", ttmaps);
        Map<String,Object> subNode = new HashMap<>();
        subNode.put("Chines","108");
        subNode.put("English","118");
        subNode.put("Math","120");
        map.put("name","张三");
        map.put("score",subNode);
        JSONObject jsonObject = new JSONObject(map);
        String s = JSONObject.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
        System.out.println(s);


    }

}
