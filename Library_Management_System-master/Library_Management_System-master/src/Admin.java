
import java.util.*;
import java.sql.*;

public class Admin {
    private int admin_id;
    private String admin_name;
    private String admin_password;

    Scanner sc = new Scanner(System.in);
    DBMS dbms;

    {
        try {
            dbms = new DBMS();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public Admin(int admin_id, String admin_name, String admin_password) {
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.admin_password = admin_password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "admin_id=" + admin_id +
                ", admin_name='" + admin_name + '\'' +
                ", admin_password='" + admin_password + '\'' +
                '}';
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public void addAdmin() throws Exception{
        String query ="insert into admin values(?,?,?)";
        PreparedStatement pst = dbms.getCon().prepareStatement(query);
        pst.setInt(1,admin_id);
        pst.setString(2,admin_name);
        pst.setString(3,admin_password);
        pst.execute();

    }



}

class AdminOperation{
    DBMS dbms;

    {
        try {
            dbms = new DBMS();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showHistory() throws Exception{
        System.out.println("---- Books Library---- ");
        String sql="select * from history";
        Statement stmt = dbms.getStmt();
        ResultSet rs  = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println(rs.getInt(1)+", "+rs.getInt(2)+", "+rs.getDate(3)+", "+rs.getDate(4)+", "+rs.getDate(5));
        }
        Main.admin();

    }

    public void showActiveBook() throws Exception{
        System.out.println("---- Books Library---- ");
        String sql="select * from relationship";
        Statement stmt = dbms.getStmt();
        ResultSet rs  = stmt.executeQuery(sql);
        while(rs.next()){
            System.out.println(rs.getInt(1)+", "+rs.getInt(2)+", "+rs.getDate(3)+", "+rs.getDate(4) );
        }
        Main.admin();

    }


}
