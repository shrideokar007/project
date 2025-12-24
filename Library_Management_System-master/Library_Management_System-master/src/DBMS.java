import java.sql.*;

public class DBMS {
    Connection con;
    Statement stmt;
    ResultSet rs;
    PreparedStatement pstmt;
    public final String  url="jdbc:mysql://localhost:3306/library_system";
    public final String  user="root";
    public final String  password="Aditya2003";

    DBMS() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection(url,user,password);
        stmt=con.createStatement();
//        if(con!=null) System.out.println("Connection Successful");
//        else System.out.println("Connection Failed");
        pstmt=null;
        rs=null;

    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }
}
