import java.util.StringTokenizer;
import java.util.logging.Logger;

public class KeyValue
{
    // information from properties.txt
    private String key = null;
    private String value = null;
    KeyValue(String str)
    {
        StringTokenizer token = new StringTokenizer(str,"=");
        if (token.countTokens() == 2)
        {
            this.key = token.nextToken();
            this.value = token.nextToken();
        } else
        {
            Error e = new Error("Input String does not fit the format, {key}={value}.");
            e.printStackTrace();
            System.exit(2);
        }
    }

    KeyValue(String str1, String str2)
    {
        this.key = str1;
        this.value = str2;
    }

    public String getKey()
    {
        return this.key;
    }

    public String getValue()
    {
        return this.value;
    }
}
