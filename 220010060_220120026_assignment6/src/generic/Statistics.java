package generic;

import java.io.PrintWriter;

public class Statistics {

    // TODO add your statistics here
    static int Instructionnumber; static float CPI;
    static int numbCycles; static float IPC;

    public void setIPC() {
        Statistics.IPC = (float)Instructionnumber/(float)numbCycles;
    }
    
    public void setNumberOfCycles(int nCycles) {
        Statistics.numbCycles = nCycles;
    }
    public void setNumberOfInstructions(int nInstrs) {
        Statistics.Instructionnumber = nInstrs;
    }

    public void setCPI() {
        Statistics.CPI = (float)numbCycles/(float)Instructionnumber;
    }
    
    public static void printStatistics(String statFile)
    {
        try
        {
            PrintWriter writer = new PrintWriter(statFile);
           
            writer.println("IPC = " + IPC);
            writer.println("Number of instructions executed and cycles taken = " + Instructionnumber + " instructions and " + numbCycles + " cycles");
            

            // TODO add code here to print statistics in the output file

            writer.close();
        }
        catch(Exception e)
        {
            Misc.printErrorAndExit(e.getMessage());
        }
    }

    
}
