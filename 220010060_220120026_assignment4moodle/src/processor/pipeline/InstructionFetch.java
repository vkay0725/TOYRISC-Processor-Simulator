package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor PROC;
	IF_EnableLatchType IFEnableLatch;
	EX_IF_LatchType EX_IFLatch;
	IF_OF_LatchType IF_OFLatch;

	public void performIF() {
		if (IFEnableLatch.isIF_enable()) {
			if (EX_IFLatch.getIF_Enable()) {
				int newPC = EX_IFLatch.getPC();
				PROC.getRegisterFile().setProgramCounter(newPC);
				EX_IFLatch.setIF_Enable(false);
			}
			int currentPC = PROC.getRegisterFile().getProgramCounter();
			System.out.println("current program counter = " + currentPC);
			int newInstruction = PROC.getMainMemory().getWord(currentPC);
			IF_OFLatch.setInstruction(newInstruction);
			PROC.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_OFLatch.setOF_enable(true);
		}
	}
	

	public InstructionFetch(Processor PROC, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{   this.PROC = PROC; 
		this.IFEnableLatch = iF_EnableLatch;
		this.IF_OFLatch = iF_OF_Latch;
		this.EX_IFLatch = eX_IF_Latch;
	}

}

