package generic;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import processor.Clock;
import processor.Processor;

public class Simulator {
	static Processor processor;
	static boolean simulationComplete;

	public static void setupSimulation(String assemblyProgramFile, Processor p) {
		Simulator.processor = p;
		try {
			pload(assemblyProgramFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		simulationComplete = false;
	}

	static void pload(String assemblyProgramFile) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(assemblyProgramFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream dis = new DataInputStream(is);

		int address = -1;
		while (dis.available() > 0) {
			int next = dis.readInt();
			if (address == -1) {
				processor.getRegisterFile().setProgramCounter(next);
			} else {
				processor.getMainMemory().setWord(address, next);
			}
			address += 1;
		}

		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);
	}

	public static void simulate() {
		while (!simulationComplete) {
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getIFUnit().performIF();
			Clock.incrementClock();

			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);
			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);
		}

		System.out.println("Cycles: " + Statistics.getNumberOfCycles());
		System.out.println("OF Stalls: "
				+ (Statistics.getNumberOfInstructions() - Statistics.getNumberOfRegisterWriteInstructions()));
		System.out.println("Wrong Branch Instructions: " + Statistics.getNumberOfBranchTaken());
	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
