//package com.hascode.samples.jsoup;

import java.io.IOException;
import java.util.*;
import java.lang.*;

import org.jsoup.helper.Validate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


public class StatisticalInsights {
	public static void main(final String[] args) throws IOException {
        String playerFirstName = "empty";      // Argument 1
        String playerLastName = "empty";      // Argument 2
        String fullName = "empty" ;
        String playerName = " NA";  // set default value for Player we are looking for
        String resultPageUrl = "na";
        
        /* Get command prompt arguments for Player's Name */
        /**************************************************/
        System.out.print(" First Name : ");
        Scanner nameScanner = new Scanner(System.in);
        playerFirstName = nameScanner.nextLine();
        
        System.out.print(" Last Name : ");
        Scanner lastNameScanner = new Scanner(System.in);
        playerLastName = lastNameScanner.nextLine();
        
        
        fullName = playerFirstName + " " + playerLastName;
        System.out.println("Searching for " + fullName + " ...");
        /****************************************************/
        
        /******************************************************
         Here you will take the fullName and search it (sumbit
         a POST form), you will get a result page. Use result page
         URL to find player and their appropriate URL
         *****************************************************/
        
        resultPageUrl = "http://statsheet.com/mcb/players/search?s=" + playerFirstName + "+" + playerLastName + "&i=1";

        
        // using JSoup to conncet to Result page form search
        Document searchDoc = Jsoup.connect(resultPageUrl).userAgent("Mozilla").timeout(6000).get();
            
    
        System.out.println("Found link .. " + resultPageUrl);
        
        int numOfResultsInt;
        
        Elements numOfResults = searchDoc.select("section#content.clearfix div.col.col12 > p");
        
        
        numOfResultsInt = numOfResults.size();
        numOfResultsInt = numOfResultsInt - 1;
        
        
        System.out.println("Found :" + numOfResultsInt + " Results");
    }
    

}
