/**
 * Utility package contains the tools that are used for software quality
 * assurance, and to help the software developers of this project "NetSim"
 */
package utility;

// Import package
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Class to print the results to selected a text file, by piping the data
 * from the System's standard output stream to that file. This excludes all
 * error output streams.
 *
 * All results pertaining to the fitness of a Chromosome, and its pleiotropy
 * and redundancy shall be printed with this command.
 *
 * Reference: Harvey M. Deitel and Paul J. Deitel, "Java How to Program,"
 * 3rd Edition, Prentice-Hall, New Jersey, 1999, pp.43;
 * Rogers Cadenhead, "Sams Teach Yourself Java 2 in 24 Hours," 2nd Edition,
 * Techmedia, New Delhi, 2000, pp. 97-98
 * Cay Horstmann, "Big Java," John Wiley & Sons, Indianapolis, IN, 2002,
 * pp. 23-24
 *
 * @author Zhiyang Ong and Andy Lo
 * @version 0.3.8
 * @acknowledgement Tishampati Dhar
 */
public class PrintText {
	// Instance variables and constants
	private static final char COMMA = ',';
	private static final char TAB = '	';
	private static final char JTAB = '\t';
	private static final char CHARACTER_SPACE = ' ';
	private static final char SEMI_COLON = ';';
	private static final char COLON = ':';
	private static final char UNICODEe = '\u00E9';
	private static char separator;
	
	// Print writer to write output to a file
	private static PrintWriter writer;
	// Pipe output to a file
	private static PrintStream pipe;
	
	// --------------------------------------------------------------------

	// Default constructor...
	public PrintText() {
		// Do nothing...
		Debugger.printErr("Do not instantiate the Class PrintText");
	}

	// --------------------------------------------------------------------
	
	/**
	 * Method to redirect the output of the simulation from the optimization
	 * of the telecommunications network to a selected output file
	 * @param filename is the name of the destination file containing the
	 *	simulation results
	 * @throws PreconditionException if there exists a file which name is
	 *	the same as the String in filename
	 */
	public static void pipeResults(String filename) { //throws IOException {
		/**
		 * Create an abstract FIle that will pipe the standard output
		 * streams to a text file.
		 */
		File outFile = new File(filename+getDateTime()+".txt");
		/**
		 * If the file already exists, print the results into the terminal
		 * Inform the user that this file exists and cannot be overwritten
		 */
		Assertion.pre(!outFile.exists(),
			"Simulation results will be piped to the file named: "+filename,
			"A file with the name "+filename+" exists. A new file must be "
			+"created for storing simulation results");
		try{
			// Direct the output stream from the terminal to the output file
			writer = new PrintWriter(new FileWriter(outFile));
			//outFile = new File("checkThis.txt");
			//pipe = new PrintStream(outFile);
		}catch(IOException e) {
			Debugger.printErr("Error opening and/or writing to the file named: "
				+filename);
			Debugger.printErr("Simulation results will be printed in the "
				+"\'standard\' output and error streams.");
		}
	}
	
	/**
	 * Method to print current simulation results to the file containing the
	 * the simulation results
	 * @param results contains the current simulation results from the
	 *	fittest Chromosome of the latest evolved generation
	 */
	public static void printResults(String results) {
		// Print current simulation results...
		writer.println(results);
		/**
		 * # Modified by Andy Lo 24 Apr 2005:
		 *      Added instruction for the print writer to flush, since the 
		 *  stream might have saved some data in the buffer. Thus by calling 
		 *  flush(), the data in the buffer will will be written immediately 
		 *  to their intended destination (output file).
		 */
		writer.flush();
	}
	
	/**
	 * Method to close the PrintWriter
	 */
	public static void close() {
		writer.close();
	}
	
	/**
	 * Method to get the time and date now as a string
	 * @return time and date now as a string
	 */
	public static String getDateTime() {
		DateFormat dateFormat =
			DateFormat.getDateInstance(DateFormat.DEFAULT,Locale.US);
		String date = dateFormat.format(new Date());
		dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT,Locale.US);
		date = date+"-"+dateFormat.format(new Date());
		date = parse(date);
		return date;
	}
	
	/**
	 * Method to get the filename of the simulation results
	 * @param date indicates the time and date now
	 * @return the time and date now without commas, colons,
	 *	and spaces
	 */
	public static String parse(String date) {
		StringBuffer sb = new StringBuffer(date);
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<sb.length(); i++) {
			char curChar = sb.charAt(i);
			
			switch(curChar) {
				case ' ':
					buffer.append('-');
					break;
				case ',':
					//buffer.append('-');
					break;
				case ':':
					buffer.append('-');
					break;
				default:
					buffer.append(curChar);
					//System.out.println("Current character is: "+curChar);
			}
		}
		date = buffer.toString();
		return date;
	}
	
	/**
	 * Method to print current simulation results to the file containing the
	 * the simulation results using the FileStream
	 * @param results contains the current simulation results from the
	 *	fittest Chromosome of the latest evolved generation
	 * @return nothing
	 */
	/*
	public static void printStream(String results) {
		// Print current simulation results...
		pipe.println(results);
	}
	*/
	
	/**
	 * Method to close the PrintStream
	 * @param nothing
	 * @return nothing
	 */
	/*
	public static void closeStream() {
		pipe.close();
	}
	*/
}
