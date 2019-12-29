// Name:  Yoni Xiong
// Description: Course Class

public class Course {

    private String courseName;                  // Name of song
    private double courseHours;                 // Name of song artist
    private double courseGrade;                 // Length of song (seconds)

    public Course() {
        courseName = "empty";                 // Default name of course
        courseHours = 0.0;                    // Default number of hours
        courseGrade = 0.0;                    // Default grade (%)
    }

    public Course(String initCourseName, double initCourseGrades, double initCourseHours) {
        courseName = initCourseName;            // Name of course
        courseHours = initCourseHours;          // Number of hours
        courseGrade = initCourseGrades;         // grade (%)
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseHours(double courseHours) {
        this.courseHours = courseHours;
    }

    public void setCourseGrade(double courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseName() {
        return(courseName);
    }

    public double getCourseHours() {
        return(courseHours);
    }

    public double getCourseGrade() {
        return(courseGrade);
    }


    @Override
    public String toString() {
        String courseNameString;          
        String courseHoursString;          
        String courseGradeString;        
        String courseString;        

        courseNameString = "Course Name: " + courseName;
	courseGradeString = "Grade: " + courseGrade; 
        courseHoursString = "Hours: " + courseHours;

        courseString = courseNameString + "\n" + courseGradeString + "\n" + courseHoursString + "\n";

        return(courseString);
    }
}
