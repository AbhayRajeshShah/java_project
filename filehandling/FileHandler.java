package filehandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import struct.DNode;

public class FileHandler {
    public static String path = "C:/Users/Abhay/java/java-proj/contacts.txt";
    File f = new File(path);
    public static DNode head;
    public static void writeFile(String message,DNode updatedHead){
        try{
            FileWriter w = new FileWriter(path);
            head = updatedHead;
            DNode temp=head;
            String writeData="";
            int index=1;
            while(temp!=null){
                writeData+=index+"\n"+temp.name+"\n"+temp.number+"\n"+temp.gmail+"\n"+"\n";
                index++;
                temp=temp.next;
            }
            w.write(writeData);
            w.close();
            System.out.println(message);
        }catch(Exception e){
            System.out.println(e);
        }  
    }
    public static DNode readFile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String[] data = new String[3];
            String st;
            DNode temp = null;
            DNode prev = null;
            while ((st = reader.readLine()) != null) {
                for (int i = 0; i < 4; i++) {
                    if(i!=0){
                        data[i-1] = st;
                    }       
                    st = reader.readLine();
                }
                DNode newNode = new DNode(data[0], data[1], data[2]);
                if (temp == null) {
                    temp = newNode;
                    temp.prev = null;
                    prev = temp;
                } else {
                    prev.next = newNode;
                    newNode.prev = prev;
                    prev = newNode;
                }
                head=temp;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return head;
    }
}
