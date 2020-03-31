package example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ExampleMapperTest {

    private Example test;
    private IExampleMapper mapper;
    private Connection connection;

    private static final Integer id = 1;
    private static final String text = "Hello world";

    @Before
    public void setUp() throws SQLException {
        mapper = new ExampleMapper(true);
        test = new Example(id, text);
        connection = ConnectionPool.getConnection();
        mapper.insert(test);
        mapper.insert(new Example(2, "test qwerty"));
    }

    @Test
    public void whenLoaded_find() throws SQLException {
        Example example = mapper.find(1);
        System.out.println("loaded example -> " + example.toString());
        Assert.assertTrue("loaded example is not correct", test.equals(example));
    }

    @Test
    public void whenLoaded_Contains() throws SQLException{
        List<Example> examples = mapper.getContainsText("world");
        System.out.println("found like:  -> " + examples.toString());
        Assert.assertTrue("Conaints size is not correct", examples.size() == 1);
        Assert.assertTrue("Conaints result is not correct", test.equals(examples.get(0)));
    }

    @After
    public void terminate() throws SQLException {
        System.out.println("deleting example");
        mapper.delete(id);
        connection.close();
    }
}
