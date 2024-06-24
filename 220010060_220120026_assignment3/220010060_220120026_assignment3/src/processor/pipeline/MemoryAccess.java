package processor.pipeline;

import generic.Instruction;
import processor.Processor;

public class MemoryAccess {
	Processor procss;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable()){
			Instruction instruction = EX_MA_Latch.getInstruction();
			int ALU_Result = EX_MA_Latch.getaluResult();
			MA_RW_Latch.setaluResult(ALU_Result);
            String op = instruction.getOperationType().toString();
			if(op.equals("store")){
				int rs1 = instruction.getSourceOperand1().getValue(); int inp = procss.getRegisterFile().getValue(rs1);
				procss.getMainMemory().setWord(ALU_Result, inp);
			}else if(op.equals("load")){
				int ldResult = procss.getMainMemory().getWord(ALU_Result);
				MA_RW_Latch.setldResult(ldResult);
			}

			MA_RW_Latch.setInstruction(instruction);
			MA_RW_Latch.setRW_enable(true);
		}   EX_MA_Latch.setMA_enable(false);
	}

	public MemoryAccess(Processor procss, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{  this.EX_MA_Latch = eX_MA_Latch;
		this.procss = procss;
		this.MA_RW_Latch = mA_RW_Latch;
	}
}