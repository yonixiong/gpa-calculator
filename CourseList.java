// Name:  Yoni Xiong
import java.io.*;
public class CourseList extends Course {

    private static final int MAXSIZE = 100;             // Maximum size of course list
    Course[] courseList = new Course[MAXSIZE];      			// Array containing MAXSIZE course objs
    private int count;                                  // Num courses in list
    private String userName;                            // name of list owner
	double startingGPA; 
	double prevHours; 

    public CourseList() {
        userName = "My Courses";                   // Default list name
        count = 0;                                      // Num courses at init
		startingGPA = -1;
		prevHours = 0; 

        for (int i = 0; i < MAXSIZE; i++) {             // Loop through MAXSIZE
            courseList[i] = new Course();              
        }
    }

    public CourseList(String title, double startingGPA, double prevHours) {
        userName = title + "'s Courses";                        
        count = 0;
		this.startingGPA = startingGPA; 
		this.prevHours = prevHours; 

        for (int i = 0; i < MAXSIZE; i++) {             
            courseList[i] = new Course();              
        }
    }

    public void addCourse(String initCourseName, double initCourseGrades, double initCourseHours)
                throws ArrayIndexOutOfBoundsException {

        if (!(count >= MAXSIZE)) {                          
            courseList[count].setCourseName(initCourseName);         
            courseList[count].setCourseHours(initCourseHours);                 
            courseList[count].setCourseGrade(initCourseGrades);
        }        
        else {
			
            throw new ArrayIndexOutOfBoundsException("Error -- course list is full. " +
                                                     "Maximum size is "
                                                     + MAXSIZE);
        }
        // Incr num songs
        count++;
    }

    public int removeCourse(String name) throws ArrayIndexOutOfBoundsException {
        
		int indexRemoved = -1;                       

        if (count == 0) {                            
            // Error
            throw new ArrayIndexOutOfBoundsException("Error -- course list is empty.");
        }
        else {
            for (int i = 0; i < count; i++) {                           
                if (courseList[i].getCourseName().equals(name))  {  

                    for (int j = i; j < count - 1; j++) {         
                        copyCourse(courseList, j);                      
                    }

                    courseList[count - 1].setCourseName("empty");       
                    courseList[count - 1].setCourseHours(0.0);             
                    courseList[count - 1].setCourseGrade(0.0);

                    indexRemoved = i;                               
                    count--;                                        
                }
            }
        }
        return (indexRemoved);
    }

    public void copyCourse(Course[] courseList, int position) {
        courseList[position].setCourseName(courseList[position + 1].getCourseName());
        courseList[position].setCourseHours(courseList[position + 1].getCourseHours());
        courseList[position].setCourseGrade(courseList[position + 1].getCourseGrade());
    }

    public void printCourseList(PrintStream pOut) {
        if (courseList.length == 0 || count == 0) {               
            pOut.println("Course list is empty\n");
        }
        else {
            // Print out playlist block
            pOut.print(userName.toUpperCase() + " ");
            if (count == 1) {
                pOut.println("(" + count + " course)");       
            }
            else {
                pOut.println("(" + count + " courses)");     
            }
            System.out.println();

	    if(startingGPA > 0){
		pOut.println("Previous Stats (entered by user)");
	    	pOut.println("GPA: " + this.startingGPA);
	    	pOut.println("Hours: " + this.prevHours); 
		pOut.println();
	    }

            for (int i = 0; i < count; i++) {                
                pOut.println(courseList[i].toString());     
        
            }
        }
    }

    public double calcGPA() {
		
	double finalGPA = 0;
        double sumHours = 0;

        for (int i = 0; i < count; i++) {
            finalGPA = (finalGPA) + (courseList[i].getCourseGrade() * courseList[i].getCourseHours());
            sumHours = courseList[i].getCourseHours()+ sumHours;
        }
        finalGPA = finalGPA / sumHours;

        if (this.startingGPA != -1) {
            finalGPA = ((finalGPA * sumHours) + (this.startingGPA * this.prevHours))/(sumHours + this.prevHours);
        }
		
        return(finalGPA);
    }

    public int searchCourseList(String name) {
        int found = -1;                                             
  
        for (int i = 0; i < count; i++) {                              
            if (courseList[i].getCourseName().contains(name)) {     
			
                found =i;                                            
            }
        }
        return(found);
    }

    public int updateCourse(String name, double newCourseGPA, double newCourseHours){
        int index = searchCourseList(name);

        if(index == -1){
            return -1; 
        }

        courseList[index].setCourseGrade(newCourseGPA);
        courseList[index].setCourseHours(newCourseHours);
        return 1; 
    }

    public String getCourseListTitle() {
        return(userName);
    }
    public String getUserName(){
	String[] result = userName.split("'");
	return result[0];
    }

    public int getCount() {
        return(count);
    }
	
    public double getPrevHours(){
	return(prevHours);
	}
	
    public double getStartingGPA(){
	return (startingGPA);
   }
   public double getGPA(int index){
	return courseList[index].getCourseGrade();
   }
   public double getHours(int index){
	return courseList[index].getCourseHours();
   }
   public String getName(int index){
	return courseList[index].getCourseName();
   }
}
