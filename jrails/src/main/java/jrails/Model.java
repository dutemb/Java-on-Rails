package jrails;

import java.util.List;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Model {
    public Integer id = Integer.valueOf(0);

    public void save() {
       //hey
        HashMap<Integer,Model>info = readToHashmap();

         if(id.intValue() != 0){
         if(!info.containsKey(id))throw new RuntimeException();        
        }

        if(id.intValue() == 0){
            Integer lastline = Integer.valueOf(0);
            for(Integer cur : info.keySet()){
                if(lastline.intValue()<cur.intValue()){
                    lastline = cur;
                }
            }
            id = Integer.valueOf(lastline+1);
           info.put(id,this);
        }else{
            info.replace(id,this);
        }
        
       // info.put(id,this);
        writeToDatabase(info);
     
    }

    private static HashMap<Integer,Model> readToHashmap(){
        HashMap<Integer,Model> info = new HashMap<Integer,Model>();
        File curDatabase = new File("database.tsv");
        try{
           boolean exists = curDatabase.createNewFile();
           if(!exists){
              Scanner scanner = new Scanner(curDatabase);
              while(scanner.hasNextLine()){
                  String curLine = scanner.nextLine();
                  String[] hashPair = curLine.split("\t");
                  Integer key = Integer.valueOf(hashPair[0]);
                  Class cls = Class.forName(hashPair[1]);
                      Model val = (Model)cls.getConstructor().newInstance();
                      val.id = key;
                      int populateVal = 2;
                      for(Field f : cls.getDeclaredFields()){
                          if(f.isAnnotationPresent(Column.class)){
                              Class fType = f.getType();
                              if(fType == int.class || fType == Integer.class){
                                  f.set(val,Integer.parseInt(hashPair[populateVal]));
                              }else if(fType == boolean.class){
                                  f.set(val, Boolean.parseBoolean(hashPair[populateVal]));
                              }else if(fType == String.class){
                                  if(hashPair[populateVal] != "null"){
                                      f.set(val, hashPair[populateVal]);
                                  }else{
                                       f.set(val, null);
                                  }
                              }else{
                                  throw new IllegalArgumentException();
                              }
                          }
                          populateVal++;
                      }
                      info.put(key,val);
                  
               // scanner.close();
              }
              scanner.close();
           }
        }catch(Exception e){        
            throw new IllegalArgumentException();
        }
        return info;
    }

  private static void writeToDatabase(HashMap<Integer,Model> data){
      try{
       FileWriter writer = new FileWriter("database.tsv");
       for(HashMap.Entry<Integer,Model> entry : data.entrySet()){
           Model v = entry.getValue();          
           String curLine = entry.getKey().toString() + "\t" + v.getClass().getName() + "\t";
           for(Field f : v.getClass().getDeclaredFields()){
               if(f.isAnnotationPresent(Column.class))
               curLine = curLine + f.get(v) + "\t";
           }
           writer.write(curLine + "\n");
       }
       writer.close();
      }catch(Exception e){
          throw new IllegalArgumentException();
      }
  }

    public int id() {
        return id.intValue();
    }

    public static <T> T find(Class<T> c, int id) {
        HashMap<Integer, Model> info = readToHashmap();
        Model m = info.get(Integer.valueOf(id));
        return c.cast(m);
    }

    public static <T> List<T> all(Class<T> c) {
        HashMap<Integer, Model> info = readToHashmap();
        List ret = new LinkedList<T>();
        for(HashMap.Entry<Integer, Model> cur : info.entrySet()) {
            try {
                ret.add(c.cast(cur.getValue()));
            } catch (ClassCastException e){}
        }
        return ret; 
    }

    public void destroy() {
         HashMap<Integer, Model> info = readToHashmap();
        if (info.remove(this.id) == null) {
            throw new IllegalArgumentException();
        }
        writeToDatabase(info);
    }

    public static void reset() {
        writeToDatabase(new HashMap<Integer, Model>());
    }
}
