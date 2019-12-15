/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package o1;


import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.sourceforge.tess4j.*;

/**
 *
 * @author AbuIb
 */
public class O1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String result = "";

        File PdfFile = new File("C:\\Users\\AbuIb\\Desktop\\challenge.pdf");
        ITesseract instance = new Tesseract(); 

        String dataPath = "C:\\Users\\AbuIb\\Desktop\\Tess4J\\tessdata";
        instance.setDatapath(new File(dataPath).getPath());

        try {
            result = instance.doOCR(PdfFile);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        String patternDates = "(\\d)?(\\d)\\/\\d{2}\\/\\d{4}";
        String patternSerialNumbers = "\\d{3}-\\d{3}-\\d{3}-\\d{3}";
        String patternCities = "from \\w{3,10}";
        String patternAges = "(\\d)?(\\.)?(\\d) ([Y,y])ears old";
        String patternCatNames = "name is \\w{2,10}";
        

        Pattern pDates = Pattern.compile(patternDates);
        Matcher matcherDates = pDates.matcher(result);

        Pattern pSerialNumbers = Pattern.compile(patternSerialNumbers);
        Matcher matcherSerialNumbers = pSerialNumbers.matcher(result);

        Pattern pCities = Pattern.compile(patternCities);
        Matcher matcherCities = pCities.matcher(result);

        Pattern pAges = Pattern.compile(patternAges);
        Matcher matcherAges = pAges.matcher(result);

        Pattern pCatNames = Pattern.compile(patternCatNames);
        Matcher matcherCatNames = pCatNames.matcher(result);

        System.out.println("\n   a. Dates:");
        
        while (matcherDates.find()) {
            String sD = matcherDates.group();
            String sD1 = sD;

            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sD1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                sD1 = dateFormat.format(date1);
                System.out.println("   " + sD1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n   b. Serial Numbers:");
        
        while (matcherSerialNumbers.find()) {

            String sSN = matcherSerialNumbers.group();
            String[] aSN = sSN.split("-");
            System.out.print("   Value 1 :" + aSN[0]);
            System.out.print("   Value 2 : " + aSN[1]);
            System.out.print("   Value 3 :" + aSN[2]);
            System.out.print("   Value 4 :" + aSN[3] + "\n");

        }

        System.out.println("\n   c. Cities:");
        
        while (matcherCities.find()) {
            String sC = matcherCities.group();
            String[] aC = sC.split("from");
            for (String city : aC) {
                System.out.println("   " + city);
            }

        }

        System.out.println("\n   d. Ages:");
        
        while (matcherAges.find()) {
            String sAg = matcherAges.group();
            String[] aAg = sAg.split("([Y,y])ears old");
            for (String age : aAg) {
                System.out.println("   " + age);
            }
        }

        System.out.println("\n   e. Cat Names:");
        
        while (matcherCatNames.find()) {
            String sCN = matcherCatNames.group();
            String[] aAg = sCN.split("name is");
            for (String name : aAg) {
                System.out.println("   " + name);
            }
        }

    }
}
