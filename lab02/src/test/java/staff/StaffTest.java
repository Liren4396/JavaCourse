package staff;

import java.time.LocalDate;

public class StaffTest {
    public static void main(String[] args) {
        LocalDate date1 = LocalDate.of(1990, 01, 01);
        StaffMember java = new StaffMember("java", date1, LocalDate.now());
        Lecturer bigJava1 = new Lecturer(java, "unsw", "comp");
        java.toString();
        StaffMember java2 = new StaffMember("java", LocalDate.of(2000, 01, 02), LocalDate.now());
        Lecturer bigJava2 = new Lecturer(java2, "unsw", "comp");
        if (java.equals(java2)) {
            System.out.println("same staff");
        } else {
            System.out.println("diff staff");
        }
        if (bigJava1.equals(bigJava2)) {
            System.out.println("same lecturer");
        } else {
            System.out.println("diff lecturer");
        }
    }
}
