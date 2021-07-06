public class Distance {
    private String name;
    private double lat;
    private double lng;

    Distance(String name, double lat, double lng)
    {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String writeDistance()
    {
        String answer = String.format("Country : %s\nlatitude = %f\nlongitude = %f\n--------------------",this.name,this.lat,this.lng);
        return answer;
    }

    public static String getDistance(Distance a, Distance b)
    {
        String answer;

        String[] str1 = a.writeDistance().split("\n");
        String[] str2 = b.writeDistance().split("\n");

        double Distance = 0;

        for(int i = 0 ; i < str1.length; i ++)
        {
            if (str1[i].contains("latitude"))
            {
                double lat1 = Double.parseDouble(str1[i].split(" ")[2]);
                double lat2 = Double.parseDouble(str2[i].split(" ")[2]);
                Distance = Distance + Math.pow(lat1-lat2,2);
            }
            else if (str1[i].contains("longitude"))
            {
                double lng1 = Double.parseDouble(str1[i].split(" ")[2]);
                double lng2 = Double.parseDouble(str2[i].split(" ")[2]);
                Distance = Distance + Math.pow(lng1-lng2,2);
            }
        }
        answer = a.writeDistance() + "\n" + b.writeDistance() + "\n" + "is\n" + Double.toString(Distance);
        return answer;
    }
}
