import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestJsonToDB {
    public static void main(String args[]){
        JsonToDB jsonToDB=new JsonToDB();
        jsonToDB.preparedStatementInsertOperation();

    }
    /* public static void addProduct()
     {


         try{

             Object obj = parser.parse(new FileReader("c.\\itemize.json"));
             JSONObject jsonObject = (JSONObject) obj;

             String pr = (String) jsonObject.get("Pr");
             //Put pr into database

             String n = (String) jsonObject.get("n");
             //Put n into database

             String yst = (String) jsonObject.get("yst");
             //Put yst into database

             String wh = (String) jsonObject.get("wh");
             //Put wh into database

         }
     }*/
    public static void preparedStatementInsertOperation(){
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        JSONParser parser=new JSONParser();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root","root");
            String sqlInsert= "insert into employee2 values(?,?,?,?)";
            preparedStatement=connection.prepareStatement(sqlInsert);
            Object obj = parser.parse(new FileReader("C:\\Users\\MT1009\\IdeaProjects\\com.modak.Project\\src\\abc.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject   itemize = (JSONObject) jsonObject.get("itemize");
            String pr = (String) itemize.get("pr");
            System.out.println(pr);
            // Parameters start with 1
            preparedStatement.setString(1, pr);

            String n = (String) itemize.get("n");
            preparedStatement.setString(2, n);

            String yst = (String) itemize.get("yst");
            preparedStatement.setString(3, yst);

            String wh = (String) itemize.get("wh");
            preparedStatement.setString(4, wh);
            int status = preparedStatement.executeUpdate();

            //int i=preparedStatement.executeUpdate();
           /* Object obj = parser.parse(new FileReader("C:\\work area\\Project"));
            JSONObject jsonObject = (JSONObject ) obj;

            String pr = (String) itemize.get("Pr");
            String n = (String) itemize.get("n");
            String yst = (String) itemize.get("yst");
            String wh = (String) itemize.get("wh");
           */
            System.out.println("Number of rows affected : "+status);
            preparedStatement.close();
            connection.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(!preparedStatement.isClosed() && preparedStatement!=null) {
                    preparedStatement.close();
                }
                if(!connection.isClosed() && connection!=null) {
                    connection.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}