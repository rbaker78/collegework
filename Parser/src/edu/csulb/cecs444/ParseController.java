/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csulb.cecs444;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Rober_000
 */
public class ParseController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Parser p = new Parser();
        System.out.println("Running");
        JOptionPane window = new JOptionPane();
        JTextArea textArea = new JTextArea();
        textArea.setRows(15);
        JScrollPane textAreaScroller = new JScrollPane(textArea);
        p.parseFromFile("test2.txt", textArea);
        window.showMessageDialog(null, textAreaScroller, "Parser information: ", window.INFORMATION_MESSAGE);
    }
    
}
