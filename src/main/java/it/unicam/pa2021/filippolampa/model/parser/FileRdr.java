package it.unicam.pa2021.filippolampa.model.parser;

import java.io.*;

public class FileRdr {

    public static String readFile(String filePath)  {
        File file = new File(filePath);
        StringBuilder programString = new StringBuilder();
        String st;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null)
                programString.append(st);
        }
        catch (Exception e){
            System.out.println("Errore durante la lettura del file");
            return "-1";
        }
        return programString.toString();
    }

}


