package staff;

import java.time.LocalDate;

public class Lecturer extends StaffMember {
    private String _school;
    private String _status;

    /**
     * construct Lecturer
     * 
     * @param name
     * @param hirDate
     * @param enDate
     * @param school
     * @param status
     */
    Lecturer(String name, LocalDate hirDate, LocalDate enDate, String school, String status) {
        super(name, hirDate, enDate);
        _school = school;
        _status = status;
    }
    Lecturer(StaffMember s1, String school, String status) {
        super(s1);
        _school = school;
        _status = status;
    }
    /**
     * get school of the lecturer
     * @return
     */
    public String get_school() {
        return _school;
    }
    /**
     * get status of the lecturer
     * @return
     */
    public String get_status() {
        return _status;
    }
    /**
     * set lectuer's school 
     * @param _school
     */
    public void set_school(String _school) {
        this._school = _school;
    }
    /**
     * set lectuer's status
     * @param _status
     */
    public void set_status(String _status) {
        this._status = _status;
    }
    /**
     * print class Lecturer
     * @return String
     */
    public String toString() {
        return get_name() + _school + _status + get_hireDate() + get_endDate();
    }

    public boolean equals(Lecturer obj) {
        if (this.hashCode() != obj.hashCode()) {
            return false;
        }
        if (_school != obj._school || _status != obj.get_status()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        LocalDate date1 = LocalDate.of(1990, 01, 01);
        StaffMember java = new StaffMember("java", date1, LocalDate.now());
        Lecturer bigJava1 = new Lecturer(java, "unsw", "comp");
        java.toString();
        StaffMember java2 = new StaffMember("java", LocalDate.of(2000, 01, 02), LocalDate.now());
        Lecturer bigJava2 = new Lecturer(java2, "unsw", "comp");
        if (bigJava1.equals(bigJava2)) {
            System.out.println("same");
        } else {
            System.out.println("diff");
        }
    }
}
