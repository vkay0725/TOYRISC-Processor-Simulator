package generic;
import java.nio.ByteBuffer;
import processor.Clock;
import processor.Processor;
import java.io.IOException;
import processor.memorysystem.MainMemory;
import java.io.InputStream;
import processor.pipeline.RegisterFile;
import java.io.FileInputStream;



public class Simulator {
    static boolean simulationComplete;
    static Processor processor;
    public static long storeresp;
    static EventQueue eventQueue;
    public static int ins_count;

   

    static void loadProgram(String assemblyProgramFile) {
        
        try (
                InputStream inputStream = new FileInputStream(assemblyProgramFile);
        ) { int i = 0; int j = 0; int pc = 0;
            MainMemory MainMemory = new MainMemory();
            RegisterFile RegisterFile = new RegisterFile();
            byte[] buffer = new byte[4];
            
            while (inputStream.read(buffer) != -1) {
                ByteBuffer wrapped = ByteBuffer.wrap(buffer);
                int number = wrapped.getInt();
                if (j != 0) {
                    MainMemory.setWord(i, number);
                    i = i+1;
                }
                if (j == 0) {
                    pc = number;
                }
                j =j+1;

            }
            processor.setMainMemory(MainMemory);

           
            RegisterFile.setProgramCounter(pc);

            RegisterFile.setValue(1, 65535);
            RegisterFile.setValue(0, 0);
            RegisterFile.setValue(2, 65535);

            processor.setRegisterFile(RegisterFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public static void simulate() {
        int cycles = 0;
        while (!Simulator.simulationComplete) {
            processor.getRWUnit().performRW();
            processor.getMAUnit().performMA();
            processor.getEXUnit().performEX();
            eventQueue.processEvents();
            processor.getOFUnit().performOF();
            processor.getIFUnit().performIF();
            Clock.incrementClock();
            cycles= cycles + 1;
            
        }
       
        Statistics stat = new Statistics();
        stat.setNumberOfCycles(cycles);
        stat.setNumberOfInstructions(ins_count);
        stat.setCPI();
        stat.setIPC();
    }

    public static EventQueue getEventQueue() {
        return eventQueue;
    }
    

    public static void setSimulationComplete(boolean value) {
        simulationComplete = value;
    }

    public static void setupSimulation(String assemblyProgramFile, Processor p) {
        Simulator.processor = p;
        loadProgram(assemblyProgramFile);
        simulationComplete = false;
        eventQueue = new EventQueue();
        ins_count=0;
        storeresp=0;
    }
}