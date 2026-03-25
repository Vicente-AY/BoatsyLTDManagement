package utils;

import boat.Boat;
import employee.Employee;

import java.io.*;
import java.util.ArrayList;

public class Data {

    ArrayList<Boat> boats = new ArrayList<Boat>();
    ArrayList<Employee> employees = new ArrayList<Employee>();
    File employeeList = new File("employee.dat");
    File boatList = new File("boats.dat");
    File executionDay = new File("executionDay.txt");

    public ArrayList<Employee> chargeEmployees(){

        if(employeeList.exists() && employeeList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(employeeList))){
                employees = (ArrayList<Employee>) input.readObject();
            }
            catch(IOException | ClassNotFoundException e){
                System.err.println("Error charging Employees " + e.getMessage());
            }
        }
        return employees;
    }

    public void saveEmployees(ArrayList<Employee> employees){

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(employeeList))){
            output.reset();
            output.writeObject(employees);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error saving Employees " + e.getMessage());
        }
    }

    public ArrayList<Boat> chargeBoats(){

        if(boatList.exists() && boatList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(boatList))){
                boats = (ArrayList<Boat>) input.readObject();
            }
            catch(IOException | ClassNotFoundException e){
                System.err.println("Error charging Boats " + e.getMessage());
            }
        }
        return boats;
    }

    public void saveBoats(ArrayList<Boat> boats){

        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(boatList))){
            output.reset();
            output.writeObject(boats);
            output.flush();
        }
        catch(IOException e){
            System.err.println("Error saving Employees " + e.getMessage());
        }
    }

    public String chargeLastExecution(){

        try(BufferedReader br = new BufferedReader(new FileReader(executionDay))){
            String lastDayExecution = br.readLine();
            return lastDayExecution;
        }
        catch(IOException ioe){
            System.out.println("Error reading File " + ioe.getMessage());
            return "";
        }
    }

    public void saveLastExecution(String lastExecution){

        try(PrintWriter out = new PrintWriter(new FileWriter(executionDay, false))){
            out.print(lastExecution);
        }
        catch(IOException ioe){
            System.out.println("Error writing File " + ioe.getMessage());
        }
    }
}
