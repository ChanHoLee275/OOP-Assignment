import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Professor> professors = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args){
        // file path
        String path = System.getProperty("user.dir");
        // read file
        String line1 = readStudent(path + "/res/student.csv",students);
        String line2 = readProfessor(path + "/res/professor.csv",professors);
        String line3 = readReservation(path + "/res/reservation.csv",reservations);

        // make reservationRecord.csv file
        File record = new File(path+"/res/reservationRecord.csv");
        if(! record.exists()){
            try{
            record.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        // program start point
        try{
            Display.running();
        }
        catch (Exception e){
            String message = e.getMessage();
            System.out.println(message);
        }
        finally {
            writeStudent(path + "/res/student.csv", line1, students);
            writeProfessor(path + "/res/professor.csv", line2, professors);
            writeReservation(path + "/res/reservation.csv", line3, reservations);
        }
    }

    public static String readStudent(String path, ArrayList<Student> arrayList){
        String output = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            output = br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                arrayList.add(new Student(values));
            }
            br.close();
        }
        catch(Exception e){

        }
        return output;
    }

    public static String  readProfessor(String path, ArrayList<Professor> arrayList){
        String output = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            output = br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                arrayList.add(new Professor(values));
            }
            br.close();
        }
        catch(Exception e){

        }
        return output;
    }

    public static String  readReservation(String path, ArrayList<Reservation> arrayList){
        String output = null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            output = br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                arrayList.add(new Reservation(values));
            }
            br.close();
        }
        catch(Exception e){

        }
        return output;

    }

    public static void writeStudent(String path, String buffer,ArrayList<Student> arrayList){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(buffer);
            writer.write("\n");
            for(int i = 0; i < arrayList.size(); i++){
                writer.write(arrayList.get(i).getName() + "," + arrayList.get(i).getDepart() + ","+ arrayList.get(i).getStudentId() +","+arrayList.get(i).getPhoneNum());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeProfessor(String path, String buffer, ArrayList<Professor> arrayList){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(buffer);
            writer.write("\n");
            for(int i = 0; i < arrayList.size(); i++){
                writer.write(arrayList.get(i).getName() + "," + arrayList.get(i).getDepart() + ","+ arrayList.get(i).getProfessorId() +","+arrayList.get(i).getClassNum());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeReservation(String path, String buffer, ArrayList<Reservation> arrayList){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(buffer);
            writer.write("\n");
            for(int i = 0; i < arrayList.size(); i++){
                String[] AM = arrayList.get(i).getAM();
                String[] PM = arrayList.get(i).getPM();
                String possible = "";
                for(int j = 0; j < AM.length; j++){
                    possible += AM[j] + "," + PM[j] + ",";
                }
                possible = possible.substring(0,possible.length()-1);
                writer.write(arrayList.get(i).getRoomNum()+","+ possible);
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private static class Display{

        static Scanner input = new Scanner(System.in);

        private static String path = System.getProperty("user.dir");
        private static File record = new File(path+"/res/reservationRecord.csv");

        public static int running() throws Exception{
            while(true){
                show();
                int flag = input.nextInt();
                switch (flag){
                    case 1:
                        System.out.println("[1. 전체 예약 현황 조회]");
                        Collections.sort(reservations);
                        int length = reservations.size();
                        for(int i = 0; i < length; i++){
                            Reservation item = reservations.get(i);
                            System.out.println(item.getRoomNum());
                            String[] morning = item.getAM();
                            String[] evening = item.getPM();
                            String line1 = "오전 : ";
                            String line2 = "오후 : ";
                            for(int j = 0; j < 7; j ++){
                                if(morning[j].equals("possible")) {
                                    line1 += ("6/" + String.valueOf(j+1) + ", ");
                                }
                                if(evening[j].equals("possible")) {
                                    line2 += ("6/" + String.valueOf(j+1) + ", ");
                                }
                            }
                            line1 = line1.substring(0,line1.length()-2);
                            System.out.println(line1);

                            line2 = line2.substring(0,line2.length()-2);
                            System.out.println(line2);
                        }
                        break;
                    case 2:
                        System.out.println("[2. 호실 예약 현황 조회]");
                        System.out.print(">>> ");
                        int room = input.nextInt();
                        String line = "가능한 시간 : ";
                        for(int i = 0; i < reservations.size() ; i ++){
                            Reservation item = reservations.get(i);
                            if(room == item.getRoomNum()){
                                String[] morning = item.getAM();
                                String[] evening = item.getPM();
                                for(int j = 0; j < morning.length; j ++){
                                    if(morning[j].equals("possible")) {
                                        line += ("6/" + String.valueOf(j+1) + "오전, ");
                                    }
                                    if(evening[j].equals("possible")) {
                                        line += ("6/" + String.valueOf(j+1) + "오후, ");
                                    }
                                }
                                line = line.substring(0,line.length()-2);
                                System.out.println(line);
                                break;
                            }
                        }
                        if(line.equals("가능한 시간 : ")){
                            System.out.println("존재하지 않는 강의실입니다.");
                        }
                        break;
                    case 3:
                        System.out.println("[3. 예약하기 및 예약 취소하기]");
                        System.out.println("1. 예약하기");
                        System.out.println("2. 예약취소");
                        System.out.print(">>> ");
                        int change = 0;
                        int want = input.nextInt();
                        if(want == 1){
                            System.out.println("이름, 아이디, 호실번호, 시간 사유 입력");
                            System.out.print(">>> ");
                            input.nextLine();
                            String data = input.nextLine();
                            String reservationRecord = null;
                            String[] info = data.split(" ");
                            for(int i = 0; i < students.size(); i ++){
                                Student item = students.get(i);
                                if(item.getName().equals(info[0])){
                                    if(item.getStudentId().equals(info[1])) {
                                        String number = info[2];
                                        String time = info[3];
                                        String AMPM = time.substring(3);
                                        number = number.substring(0,number.length()-1);
                                        time = time.substring(2,3);
                                        for(int j = 0; j < reservations.size(); j++){
                                            Reservation roomInfo = reservations.get(j);
                                            if(number.equals(String.valueOf(roomInfo.getRoomNum()))){
                                                if(AMPM.equals("오전")){
                                                    String[] morning = roomInfo.getAM();
                                                    if(morning[Integer.parseInt(time) - 1].equals("possible")){
                                                        roomInfo.setAM(Integer.parseInt(time)-1,"impossible");
                                                        reservationRecord = item.getName() + "," + item.getStudentId() + "," + roomInfo.getRoomNum() + ",6/" + time + "오전" + "," + info[4];
                                                        change = 1;
                                                        break;
                                                    }
                                                }
                                                else if(AMPM.equals("오후")){
                                                    String[] evening = roomInfo.getPM();
                                                    if(evening[Integer.parseInt(time) - 1].equals("possible")){
                                                        roomInfo.setPM(Integer.parseInt(time)-1,"impossible");
                                                        reservationRecord = item.getName() + "," + item.getStudentId() + "," + roomInfo.getRoomNum() + ",6/" + time + "오후" + "," + info[4];
                                                        change = 1;
                                                        break;
                                                    }

                                                }
                                            }
                                        }
                                        if(change == 1){
                                            System.out.println(info[2] + " " + info[3] + "에 " + "예약되었습니다.");
                                            // write reservationRecord in reservationRecord.csv
                                            try {
                                                BufferedWriter writer = new BufferedWriter(new FileWriter(record,true));
                                                writer.write(reservationRecord);
                                                writer.write("\n");
                                                writer.close();
                                            } catch (IOException e){
                                                e.printStackTrace();
                                            }

                                        }
                                        else {
                                            throw new Exception("예약 실패!");
                                        }

                                    }
                                    break;
                                }

                            }

                        }
                        else if(want == 2){
                            change = 1;
                            System.out.println("이름, 아이디, 호실번호, 시간 입력");
                            System.out.print(">>> ");
                            input.nextLine();
                            String[] data = input.nextLine().split(" ");
                            String info = "";
                            for(int i = 0; i < data.length ; i++){
                                if(i == 2){
                                    info += (data[i].substring(0,data[i].length()-1) + ",");
                                }
                                else {
                                    info += (data[i] + ",");
                                }
                            }
                            info = info.substring(0,info.length()-1);
                            ArrayList<String> reservationRecords = new ArrayList<String>();
                            // read file
                            try{
                                BufferedReader reader = new BufferedReader(new FileReader(record));
                                String row;
                                while((row = reader.readLine()) != null){
                                    reservationRecords.add(row);
                                }
                            } catch(IOException e){
                                e.printStackTrace();
                            }

                            int idx = -1;
                            // ch
                            for(int i = 0; i < reservationRecords.size() ; i ++){
                                String reservationRecord = reservationRecords.get(i);
                                if(info.equals(reservationRecord.substring(0,info.length()))){
                                    idx = i;
                                    break;
                                }
                            }

                            if(idx != -1){
                                String[] reservationData = reservationRecords.get(idx).split(",");
                                int roomNumber = Integer.parseInt(reservationData[2]);
                                String time = reservationData[3];
                                for(int i = 0; i < reservations.size(); i++){
                                    if(roomNumber == reservations.get(i).getRoomNum()){
                                        int index = Integer.parseInt(time.substring(2,3)) - 1;
                                        if(time.substring(3).equals("오전")){
                                            reservations.get(i).setAM(index,"possible");
                                        }
                                        else{
                                            reservations.get(i).setPM(index,"possible");
                                        }
                                    }
                                }
                                reservationRecords.remove(idx);
                            }

                            try{
                                BufferedWriter writer = new BufferedWriter(new FileWriter(record));
                                for(int i = 0; i < reservationRecords.size(); i++){
                                    writer.write(reservationRecords.get(i));
                                    writer.write("\n");
                                }
                                writer.close();
                            } catch(IOException e){
                                e.printStackTrace();
                            }
                            if(idx != -1){
                                System.out.println("예약이 취소되었습니다.");
                            }

                        }
                        else{
                            System.out.println("1 또는 2를 입력하십시오.");
                        }
                        if(change == 0){
                            throw new Exception("예약 실패!");
                        }
                        break;
                    case 4:
                        System.out.println("[4. 나의 예약 조회]");
                        System.out.println("이름과 아이디(학번 또는 교직원 번호) 입력");
                        System.out.print(">>> ");
                        input.nextLine();
                        String[] info = input.nextLine().split(" ");
                        ArrayList<String> reservations = new ArrayList<>();
                        BufferedReader reader = new BufferedReader(new FileReader(record));
                        String reservation;

                        while((reservation = reader.readLine()) != null){
                            reservations.add(reservation);
                        }

                        int idx = -1;
                        String[] reservationInfo = null;
                        for(int i = 0; i < reservations.size(); i++){
                            reservationInfo = reservations.get(i).split(",");
                            if(reservationInfo[0].equals(info[0]) && reservationInfo[1].equals(info[1])) {
                                idx = i;
                                break;
                            }
                        }

                        if(idx != -1){
                            reservationInfo = reservations.get(idx).split(",");
                            System.out.println("이름: " + reservationInfo[0] + ", 아이디: " + reservationInfo[1] + ", 호실번호: "+ reservationInfo[2] + ", 시간: " + reservationInfo[3] +", 예약사유: "+ reservationInfo[4]);
                        }
                        else{
                            System.out.println("없는 예약입니다. 이름과 아이디를 다시 확인해주세요.");
                        }
                        break;
                    case 5:
                        System.out.println("[5. 학생 인적사항 변경]");
                        System.out.println("1. 인적사항 조회");
                        System.out.println("2. 이름 변경");
                        System.out.println("3. 학생 삭제");
                        System.out.print(">>> ");
                        int order = input.nextInt();
                        if(order == 1){
                            for(int i = 0; i < students.size(); i++){
                                System.out.printf("name: %s, department: %s, studentId: %s, phoneNum: %s\n",students.get(i).getName(),students.get(i).getDepart(),students.get(i).getStudentId(),students.get(i).getPhoneNum());
                            }
                        }
                        else if(order == 2){
                            System.out.println("2. 이름 변경");
                            System.out.print(">>> ");
                            input.nextLine();
                            String name1 = input.nextLine();
                            int id = -1;
                            for(int i = 0; i < students.size(); i++){
                                if(name1.equals(students.get(i).getName())){
                                    id = i;
                                }
                            }
                            if(id != -1) {
                                System.out.println("어떤 이름으로 변경하시겠습니까?");
                                System.out.print(">>> ");
                                String name2 = input.nextLine();
                                students.get(id).setName(name2);
                                System.out.println("이름 변경이 완료되었습니다.");
                            }
                            else{
                                System.out.println("없는 이름입니다. 다시 확인해주세요.");
                            }
                        }
                        else if(order == 3){
                            System.out.println("3. 학생 삭제");
                            System.out.print(">>> ");
                            input.nextLine();
                            String name1 = input.nextLine();
                            int id = -1;
                            for(int i = 0; i < students.size(); i++){
                                if(name1.equals(students.get(i).getName())){
                                    id = i;
                                }
                            }
                            if(id != -1) {
                                students.remove(id);
                                System.out.println("삭제되었습니다.");
                            }
                            else{
                                System.out.println("없는 이름입니다. 다시 확인해주세요.");
                            }
                        }
                        else {
                            System.out.println("잘못된 입력을 입력하였습니다.");
                        }

                        break;
                    case 6:
                        System.out.println("[6. 교수 인적사항 조회]");
                        for(int i = 0; i < professors.size(); i++){
                            System.out.printf("name: %s, department: %s, professorId: %s, classNum: %d\n",professors.get(i).getName(),professors.get(i).getDepart(),professors.get(i).getProfessorId(),professors.get(i).getClassNum());
                        }
                        break;
                    case 7:
                        System.out.println("[7. 종료]");
                        System.out.println("종료합니다.");
                        return 0;
                    default:

                }

            }
        }

        private static void show(){
            System.out.println("==== 강의실 대여 및 인적사항 조회 ====");
            System.out.println("1. 전체 예약 현황 조회");
            System.out.println("2. 호실 예약 현황 조회");
            System.out.println("3. 예약하기 및 예약 취소하기");
            System.out.println("4. 나의 예약 조회");
            System.out.println("5. 학생 인적사항 변경");
            System.out.println("6. 교수 인적사항 조회");
            System.out.println("7. 종료");
            System.out.print(">>> ");
        }
    }
}
