package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	IF_OF_LatchType IF_OF_Latch;
	Processor PROC;
	IF_EnableLatchType IF_EnableLatch;
	EX_IF_LatchType EX_IF_Latch;
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{
			if(EX_IF_Latch.getIsBranch_enable()){
				EX_IF_Latch.setIsBranch_enable(false);
				int new_PC = EX_IF_Latch.getPC();
				PROC.getRegisterFile().setProgramCounter(new_PC);
				
			}
			int currentPC = PROC.getRegisterFile().getProgramCounter();
			int newInstruction = PROC.getMainMemory().getWord(currentPC); IF_OF_Latch.setInstruction(newInstruction);
			PROC.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_enable(false);
		}
	}

	public InstructionFetch(Processor PROC, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{   this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.PROC = PROC;
		this.EX_IF_Latch = eX_IF_Latch;
	}
}
