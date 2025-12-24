import java.sql.PreparedStatement;
import java.sql.ResultSet;
import  java.util.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void validate(int id,String pass,String table) throws Exception{

        String sql;
        if(table.equals("student")) sql="select * from "+table+" where student_id = ? and password = ? ";
        else sql="select * from "+table+" where admin_id = ? and password = ? ";
        PreparedStatement pst = new DBMS().getCon().prepareStatement(sql);

        pst.setInt(1,id);
        pst.setString(2,pass);
        ResultSet rs = pst.executeQuery();

        if(rs.next()){
            if(table.equals("student")){
                Student s;
                System.out.println("Welcome "+id);
                System.out.println("Your Profile :-");
                s=new Student(rs.getInt(1),rs.getString(2),rs.getString(4),rs.getString(5),rs.getInt(3));
                System.out.println(s);
                student(s);

            }else{
                Admin a;
                System.out.println("Welcome "+id);
                System.out.println("Your Info :-");
                a=new Admin(rs.getInt(1),rs.getString(2),rs.getString(3));
                System.out.println(a);


                admin();

            }
        }else {
            if(table.equals("student")){
                System.out.println("Invalid Student Login");
               studentRegistraionAndLogin();
            }else{
                System.out.println("Invalid Admin Login");
                adminRegistraionAndLogin();
            }
        }
    }

    public static void student(Student s) throws Exception{
        int choice;
        BookOperation bOpt = new BookOperation();
        AdminOperation aOpt = new AdminOperation();
        StudentOperation sOpt = new StudentOperation();
        do{
            System.out.println("-----Students Operations-----");
            System.out.println("1.Show  Books");
            System.out.println("2.Issued Book");
            System.out.println("3.Return Book");
            System.out.println("4.Show Issued Book");
            System.out.println("5.Logout ->>");
            System.out.println("Enter your choice");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    sOpt.ShowBooks(s);
                    break;
                case 2:
                    sOpt.issueBook(s);
                    break;
                case 3:
                    sOpt.returnBook(s);
                    break;
                case 4:
                    sOpt.ShowIssuedBooks(s);
                    break;
                case 5:
                    studentRegistraionAndLogin();
                    break;
                default:
                    System.out.println("Invalid choice");

            }
        }while(choice>0 && choice<4);
    }


    public static void admin() throws Exception{
        int choice;
        BookOperation bOpt = new BookOperation();
        AdminOperation aOpt = new AdminOperation();
        do{
            System.out.println("-----Admin Operations-----");
            System.out.println("1.Show Student List");
            System.out.println("2.Show Admin List");
            System.out.println("3.Show Books List");
            System.out.println("4.Add Book");
            System.out.println("5.Update Book");
            System.out.println("6.Delete Book");
            System.out.println("7.Show History");
            System.out.println("8.Show Issued Books History");
            System.out.println("9.Logout ->>");
            System.out.println("Enter your choice");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    bOpt.ShowBooks();
                    break;
                case 4:
                    bOpt.addBook();
                    break;
                case 5:
                    bOpt.updateBook();
                    break;
                case 6:
                    bOpt.deleteBook();
                    break;
                case 7:
                    aOpt.showHistory();
                    break;
                case 8:
                    aOpt.showActiveBook();
                    break;
                case 9:
                   adminRegistraionAndLogin();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }while(choice>0 && choice<9);
    }


    public static void studentRegistraionAndLogin() throws Exception{
        int s;
        System.out.println("*******************");
        System.out.println("Welcome Student Registration\\Login");
        System.out.println("*******************");
        System.out.println("1.Login");
        System.out.println("2.Register");
        System.out.println("3.Back->>");
        System.out.println("*******************");
        System.out.println("Enter your choice");
        s = sc.nextInt();
        switch(s){
            case 1:
                System.out.println("Enter your Id");
                int id = sc.nextInt();
                System.out.println("Enter your Password");
                String password = sc.next();
                validate(id,password,"student");
                break;
            case 2:
                System.out.println("Enter Id :-");
                int id2 = sc.nextInt();
                System.out.println("Enter your Name :-");
                String name = sc.next();
                System.out.println("Enter Contact No. :-");
                int contact_no = sc.nextInt();
                System.out.println("Enter Email id :-");
                String email = sc.next();
                System.out.println("Enter Password :-");
                String pass = sc.next();
                Student st =new Student(id2,name,email,pass,contact_no);
                st.addStudent();
                System.out.println("Successfully added student");
                break;
            case 3:
                main(new String[]{"ram","Sham"});
                break;
            default:
                System.out.println("Wrong choice");

        }
    }

    public static void adminRegistraionAndLogin() throws Exception{
        int a;
        System.out.println("*******************");
        System.out.println("Welcome Admin Registration\\Login");
        System.out.println("*******************");
        System.out.println("1.Login");
        System.out.println("2.Register");
        System.out.println("3.Back->>");
        System.out.println("*******************");
        System.out.println("Enter your choice");
        a = sc.nextInt();
        switch(a){
            case 1:
                System.out.println("Enter your Id");
                int id = sc.nextInt();
                System.out.println("Enter your Password");
                String password = sc.next();
                validate(id,password,"admin");
                break;
            case 2:
                System.out.println("Enter Id :-");
                int id2 = sc.nextInt();
                System.out.println("Enter your Name :-");
                sc.nextLine();
                String name =  sc.nextLine();
                System.out.println("Enter Password :-");
                String pass = sc.next();
                Admin ad =new Admin(id2,name,pass );
                ad.addAdmin();
                System.out.println("Successfully added admin");
                break;
            case 3:
                main(new String[]{"ram","Sham"});
                break;
            default:
                System.out.println("Wrong choice");

        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int c;
        do{
            System.out.println("*******************");
            System.out.println("Welcome to Library Management System");
            System.out.println("*******************");
            System.out.println("1.Admin");
            System.out.println("2.Students");
            System.out.println("*******************");
            System.out.println("Enter your choice");
            c = sc.nextInt();
            switch(c){
                case 1:
                    adminRegistraionAndLogin();
                    break;
                case 2:

                    studentRegistraionAndLogin();
                    break;
                default:
                    System.out.println("Wrong choice");

            }
        }while(c>0 && c<3 );;
    }
}