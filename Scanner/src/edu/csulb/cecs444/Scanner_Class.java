/* Created by Robert Baker
 * For Educational purposes
 * And to turn in CECS 444
 */


package edu.csulb.cecs444;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTextArea;
/**
 *
 * @author Robert Baker <robert.baker.91pkt@gmail.com>
 */
public class Scanner_Class {
private int current_read;
    private int state;
    private String token_under_construction;
    ArrayList<String> userDeclared;
    
    public Scanner_Class(){
        current_read = 0;
        state = 0;
        token_under_construction = "";
        userDeclared = new ArrayList();
    }
    private int next_state(int a, int b) throws FileNotFoundException{
        //[113][30]
        int stateTable[][] = new int [113][30];
        //How do I want to call these?!
        createTable(stateTable, "StateTable.txt");
        return stateTable[a][b];
    }
    private int action(int a, int b) throws FileNotFoundException{
        //0 = ERROR, 1= MA, 2=HR
        int actionTable[][] = new int [113][30];
        createTable(actionTable, "ActionTable.txt");
        return actionTable[a][b];
    }
    private int look_up(int a, int b) throws FileNotFoundException{
        //0 = MA/E, 1=INT, 2=ID, 3=ASSIGN, 4=ADD
        //5= SEMI, 6=MULT, 7=OPAR, 8=CPAR, 9=REAL
        int lookUpTable[][] = new int [113][30];
        createTable(lookUpTable, "LookUpTable.txt");
        return lookUpTable[a][b];
    }
    public void readCharacters(String fileName, JTextArea outputArea, ArrayList<String>reservedWords) throws FileNotFoundException, IOException{
        char current_char = ' ';
        boolean buffered = false;
        @SuppressWarnings("UnusedAssignment")
        Scanner scanner = null;
        File file = new File(fileName);
        scanner = new Scanner(file);

        while(scanner.hasNext()){
            char[] charList2 = scanner.nextLine().toCharArray();
            char[] charList = new char[charList2.length + 1];
            for (int i = 0; i<charList.length-1; i++){
                charList[i] = charList2[i];
            }
            charList[charList.length-1] = '\n';
            for(int i = 0; i<charList.length; i++){
                if((!buffered) || (current_char==' ') || (current_char=='\n')){
                    current_char = charList[i];
                    
                }
                System.out.println("Current Char: " + current_char);

                if(Character.isLetter(current_char)){
                    current_read = 1;
                }
                else if(Character.isDigit(current_char)){
                    current_read = 0;
                }else{
                    switch(current_char){
                        case '$':
                            current_read = 2;
                            break;
                        case '"':
                            current_read = 3;
                            break;
                        case '.':
                            current_read = 4;
                            break;
                        case ',':
                            current_read = 5;
                            break;
                        case '+':
                            current_read = 6;
                            break;
                        case '-':
                            current_read = 7;
                            break;
                        case '^':
                            current_read = 8;
                            break;
                        case ':':
                            current_read = 9;
                            break;
                        case '[':
                            current_read = 10;
                            break;
                        case ']':
                            current_read = 11;
                            break;
                        case '<':
                            current_read = 12;
                            break;
                        case '>':
                            current_read = 13;
                            break;
                        case '(':
                            current_read = 14;
                            break;
                        case ')':
                            current_read = 15;
                            break;
                        case '{':
                            current_read = 16;
                            break;
                        case '}':
                            current_read = 17;
                            break;
                        case '/':
                            current_read = 18;
                            break;
                        case '*':
                            current_read = 19;
                            break;
                        case '=':
                            current_read = 20;
                        case ';':
                            current_read = 21;
                            break;
                        case '\'':
                            current_read = 22;
                            break;
                        case '~':
                            current_read = 23;
                            break;
                        case '!':
                            current_read = 24;
                            break;
                        case '#':
                            current_read = 25;
                            break;
                        case '&':
                            current_read = 26;
                            break;
                        case '@':
                            current_read = 27;
                            break;
                        case '\n':
                            current_read = 28;
                            break;
                        case '_':
                            current_read = 29;
                            break;
                        case ' ':
                            current_read = 29;
                            break;
                        default:
                            current_read = 31;
                            break;
                    }
                }

                System.out.println("State: " + state + " CR: " + current_read);
                if((next_state(state, current_read) !=  -1) && action(state, current_read) == 1){
                    buffered = false;
                    token_under_construction = token_under_construction + current_char;
                    state = next_state(state, current_read);
                }
                else if(next_state(state, current_read) ==  -1 && action(state, current_read) == 2){

                    buffered = true;
                    switch(look_up(state, current_read)){
                        case 1:
                            //System.out.println("Token discovered is an INTEGER: " + token_under_construction);
                            outputArea.append("Token discovered is an FILE: " + token_under_construction + "\n");
                            break;
                        case 2:
                            //System.out.println("Token discovered is an IDENTIFIER: " + token_under_construction);
                            //outputArea.append("Token discovered is an IDENTIFIER: " + token_under_construction +"\n");
                            isReservedWord(token_under_construction, reservedWords, outputArea);
                            
                            break;
                        case 3:
                            //System.out.println("Token discovered is an ASSIGNMENT OPERATOR: " + token_under_construction);
                            outputArea.append("Token discovered is a COMMENT: " + token_under_construction);
                            break;
                        case 4:
                            //System.out.println("Token discovered is an ADDITION OPERATOR: " + token_under_construction);
                            outputArea.append("Token discovered is an INTEGER: " + token_under_construction + "\n");
                            break;
                        case 5:
                            //System.out.println("Token discovered is a SEMICOLON: " + token_under_construction);
                            outputArea.append("Token discovered is CURRENCY: " + token_under_construction + "\n");
                            break;
                        case 6:
                            //System.out.println("Token discovered is a MULTIPLY OPERATOR " + token_under_construction);
                            outputArea.append("Token discovered is a REAL NUMBER: " + token_under_construction + "\n");
                            break;
                        case 7:
                            //System.out.println("Token discovered is an OPEN PARENTHESES: " + token_under_construction);
                            outputArea.append("Token discovered is a SCIENTIFIC NOTATION: " + token_under_construction + "\n");
                            break;
                        case 8:
                            //System.out.println("Token discovered is a CLOSE PARENTHESES: " + token_under_construction);
                            outputArea.append("Token discovered is a STRING LITERAL: " + token_under_construction +  "\n");
                            break;
                        case 9:
                            //System.out.println("Token discovered is a REAL NUMBER: " + token_under_construction);
                            outputArea.append("Token discovered is a SIMPLE OPERATOR: " + token_under_construction + "\n");
                        case 10:
                            //System.out.println("Token discovered is a REAL NUMBER: " + token_under_construction);
                            outputArea.append("Token discovered is a COMPLEX OPERATOR: " + token_under_construction + "\n");
                        default:
                            //System.out.println("error");
                            break;
                            
                    } //End Switch
                    state = 0;
                    token_under_construction = "";
                    i--;
                } //End ELSEIF
            } //END FOR
        } //END WHILE

        //Catch the last character
                   switch(look_up(state, current_read)){
                        case 1:
                            //System.out.println("Token discovered is an INTEGER: " + token_under_construction);
                            outputArea.append("Token discovered is a FILE: " + token_under_construction + "\n");
                            break;
                        case 2:
                            //System.out.println("Token discovered is an IDENTIFIER: " + token_under_construction);
                            //outputArea.append("Token discovered is an IDENTIFIER: " + token_under_construction +"\n");
                            isReservedWord(token_under_construction, reservedWords, outputArea);
                            break;
                        case 3:
                            //System.out.println("Token discovered is an ASSIGNMENT OPERATOR: " + token_under_construction);
                            outputArea.append("Token discovered is a COMMENT: " + token_under_construction);
                            break;
                        case 4:
                            //System.out.println("Token discovered is an ADDITION OPERATOR: " + token_under_construction);
                            outputArea.append("Token discovered is an INTEGER: " + token_under_construction + "\n");
                            break;
                        case 5:
                            //System.out.println("Token discovered is a SEMICOLON: " + token_under_construction);
                            outputArea.append("Token discovered is CURRENCY: " + token_under_construction + "\n");
                            break;
                        case 6:
                            //System.out.println("Token discovered is a MULTIPLY OPERATOR " + token_under_construction);
                            outputArea.append("Token discovered is a REAL NUMBER: " + token_under_construction + "\n");
                            break;
                        case 7:
                            //System.out.println("Token discovered is an OPEN PARENTHESES: " + token_under_construction);
                            outputArea.append("Token discovered is a SCIENTIFIC NOTATION: " + token_under_construction + "\n");
                            break;
                        case 8:
                            //System.out.println("Token discovered is a CLOSE PARENTHESES: " + token_under_construction);
                            outputArea.append("Token discovered is a STRING LITERAL: " + token_under_construction +  "\n");
                            break;
                        case 9:
                            //System.out.println("Token discovered is a REAL NUMBER: " + token_under_construction);
                            outputArea.append("Token discovered is a SIMPLE OPERATOR: " + token_under_construction + "\n");
                        case 10:
                            //System.out.println("Token discovered is a REAL NUMBER: " + token_under_construction);
                            outputArea.append("Token discovered is a COMPLEX OPERATOR: " + token_under_construction + "\n");
                        default:
                            //System.out.println("error");
                            break;
                            
                    } //End Switch
        generateTable();
        scanner.close();
    }
    
