


//Jake White
//Tommy Hoffmann
//Ryan Smith

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

        /****************** Variables **********************/

        String playerFirstName = "empty";      // Argument 1
        String playerLastName = "empty";      // Argument 2
        String statToLookAt = "empty";    // Argument 3
        String numberThreshold = "empty"; // Argument 4
        String searchName= "empty";
        String fullName = "empty" ;
        String firstNameToken = "empty";
        String lastNameToken = "empty";
        String answer;
        
        String playerName = " NA";  // set default value for Player we are looking for
        String resultLinkString = "na";
        String gameStatsUrl = "na";
        
        int gameCounter = 0;
        int numOfGamesToCheck;
        int x = 0;               // # of games to look at
    
        
        int thresholdInt = 0;   // numberThreshold converted to int - deafault at 0
        int statCol = 2;           

    /*************************** Assure statsheet.com is running *********************************************************/

        Document searchDoc2 = Jsoup.connect("http://downforeveryoneorjustme.com/http://statsheet.com/").userAgent("Mozilla").timeout(6000).get();

        Elements containsYN = searchDoc2.select("div#container");
        String isSiteUpString = containsYN.toString();

        // Use regex to parse result of downforeveryoneorjustme.com for word 'not' which indicates "it's not just you" regarding the sites status
        Boolean isSiteUp = isSiteUpString.matches(".*not.*"); 
        
        System.out.print('\n');
        
        if(isSiteUp)
        {
          System.out.print("Cannot connect to StatSheet.com");
          System.exit(0);
        }
        else{System.out.print("... Connecting to StatSheet.com ...\n");}

        

    /**********************************************************************************************************/

        /* Get command prompt arguments for Player's Name */
        /**************************************************/
        System.out.print("\n");
        System.out.print(" First Name : ");
        Scanner nameScanner = new Scanner(System.in);
        playerFirstName = nameScanner.nextLine();
        
        System.out.print(" Last Name : ");
        Scanner lastNameScanner = new Scanner(System.in);
        playerLastName = lastNameScanner.nextLine();
        
        
        //Check for Spaces in name
        if(playerFirstName.indexOf(' ') >= 0){
                //System.out.println("contains spaces");
                //delete space
                playerFirstName = playerFirstName.replace(" ","");
                //System.out.println("new first name: " + playerFirstName);
                //.split(' ').join('_')
        }

        if(playerLastName.indexOf(' ') >= 0){
                //System.out.println("contains spaces");
                //delete space
                playerLastName = playerLastName.replace(" ","");
                //System.out.println("new first name: " + playerFirstName);
                //.split(' ').join('_')
        }


        fullName = playerFirstName + " " + playerLastName;
        System.out.print('\n');
        System.out.println("Searching for " + fullName + " ...");

        /****************************************************/
        
        /******************************************************
         Here you will take the fullName and search it (sumbit
         a POST form), you will get a result page. Use result page
         URL to find player and their appropriate URL
         *****************************************************/


        String resultPageUrl;
        resultPageUrl = "http://statsheet.com/mcb/players/search?s=" + playerFirstName + "+" + playerLastName + "&i=1";

        
        // using JSoup to conncet to Result page form search
        Document searchDoc = Jsoup.connect(resultPageUrl).userAgent("Mozilla").timeout(6000).get();

        String resultOffset = " p + p "; // original offset of results puts searchName on 1st result
        

    
        System.out.println("Found link .. " + resultPageUrl);
        
        int numOfResultsInt;
        
        Elements numOfResults = searchDoc.select("section#content.clearfix div.col.col12 > p");
        
        
        numOfResultsInt = numOfResults.size();
        numOfResultsInt = numOfResultsInt - 1;
        

        int w = 0;
        
        while (w == 0) 
        {    // so we can break out of this sequence in certain cases
            
            // exit of no results were found
            if (numOfResultsInt == 0)
            {
                System.out.println("** Error ** No Results Found ** Check Spelling **");
                System.exit(0);
                
            }
            
            if (numOfResultsInt != -1)
            {
                searchName = fullName;

            } else {
                numOfResultsInt = 1; // one result was indeed there
                // Player Found - resultLinkString URL can be used
                resultLinkString = resultPageUrl;
                System.out.println("# of Search results: " + numOfResultsInt);
                break;
            
            }
               
            
            System.out.println("# of Search results: " + numOfResultsInt);
                
            w = 1; // we not return to the top of the while loop
            
            String findSearchName = "no";
            
            int v = 0; // counter for search results

                
            // we keep searching for fullName in search results until we find it or have looked through every search result
            while (v < numOfResultsInt) 
            {
            
               if (findSearchName == "no") 
               {
               
                   Elements searchResultNames = searchDoc.select("section#content.clearfix div.col.col12 > table +" + resultOffset + "> strong > a");
                   searchName = searchResultNames.first().text();
                       
                   StringTokenizer nameTokens = new StringTokenizer(searchName);
                   
                   int tokenCounter = 0;
                   while (nameTokens.hasMoreElements()) 
                   {
                           
                      if (tokenCounter == 0) 
                      {
                        System.out.println("First Name Token : " + nameTokens.nextElement().toString());
                     
                      }
                         
                      if (tokenCounter == 1) 
                      {
                        lastNameToken = nameTokens.nextElement().toString() ;
                        System.out.println(lastNameToken);
                         
                         
                      }
                      
                      tokenCounter = tokenCounter + 1;
            
                   }
                    
                   if (playerFirstName.equals("") && lastNameToken.equals(playerLastName)) 
                   {
                       
                       System.out.print(" Is this the player (type Y or N): ");
                       Scanner yesOrNoScanner = new Scanner(System.in);
                       answer = nameScanner.nextLine();
                       
                       if (answer.equals("Y")) 
                       {
                       
                       findSearchName = "yes";
                       
                       break;
                       
                       }
                       
                       
                   }
                       
                   if (searchName.equals(fullName))
                   {
                      findSearchName = "yes";
                      break;
                   } else {
                      resultOffset = resultOffset + "+ p ";
                      v = v + 1;
                   }
            
                   
               }
            }
            

                if (v == numOfResultsInt && findSearchName == "no") {
                
                System.out.println("Damn!! Couldn't Find the Player easily");
                System.exit(0);
            }
            
            // once correct player is found find the link to his page
            Elements resultLinks = searchDoc.select("section#content.clearfix div.col.col12 > table +" + resultOffset + "> strong > a");
                
                for (Element link : resultLinks) {
                    String href = link.attr("href");
                    resultLinkString = href;
                
        
                }
        
        }//////End of While
        
        System.out.println("Found link .. " + resultLinkString);
        System.out.println("\n");
        
        // Retrieve Stat category name from user
        System.out.print("Enter Stat Name : ");
        Scanner statScanner = new Scanner(System.in);
        String statName = statScanner.nextLine();
        
        
        // Exit Program if no Arguments are given
        if (statName.equals("")) {
            System.out.println("*** ERROR *** You Didn't Enter A Stat : *** " +
                               "\nEnter a Stat eg. Points" );
            System.exit(0);
        }
        
        statToLookAt = statName;
        
        // Retrieve the number threshold so we can
        //  count games above the threshold
        System.out.print("Enter Number Threshold : ");
        Scanner numScanner = new Scanner(System.in);
        String numThreshold = numScanner.nextLine();
        System.out.println("________________________________________");
        
        // Exit Program if no Arguments are given
        if (numThreshold.equals("")) {
            System.out.println("*** ERROR *** You Didn't Enter A Number Threshold *** " +
                               "\nEnter a Number to compares stats To eg. 25" );
            System.exit(0);
        }

        numberThreshold = numThreshold;
        
        /*
         *We now we have our statToLookAt and threshold value
         *Check Stat and match it to its corresponding Stat Column
         ******************************************/
        
        if (statToLookAt.equals("Points") || statToLookAt.equals("Pts") ||
            statToLookAt.equals("Points ") || statToLookAt.equals("Pts "))
        {
            statToLookAt = "Points";
            statCol = 3;
        }
        if (statToLookAt.equals("Assists") || statToLookAt.equals("Asts") ||
            statToLookAt.equals("Assists ") || statToLookAt.equals("Asts "))
        {
            statToLookAt = "Assists";
            statCol = 13;
        }
        if (statToLookAt.equals("Rebounds")|| statToLookAt.equals("Rebs") ||
            statToLookAt.equals("Rebounds ")|| statToLookAt.equals("Rebs "))
        {
            statToLookAt = "Rebounds";
            statCol = 12;
        }
        if (statToLookAt.equals("Steals")|| statToLookAt.equals("Stls")){
            statToLookAt = "Steals";
            statCol = 14;
        }
        if (statToLookAt.equals("Blocks")|| statToLookAt.equals("Blks")){
            statToLookAt = "Blocks";
            statCol = 15;
        }
        if (statToLookAt.equals("TOs")|| statToLookAt.equals("TO's")){
            statCol = 16;
        }
        // Checked Stat and couldn't find match .. exiting
        if (statCol == 2) {
            System.out.println("*** ERROR *** Didn't Recognize Stat Name *** " +
                               "\nEnter a Stat then a Number - eg. Rebounds 10" );
            System.exit(0);
        }
        /************************************************/
        
        
        // No that we have safe inputs from the user
        // use JSoup to conncet and retrieve url's html
        Document playerHomePageDoc = Jsoup.connect(resultLinkString)
        .userAgent("Mozilla").timeout(6000).get();
        
        // Once link to player page is found, parse html to find the
        //  GameStats page where the individual game logs live, Name it GameStatsUrl
        // ** need better variable names to describe whats going on
        Elements gameStatsLink = playerHomePageDoc.select("section#content.clearfix div.col.col12 > ul > li + li > a");
        String yyy = gameStatsLink.first().text();
        
        int xxx = 0;
        for (Element link : gameStatsLink) {
            
            if (x == 0) {
                String href = link.attr("href");
                gameStatsUrl = href;
                break;
            }
            
            xxx = xxx + 1;
        }
        
        gameStatsUrl = "http://statsheet.com" + gameStatsUrl;
                
    /*************************** Text color manipulator variables ***************
     **************************************/
        String ANSI_RESET = "\u001B[0m";        // called at end of string to reset to original text color
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";         // called at begining of string to print text as red
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";
    /*****************************************************************************
     ***************************************/

        // testing color 

        //System.out.println(ANSI_RED + "This Text is red??" + ANSI_RESET);
        //System.out.println("Hopefully this isn't");
        
        // using JSoup to conncet and retrieve url's html
        Document doc = Jsoup.connect(gameStatsUrl)
        .userAgent("Mozilla").timeout(6000).get();
        
        playerName = "NA";
        gameCounter = 0;
        
        playerName = "player name here";
        
        Elements GameRows = doc.select("div.wrapper form > table > thead + tbody > tr");
        Elements playerNameAndNumber = doc.select("div.col.col12 table > tbody > tr  > td > a");
        
        String PNNselection = playerNameAndNumber.first().text();
        
        numOfGamesToCheck = GameRows.size();
        
        System.out.println("  Name and Number: " + PNNselection);
        System.out.println("  Games in Season: " + numOfGamesToCheck);
        
        String row = " tr ";        // original row offset
        int numOfGamesMissed = 0;
        
        while (x < numOfGamesToCheck) {
            
            String rowCounter = " + tr "; // move down one row
            
            Elements rowToLookAt = doc.select("div.wrapper form > table > thead + tbody >" + row + "> td");
            
            
            String selection = rowToLookAt.get(statCol).text();
            
            // if stat (selection) is empty restart while look
            if (selection.equals("")) {
                //System.out.println("*** ERROR *** FOUND ");
                row = row + rowCounter;
                numOfGamesToCheck--;
                numOfGamesMissed++;
                continue;
                
                //System.exit(0);
            }
            
            if (selection.equals("-")) {
                System.out.println("*** ERROR *** dash Found ");
                System.exit(0);
            }
            
            
            int i = Integer.parseInt(selection); // convert to Int
            
            thresholdInt = Integer.parseInt(numberThreshold); // convert to Int
            
            String thresholdCheck; // checks if the stat is above the threshold
            
            if (i > thresholdInt - 1){
                
                thresholdCheck = "yes";
                gameCounter = gameCounter + 1;
                
            } else thresholdCheck = "no";
            
            x = x + 1;
            
            String message = ("***Game " + x + " ***");
            
            row = row + rowCounter;
            
            
        }
        System.out.println("  Missed :" + numOfGamesMissed + " Games");
        System.out.println("\033[31m  " + gameCounter + " Games having " + thresholdInt + " " + statToLookAt + " or more \033[30m");
        
        
        

    }
}





