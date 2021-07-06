public class Professor implements Comparable{

    private String name = null;
    private String depart = null;
    private String professorId = null;
    private int classNum = 0;

    Professor(String[] line){
        this.name = line[0];
        this.depart = line[1];
        this.professorId = line[2];
        this.classNum = Integer.parseInt(line[3]);
    }

    public String getName() {
        return name;
    }

    public String getDepart() {
        return depart;
    }

    public String getProfessorId() {
        return professorId;
    }

    public int getClassNum() {
        return classNum;
    }

    @Override
    public int compareTo(Object o){
        Integer classNum1 = this.getClassNum();
        Integer classNum2 = ((Professor)o).getClassNum();
        return classNum1.compareTo(classNum2);
    }
}
