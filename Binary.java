/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fred
 */
import java.util.Scanner;
import java.util.StringTokenizer;

public class Binary {
    public static int ByteA[] = new int[32];
    public static int ByteB[] = new int[32];
    public static int ByteN[] = new int[32]; 
    public static int ByteR[] = new int[32];
    //public static int InputA = 2221;
    //public static int InputB = -1201;
    
    public static void main(String[] args){
        System.out.println("Enter a calcul: ");
        Scanner scan = new Scanner(System.in);
        String calcul = scan.nextLine();
        
        StringTokenizer equation = new StringTokenizer(calcul);
        String op1 = equation.nextToken();
        String opr = equation.nextToken();
        String op2 = equation.nextToken();
        
        int InputA = Integer.parseInt(op1);
        int InputB = Integer.parseInt(op2);
        
        convertion(InputA, ByteA);
        convertion(InputB, ByteB);
        
        switch (opr){
            case "+":
                add(ByteA, ByteB);
                display(ByteR);
                BinToDec(ByteR);
                break;
                
            case "-":
                sub(ByteA, ByteB);
                display(ByteR);
                BinToDec(ByteR);
                break;
                
            case "*":
                mul(ByteA, ByteB);
                display(ByteR);
                BinToDec(ByteR);
                break;
                
            default:
                System.out.println("Invalid input");
                break;
        }//end switch
    }//end main
    
    public static void display(int[] Byte){
        for(int i = 0; i < Byte.length; i++){
            System.out.print(Byte[i]);
            if((i+1)%4 == 0)
                System.out.print(" ");
        }//end for
        System.out.println();
    }//end display
    
    public static void convertion(int newInput, int newByte[]){
        if(newInput < 0){
            newInput = 0 - newInput;
            DecToBin(newInput, newByte);
            oppo(newByte);
            plusOne(newByte);
        }//end if
        else{
            DecToBin(newInput, newByte);
        }//end else
    }//end convertion
    
    public static void oppo(int[] newByte){
        //System.out.println("It's Oppo");
        for(int i = 0; i < newByte.length ; i++){
            if(newByte[i] == 0)
                newByte[i] = 1;
            else
                newByte[i] = 0;
        }//end for
        //display(newByte);
    }//end oppo
    
    public static void plusOne(int[] newByte){
        //System.out.println("It's plusOne");
        int i = 31;
        while(i != 0){
            if(newByte[i] == 0){
                newByte[i] = 1;
                i = 0;
            }//end if
            else{
                newByte[i] = 0;
                i--;
            }//end else
        }//end while
        //display(newByte);
    }//end plusOne
    
    public static void DecToBin(int newInput, int newByte[]){
        int mod = 0;
        for(int i = 31; i >= 0; i--){
            mod = newInput % 2;
            if(mod == 0){
                newByte[i] = 0;
                newInput = newInput / 2;
            }//end if
            else{
                newByte[i] = 1;
                newInput = (newInput - 1)/2;
            }//end else
        }//end for
    }//end DecToBin
    
    public static void BinToDec(int newByte[]){
        int newInput = 0;
        int i = 0;
        int power = 0;
        if(newByte[0] == 0){
            for(i = 0; i < newByte.length; i++){
                power = (int) Math.pow(2,(31 - i));
                newInput = newInput + newByte[i] * power;
            }//end for
        }//end if
            
        else{
            oppo(newByte);
            plusOne(newByte);
            for(i = 0; i < newByte.length; i++){
                power = (int) Math.pow(2,(31 - i));
                newInput = newInput + newByte[i] * power;
            }//end for
            newInput = 0 - newInput;
        }//end else
        System.out.println(newInput);
    }//end BinToDec
    
    public static void add(int[] bin1, int[] bin2){
        int Ci = 0;
        for(int i = bin2.length-1; i >= 0; i--){
            if(bin1[i]+bin2[i]+Ci <= 1) {
                ByteR[i] = bin1[i]+bin2[i]+Ci;
                Ci = 0;
            }//end if
            else{
                ByteR[i] = bin1[i]+bin2[i]+Ci-2;
                Ci = 1;
            }//end else
        }//end for
    }//end add
    
    public static void sub(int[] bin1, int[] bin2){
        for(int i = 0; i < bin2.length; i++)
                    ByteN[i] = bin2[i];
        oppo(ByteN);
        plusOne(ByteN);
        add(bin1, ByteN);
    }//end sub
    
    public static void mul(int[] bin1, int[] bin2){
        int[] prod = new int[65];
        int pc = 0;
        int x = 63;
        int i = 0;
        
        for(i = 0; i < prod.length; i++){
            if (i < 32 || i == 64)
                prod[i] = 0;
            else
               prod[i] = bin2[i-32];
        }//end for
        
        while(pc < bin1.length){
            if(prod[x] == 0 && prod[x+1] == 1){
                add(prod, bin1);
                for(i = 0; i < bin2.length; i++)
                    prod[i] = ByteR[i];
                shift(prod);
            }//end if
            else if(prod[x] == 1 && prod[x+1] == 0){
                sub(prod, bin1);
                for(i = 0; i < bin2.length; i++)
                    prod[i] = ByteR[i];
                shift(prod);
            }//end else if
            else{
                shift(prod);
            }//end else
            pc++;
        }//end while
        for(i = 32; i < prod.length - 1; i++)
                    ByteR[i-32] = prod[i];
    }//end mul
    
    public static void shift(int[] prod){
        for(int i = prod.length - 1; i != 0; i--){
            if(i == 0)
                prod[i] = prod[i];
            else{
                prod[i] = prod[i-1];
            }//end else
        }//end for
    }//end prod
}//end Binary