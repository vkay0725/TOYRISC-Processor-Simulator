package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {

    boolean occupiedEX;
    Instruction instuc;
    boolean NOPisit;
    boolean enabledEX;
    public int PCounter;

    public void updatesinstuc(Instruction instuc) {
        this.instuc = instuc;
    }
    public void doEXenable(boolean flagenableeX) {
        enabledEX = flagenableeX;
    }

    public void markEXbusy(boolean occuEX) {
        occupiedEX = occuEX;
    }


    public OF_EX_LatchType() {
        Instruction nop = new Instruction();
        nop.setOperationType(Instruction.OperationType.nop);
        enabledEX = false;occupiedEX = false;
        instuc = nop;}
       
    public boolean checkoccupiedEXis() {
            return occupiedEX;
        }

        public boolean enabledExis() {
            return enabledEX;
        }

        public Instruction getInstuc() {
            return this.instuc;
        }
    
}
