import java.util.InputMismatchException;
import java.util.Scanner;

import phonebook.PhoneBook;

public class PhoneBookApp {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        char ans;
        int ch, a;
        Boolean invalid = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n******** PHONE BOOK *********");
        System.out.println("\n\nWhat is your name?\n");
        String name = scanner.nextLine();
        phoneBook.readF();
        System.out.println("\n\n!!!!!!!!!!!!!!!!!!!!!!!   WELCOME " + name + "   !!!!!!!!!!!!!!!!!!!!!");
        if(phoneBook.head==null){
            System.out.println("\n\n\nLet's create our phonebook " + name + "  \n");
            phoneBook.accept();
            phoneBook.sort();
        }       
        do {
            System.out.println("\n\n\n1) Display your phone book");
            System.out.println("2) Insert new contact");
            System.out.println("3) Update details on an existing contact");
            System.out.println("4) Delete contact");
            System.out.println("5) Delete same name in the phonebook");
            System.out.println("6) Delete same numbers in the phonebook");
            System.out.println("7) Search");
            try{
                String s = scanner.nextLine();
                if(s==""){
                    s=scanner.nextLine();
                }
                ch = Integer.parseInt(s);
            }catch(NumberFormatException e){
                ch=9;
            }
            switch (ch) {
                case 2:
                    phoneBook.insert();
                    phoneBook.sort();
                    break;
                case 1:
                    phoneBook.display();
                    break;
                case 3:
                    System.out.println("\nEnter the name of the person whose details you want to update...");
                    String n = scanner.next();
                    phoneBook.update(n);
                    phoneBook.sort();
                    break;
                case 4:
                    System.out.println("\nEnter the name you want to delete from the phonebook");
                    String deleteName = scanner.next();
                    phoneBook.deleteContact(deleteName);
                    break;
                case 5:
                    phoneBook.deleteSameName();
                    phoneBook.display();
                    break;
                case 6:
                    phoneBook.deleteSameNumber();
                    phoneBook.display();
                    break;
                case 7:
                    do {
                        System.out.println("1. Search by Name");
                        System.out.println("2. Search by Number");
                        System.out.println("3. Search by G-Mail");
                        a = scanner.nextInt();
                        switch (a) {
                            case 1:
                                System.out.println("Enter the name to be searched");
                                String searchName = scanner.next();
                                phoneBook.searchByField(searchName, "name");
                                break;
                            case 2:
                                System.out.println("Enter the number to be searched");
                                String searchNumber = scanner.next();
                                phoneBook.searchByField(searchNumber, "number");
                                break;
                            case 3:
                                System.out.println("Enter the G-Mail to be searched");
                                String searchGmail = scanner.next();
                                phoneBook.searchByField(searchGmail, "gmail");
                                break;
                            default:
                                System.out.println("Invalid input given");

                        }
                        System.out.println("Do you want to continue searching? (y/n): ");
                        ans = scanner.next().charAt(0);
                    } while (ans == 'y');
                    break;
                default:
                    System.out.println("Invalid input");
                    invalid=true;
            }
            if(!invalid){
                System.out.println("Do you want to continue operations? (y/n): ");
                ans='n';
                ans = scanner.next().charAt(0);
            }else{
                ans='y';
                invalid=false;
            }
            
        } while (ans == 'y');
        System.out.println("\nThank you for using Phone Book !");
    }
}
