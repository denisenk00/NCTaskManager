package ua.edu.sumdu.j2se.denysenko.tasks.model;


import com.google.gson.Gson;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        DataOutput dataOutput = new DataOutputStream(out);
        dataOutput.writeInt(tasks.size());
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task a = it.next();
            dataOutput.writeInt(a.getTitle().length());
            dataOutput.writeUTF(a.getTitle());
            dataOutput.writeBoolean(a.isActive());
            dataOutput.writeInt(a.getRepeatInterval());
            if (a.isRepeated()) {
                dataOutput.writeLong(a.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                dataOutput.writeLong(a.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            } else {
                dataOutput.writeLong(a.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        DataInput dataInput = new DataInputStream(in);
        int size = dataInput.readInt();
        for(int i = 0; i < size; i++){
            Task a = new Task();
            int sizeTitle = dataInput.readInt();
            a.setTitle(dataInput.readUTF());
            a.setActive(dataInput.readBoolean());
            int interval = dataInput.readInt();
            if(interval != 0){
                a.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(dataInput.readLong()), ZoneId.systemDefault()),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(dataInput.readLong()), ZoneId.systemDefault()),
                        interval);
            }
            else{
                a.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(dataInput.readLong()), ZoneId.systemDefault()));
            }
            tasks.add(a);
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            write(tasks, fileOutputStream);
            fileOutputStream.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void readBinary(AbstractTaskList tasks, File file) {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            read(tasks, fileInputStream);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson gson = new Gson();
        LinkedTaskList taskList = new LinkedTaskList();
        tasks.getStream().forEach(taskList::add);
        gson.toJson(taskList, out);
        out.flush();
    }
    public static void read(AbstractTaskList tasks, Reader in){
        Gson gson = new Gson();
        LinkedTaskList taskList = gson.fromJson(in, LinkedTaskList.class);
        Iterator <Task> it = taskList.iterator();
        while(it.hasNext()){
            tasks.add(it.next());
        }
    }
    public static void writeText(AbstractTaskList tasks, File file) {
        try(FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            LinkedTaskList taskList = new LinkedTaskList();
            tasks.getStream().forEach(taskList::add);
            gson.toJson(taskList, fileWriter);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void readText(AbstractTaskList tasks, File file) {
        try(FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            LinkedTaskList taskList = gson.fromJson(fileReader, LinkedTaskList.class);
            Iterator <Task> it = taskList.iterator();
            while(it.hasNext()){
                tasks.add(it.next());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
