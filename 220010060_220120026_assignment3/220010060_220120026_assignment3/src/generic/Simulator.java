package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {
	static boolean simulationComplete;	
	static Processor processor;
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TOD0
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		InputStream tumajar=null;
		try{
			tumajar= new FileInputStream(assemblyProgramFile);
			DataInputStream bisaun = new DataInputStream(tumajar);
			int next=0;
			if(bisaun.available()>0){
				next = bisaun.readInt();
				processor.getRegisterFile().setProgramCounter(next);
			} 
			for(int address=0;bisaun.available()>0;address++){
				next=bisaun.readInt();
				processor.getMainMemory().setWord(address, next);
			}
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1,65535);
			processor.getRegisterFile().setValue(2, 65535);
			bisaun.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}
	
	
	public static void simulate()
	{
		int num_instruct = 0;
		int num_cycles = 0;
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			
			num_cycles = num_cycles + 1; 
			num_instruct = num_instruct + 1;
			
		}

		Statistics.setNumberOfCycles(num_cycles);
		Statistics.setCPI(num_instruct, num_cycles);

		Statistics.setNumberOfInstructions(num_instruct);
		Statistics.setIPC(num_instruct, num_cycles);
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}