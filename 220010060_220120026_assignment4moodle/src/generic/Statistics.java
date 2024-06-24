package generic;
import java.io.PrintWriter;
public class Statistics {
	
	static int Instructionnumber;
	static int numbCycles;
	static int OfBranchTaken;
	static int RegisterWriteInstructionss;

	public static void printStatistics(String statFile) {
		try {
			PrintWriter writer = new PrintWriter(statFile);

			writer.println("Number of instructions executed = " + Instructionnumber);
			writer.println("Number of cycles taken = " + numbCycles);
			writer.close();
		} catch (Exception e) {
			Misc.printErrorAndExit(e.getMessage());
		}
	}

	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.Instructionnumber = numberOfInstructions;
	}

	public static int getNumberOfCycles() {
		return numbCycles;
	}

	public static void setNumberOfOFInstructions(int numOFStageInstructions) {
		
	}
	public static int getNumberOfBranchTaken() {
		return OfBranchTaken;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numbCycles = numberOfCycles;
	}

	public static int getNumberOfInstructions() {
		return Instructionnumber;
	}

	public static void setnumberOfRegisterWriteInstructions(int numberOfRegisterWriteInstructions) {
		Statistics.RegisterWriteInstructionss = numberOfRegisterWriteInstructions;
	}

	public static int getNumberOfRegisterWriteInstructions() {
		return RegisterWriteInstructionss;
	}

	public static int getNumberOfOFInstructions() {
		return 0;
	}

	public static void setNumberOfBranchTaken(int numberOfBranchTaken) {
		Statistics.OfBranchTaken = numberOfBranchTaken;
	}
}
