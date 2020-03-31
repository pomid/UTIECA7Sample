package example;

import java.sql.SQLException;
import java.util.List;

public interface IExampleMapper extends IMapper<Example, Integer> {

    List<Example> getContainsText(String text) throws SQLException;

}