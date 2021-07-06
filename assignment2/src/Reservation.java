public class Reservation implements Comparable{

    private int roomNum = 0;
    private String[] AM = new String[7];
    private String[] PM = new String[7];

    Reservation(String[] line){

        this.roomNum = Integer.parseInt(line[0]);

        for(int i = 0; i < 7; i++){
            this.AM[i] = line[2*i+1];
            this.PM[i] = line[2*i+2];
        }
    }

    public int getRoomNum() {
        return roomNum;
    }

    public String[] getAM() {
        return AM;
    }

    public String[] getPM() {
        return PM;
    }

    public void setAM(int idx, String str){
        this.AM[idx] = str;
    }

    public void setPM(int idx, String str){
        this.PM[idx] = str;
    }

    @Override
    public int compareTo(Object o) {
        Integer roomNum1 = this.roomNum;
        Integer roomNum2 = ((Reservation)o).getRoomNum();
        return roomNum1.compareTo(roomNum2);
    }
}
