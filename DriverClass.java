// unit4.hw
//group members: Vianna Huynh

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class DriverClass
{
    private static College valenceCollege;
    private static Scanner firstScan = new Scanner(System.in);

    private static String mainMenu() 
    {
        String option = "0";
        // return users choice of the menu
        System.out.println("Choose from the following options:");
        System.out.println("\t1- Add a new student");
        System.out.println("\t2- Add/Delete a course");
        System.out.println("\t3- Search for a student");
        System.out.println("\t4- Print fee invoice");
        System.out.println("\t5- Print fee invoice sorted by crn");
        System.out.println("\t0- Exit program");

        option = firstScan.nextLine();
        return option;
    }

    private static void subMenu(int studentId)
    {
        System.out.println("Here are the courses [" + valenceCollege.getStudentById(studentId).getStudentName() + "] is taking:");
        valenceCollege.printStudentCourses(studentId);
        System.out.println("Choose from:");
        System.out.println("A- Add a new course for [" + valenceCollege.getStudentById(studentId).getStudentName() + "]");
        System.out.println("D- Delete a course from [" + valenceCollege.getStudentById(studentId).getStudentName() + "]’s schedule");
        System.out.println("C- Cancel operation");
        System.out.print("Enter your selection: ");
        String choice = firstScan.nextLine().toLowerCase();
        if (choice.equals("a")) 
        {
            System.out.print("Enter course number to add: ");
            int crn = firstScan.nextInt();
            firstScan.nextLine(); 
            valenceCollege.addCourse(studentId, crn);
        } 
        else if (choice.equals("d")) 
        {
            System.out.print("Enter course number to delete: ");
            String code = firstScan.nextLine();
            elements = code.split("/");
            int crn = Integer.parseInt(elements[0]);
            valenceCollege.deleteCourse(studentId, crn);
        } 
        else if (!choice.equals("c")) 
        {
            System.out.println("Invalid option.");
        }
    }

    private static void addNewStudent() {
        System.out.print("Enter the student’s id: ");
        int id = firstScan.nextInt();
        firstScan.nextLine(); 

        if (valenceCollege.searchById(id)) 
        {
            System.out.println("Sorry, " + id + " is already assigned to another student");
            return;
        }

        System.out.print("Enter student’s name: ");
        String name = firstScan.nextLine();

        System.out.print("Enter how many courses " + name + " is taking? ");
        int numCourses = firstScan.nextInt();
        ArrayList<Integer> courses = new ArrayList<>();

        System.out.println("Enter the " + numCourses + " course numbers");
        for (int i = 0; i < numCourses; i++) {
            int crn = firstScan.nextInt();
            courses.add(crn);
        }

        System.out.print("Enter " + name + "’s current gpa: ");
        double gpa = firstScan.nextDouble();

        Student student = new Student(name, id, gpa, courses);
        valenceCollege.enrollStudent(student);
        System.out.println("Student added successfully!");
    }

    private static void searchForStudent() 
    {
        System.out.print("Enter the student’s id: ");
        int id = firstScan.nextInt();
        if (valenceCollege.searchById(id)) {
            System.out.println("Student found with ID: " + id);
        } else {
            System.out.println("No Student found!");
        }
    }

    private static void printFeeInvoice() 
    {
        System.out.print("Enter the student’s id: ");
        int studentId = firstScan.nextInt();
        valenceCollege.printInvoice(studentId);
    }

    private static void printSortedInvoice() 
    {
        System.out.print("Enter the student’s id: ");
        int studentId = firstScan.nextInt();
        valenceCollege.printSortedInvoice(studentId);
    }
    // MAIN
    public static void main(String[] args)
    {
        valenceCollege = new College();
        String option = mainMenu();
        
        while (option.compareTo("0")!= 0)
        {
            switch (option)
            {
                case "0":
                    System.out.println("Goodbye!");
                    break;
                case "1":
                    // add a new student
                    addNewStudent();
                    break;
                case "2":
                    // add/delete a course
                    System.out.print("Enter the student's id: ");
                    int id = firstScan.nextInt();
                    firstScan.nextLine(); 
                    subMenu(id);
                    break;
                case "3":
                    //search for a student
                    searchForStudent();
                    break;
                case "4":
                    // print fee invoice
                    printFeeInvoice();
                    break;
                case "5":
                    // print fee invoice sorted by crn
                    printSortedInvoice();
                    break;
            }// end of switch
            option = mainMenu();
        }// end of while
    
    }// end main
}

//----------------------------------------------
class Student
{
    private String studentName;
    private double gpa;
    private int studentId;
    private ArrayList <Integer> listOfCrns;
    private double totalPayment;
    //constructor
    public Student(String studentName, int studentId, double gpa, ArrayList<Integer> listOfCrns)
    {
        this.studentName = studentName;
        this.studentId = studentId;
        this.gpa = gpa;
        this.listOfCrns = listOfCrns;
    }

    // to return the total payment amount
    private double calculateTotalPayment()
    {
        double totalCredits = listOfCrns.size();
        totalPayment = totalCredits * 120.25 + 35.00;

        // calculate if the student has a high GPA and higher than 700
        if (gpa >= 3.5 && totalPayment > 700)
        {
            totalPayment *= 0.75;
        }

        return totalPayment;
    }

