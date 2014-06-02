/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
//package utility.tests;

/**
 * Class to test the file PrintText
 *
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.8
 * @acknowledgement Tishampati Dhar
 */

// Import packages
import java.io.*;
import utility.*;
import java.util.*;
import java.text.*;
// Class definition

// =========================================================================

public class TestPrintText {
	// Instance variables and constants
	private static final char COMMA = ',';
	private static final char TAB = '	';
	private static final char JTAB = '\t';
	private static final char CHARACTER_SPACE = ' ';
	private static final char SEMI_COLON = ';';
	private static final char COLON = ':';
	private static final char UNICODEe = '\u00E9';
	private static char separator;
	
	// Name of output file
	private static String outputFile = "simulation";
	
	// number of numbers
	private static int numberOfNumbers = 1000;
	
	// --------------------------------------------------------------------

	// Default constructor...
	public TestPrintText() {
		// Do nothing...
		Debugger.printErr("Do not instantiate the Class TestPrintText");
	}

	// --------------------------------------------------------------------

	// Main method...
	public static void main(String[] args) {
		Debugger.pipeResult("PrintTextNormal.txt", "PrintTextError.txt");
        Debugger.enableTrace(true);
		
		System.out.println("Start piping...");
		Debugger.debug("###############################################");
		Debugger.debug("Start Here! Testing PrintText");
		Debugger.printErr("--------------------------------------------");
		Debugger.printErr("Start Here! Testing PrintText");
		
		// Array of numbers
		int[] numbers = new int[numberOfNumbers];
		// Produce some Random Numbers...
		Random rand = new Random();
		// Create 1000 random numbers...
		for(int i=0; i<numberOfNumbers; i++) {
			// ... and put them in the array
			numbers[i] = rand.nextInt();
		}
		
		// Put the numbers into sets of 6 each line and print them into a text file
		PrintText.pipeResults(outputFile);
		separator = SEMI_COLON;
		PrintText.printResults("Generation"+separator+"Pleiotropy"
			+separator+"Redundancy"+separator+"CostFunction1"+separator
			+"CostFunction2"+separator+"CostFunction3");
		/*
		PrintText.printStream("Generation"+separator+"Pleiotropy"
			+separator+"Redundancy"+separator+"CostFunction1"+separator
			+"CostFunction2"+separator+"CostFunction3");
		*/
		//Print the random numbers into the file...
		for(int i=0; i<(numberOfNumbers/6); i++) {
			// ... add a SEPARATOR between values
			/*
			PrintText.printStream(""+i+separator+numbers[i]
				+separator+numbers[i++]+separator+numbers[i++]
				+separator+numbers[i++]+separator+numbers[i++]);
			*/
			PrintText.printResults(""+i+separator+numbers[i]
				+separator+numbers[i++]+separator+numbers[i++]
				+separator+numbers[i++]+separator+numbers[i++]);
		}
		
		PrintText.close();
		//PrintText.closeStream();
		
		/*
		System.out.println("Char printed is COMMA: "+COMMA+"THAT'S IT");
		System.out.println("Char printed is TAB: "+TAB+"THAT'S IT");
		System.out.println("Char printed is JTAB: "+JTAB+"THAT'S IT");
		System.out.println("Char printed is CHARACTER_SPACE: "+CHARACTER_SPACE
			+"THAT'S IT");
		System.out.println("Char printed is SEMI_COLON: "+SEMI_COLON+"THAT'S IT");
		System.out.println("Char printed is COLON: "+COLON+"THAT'S IT");
		System.out.println("Char printed is UNICODEe: "+UNICODEe+"THAT'S IT");
		System.out.println("END TEST");
		*/
		System.out.println("End piping...");
		Debugger.debug("Testing PrintText End Here!");
		Debugger.debug("###############################################");
		Debugger.printErr("Testing PrintText End Here!");
		Debugger.printErr("--------------------------------------------");
	}
}