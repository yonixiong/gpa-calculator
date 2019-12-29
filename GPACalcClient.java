import java.util.*;
import java.io.*;


public class GPACalcClient{
    public static void main(String[] args) throws IOException {
        
        Scanner input = new Scanner(System.in); 
        CourseList courseList;
        printGPAKey();
        
	// ask if we should load from a save file 
	System.out.println("Would you like to load previously saved data? (y/n) "); 
	char ans = promptChar(input);
	if (ans =='Y'){
        	String fileName = promptFileName(input);
		courseList = parseConfig(fileName);
	}

	else { 
        	System.out.println("Please enter your name: ");
        	String name = promptString(input);

        	double numClasses = promptNumClasses(input);
        	double currGPA = promptCurrGPA(input);
		double prevHours;
        	if (currGPA != 0){
                	prevHours = promptCurrHours(input);
        	}
		else {
			prevHours = 0.0;
		}
        	System.out.println(); 

        	courseList = new CourseList(name, currGPA, prevHours); 
        
        	// add classes
        	for (int i = 0; i < numClasses; i++){
            		String courseName = promptCourse(input, i+1);
            		double gpa = promptGPA(input);
            		double hours = promptHours(input);
            		courseList.addCourse(courseName, gpa, hours);
            		System.out.println();

        	}
        	//System.out.println();
        }
		char menuChoice = 'N';
        	while (menuChoice != 'Q'){
                    
            	menuChoice = getUserInput (input); 
            
           	processUserInput(menuChoice, courseList, input); 
            
                //System.out.println(); 
        	}
}
    public static void printGPAKey(){
        System.out.println();
        System.out.println("GRADE POINT AVERAGE (GPA) KEY");
        System.out.println("A = 4.00");
        System.out.println("A- = 3.70");
        System.out.println("B+ = 3.33");
        System.out.println("B = 3.00");
        System.out.println("B- = 2.70");
        System.out.println("C+ = 2.30");
        System.out.println("C = 2.00");
        System.out.println("C- = 1.70");
        System.out.println("D+ = 1.30");
        System.out.println("D = 1.00");
        System.out.println("D- = 0.70");
        System.out.println();
    }
	public static void printMenu() {

        // print menu
	System.out.println();
        System.out.println("MENU");
        System.out.println("C - Calculate final gpa");
        System.out.println("U - Update a class");
	System.out.println("A - Add a class");
        System.out.println("R - Remove a class");
        System.out.println("P - Print schedule");
        System.out.println("S - Save current data to file");
        System.out.println("Q - Quit");
        System.out.println();
    }
    public static char getUserInput(Scanner input) {

        char menuChoice; // menu option chosen my user

        // print menu of options
        printMenu();

        // prompt user for menu choice
        menuChoice = promptMenuChoice(input);

        // check choice and re-prompt if not on menu
        while (!checkChoice(menuChoice)) {
            System.out.println();
            System.out.println("Please choose a valid option");
            System.out.println();
            menuChoice = promptMenuChoice(input);
        }

        return menuChoice;
    }
    public static CourseList parseConfig(String fileName){
	try{
		File file = new File (fileName + ".txt"); 
		Scanner fileScan = new Scanner(file); 
		fileScan.useDelimiter(";");
		String userName = fileScan.next();
		double prevGPA = fileScan.nextDouble();
		double prevHours = fileScan.nextDouble();
		int courseNum = fileScan.nextInt();
		String courseName; 
		double courseGrade; 
		double courseHours;
		CourseList oldCourseList = new CourseList(userName, prevGPA,
					 prevHours); 
		for (int i = 0; i < courseNum; i++){
			courseName = fileScan.next();
			courseGrade = fileScan.nextDouble();
			courseHours = fileScan.nextDouble();
			oldCourseList.addCourse(courseName, courseGrade, courseHours);
		}
		return oldCourseList; 
	}
	catch (FileNotFoundException e){ return new CourseList();}
}
    public static char promptMenuChoice(Scanner input) {

        String userInput; // user's input

        // prompt user to choose option
        System.out.print("Choose an option: ");
        userInput = input.nextLine().toUpperCase();

        // re-prompt user if they enter nothing
        while (userInput.isEmpty()) {
            System.out.println();
            System.out.println("Please choose a valid option");
            System.out.println();
            System.out.println("Choose an option: ");
            userInput = input.nextLine().toUpperCase();
        }

        // the first character of user input (not-empty) is the menu option chosen
        return userInput.charAt(0);
    }

