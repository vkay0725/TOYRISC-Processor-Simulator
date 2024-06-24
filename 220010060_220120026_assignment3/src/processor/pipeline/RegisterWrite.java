package processor.pipeline;

import generic.Instruction;
import generic.Simulator;
import generic.Instruction.OperationType;
import processor.Processor;

public class RegisterWrite {
	
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Processor PROCS;
	
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			Instruction instruction = MA_RW_Latch.getInstruction();
			String op = instruction.getOperationType().toString();
			if(op.equals("end")){
				Simulator.setSimulationComplete(true);
			}
			else if(op.equals("load")){
				int ldResult = MA_RW_Latch.getldResult();
				int rd = instruction.getDestinationOperand().getValue();
				PROCS.getRegisterFile().setValue(rd, ldResult);
			}else if(op.equals("store")||op.equals("jmp")||op.equals("beq")||op.equals("blt")||op.equals("bne")||op.equals("bgt")){
				
			}else{
				int rd = instruction.getDestinationOperand().getValue();
				int aluResult = MA_RW_Latch.getaluResult();
				
				PROCS.getRegisterFile().setValue(rd, aluResult);
			}
			
			IF_EnableLatch.setIF_enable(true);
			MA_RW_Latch.setRW_enable(false);
			
		}
	}
	public RegisterWrite(Processor PROCS, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.PROCS = PROCS;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

}