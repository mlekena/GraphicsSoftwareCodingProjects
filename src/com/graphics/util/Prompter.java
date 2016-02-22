/**
 * 
 */
package com.graphics.util;

import javax.swing.JOptionPane;
/**
 * @author mlekena
 *
 */
public class Prompter {

	public static Integer[] getScreenSize(){
		int width = askForPositiveIntPrompt("Enter screen Width number of grid squares.");
		int height = askForPositiveIntPrompt("Enter screen Height number of grid squares");
		Integer	[] result = {width,height};
		return result;
	}
	
	private static int askForPositiveIntPrompt(String message){
		boolean exit = false;
		int number = 0;
		while (!exit){
			try {
				String input = JOptionPane.showInputDialog(message);
				number = Integer.parseInt(input);
				number = Math.abs(number);
				exit = true;
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Incorrect width entered. Please Enter a number. If negative it will be made positive");
			}
		}
		
		return number;
	}
	
	public static void main(String [] args){
		Prompter.getScreenSize();
	}
}
