package example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExampleMapper extends Mapper<Example, Integer> implements IExampleMapper {

    private static final String COLUMNS = " id, text ";
    private static final String TABLE_NAME = "example_table";

    private Boolean doManage;

    public ExampleMapper(Boolean doManage) throws SQLException {
        if (this.doManage = doManage) {
            Connection con = ConnectionPool.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            st.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                    "(" +
                            "id integer PRIMARY KEY, " +
                            "text TEXT" +
                    ");",
                    TABLE_NAME));
            st.close();
            con.close();
        }
    }

    @Override
    protected String getFindStatement(Integer id) {
        return "SELECT " + COLUMNS +
                " FROM " + TABLE_NAME +
                " WHERE id = "+ id.toString() + ";";
    }

    @Override
    protected String getInsertStatement(Example example) {
        return "INSERT INTO " + TABLE_NAME +
                "(" + COLUMNS + ")" + " VALUES "+
                "("+
                    example.getId().toString() + "," +
                    '"' + example.getText() + '"' +
                ");";
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected Example convertResultSetToObject(ResultSet rs) throws SQLException {
        return  new Example(
                rs.getInt(1),
                rs.getString(2)
        );
    }

    @Override
    public List<Example> getContainsText(String text) throws SQLException {
        List<Example> result = new ArrayList<Example>();
        String statement = "SELECT " + COLUMNS + " FROM " + TABLE_NAME +
                " Where text LIKE " + "'%" + text + "%'";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

}
