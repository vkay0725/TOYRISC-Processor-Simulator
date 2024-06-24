package processor.pipeline;

public class RegisterFile {
    boolean freezed = false;
    int programCounter;
    int programIndex;
    int haltedpcounter;
    int[] registerFile;
   

    public void incrementProgramCounter() {
        this.programCounter++;
    }


    public boolean isFreezed() {
        return freezed;
    }

    public int haltedpc() {
        return haltedpcounter;
    }


    public RegisterFile() {
        registerFile = new int[32];
        registerFile[0] = 0;            
    }

    public void setValue(int registerNumber, int value) {
        registerFile[registerNumber] = value;
    }


    public String makestring() {
        String str = "";
        for(int i = 0; i < 32; i++) {
            str += "R" + i + ": " + registerFile[i] + "\n";
        }
        return str;
    }
    public int getValue(int registerNumber) {
        return registerFile[registerNumber];
    }


    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

   public void HALTIT(boolean freezed) {
        this.freezed = freezed;
    }
    
    public void haltedPC(int freezedprogramCounter) {
        this.haltedpcounter = freezedprogramCounter;
    }

    public int getProgramCounter() {
        return programCounter;
    }
    
}
