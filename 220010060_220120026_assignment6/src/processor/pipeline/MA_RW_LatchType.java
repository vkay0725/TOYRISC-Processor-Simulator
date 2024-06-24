package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
    int resultALU;
    Instruction instruc;
    boolean RWbusyflagged;
    public boolean nopTask;
    public int PCounter;
    boolean enabledRW;
    int RWoccupiednope;
    int datamemory;

    
    public void ALUupdate(int output) {
        resultALU = output;
    }
    public int getLoad_Output() {
        return datamemory;
    }

   public void setRWbusyflagged(boolean RW_busy) {
        this.RWbusyflagged = RW_busy;
    }

    public void setEnabledRW(boolean rW_enable) {enabledRW = rW_enable;
    }

    public int resultALUget() {return resultALU;
    }
    public boolean isRWbusyflagged() {
        return RWbusyflagged;
    }
    public void setInstruc(Instruction commandinstuc) {instruc = commandinstuc;
    }
    public Instruction getInstruc() {
        return instruc;
    }

    public MA_RW_LatchType() {
        Instruction nop = new Instruction();
        nop.setOperationType(Instruction.OperationType.nop);
        resultALU = 0;
        enabledRW = false;RWbusyflagged = false;
        RWoccupiednope = 0;instruc = nop;
     
    }

    public boolean enabledRWis() {
        return enabledRW;
    }
    public void setLoad_Output(int output) {
        datamemory = output;
    }
 


}
