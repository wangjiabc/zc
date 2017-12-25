package com.voucher.manage.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LocalFile {

	public static void saveDataToFile(String path,String fileName,String data) {  
        BufferedWriter writer = null;  
        File file = new File(path + fileName + ".json");  
        File targetFile=new File(path);
        System.out.println(path);
        if(!targetFile.exists()){
            targetFile.mkdirs();//创建目录
        }
        //如果文件不存在，则新建一个  
        if(!file.exists()){  
            try {  
                file.createNewFile();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        //写入  
        try {  
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));  
            writer.write(data);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {  
            try {  
                if(writer != null){  
                    writer.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
       System.out.println("文件写入成功！");  
    }  
	
	
	public static String getDatafromFile(String path,String fileName) {  

        BufferedReader reader = null;  
        String laststr = "";  
        try {  
            FileInputStream fileInputStream = new FileInputStream(path+fileName);  
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");  
            reader = new BufferedReader(inputStreamReader);  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
                laststr += tempString;  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return laststr;  
    }  

	
}
