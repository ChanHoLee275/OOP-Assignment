import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Countries {
    public String[][] data = new String[245][3];
    private String country;
    private double lat;
    private double lng;

    String path = System.getProperty("user.dir");
    String readPath = path + "/Countries.csv";

    Countries(String str)
    {

        try{
            this.country = str;
            FileReader fr = new FileReader(readPath);
            BufferedReader br = new BufferedReader(fr);
            int index = 0;
            String readLine = br.readLine();

            while(readLine != null)
            {
                String[] strArr = readLine.split(",");
                for(int i = 0 ; i < 3; i++ )
                {
                    data[index][i] = strArr[i];
                }
                index++;
                readLine = br.readLine();
            }
        }catch(FileNotFoundException e1)
        {
            e1.getStackTrace();
            System.exit(3);
        }catch(IOException e2)
        {
            e2.getStackTrace();
            System.exit(4);
        }

        for(int i = 0 ; i < 245 ; i ++)
        {
            if(this.data[i][0].equals(str))
            {
                this.lat = Double.parseDouble(this.data[i][1]);
                this.lng = Double.parseDouble(this.data[i][2]);
                break;
            }
        }
    }

    public String getCountry()
    {
        return this.country;
    }

    public double getLat()
    {
        return this.lat;
    }

    public double getLng()
    {
        return this.lng;
    }
}
