/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findsameitems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sallai
 */
public class FindSameItems {

    /**
     * @param args the command line arguments
     */
    
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
    public static void main(String[] args){
        // TODO code application logic here
        HashSet<Student> DiakokGIList = new HashSet<>();
        HashSet<Student> DiakokGMList = new HashSet<>();               
        
        ReadFile(DiakokGIList, "Diakok_GI.csv");
        ReadFile(DiakokGMList, "Diakok_GM.csv");
        
        //WriteOut(DiakokGIList);
        //WriteOut(DiakokGMList);
        
        HashSet<Student> IdenticalStudents = IdenticalItems(DiakokGIList, DiakokGMList);
        
        WriteFile(IdenticalStudents);
        
        WriteOut(IdenticalStudents);
        
        
       
    }

    private static void ReadFile(HashSet<Student> Set, String fileName){
       
        try {
            File newFile = new File(fileName);
            Scanner scn = new Scanner(newFile, "iso-8859-2");  
            while(scn.hasNextLine()){
                String line = scn.nextLine();
                String[] array = line.split(";");
            
                Student newStudent = new Student();
            
                newStudent.studentID = array[0];
                newStudent.name = array[1];
                newStudent.dateOfBirth = df.parse(array[2]);
                newStudent.countyCode = Integer.parseInt(array[3]);
            
            Set.add(newStudent);
            }
                                  
                    
        } catch (FileNotFoundException ex) {
            System.out.println("A fáj nem található!");
        } catch(ParseException ex){
            System.out.println("Helytelen dátum formátum!");
        }
    }

    private static void WriteOut(HashSet<Student> DiakokGI) {
        for (Student diak : DiakokGI) {
            System.out.println(diak);
        }
        
    }

    private static HashSet IdenticalItems(HashSet<Student> DiakokGI, HashSet<Student> DiakokGM) {            
        HashSet<Student> IdenticalStudents = new HashSet<>();        
        for (Student diakGI : DiakokGI) {
            for (Student diakGM : DiakokGM) {
                if (diakGI.equals(diakGM)) {
                    IdenticalStudents.add(diakGI);                        
                }
            }
        }                                         
      
        return IdenticalStudents;
    }

    private static void WriteFile(HashSet<Student> IdenticalStudents) {
        try {
            PrintStream ps = new PrintStream("IdenticalStudents.csv");
            for (Student Student : IdenticalStudents) {
                ps.println(Student.studentID + ";" + Student.name + ";" + df.format(Student.dateOfBirth) + ";" + Student.countyCode);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }
        
}

class Student{
    String studentID;
    String name;
    Date dateOfBirth;
    int countyCode;

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.studentID);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 53 * hash + this.countyCode;
        hash = 53 * hash + Objects.hashCode(this.df);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (this.countyCode != other.countyCode) {
            return false;
        }
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        if (!Objects.equals(this.df, other.df)) {
            return false;
        }
        return true;
    }
        
    @Override
    public String toString() {
        return "Student{" + "Student ID =" + studentID + ", name =" + name + ", date of birth =" + df.format(dateOfBirth) + ", county code=" + countyCode + '}';
    }
}

    
