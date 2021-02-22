package org.synalogic_test.readerapi.countingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.synalogic_test.readerapi.model.Document;


@Service
public class CountingUtilsService {

    private int wordcount  = 0;
    private int charCount = 0;
    DecimalFormat df = new DecimalFormat("###.###");
    private double avgWordCount =0.000;
    private Map<Integer,Integer> wordLengths = new HashMap<>();

    //initialization of map upon new creation of class
    public CountingUtilsService()
    {
        wordLengths.put(1, 0);
        wordLengths.put(2, 0);
        wordLengths.put(3, 0);
        wordLengths.put(4, 0);
        wordLengths.put(5, 0);
        wordLengths.put(7, 0);
        wordLengths.put(10, 0);
    }

    public Document readFromPath(Path path) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        Document temp = Document.builder().build();

        try (BufferedReader reader = Files.newBufferedReader(path);) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
                temp = wordCounter(line,temp,false);
            }
            temp = wordCounter(line,temp,true);
        }
        return temp;
    }

    private Document wordCounter(String line, Document temp, boolean isEnd)
    {
        // reach end of document
        if(isEnd)
        {
            temp.setWordCount(wordcount); 
            avgWordCount = calculateWordLength(charCount,wordcount);
            getModes(wordLengths,temp);
            temp.setAvgWordCount(avgWordCount);
            temp.setWordLengths(wordLengths);
            return temp;
        }
        
        String[] wordList = line.split("\\s+");
        ArrayList<String> filteredWordList = isValidWords(wordList);
        wordcount += filteredWordList.size();
        for(String word:filteredWordList){
            charCount +=word.length();
            wordlengthSort(temp,word);
        }
        return temp;
    }

    //given list of words checks each word is alphanumeric
    public ArrayList<String> isValidWords(String[] wordlist)
    {
        ArrayList<String> parts = new ArrayList<String>();
        for (String word: wordlist)
        {
            // & is assumed to be a subsitute for the word 'and'
            if(Pattern.matches("[&]", word))
            {
                parts.add(word);
                continue;
            }
            //assume dates are in dd/mm/yyyy based on uk dates
            if(word.contains("/"))
            {
                if(isDate(word))
                {
                    parts.add(word);
                    continue; 
                }
            }
            word= word.replaceAll("\\p{Punct}", "");
            if(!StringUtils.isAlphanumeric(word))
            {
                continue;
            }  
            parts.add(word);
        }
        return parts;
    }

    private void wordlengthSort(Document temp, String word) {
        int wordLength = word.length();
    
        switch(wordLength)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 10:
                wordLengths.put(wordLength, wordLengths.get(wordLength)+1);
                break;
            default:
                break;
        }
                 
    }

    public void getModes(Map<Integer, Integer> wordlengths, Document temp)
    {
        ArrayList<Integer> modes = new ArrayList<Integer>();
        int highCount = 0;

        for(int key : wordlengths.keySet()) {
            if(wordlengths.get(key) > highCount) {
                highCount = wordlengths.get(key);
            }
        }
    
        for(int key : wordlengths.keySet()) {
            if(wordlengths.get(key) == highCount)
                modes.add(key);
        }
        
        temp.setModeOfwordLenths(highCount);
        temp.setLetterCountModes(modes);
    }

    public boolean isDate(String dateString)
    {
        Date date = null;
        DateFormat dtF = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dtF.parse(dateString);
        } catch (ParseException pe) {
            return false;
        }
        return  date != null;     
    }

    public double calculateWordLength(int charCount, int wordcount)
    {
        double result =  (double) ((double)charCount/wordcount);

        return Double.parseDouble(df.format(result));
    }

    public String printDocumentStats(Document doc)
    {
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        
        String response = null;
        String wordCount = "Word count= " +myFormat.format(doc.getWordCount()) + "\n";
        String avgWordLength = "Average word length = " + myFormat.format( doc.getAvgWordCount() ) + "\n";
        String wordLength1 ="Number of words of length 1 is "+myFormat.format( doc.getWordLengths().get(1) ) + "\n";
        String wordLength2 ="Number of words of length 2 is " +myFormat.format(doc.getWordLengths().get(2) ) + "\n";
        String wordLength3 ="Number of words of length 3 is " +myFormat.format(doc.getWordLengths().get(3) ) + "\n";
        String wordLength4 ="Number of words of length 4 is " +myFormat.format(doc.getWordLengths().get(4) ) + "\n";
        String wordLength5 ="Number of words of length 5 is " +myFormat.format(doc.getWordLengths().get(5) ) + "\n";
        String wordLength7 ="Number of words of length 7 is " +myFormat.format(doc.getWordLengths().get(7) ) + "\n";
        String wordLength10 ="Number of words of length 10 is " +myFormat.format(doc.getWordLengths().get(10) ) + "\n";
        String wordLenthMode = "The most frequently occurring word length is " + myFormat.format( doc.getModeOfwordLenths() )
         +", for word lengths of " + doc.getLetterCountModes().toString() + "\n";

       response = wordCount+avgWordLength+wordLength1+wordLength2+wordLength3
       +wordLength4+wordLength5+wordLength7+wordLength10 + wordLenthMode;
       return response;
    }

   
    
}