    public static void processUserInput(char choice, CourseList courseList, Scanner input) {
        String courseToEdit; 
        // execute appropriate menu action
        if (choice == 'C') {
	   // calculate final gpa 
	   if(courseList.getCount() > 0){
          	 double finalGPA = courseList.calcGPA();
           	 System.out.printf("Final GPA is %4.2f%n",finalGPA);
           }
	   else {
		System.out.println("Unable to compute GPA: please check that your schedule contains courses");
	   }
        } else if (choice == 'U') {
	    // update course
            System.out.println("Please enter the course ID: ");
            courseToEdit = promptString(input);
            double newCourseGPA = promptGPA(input); 
            double newCourseHours = promptHours(input);
            if (courseList.searchCourseList(courseToEdit)!=1){
                courseList.updateCourse(courseToEdit, newCourseGPA, newCourseHours);
            }
            else {
                System.out.println("Please enter a valid course");
            }
        } else if (choice == 'S') {
            // save config to file
	    String fileName = promptFileName(input);
	    saveConfig(courseList, fileName);
        } else if (choice == 'R') {
	    // remove course
            System.out.println("Please enter the course ID: ");
            courseToEdit = promptString(input);
            if (courseList.searchCourseList(courseToEdit)!=1){
                courseList.removeCourse(courseToEdit);
            } else{
                System.out.println("Please enter valid course");
            }
        } else if (choice == 'A') {
	    System.out.println("Please enter course ID: ");
	    courseToEdit = promptString(input); 
            double newCourseGPA = promptGPA(input); 
            double newCourseHours = promptHours(input);
	    courseList.addCourse(courseToEdit, newCourseGPA, newCourseHours);	   
	} else if (choice == 'P') {
            courseList.printCourseList(System.out);
        }
     }
    public static void saveConfig(CourseList courseList, String fileName){
	try {
            FileOutputStream fostream = new FileOutputStream(fileName + ".txt");            
            BufferedOutputStream bostream = new BufferedOutputStream(fostream);
            PrintStream outP = new PrintStream(bostream);
           
		 // use outP to file
	    outP.printf("%s;%f;%f;%d", courseList.getUserName(), courseList.getStartingGPA(), 
			courseList.getPrevHours(),courseList.getCount());
	    int count = courseList.getCount();
	    for (int i = 0; i < count; i++ ){
	    	outP.printf(";%s;%f;%f", courseList.getName(i), courseList.getGPA(i),
				courseList.getHours(i));
            }
	    outP.close();
	}
	catch(FileNotFoundException e){}
}
    public static boolean checkChoice(char choice){
        if (choice != 'C'&& choice != 'U'&& choice != 'R'&& 
	    choice != 'A' && choice != 'P'&& choice != 'S'&&
	    choice != 'Q'){
            return false; 
        }
        else{
            return true; 
        }
    }

    public static String promptFileName(Scanner input){
	System.out.println("Please enter desired save-file name: ");
        return promptString(input); 
    }

    public static String promptCourse(Scanner input, int num){
        System.out.println("Please enter course#"+ num + " ID: ");
	return promptString(input);
    }

    public static String promptReplaceCourse(Scanner input, int num){
        System.out.println("Please enter course"+ num + " ID of course to replace: ");
	return promptString(input);
    }
	public static int promptNumClasses(Scanner input){
        System.out.println("Please enter number of currently enrolled courses (including labs): ");
        return promptInt(input);
    }

    public static double promptCurrGPA(Scanner input){
        System.out.println("Please enter current GPA (enter 0 if n/a): ");
        return promptDouble(input);
    }

    public static double promptGPA(Scanner input){
        System.out.println("Please enter/estimate GPA for this course: ");
        return promptDouble(input);
    }

    public static double promptCurrHours(Scanner input){
        System.out.println("Please enter current number of hours completed: ");
        return promptDouble(input);
    }

    public static double promptHours(Scanner input){
        System.out.println("Please enter number of hours for this course: ");
        return promptDouble(input);
    }
    
	public static int promptInt(Scanner input) {

        String userInput; // user's input

        // read user input
        userInput = input.nextLine().trim();

        // display error and re-prompt if input is empty or not integer
        while (userInput.isEmpty() || !checkIfInt(userInput)) {
            userInput = input.nextLine();
        }

        return Integer.parseInt(userInput);
	}
	public static boolean checkIfInt(String num) {

		// try to parse string as int
		try {
        int i = Integer.parseInt(num);
		}

		// string is not an integer if there is an exception, exit and return false
		catch (NumberFormatException nfe) {
        return false;
		}

		// otherwise string is integer
    return true;
	}
	public static double promptDouble(Scanner input) {

        String userInput; // user's input

        // read user input
        userInput = input.nextLine().trim();

        // display error and re-prompt if input is empty or not integer
        while (userInput.isEmpty() || !checkIfDouble(userInput)) {
            userInput = input.nextLine();
        }

        return Double.parseDouble(userInput);
    }
	public static boolean checkIfDouble(String num) {

        // try to parse string as int
        try {
            double i = Double.parseDouble(num);
        }

        // string is not an integer if there is an exception, exit and return false
        catch (NumberFormatException nfe) {
            return false;
        }

        // otherwise string is integer
        return true;
    }
    public static String promptString(Scanner input){
        String userInput;

        // read user input
        userInput = input.nextLine().trim();

        // display error and re-prompt if input is empty or not integer
        while (userInput.isEmpty()) {
            userInput = input.nextLine();
        }
        return userInput.toUpperCase();
    }
    public static char promptChar(Scanner input){
    	
	 String userInput; // user's input

        // prompt user to choose option
        userInput = input.nextLine().toUpperCase();

        // re-prompt user if they enter nothing
        while (userInput.isEmpty()) {
            System.out.println();
            System.out.println("Please choose a valid option");
            System.out.println();
            System.out.println("Choose an option: ");
            userInput = input.nextLine().toUpperCase();
        }

        // the first character of user input (not-empty) is the menu option chosen
        return userInput.charAt(0);
    }	
}	
