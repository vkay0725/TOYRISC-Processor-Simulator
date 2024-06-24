package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;

public class MemoryAccess {
	Processor PROC;
	EX_MA_LatchType EX_MALatch;
	MA_RW_LatchType MA_RWLatch;
	IF_EnableLatchType EnableLatch_IF;

	public void performMA() {
		if (EX_MALatch.getIsNop()) {
			MA_RWLatch.setIsNop(true);
			MA_RWLatch.setInstruction(null);
			EX_MALatch.setIsNop(false);
		} else if (EX_MALatch.isMA_enable()) {
			int alu_result = EX_MALatch.getaluResult();
			Instruction inst = EX_MALatch.getInstruction();
			
			System.out.println("MA enabled: " + inst);
			MA_RWLatch.setaluResult(alu_result);
			OperationType op_type = inst.getOperationType();
			
			if (op_type == OperationType.load) {
				int load_result = PROC.getMainMemory().getWord(alu_result);
				MA_RWLatch.setldResult(load_result);
			} else if (op_type == OperationType.store) {
				int val_store = PROC.getRegisterFile().getValue(inst.getSourceOperand1().getValue());
				PROC.getMainMemory().setWord(alu_result, val_store);
			}
			
			if (inst.getOperationType().ordinal() == 29) {
				EnableLatch_IF.setIF_enable(false);
			} 
			
			MA_RWLatch.setInstruction(inst);
			MA_RWLatch.setRW_enable(true);
		}
	}
	

	public MemoryAccess(Processor PROCS, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.PROC = PROCS;
		this.EX_MALatch = eX_MA_Latch;
		this.MA_RWLatch = mA_RW_Latch;
		this.EnableLatch_IF = iF_EnableLatch;
	}


}
