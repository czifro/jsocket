import jsock.enums.FunctionType;
import jsock.util.FunctionTool;
import jsock.util.JsonTool;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by czifro on 6/14/15.
 */
public class PackageUnitTests {

    @Test
    public void testFunction() {
        String dirtyMsg = "h\0e\0l\0l\0o\0 \0w\0o\0r\0l\0d";
        String cleanMsg = "hello world";

        Function<String, String> func = FunctionTool.sanitizationFunction(FunctionType.ONLY_NULLS);

        dirtyMsg = FunctionTool.runFunction(dirtyMsg, func);

        Assert.assertEquals(cleanMsg, dirtyMsg);
    }

    @Test
    public void testToJson() {
        String json = "{\"Age\":22,\"DOB\":\"Test\",\"Name\":\"Will\"}";
        Person p = new Person(22, "Test", "Will");

        String result = JsonTool.toJson(p, Person.class);

        Assert.assertEquals(json, result);
    }

    @Test
    public void testFromJson() {
        String json = "{\"Age\":22,\"DOB\":\"Test\",\"Name\":\"Will\"}";
        Person p = new Person(22, "Test", "Will");

        Person result = JsonTool.fromJson(json, Person.class);

        Assert.assertTrue(p.areEqual(result));
    }

    private class Person {
        public int Age;
        private String DOB;
        public String Name;

        public Person(int Age, String DOB, String Name)
        {
            this.Age = Age;
            this.DOB = DOB;
            this.Name = Name;
        }

        public boolean areEqual(Person p) {
            return this.Age == p.Age && this.Name.equals(p.Name) && this.DOB.equals(p.DOB);
        }
    }
}
