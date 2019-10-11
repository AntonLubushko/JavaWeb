import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ParkServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/park",
                    "postgres",
                    "1234");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM garages;");

            PrintWriter printWriter = response.getWriter();
            JSONArray array = new JSONArray();
            while (resultSet.next()) {
                JSONObject record = new JSONObject();
                //Inserting key-value pairs into the json object
                record.put("id", resultSet.getInt("id"));
                record.put("number", resultSet.getString("number"));
                record.put("owner", resultSet.getString("owner"));
                array.add(record);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Garages_data", array);
            printWriter.println(jsonObject.toJSONString());

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}