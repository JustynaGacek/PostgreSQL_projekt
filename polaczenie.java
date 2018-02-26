import com.jcraft.jsch.*;
import java.util.Properties;
import java.sql.*;
import java.io.*;
import java.util.*;

//klasa sluzy do laczenia sie z pascale poprzez tunelowanie
//oraz do laczenia sie z baza
public class PascalConnection {
    public Connection c;
    private boolean tunnelIsOpen;

    private static volatile PascalConnection db = null;

    public static PascalConnection getInstance() {
        PascalConnection result = db;
        if (db == null) {
            synchronized (PascalConnection.class) {
                db = new PascalConnection();
                if (result == null) {
                    db = result = new PascalConnection();
                }
            }
        }
        return result;
    }

    private PascalConnection() {
        int i;
        tunnelIsOpen = false;
        for(i = 1102; i <= 1200; ++i) {
            System.out.println("opening tunnel i = " + i);
            tunnelIsOpen = openSSH(i, 5432, "5gacekj", "haslo do konta");
            if(tunnelIsOpen) {
                url = "jdbc:postgresql://localhost:" + localPort + "/u5gacekj";
                System.out.println("url = " + url);
                break;
            }
        }
        openPostgres();
    }

    private String dbuser;
    private String dbpass;

    private int localPort;
    private String url;

    private JSch jsch;
    private Session session;
   
    private boolean openSSH(int localPort, int remotePort, String user, String password) {
       
        try {
            jsch = new JSch();
            session = jsch.getSession(user, "pascal.fis.agh.edu.pl", 22);
            session.setPassword(password);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            this.localPort = session.setPortForwardingL(localPort,
                    "pascal.fis.agh.edu.pl",
                    remotePort);

        } catch (JSchException e) {
            return false;
        }


        return true;
    }

    public void openPostgres(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Nie znaleziono sterownika!");
            System.out.println("Wydruk sledzenia bledu i zakonczenie.");
            cnfe.printStackTrace();
            System.exit(1);
        }

        c = null;
        try {
            c = DriverManager.getConnection(url, "u5gacekj", "haslo do bazy");
        } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
            se.printStackTrace();
            System.exit(1);
        }
        System.out.println("Successfull connection");
    }
    
    public Connection getConnection() {
        return c;
    }
 
    public void close()
    {
        try {
            c.close();
            session.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
