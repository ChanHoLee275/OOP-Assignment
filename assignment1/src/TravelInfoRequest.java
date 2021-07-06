import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelInfoRequest {
    public static void main(String[] arg)
    {
        String path = System.getProperty("user.dir");
        String readPath1 = path + "/template_file.txt";
        String readPath2 = path + "/properties.txt";
        String writePath = path + "/output_file.txt";

        LinkedList<String> inputMessage = new LinkedList<>();
        LinkedList<String> outputMessage = new LinkedList<>();
        Hashtable<String,String> properties = new Hashtable<String,String>();

        try
        {
            // read template_file.txt
            FileReader fr1 = new FileReader(readPath1);
            BufferedReader br1 = new BufferedReader(fr1);

            // read template_file.txt
            FileReader fr2 = new FileReader(readPath2);
            BufferedReader br2 = new BufferedReader(fr2);

            String readLine1 = br1.readLine();


            while(readLine1 != null)
            {
                inputMessage.add(readLine1);
                readLine1 = br1.readLine();
            }

            String readLine2 = br2.readLine();
            String key;
            String value;

            while(readLine2 != null)
            {
                KeyValue str = new KeyValue(readLine2);
                key = str.getKey();
                value = str.getValue();
                properties.put(key,value);
                readLine2 = br2.readLine();

            }

            // Date properties
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            KeyValue str = new KeyValue("date",formatter.format(calendar.getTime()));
            key = str.getKey();
            value = str.getValue();
            properties.put(key,value);

            br1.close();
            fr1.close();

            br2.close();
            fr2.close();
        }catch (FileNotFoundException e){
            e.getStackTrace();
            System.exit(1);
        }

        catch(IOException e)
        {
            e.getStackTrace();
            System.exit(2);
        }

        int numberOfLines = inputMessage.size();
        List<String> keys = Collections.list(properties.keys());
        Distance start = null;
        Distance depart = null;
        // changing keys in message to values in properties
        for(int i = 0; i < numberOfLines; i++)
        {
            String str = inputMessage.pop();
            if (str.contains("{"))
            {
                String[] messageArr = str.split("[{}]");
                String output = "";
                for (int j = 0; j < messageArr.length; j++)
                {
                    // country 설정
                    if(messageArr[j].equals("startcountry"))
                    {
                        Countries startCountry = new Countries(properties.get(messageArr[j]));
                        start = new Distance(properties.get(messageArr[j]),startCountry.getLat(),startCountry.getLng());
                    }
                    else if(messageArr[j].equals("departcountry"))
                    {
                        Countries departCountry = new Countries(properties.get(messageArr[j]));
                        depart = new Distance(properties.get(messageArr[j]), departCountry.getLat(), departCountry.getLng());
                    }
                    if (keys.contains(messageArr[j]))
                    {
                        output = output.concat(properties.get(messageArr[j]));
                    }
                    else
                    {
                        output = output.concat(messageArr[j]);
                    }
                }
                outputMessage.add(output);
            }
            else
            {
                outputMessage.add(str);
            }
        }
        // write message to output_file.txt

        numberOfLines = outputMessage.size();
        try
        {
            File output = new File(writePath);
            output.createNewFile();
        } catch(IOException e)
        {
            e.getStackTrace();
        }

        // write message to output_file.txt
        try
        {
            FileWriter fw = new FileWriter(writePath);
            BufferedWriter bw = new BufferedWriter(fw);
            String writeline;

            for(int i = 0; i < numberOfLines; i ++)
            {
                writeline = outputMessage.pop();
                if(writeline.equals("<add info>"))
                {
                    writeline = Distance.getDistance(start,depart);
                }
                bw.write(writeline);
                bw.newLine();
            }

            bw.close();
            fw.close();

        } catch(IOException e)
        {
            e.getStackTrace();
        }
    }
}
