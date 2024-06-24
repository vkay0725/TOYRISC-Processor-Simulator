package generic;

import java.io.PrintWriter;

public class Statistics {
	
	static int numberOfCycles;
	static float iPC;
	static int numberOfInstructions;
	static float cPI;

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
	
	public static void setCPI(int numinst, int numcycles) {
		cPI = (float)numcycles / (float)numinst;
	}

	public static int getNumberOfInstructions() {
		return numberOfInstructions;
	}
	public static void setIPC(int numinst, int numcycles) {
		iPC = (float)numinst / (float)numcycles;
	}

	
	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			writer.println("IPC = " + iPC);
			writer.println("CPI = " + cPI);
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}

	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}
	public static float getIPC() {
		return iPC;
	}
	
	public static float getCPI() {
		return cPI;
	}
	public static int getNumberOfCycles() {
		return numberOfCycles;
	}
}