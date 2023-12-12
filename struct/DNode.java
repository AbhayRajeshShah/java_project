package struct;

public class DNode {
    public String number;
    public String gmail;
    public String name;
    public DNode prev;
    public DNode next;

    public DNode(String n, String r, String g) {
        name = n;
        number = r;
        gmail = g;
        next = null;
        prev = null;
    }
}