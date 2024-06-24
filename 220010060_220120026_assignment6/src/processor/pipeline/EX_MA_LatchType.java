package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
    boolean enableMALatch;
    Instruction instruction;
    boolean noOpInstructionSet;
    int checkflagstatus;
    boolean memoryAccessStage;
    public int currentInstructionPointer;
    int computedResult;

    public void updateMAStatus(boolean maInput) {
        this.memoryAccessStage = maInput;
    }

    public void setFlag(int flag) {
        this.checkflagstatus = flag;
    }
    public Instruction getInstruction() {
        return instruction;
    }
    public void updateALUResult(int result) {
        computedResult = result;
    }

    public boolean occupiedMA() {
        return memoryAccessStage;
    }
    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
    public int obtainALUResult() {
        return computedResult;
    }

   public boolean isEnableMALatch() {
        return enableMALatch;
    }

    public int getFlag() {
        return checkflagstatus;
    }
   
    public void setEnableMALatch(boolean malatchStatus) {
        enableMALatch = malatchStatus;
    }
    public EX_MA_LatchType() {
        Instruction nop = new Instruction();
        nop.setOperationType(Instruction.OperationType.nop);
        memoryAccessStage = false;computedResult = 0;
        currentInstructionPointer = -1;
        noOpInstructionSet = false;
        enableMALatch = false;checkflagstatus = 0;
    }
    

}
