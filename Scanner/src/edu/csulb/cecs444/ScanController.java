/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csulb.cecs444;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Robert Baker <robert.baker.91pkt@gmail.com>
 */
public class ScanController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner_Class sc = new Scanner_Class();
        JOptionPane window = new JOptionPane();
        String fileLocation;
        JTextArea fileArea = new JTextArea();
        JScrollPane faScroller = new JScrollPane(fileArea);
        ArrayList<String> reservedWordsList = new ArrayList <>() ;
        generateRWArray(reservedWordsList);
        JTextArea outputArea = new JTextArea();
        outputArea.setRows(10);
        JScrollPane oaScroller = new JScrollPane(outputArea);
        fileLocation = window.showInputDialog("Enter a text File to  open: ");
        Scanner scanner = new Scanner(new File(fileLocation));
        String line;
        
        while(scanner.hasNext()){
            line = scanner.nextLine();
            fileArea.append(line + "\n");
        }
        window.showMessageDialog(null, faScroller, "Oringial File", window.INFORMATION_MESSAGE);
        scanner.close();
        
        sc.readCharacters(fileLocation, outputArea, reservedWordsList);
        window.showMessageDialog(null, oaScroller, "Output", window.INFORMATION_MESSAGE);  
    }
    
    public static void generateRWArray(ArrayList<String> reservedWords) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("reservedWords.txt"));
        //Add the words to the arraylist
        while (scanner.hasNext())
        {
            String word = scanner.nextLine();
            reservedWords.add(word);
        }
        //Outer for loop, sort through each value in the array
        for(int j=0; j<reservedWords.size();j++)
        {
            //Inner for loop
            for (int i=j+1 ; i<reservedWords.size(); i++)
            {
                //Swap words
                if(reservedWords.get(i).compareTo(reservedWords.get(j))<0)
                {
                    String temp= reservedWords.get(j);
                    reservedWords.set(j, reservedWords.get(i));
                    reservedWords.set(i, temp);
                }
            }
        }
    }
    
}
