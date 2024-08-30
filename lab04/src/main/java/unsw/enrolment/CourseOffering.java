package unsw.enrolment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import unsw.enrolment.exceptions.InvalidEnrolmentException;

public class CourseOffering extends Course {
    private Course course;
    private String term;
    private List<Enrolment> enrolments = new ArrayList<Enrolment>();

    public CourseOffering(Course course, String term) {
        super(course.getCourseCode(), course.getTitle());
        this.course = course;
        this.term = term;
        this.course.addOffering(this);
    }

    public Course getCourse() {
        return course;
    }

    public String getCourseCode() {
        return course.getCourseCode();
    }

    public List<Course> getCoursePrereqs() {
        return course.getPrereqs();
    }

    public String getTerm() {
        return term;
    }

    public Enrolment addEnrolment(Student student) throws InvalidEnrolmentException {
        if (checkValidEnrolment(student)) {
            Enrolment enrolment = new Enrolment(this, student);
            enrolments.add(enrolment);
            student.addEnrolment(enrolment);
            return enrolment;
        } else {
            throw new InvalidEnrolmentException("student has not satisfied the prerequisites");
        }
    }
    private boolean checkValidEnrolment(Student student) {
        List<Course> prereqs = getCoursePrereqs();
    
        return prereqs.stream().allMatch(prereq ->
            student.getEnrolments().stream().anyMatch(enrolment ->
                enrolment.getCourse().equals(prereq) &&
                enrolment.getGrade() != null &&
                enrolment.getGrade().getMark() >= 50 &&
                !("FL".equals(enrolment.getGrade().getGrade()) || "UF".equals(enrolment.getGrade().getGrade()))
            )
        );
    }
    
    public List<Student> studentsEnrolledInCourse() {
        List<Student> students = enrolments.stream()
        .map(Enrolment::getStudent)
        .sorted(
            Comparator
            .comparing(Student::getProgram)
            .thenComparing(Student::getStreamsLen)
            .thenComparing(Student::getName)
            .thenComparing(Student::getZid)
        )
        .collect(Collectors.toList());
        students.stream().forEach(s -> System.out.println(s.getName()));
        return students;
    }
}