    public void createTable(int table[][], String file) throws FileNotFoundException{
        
        Scanner scan = new Scanner(new File(file));
        int count = 0;
        while(scan.hasNext()){
            //rawData = raw string of the entire line of the file
            String rawData = scan.nextLine();
            //rawArray = String type array of the line, split on the comma's
            String rawArray[] = rawData.split(",");
            for (int i = 0; i<rawArray.length; i++){
                table[count][i] = Integer.parseInt(rawArray[i]);
            }
           count++;
        }          
    }
    
    public void isReservedWord(String word, ArrayList<String> reservedWords, JTextArea outputArea){
        if (reservedWords.contains(word)){
            outputArea.append("Token " + word + " is an identifier and a reserved word \n");
        }
        else{
            userDeclaredIdentifier(word, outputArea);
        }
    }
    
    public void userDeclaredIdentifier(String word, JTextArea outputArea){
        for (String ud : userDeclared){
            System.out.println("User Declared Word: "+ud);
        }
        System.out.println("Truth: " +userDeclared.contains(word));
        
        if (userDeclared.contains(word)){
            outputArea.append("Token " + word + " is an identifier and was found in the table \n" );
        }else{
            outputArea.append("Token " + word + " is an identifier and has been placed in the table \n");
        }
        userDeclared.add(word);
    }
    public void generateTable() throws FileNotFoundException{
       ArrayList<String> uniqueUD = new ArrayList();
        PrintWriter pw = new PrintWriter(new File("userDeclared.txt"));
        for (String ud : userDeclared){
            if (!uniqueUD.contains(ud)){
                uniqueUD.add(ud);
            }//END IF
        }//END FOR
        int count = 0;
        for (String uud : uniqueUD){
            for (String ud : userDeclared){
                if (ud.equals(uud)){
                    count++;
                }//end if
            }//end inner loop
            pw.append("User Declared Identifier: " + uud + " occured " + count + " times " +  "\r\n");
            count = 0;
        }//end outer loop
        pw.close();
    }
}
