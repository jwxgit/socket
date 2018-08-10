import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import msg.ChildInfo;
import msg.Inoculation;
import msg.SocketMessage;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
    public static void main(String[] args) {
        SocketMessage message = new SocketMessage();
        message.setMsgId("11");
        ChildInfo childInfo = new ChildInfo();
        childInfo.setChilName("哈哈");
        message.setData(childInfo);
        List<Inoculation> inoculationList = new ArrayList<Inoculation>();
        Inoculation inoculation = new Inoculation();
        inoculation.setInocBactchNo("111111");
        inoculationList.add(inoculation);
        String str = JSON.toJSONString(message);
        System.out.println(str);
        SocketMessage msg = JSON.parseObject(str, SocketMessage.class);
        JSONObject jsonObject = (JSONObject)msg.getData();
        ChildInfo childInfo1= JSONObject.toJavaObject(jsonObject, ChildInfo.class);
        System.out.println(JSON.toJSONString(jsonObject));
        return;
    }

}
