package cn.imaker;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimTextExcutor {

    private static final String BATFILE_LOACTION = "C:/Users/Administrator/Desktop/Fils/simText.bat";

    /**
     * 该函数负责校验bat文件的存在性，以及调用执行bat
     * @return result
     */
    public BatFileExecutorResultMap batFileCaller() {
        File batFile = new File(BATFILE_LOACTION);

        List<String> results = null;
        //校验文件存在性
        boolean batFileExist = batFile.exists();
        if (batFileExist) {
            //若存在，执行bat文件获取结果list
            results = batFileExecutor(BATFILE_LOACTION);
            return BatFileExecutorResultMap.success(true,results.size(),results);
        }else{
            System.out.println(".bat文件不存在");
            return BatFileExecutorResultMap.error();
        }


    }

    public List<String>  batFileExecutor(String batFileLocation){
        List<String> results = new ArrayList<>();
        try {
            Process child = Runtime.getRuntime().exec(batFileLocation);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                //如果以“material”结尾，说明为一个结果，加入到结果的list里面
                if(line.endsWith("material")){
                    results.add(line);//添加
                }
            }
            in.close();//关闭inputStream
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return results;
    }
}

/**
 * @function ：返回一个处理的结果
 * @author： yelihu
 * @time： 2019年9月17日
 */
class BatFileExecutorResultMap extends HashMap<String, Object> {

    /** Key's name */
    public static final String BAT_FILE_EXIST = "exist";
    public static final String RESULT_SIZE = "size";
    public static final String DATA  = "data";

    /** bat文件存在 */
    private boolean exist;

    /** 返回长度 */
    private int size;

    /** 数据对象 */
    private List<String> data;


    /**
     * 调用失败的构造方法，传入.bat文件的存在状态
     * @param exist
     */
    public BatFileExecutorResultMap(boolean exist){
        super.put(BAT_FILE_EXIST,exist);
    }

    /**
     * 调用成功构造方法，传入.bat文件的存在状态，结果list的大小和结果的list
     * @param exist
     * @param size
     * @param data
     */
    public BatFileExecutorResultMap(boolean exist,int size,List<String> data){
        super.put(BAT_FILE_EXIST,exist);
        super.put(RESULT_SIZE,size);
        super.put(DATA,data);
    }


    public static BatFileExecutorResultMap error()
    {
        return new BatFileExecutorResultMap(false);
    }

    /**
     *
     * @param exist
     * @param size
     * @param data
     * @return
     */
    public static BatFileExecutorResultMap success(boolean exist,int size,List<String> data)
    {
        return new BatFileExecutorResultMap(true,data.size(),data);
    }

}