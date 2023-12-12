package phonebook;
import java.util.InputMismatchException;
import java.util.Scanner;

import filehandling.FileHandler;
import phonebook.PhoneBookInterface;
import struct.DNode;
import database.JDBC;

public abstract class AbstractPhonebook implements PhoneBookInterface{
    public DNode head;
    public DNode temp;
    public DNode ptr;
    private DNode ptr1;
    private DNode ptr2;
    private DNode dup;
    public int noOfContacts = 0;
    public JDBC jdbc;

    AbstractPhonebook(){
        head = null;
        temp = null;
        ptr = null;
        ptr1 = null;
        ptr2 = null;
        dup = null;
        jdbc = new JDBC();
    }

    @Override
    public void insert() {
        accept();
    }

    public void sort() {
        DNode i, j;
        String tempName;
        for (i = head; i.next != null; i = i.next) {
            for (j = i.next; j != null; j = j.next) {
                if (i.name.compareTo(j.name) > 0) {
                    tempName = i.name;
                    i.name = j.name;
                    j.name = tempName;
                }
            }
        }
    }


    @Override
    public void deleteSameName() {
        ptr1 = head;
        int updates =0;
        while (ptr1 != null && ptr1.next != null) {
            ptr2 = ptr1;
            while (ptr2.next != null) {
                if (ptr1.name.equals(ptr2.next.name)) {
                    dup = ptr2.next;
                    ptr2.next = ptr2.next.next;
                    dup = null;
                    updates++;
                } else {
                    ptr2 = ptr2.next;
                }
            }
            ptr1 = ptr1.next;
        }
        noOfContacts--;
        FileHandler.writeFile("Deleted "+updates+" Contacts with same name", head);
        jdbc.update(head);
    }

    @Override
    public void deleteContact(String s){
        if(head!=null){
        int c = 0;
        ptr = head;
        while (ptr != null) {
            if (s.equals(ptr.name)) {
                c = 1;
                break;
            }
            ptr = ptr.next;
        }
        if(c==1){
            if(ptr.prev==null){
                ptr.next.prev = null;
                head = ptr.next;
            }
            else if(ptr.next==null){
                ptr.prev.next=null;
            }else{
                ptr.prev.next = ptr.next;
                ptr.next.prev = ptr.prev;
                ptr = null;
            }
        noOfContacts--;
        FileHandler.writeFile("Deleted contact with name: "+s, head);
        jdbc.update(head);
        }else{
            System.out.println("Name not found");
        }
        }else{
            System.out.println("You dont have any contacts currently.");
        }      
    }

    @Override
    public void deleteSameNumber() {
        ptr1 = head;
        int updates =0;
        while (ptr1 != null && ptr1.next != null) {
            ptr2 = ptr1;
            while (ptr2.next != null) {
                if (ptr1.number.equals(ptr2.next.number)) {
                    dup = ptr2.next;
                    ptr2.next = ptr2.next.next;
                    dup = null;
                    updates++;
                } else {
                    ptr2 = ptr2.next;
                }
            }
            ptr1 = ptr1.next;
        }
        noOfContacts--;
        jdbc.update(head);
        FileHandler.writeFile("Deleted "+updates+" Contacts with same number", head);
    }

    public String getString(DNode p,String field){
        if(field.equals("name")){
                 return ptr.name;
            }else if(field.equals("number")){
                return ptr.number;
            }else if(field.equals("gmail")){
                return ptr.gmail;
            }
        return ptr.name;
    }

   @Override
   public void searchByField(String na,String field){
    ptr = head;
    Boolean found = false;
        while (ptr != null) {
            if (na.equals(this.getString(ptr, field))) {
                found=true;
                System.out.println("Gmail found");
                System.out.println("Contact details are below:");
                System.out.println("\n\nNAME  ::\t" + ptr.name);
                System.out.println("NUMBER::\t+91-" + ptr.number);
                System.out.println("G-MAIL::\t" + ptr.gmail);
            }
            ptr = ptr.next;
        }
        if(!found){
            System.out.println("Contact with "+field+" "+na+" not found");
        }
   }



    @Override
    public void update(String n) {
        char ans;
        int c=1;
        ptr = head;
        Boolean foundName = false;
        while (ptr != null) {
            if (n.equals(ptr.name)) {
                foundName = true;
                do {
                    Scanner scanner = new Scanner(System.in);
                    Boolean valid = false;
                    do{
                        System.out.println("\nWhat do you want to update?");
                        System.out.println("1. Name");
                        System.out.println("2. Phone Number");
                        System.out.println("3. G-Mail");        
                        try{
                            c = scanner.nextInt();             
                        }catch(InputMismatchException e){
                            System.out.println("Enter valid Input");
                            valid=false;
                        }
                    }while(valid==false);
                    switch (c) {
                        case 1:
                            System.out.println("Enter new name: ");
                            ptr.name = scanner.next();
                            break;
                        case 2:
                            System.out.println("Enter new phone number: ");
                            ptr.number = scanner.next();
                            while (ptr.number.length() != 10) {
                                System.out.println("Enter a valid number: ");
                                ptr.number = scanner.next();
                            }
                            break;
                        case 3:
                            System.out.println("Enter new G-Mail: ");
                            ptr.gmail = scanner.next();
                            break;
                    }
                    System.out.println("Do you want to continue updating? (y/n)");
                    ans = scanner.next().charAt(0);
                } while (ans == 'y');
            }
            ptr = ptr.next;
        }
        if(foundName){
            FileHandler.writeFile("Updated contact with name: "+n, head);
            jdbc.update(head);
        }else{
            System.out.println("Contact with name not found");
        }
    }

    @Override
    public abstract void accept();

    @Override
    public abstract void display();

    @Override
    public abstract void readF();
}
