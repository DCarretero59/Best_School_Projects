package ConexionBase;

/**
 *
 * @author CAD5TL
 */
import com.mysql.jdbc.Connection;
import java.sql.*;

public class DataBase {

    Connection dbConnection = null;

    public DataBase() {//DataBase constructor
        generateConnection();//Generate Connection to the database
    }

    private void generateConnection() {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            // MySQL MM JDBC driver
            Class.forName(driverName);
            String serverName = "localhost"; //Server address
            String mydatabase = "akinator";  //Data Base Schema
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase; // a JDBC url ///
            String username = "root"; //Data Base User
            String password = "Coco3263616235"; //User Password
            dbConnection = (Connection) DriverManager.getConnection(url, username, password); //Data Base Connection
            System.out.println("Connection successful");
        } catch (ClassNotFoundException e) {
            // Could not connect to the database //
            javax.swing.JOptionPane.showMessageDialog(null, "Could not connect to the database");
            System.exit(0);
        } catch (SQLException e) {
            // Could not connect to the database //
            System.out.println(e.getMessage()); //
            javax.swing.JOptionPane.showMessageDialog(null, "Could not connect to the database");
            System.exit(0);
        } catch (Exception e){
            // Could not connect to the database //
            javax.swing.JOptionPane.showMessageDialog(null, "Could not connect to the database");
            System.exit(0);

        }
    }// End generateConnection

    public boolean getExist(String strQry) {//Statements exists
        boolean flag = false;
        Statement sentencia;
        ResultSet rs = null; //Initialize ResultSet to null
        try {
            sentencia = dbConnection.createStatement();
            rs = sentencia.executeQuery(strQry);
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Query could not be executed. Error: "+e.getMessage()); //Error executing the query
        }
        try {
            if (rs.next()) {
                if (rs.getString(1) != null) {
                    flag = true;//Statement exists
                }
            }
        } catch (SQLException e) { //
            System.out.println("No encontro ningun registro"); //no encontro ningun registro
        }
        return flag;
    }//END GETEXIST

    public Connection getConnection() { //Access data base connection
        return dbConnection; //return the connection
    }//End getConnnection

    public String getValue(String strQry) {//Get FIRST query value as a String
        Statement sentencia;
        ResultSet rs = null;
        String Value = "";
        try {
            sentencia = dbConnection.createStatement();
            rs = sentencia.executeQuery(strQry); //Execute specified query
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Query could not be executed. Error: "+e.getMessage()); //Error executing the query
        }
        try {
            if (rs.next()) {//Move to the first value in the ResultSet
                Value = rs.getString(1); //Save the query value to variable Value
                if (Value == null) {
                    Value = "";//If query value is equal to null, variable Value equals to empty String
                } else {
                    Value = Value.trim();
                }
            } else {
                Value = "";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Value;

    }//FIN GETVALUE

    public int getValueInt(String strQry) {
        Statement sentencia;
        ResultSet rs = null;
        int Value = -1;
        try {
            sentencia = dbConnection.createStatement();
            rs = sentencia.executeQuery(strQry);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (rs.next()) {
                Value = rs.getInt(1);
            } else {
                Value = -1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //no encontro ningun registro
        }

        return Value;

    }//FIN GETVALUE

    public String getValueString(String strQry) {
        Statement sentencia;
        ResultSet rs = null;
        String Value = "";
        try {
            sentencia = dbConnection.createStatement();
            rs = sentencia.executeQuery(strQry);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (rs.next()) {
                Value = rs.getString(1);
            } else {
                Value = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //no encontro ningun registro
        }

        return Value;

    }//FIN GETVALUE

    public float getValueFloat(String strQry) {
        Statement sentencia;
        ResultSet rs = null;
        float Value = -1;
        try {
            sentencia = dbConnection.createStatement();
            rs = sentencia.executeQuery(strQry);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (rs.next()) {
                Value = rs.getFloat(1);
            } else {
                Value = -1;
            }
        } catch (SQLException e) { //
            System.out.println(e.getMessage()); //no encontro ningun registro
        }
        return Value;
    }

    public ResultSet getRecords(String strQry) {
        Statement sentencia;
        ResultSet rs = null;
        try {
            sentencia = dbConnection.createStatement();
            rs = sentencia.executeQuery(strQry);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public boolean executeQuery(String strQry) {
        Statement sentencia;
        boolean flag = false;
        try {
            sentencia = dbConnection.createStatement();
            sentencia.execute(strQry);
            flag = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage()); //
        }
        return flag;
    }//Fin EjecutaQuery

    public boolean executeTransaction(String strQrys) throws SQLException {
        boolean flag = false;
        try {
            dbConnection.setAutoCommit(false);
            String Array_Consultas[] = strQrys.split(";");
            for (int i = 0; i < Array_Consultas.length; i++) {
                Statement sentencia = dbConnection.createStatement();
                System.out.println(Array_Consultas[i]);
                sentencia.execute(Array_Consultas[i]);
            }
            dbConnection.commit();
            dbConnection.setAutoCommit(true);
            flag = true;
        } catch (SQLException e) {
            if (dbConnection != null) {
                dbConnection.rollback();
                dbConnection.setAutoCommit(true);
                flag = false;
                System.out.println("Connection rollback...");
                System.out.println(e.getMessage());
            }
        }
        return flag;
    }//ExecuteTransaction End

    public void closeConnection() {
        try {
            if (dbConnection.isClosed() == false) {
                dbConnection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Error en cerrar conexion");
            System.exit(0);
        }
    }
}
