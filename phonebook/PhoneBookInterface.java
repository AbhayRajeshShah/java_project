package phonebook;

public interface PhoneBookInterface {
    int MAX_CONTACTS=10;
    void insert();
    void sort();
    void deleteContact(String s);
    void deleteSameName();
    void deleteSameNumber();
    void searchByField(String na,String field);
    void update(String n);
    void accept();
    void display();
    void readF();   
}
