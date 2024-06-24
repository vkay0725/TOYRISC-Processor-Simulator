package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
    Processor containingProcessor;
    MA_RW_LatchType MA_RW_Latch;
    IF_EnableLatchType IF_EnableLatch;
    IF_OF_LatchType IF_OF_Latch;
    OF_EX_LatchType OF_EX_Latch;
    EX_MA_LatchType EX_MA_Latch;

    public void performRW() {
    
        if (MA_RW_Latch.enabledRWis()) {
            if (!MA_RW_Latch.nopTask) {
                int alu_output = MA_RW_Latch.resultALUget();
                Instruction commandinstruc = MA_RW_Latch.getInstruc();
                OperationType OPtype = commandinstruc.getOperationType();
                if (OPtype == OperationType.end) {
                    Simulator.setSimulationComplete(true); IF_EnableLatch.setFetchStageEnable(false);
                } else if (OPtype == OperationType.store || OPtype == OperationType.nop || OPtype == OperationType.beq || OPtype == OperationType.blt || OPtype == OperationType.jmp || OPtype == OperationType.bgt || OPtype == OperationType.bne) {
                } else if (OPtype == OperationType.load) {
                    int DOP = commandinstruc.getDestinationOperand().getValue();containingProcessor.getRegisterFile().setValue(DOP, alu_output);
                } else {
                    int DOP = commandinstruc.getDestinationOperand().getValue();containingProcessor.getRegisterFile().setValue(DOP, alu_output);
                }MA_RW_Latch.setEnabledRW(false);
            }
        }
    }
    public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType ifOfLatchType, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch) {
        this.MA_RW_Latch = mA_RW_Latch;
        this.OF_EX_Latch = oF_EX_Latch;
        this.containingProcessor = containingProcessor;
        this.EX_MA_Latch = eX_MA_Latch;
        this.IF_EnableLatch = iF_EnableLatch;
        this.IF_OF_Latch = ifOfLatchType;
    }
}
