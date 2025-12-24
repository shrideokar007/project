import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Book {

    private int book_id;
    private String book_name;
    private String book_author;
    private int quantity;

    public Book(int book_id, String book_name, String book_author, int quantity) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_author = book_author;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", book_author='" + book_author + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

class BookOperation{
    Scanner sc = new Scanner(System.in);
    DBMS dbms;

    {
        try {
            dbms = new DBMS();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void ShowBooks() throws Exception{
        System.out.println("---- Books Library---- ");
        String sql="select * from books";
        Statement stmt = dbms.getStmt();
        ResultSet rs  = stmt.executeQuery(sql);
        while(rs.next()){
            Book book =new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            System.out.println(book);
        }
        Main.admin();

    }

    public void addBook() throws Exception{
        System.out.println("----Add Book----");
        System.out.println("Enter Book ID");
        int book_id=sc.nextInt();
        System.out.println("Enter Book Name");
        sc.nextLine();
        String book_name=sc.nextLine();
        System.out.println("Enter Book Author");

        String book_author=sc.nextLine();
        sc.nextLine();
        System.out.println("Enter Quantity");
        int quantity=sc.nextInt();
        String sql="insert into books values (?,?,?,?)";
        PreparedStatement stmt = dbms.getCon().prepareStatement(sql);
        stmt.setInt(1,book_id);
        stmt.setString(2,book_name);
        stmt.setString(3,book_author);
        stmt.setInt(4,quantity);
        int row=stmt.executeUpdate();
        if(row>0) System.out.println("Book added Book Id:"+book_id);
        else System.out.println("Book is Unable to add.");
        Main.admin();

    }

    public void deleteBook() throws Exception{
        System.out.println("----Delete Book----");
        System.out.println("Enter Book Id Want to Delete :-");
        int id=sc.nextInt();
        String sql="delete from books where book_id="+id;
        PreparedStatement stmt = dbms.getCon().prepareStatement(sql);

        int row=stmt.executeUpdate();
        if(row>0) System.out.println("Book deleted Book Id:"+id);
        else System.out.println("Book is Unable to Delete.");
        Main.admin();

    }

    public void updateBook() throws Exception{
        System.out.println("----Update Book----");
        System.out.println("Enter Book ID Want to Update :-");
        int book_id=sc.nextInt();
        System.out.println("Enter Book Quantity Want to Update :-");
        int quantity=sc.nextInt();
        String sql="update books set quantity=quantity+? where book_id="+book_id;
        PreparedStatement stmt = dbms.getCon().prepareStatement(sql);
        stmt.setInt(1,quantity);
        int row=stmt.executeUpdate();
        if(row>0) System.out.println("Book Updated Book Id:"+book_id);
        else System.out.println("Book is Unable to Update.");
        Main.admin();
    }
}
