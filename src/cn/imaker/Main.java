package cn.imaker;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        SimTextExcutor excutor = new SimTextExcutor();
        BatFileExecutorResultMap map = excutor.batFileCaller();
        if((boolean)map.get(BatFileExecutorResultMap.BAT_FILE_EXIST)){
            List<String> strings = (List<String>)map.get(BatFileExecutorResultMap.DATA);

            for (String s : strings){
                System.out.println(s);
            }
        }else {
            System.out.println("haha");
        }

    }
}
