package edu.csulb.cecs444;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JTextArea;


/**
 *
 * @author Robert Baker <robert.baker.91pkt@gmail.com>
 */
public class Parser {

    private String current_token;
    private int table_entry, token_number, stack_top, wait;
    private Stack s;
    
    public Parser(){
        s = new Stack();
    }
    
    public void parseFromFile(String fileName, JTextArea textArea){
        Scanner scanner = null;
        try{
            scanner = new Scanner(new File(fileName));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        s.push(1);
        current_token = scanner.next();
        while(scanner.hasNext()){
//            current_token = scanner.next();
            System.out.println("current token " +current_token);
            if(current_token.equals("+")){
                token_number = -1;
            }else if(current_token.equals("-")){
                token_number = -2;
            }else if(current_token.equals("*")){
                token_number = -3;
            }else if(current_token.equals("/")){
                token_number = -4;
            }else if(current_token.equals("(")){
                token_number = -5;
            }else if(current_token.equals(")")){
                token_number = -6;
            }else if(current_token.equals("^")){
                token_number = -7;
            }else if(current_token.equals("i")){
                token_number = -8;
            }else{
                token_number = -9;
            }
            System.out.println("token number " +token_number);
            stack_top = (int) s.peek();
            System.out.println("STACK TOP " + stack_top);
            if(stack_top >0){
                table_entry = next_table_entry(stack_top, Math.abs(token_number));
                switch(table_entry){
                    case 1:
                        textArea.append("Fire 1 \n");
                        s.pop();
                        s.push(2);
                        s.push(3);
                        break;
                    case 2:
                        textArea.append("Fire 2 \n");
                        s.pop();
                        s.push(2);
                        s.push(3);
                        s.push(-2);
                        break;
                    case 3:
                        textArea.append("Fire 3 \n");
                        s.pop();
                        s.push(2);
                        s.push(3);
                        s.push(-2);
                        break;
                    case 4:
                        textArea.append("Fire 4 \n");
                        s.pop();
                        break;
                    case 5:
                        textArea.append("Fire 5 \n");
                        s.pop();
                        s.push(4);
                        s.push(5);
                        break;
                    case 6:
                        textArea.append("Fire 6 \n");
                        s.pop();
                        s.push(4);
                        s.push(5);
                        s.push(-3);
                        break;
                    case 7:
                        textArea.append("Fire 7 \n");
                        s.pop();
                        s.push(4);
                        s.push(5);
                        s.push(-4);
                        break;
                    case 8:
                        textArea.append("Fire 8 \n");
                        s.pop();
                        break;
                    case 9:
                        textArea.append("Fire 9 \n");
                        s.pop();
                        s.push(6);
                        s.push(7);
                        break;
                    case 10:
                        textArea.append("Fire 10 \n");
                        s.pop();
                        s.push(6);
                        s.push(7);
                        s.push(-7);
                        break;
                    case 11:
                        textArea.append("Fire 11 \n");
                        s.pop();
                        break;
                    case 12:
                        textArea.append("Fire 12 \n");
                        s.pop();
                        s.push(-6);
                        s.push(1);
                        s.push(-5);
                        break;
                    case 13:
                        textArea.append("Fire 13 \n");
                        s.pop();
                        s.push(-8);
                        break;
                    case 98:
                        textArea.append("Scan error \n");
                        break;
                    case 99:
                        textArea.append("pop error1 \n");
                        break;
                    default:
                        textArea.append("pop error2 \n");
                        break;
                }//end switch
            }//end if
            else{
                textArea.append("Match and Pop: " + current_token + "\n");
                s.pop();
                current_token = scanner.next();
            } //end elseif
            
        } //end While
        
    } //end Parse method
    
    private static int next_table_entry(int non_terminal, int token){
        final int [][] parse_table={
            //My table
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0,  98, 98, 1, 98, 1, 99, 99, 1, 98},
            {0,  2, 3, 98, 98, 98, 4, 98, 98, 4},
            {0,  5, 99, 98, 98, 5, 99, 99, 5, 98},
            {0,  8, 8, 6, 7, 98, 8, 98, 98, 8},
            {0,  9, 99, 99, 99, 9, 99, 99, 9, 98},
            {0,  11, 11, 11, 11, 98, 11, 10, 98, 11},
            {0,  99, 99, 99, 99, 12, 99, 99, 13, 99}
        };
        System.out.println("nonterminal: " + non_terminal +  " token " + token);
        return parse_table[non_terminal][token];
    } 
}
