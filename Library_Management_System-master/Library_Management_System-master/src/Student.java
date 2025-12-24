import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.sql.*;
import java.util.Date;

public class Student {
    private int student_id;
    private String student_name;
    private String student_email;
    private String student_password;
    private int contact_no;

    DBMS dbms;

    {
        try {
            dbms = new DBMS();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Student(int student_id, String student_name, String student_email, String student_password, int contact_no) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_email = student_email;
        this.student_password = student_password;
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + student_id +
                ", student_name='" + student_name + '\'' +
                ", student_email='" + student_email + '\'' +
                ", student_password='" + student_password + '\'' +
                ", contact_no=" + contact_no +
                '}';
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }

    public int getContact_no() {
        return contact_no;
    }

    public void setContact_no(int contact_no) {
        this.contact_no = contact_no;
    }

    public void addStudent() throws Exception{
        String query = "insert into student values(?,?,?,?,?)";
        String query2="create table table_"+student_name+" ( book_id int,book_name varchar(40),start_date date,end_date date)";
        int row= dbms.getStmt().executeUpdate(query2);
        PreparedStatement pstmt = dbms.getCon().prepareStatement(query);
        pstmt.setInt(1, student_id);
        pstmt.setString(2, student_name);
        pstmt.setInt(3, contact_no);
        pstmt.setString(4, student_email);
        pstmt.setString(5, student_password);
        int i=pstmt.executeUpdate();
        if(i>0 && row>0){
            System.out.println("Student added successfully");
        }else{
            System.out.println("Student adding failed");
        }
        Main.student(this);

    }


}

class StudentOperation {
    DBMS dbms;

    {
        try {
            dbms = new DBMS();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    Scanner sc =new Scanner(System.in);
    Date currDate = new Date();
    int date = currDate.getDate();
    int month=currDate.getMonth()+1;
    int year=1900+currDate.getYear();

    String startDate=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date);
    String endDate=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(date+5);


    public boolean validBookId(int book_id) throws Exception{
        Statement stmt = dbms.getStmt();
        ResultSet rs = stmt.executeQuery("select book_id from books");
        int f=0;
        while(rs.next()){
            if(rs.getInt(1) == book_id){
                 f=1;
                 break;
            }
        }
        boolean b = f == 0 ? false : true;
        return b;
    }
    public  void issueBook(Student s) throws Exception{

        System.out.println(startDate+","+endDate);
        System.out.println("Enter Student Id:-");
        int id =sc.nextInt();
        System.out.println("Enter Book Id :-");
        int book_id =sc.nextInt();
        if(validBookId(book_id)){
            ResultSet rs= dbms.getStmt().executeQuery("select quantity,book_name from books where book_id="+book_id);
            int q=0;
            String book_name="";
            while(rs.next()){q=rs.getInt(1); book_name=rs.getString(2);}
            dbms.getCon().setAutoCommit(false);
            String query1 = "insert into relationship values ("+id+","+book_id+",'"+startDate+"','"+endDate+"')";
            String query2 = "insert into  history(student_id, book_id, issue_date,return_date) values ("+id+","+book_id+", '"+startDate+"','"+endDate+"')";
            String query3="insert into table_"+s.getStudent_name()+" values("+book_id+",'"+book_name+"','"+startDate+"','"+endDate+"')";
            String query4 = "update books set quantity="+q+"-1 where book_id="+book_id;
          // System.out.println(query1);
          //  System.out.println(query2);
          //  System.out.println(query3);
           // System.out.println(query4);

            Statement stmt = dbms.getStmt();
            stmt.addBatch(query1);
            stmt.addBatch(query2);
            stmt.addBatch(query3);
            stmt.addBatch(query4);
            int a[]=stmt.executeBatch();
            int flag=0;
            for(int i:a){
                if(i==0){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                dbms.getCon().commit();
                System.out.println(" Transaction completed successfully--->Book issued");
            }
            else{
                dbms.getCon().rollback();
                System.out.println("--Transaction failed --");
            }
        }
        Main.student(s);

    }

    public void returnBook(Student s) throws Exception{

        System.out.println("Enter  Book  Id which You want to return:-");
        int book_id= sc.nextInt();
        ResultSet rs= dbms.getStmt().executeQuery("select quantity,book_name from books where book_id="+book_id);
        //System.out.println("select  return_date from relationship where book_id="+book_id+"and student_id="+s.getStudent_id());
        ResultSet rs2 = dbms.getCon().createStatement().executeQuery("select  return_date from relationship where book_id="+book_id+" and student_id="+s.getStudent_id());
        int q=0;
        while(rs.next()){q=rs.getInt(1);}
        Date d = new Date();
        while(rs2.next()){
            d=rs2.getDate(1);
        }

        String query1="delete from table_"+s.getStudent_name()+" where book_id="+book_id;
        String query2="delete from relationship where book_id="+book_id+" and student_id="+s.getStudent_id();
        String query3 = "update books set quantity="+q+"+1 where book_id="+book_id;
        int fine=0;
        if(d.getDate() > date){
            final int fineCharge=10;
            fine=(d.getDate()-date)*10;
        }
        String query4="update history set student_return_date='"+startDate+"', fine_in_rs ="+fine+" where student_id="+s.getStudent_id()+" and book_id="+book_id;
        //System.out.println(query4);
        dbms.getCon().setAutoCommit(false);
        Statement stmt = dbms.getStmt();
        stmt.addBatch(query1);
        stmt.addBatch(query2);
        stmt.addBatch(query3);
        stmt.addBatch(query4);
        stmt.executeBatch();

        int a[]=stmt.executeBatch();
        int flag=0;
        for(int i:a){
            if(i==0){
                flag=1;
                break;
            }
        }
        if(flag==0){
            dbms.getCon().commit();
            System.out.println(" Transaction completed successfully--->Book Returned");
        }
        else{
            dbms.getCon().rollback();
            System.out.println("--Transaction failed --");
        }
        Main.student(s);
    }

    public void ShowBooks(Student s) throws Exception{
        System.out.println("---- Books Library---- ");
        String sql="select * from books";
        Statement stmt = dbms.getStmt();
        ResultSet rs  = stmt.executeQuery(sql);
        while(rs.next()){
            Book book =new Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            System.out.println(book);
        }
        Main.student(s);

    }

    public void ShowIssuedBooks(Student s) throws Exception{
        System.out.println("----Your Issued Books---- ");
        String sql="select * from table_"+s.getStudent_name();
        Statement stmt = dbms.getStmt();
        ResultSet rs  = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println(rs.getInt(1)+" | "+rs.getString(2)+" | "+rs.getDate(3)+" | "+rs.getDate(4));
        }
        Main.student(s);

    }

}