    public double getTotalPayment() 
    {
        return totalPayment;
    }

    public String getCoursePrefix(int crn)
    {
        if (crn == 4587 || crn == 4580) 
        {
            return "MAT";
        }
        else if (crn == 4599 || crn == 9696 || crn == 2599)
        {
            return "COP";
        }
        else if(crn == 8997)
        {
            return "GOL";
        }
        else if (crn == 1997)
        {
            return "CAP";
        }
        else if(crn == 3696)
        {
            return "KOL";
        }
        else 
        {
            return null;
        }
    }

    public int getCourseHours(int crn)
    {
        if (crn == 4587)
        {
            return 4;
        }
        else if (crn == 4599 || crn == 9696 || crn == 2599)
        {
            return 3;
        }
        else if(crn == 3696)
        {
            return 2;
        }
        else if(crn == 8997 || crn == 4580 || crn == 1997)
        {
            return 1;
        }
        else 
            return 0;
    }

    //print the invoice like example
    public void printFeeInvoice()
    {
        System.out.println("VALENCE COLLEGE");
        System.out.println("ORLANDO FL 10101");
        System.out.println("---------------------");
        System.out.println("Fee Invoice Prepared for Student:");
        System.out.println(studentId + "-" + studentName);
        System.out.println("1 Credit Hour = $120.25");
        System.out.println("CRN CR_PREFIX CR_HOURS");
        for (Integer crn : listOfCrns) {
            System.out.println(crn + " " + getCoursePrefix(crn) + " " + getCourseHours(crn) + " $" + getCourseHours(crn) * 120.25);
        }
        System.out.println("Health & id fees $ 35.00");
        System.out.println("--------------------------------------");
        System.out.println("Total Payments $ " + calculateTotalPayment());
    }

    // setters and getters
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }
    
    public void setGpa(double gpa) 
    {
        this.gpa = gpa;
    }

    public void setStudentId(int studentId) 
    {
        this.studentId = studentId;
    }

    public void setListOfCrns(ArrayList<Integer> listOfCrns) 
    {
        this.listOfCrns = listOfCrns;
    }

    // Getters
    public String getStudentName() 
    {
        return studentName;
    }

    public double getGpa() 
    {
        return gpa;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public ArrayList<Integer> getListOfCrns() 
    {
        return listOfCrns;
    }


}// end of student class

//-----------------------------------------------
class College
{
    private ArrayList<Student> list = new ArrayList<>();
    // adds student to list
    public void enrollStudent(Student student)
    {
        list.add(student);
    }

    //returns true id student ID is found to be the id of a student
    public boolean searchById(int studentId)
    {
        for (Student student: list)
        {
            if(student.getStudentId() == studentId)
            {
                return true;
            }
        }

        return false;
    }

    // returns the student when searching by ID
    public Student getStudentById(int studentId) 
    {
        for (Student student : list) 
        {
            if (student.searchById() == true) 
            {
                return student;
            }

            else 
            {
                System.out.println("No Student found!");
                break;
            }
        }
        
        return null;
    }
    //returns true if crn can be added to listofCrns, the 
    //list of courses of the student whose id # is studentId
    public boolean addCourse(int studentId, int crn)
    {
        for (Student student : list)
        {
            if (student.getStudentId() == studentId)
            {
                student.getListOfCrns().add(crn);
                return true;
            }
        }
        return false;
    }

    //returns true if crn can be deleted from listofCrns
    public boolean deleteCourse(int studentId, int crn)
    {
        for (Student student : list)
        {
            if (student.getStudentId() == studentId)
            {
                if (student.getListOfCrns().contains(crn)) {
                    student.getListOfCrns().remove(Integer.valueOf(crn));
                    return true;
                }
            }
        }

        return false;
    }

    // prints the fee invoice for the student whose idetnitification 
    // is studentId
    public void printInvoice (int studentId)
    {
        for (Student student : list) {
            if (student.getStudentId() == studentId) {
                student.printFeeInvoice();
                return;
            }
        }
        System.out.println("No student found!");
    }

    // prints the fee invoice, sorted by course #, do not sort the
    //private field listofCrns
    public void printSortedInvoice(int studentId)
    {
        for (Student student : list) {
            if (student.getStudentId() == studentId) {
                System.out.println("VALENCE COLLEGE");
                System.out.println("ORLANDO FL 10101");
                System.out.println("---------------------");
                System.out.println("Fee Invoice Prepared for Student:");
                System.out.println(student.getStudentId() + "-" + student.getStudentName());
                System.out.println("1 Credit Hour = $120.25");
                System.out.println("CRN CR_PREFIX CR_HOURS");
                ArrayList<Integer> sortedCrns = new ArrayList<>(student.getListOfCrns());
                sortedCrns.sort(Integer::compareTo);
                for (Integer crn : sortedCrns) {
                    System.out.println(crn + " " + student.getCoursePrefix(crn) + " " + student.getCourseHours(crn) + " $" + student.getCourseHours(crn) * 120.25);
                }
                System.out.println("Health & id fees $ 35.00");
                System.out.println("--------------------------------------");
                System.out.println("Total Payments $ " + student.getTotalPayment());
                return;
            }
        }
        System.out.println("No student found with ID: " + studentId);
    }

}// end of College class