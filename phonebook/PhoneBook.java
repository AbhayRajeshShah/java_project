package phonebook;
import java.util.Scanner;
import phonebook.AbstractPhonebook;
import struct.DNode;
import filehandling.FileHandler;

public class PhoneBook extends AbstractPhonebook{

    private DNode ptr1;
    private DNode ptr2;
    private DNode dup;

    Boolean checkFull(){
        return MAX_CONTACTS<this.noOfContacts+1;
    }

    @Override
    public void readF(){
        head = FileHandler.readFile();
        DNode temp = head;
        while(temp!=null){
            temp=temp.next;
            noOfContacts++;
        }
    }

    @Override
    public void accept() {
        if(checkFull()){
            System.out.println("Limit Reached. Can't add further contacts");
        }else{
            Scanner scanner = new Scanner(System.in);
            String name, number, gmail;
            char ans;
            do {
                this.noOfContacts++;
                System.out.print("Enter Name: ");
                name = scanner.next();
                Boolean validNum = false;
                System.out.print("Enter Number: ");
                do{
                    number = scanner.next();
                    if(number.length()==10){
                        try{
                            double n = Double.parseDouble(number);
                            validNum=true;
                        }catch(NumberFormatException e){
                            validNum=false;
                            System.out.println("Enter a valid number:");
                        }
                    }else{
                        System.out.println("Enter a valid number:");
                        validNum=false;
                    }
                }while(validNum==false);
                Boolean validGmail = false;
                do{
                    System.out.print("Enter G-Mail: ");
                    gmail = scanner.next();               
                    String s[] = gmail.split("@");
                    if(s.length<2){
                        System.out.println("Enter valid mail");
                        validGmail=false;
                    }else{
                        validGmail=true;
                    }
                }while(validGmail==false);
                
                temp = new DNode(name, number, gmail);
                jdbc.insert(temp);
                if (head == null) {
                    head = temp;
                } else {
                    ptr = head;
                    while (ptr.next != null) {
                        ptr = ptr.next;
                    }
                    temp.prev = ptr;
                    ptr.next = temp; 
                }
                System.out.print("Do you want to continue? (y/n): ");
                ans = scanner.next().charAt(0);
            } while (ans == 'y' && !checkFull());
        }
        FileHandler.writeFile("Inserted Records", head);
    }

    @Override
    public void display() {
        ptr = head;
        if(ptr!=null){
            System.out.println("Here is your list of "+noOfContacts+" contact/s");
            while (ptr != null) {
                System.out.println("\n\nNAME  ::\t" + ptr.name);
                System.out.println("NUMBER::\t+91-" + ptr.number);
                System.out.println("G-MAIL::\t" + ptr.gmail);
                ptr = ptr.next;
            }
        }else{
            System.out.println("You have no contacts currently. Please add some.");
        }      
    }
}