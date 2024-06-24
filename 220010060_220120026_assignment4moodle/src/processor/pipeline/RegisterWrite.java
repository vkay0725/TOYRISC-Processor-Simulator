package processor.pipeline;

import generic.Instruction;
import generic.Simulator;
import generic.Statistics;
import processor.Processor;

public class RegisterWrite {
	Processor PROC;
	MA_RW_LatchType MA_RWLatch;
	IF_EnableLatchType EnableLatch_IF;
	
	public void performRW()
	{
		if(MA_RWLatch.getIsNop()){
			MA_RWLatch.setIsNop(false);
		}
		else if(MA_RWLatch.isRW_enable())
		{
			Instruction instruction = MA_RWLatch.getInstruction();
			String op = instruction.getOperationType().toString();
			Statistics.setnumberOfRegisterWriteInstructions(Statistics.getNumberOfRegisterWriteInstructions() + 1);
			
			if(op.equals("load")){
				int ldResult = MA_RWLatch.getldResult();
				int rd = instruction.getDestinationOperand().getValue();
				PROC.getRegisterFile().setValue(rd, ldResult);
			}else if(op.equals("store")||op.equals("jmp")||op.equals("beq")||op.equals("blt")||op.equals("bne")||op.equals("bgt")){
				
			}else if(op.equals("end")){
				Simulator.setSimulationComplete(true);
			}else{
				int aluResult = MA_RWLatch.getaluResult();
				int rd = instruction.getDestinationOperand().getValue();
				PROC.getRegisterFile().setValue(rd, aluResult);
			}
			
			
			
			MA_RWLatch.setRW_enable(false);
			if(!op.equals("end"))
			EnableLatch_IF.setIF_enable(true);
		}
	}

	public RegisterWrite(Processor PROCS, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.PROC = PROCS;
		this.MA_RWLatch = mA_RW_Latch;
		this.EnableLatch_IF = iF_EnableLatch;
	}

}