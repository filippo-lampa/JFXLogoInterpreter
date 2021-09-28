package it.unicam.pa2021.filippolampa.model.logo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExecutionResultPrinter {

    public ExecutionResultPrinter(){
        createNewFile();
    }

    private void createNewFile(){
        try {
            File myObj = new File("programResult.txt");
            if (!myObj.exists()) {
                myObj.createNewFile();
                System.out.println("File created: " + myObj.getName());
            }else{
                myObj.delete();
                myObj.createNewFile();
            }
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void printResult(String instructionString){
        try {
            FileWriter myWriter = new FileWriter("programResult.txt",true);
            myWriter.write(instructionString+"\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
