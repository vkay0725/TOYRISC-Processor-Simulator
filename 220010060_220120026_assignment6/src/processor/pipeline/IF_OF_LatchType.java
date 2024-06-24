package processor.pipeline;

public class IF_OF_LatchType {
    int instruction;
    int instructionPCounter;
    boolean occupiedfetch;
    boolean enabledOF;
    int obtainNOP;
    

    public int getInstruction() {
        return instruction;
    }
    public int getInstructionPCounter() {
        return instructionPCounter;
    }

    public void setEnabledOF(boolean enabledOFstage) {
        enabledOF = enabledOFstage;
    }

    public void setOccupiedfetch(boolean fetchBusy) {
        occupiedfetch = fetchBusy;
    }
    public void setInstructionPCounter(int pc) {
        this.instructionPCounter = pc;
    }


    public IF_OF_LatchType() {
        occupiedfetch = false;
        instruction = 0;
        enabledOF = false;
       
    }

    public void setInstruction(int instruction) {
        this.instruction = instruction;
    }
    public boolean isEnabledOF() {return enabledOF;
    }

    public boolean isOccupiedfetch() {
        return occupiedfetch;
    }

}
