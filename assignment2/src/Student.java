public class Student implements Comparable{
    private String name = null;
    private String depart = null;
    private String studentId = null;
    private String phoneNum = null;

    Student(String[] line){
        this.name = line[0];
        this.depart = line[1];
        this.studentId = line[2];
        this.phoneNum = line[3];
    }

    public String getName(){
        return this.name;
    }

    public String getStudentId(){
        return this.studentId;
    }

    public String getDepart(){
        return this.depart;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        String name1 = this.getName();
        String name2 = ((Student)o).getName();
        return name1.compareToIgnoreCase(name2);
    }
}
