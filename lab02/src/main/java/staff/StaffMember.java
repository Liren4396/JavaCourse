package staff;

import java.time.LocalDate;

/**
 * A staff member
 * @author Robert Clifton-Everest
 *
 */
public class StaffMember {
    private String _name;
    private LocalDate _hireDate;
    private LocalDate _endDate;

    /**
     * Construct staffMember
     * 
     * @param name
     * @param hirDate
     * @param enDate
     */
    StaffMember(String name, LocalDate hirDate, LocalDate enDate) {
        _name = name;
        _hireDate = hirDate;
        _endDate = LocalDate.now();
    }

    /**
     * @param hirDate
     * @param enDate
     */
    StaffMember(LocalDate hirDate, LocalDate enDate) {
        _hireDate = hirDate;
        _endDate = LocalDate.now();
    }

    public StaffMember(StaffMember s1) {
        _name = s1.get_name();
        _hireDate = s1.get_hireDate();
        _endDate = s1.get_endDate();
    }

    /**
     * get name to staffmember
     * @param _name
     */
    public String get_name() {
        return _name;
    }

    /**
     * get endDate to staffmember
     * @param _name
     */
    public LocalDate get_endDate() {
        return _endDate;
    }

    /**
     * get hireDate to staffmember
     * @param _name
     */
    public LocalDate get_hireDate() {
        return _hireDate;
    }

    /**
     * set endDate to staffmember
     * @param _name
     */
    public void set_endDate(LocalDate _endDate) {
        this._endDate = _endDate;
    }

    /**
     * set hireDate to staffmember
     * @param _name
     */
    public void set_hireDate(LocalDate _hireDate) {
        this._hireDate = _hireDate;
    }

    /**
     * set name to staffmember
     * @param _name
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * print Staff class
     * @return String
     */
    public String toString() {
        return _name + "from" + _hireDate + "to" + _endDate;
    }

    public boolean equals(StaffMember obj) {
        if (obj.hashCode() != this.hashCode()) {
            return false;
        }
        if (obj.get_name() != _name) {
            return false;
        }
        if (obj.get_endDate().compareTo(_endDate) != 0 || obj.get_hireDate().compareTo(_hireDate) != 0) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        LocalDate date1 = LocalDate.of(1990, 01, 01);
        StaffMember java = new StaffMember("java", date1, LocalDate.now());

        java.toString();
        StaffMember java2 = new StaffMember("java", LocalDate.of(2000, 01, 02), LocalDate.now());
        if (java.equals(java2)) {
            System.out.println("same");
        } else {
            System.out.println("diff");
        }
    }
}
