package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType { // Holds data passed from Execute to MemoryAccess stage
	int ALU_RES;
	boolean MA_enable;
	
	boolean noperations;
	Instruction instruction;

	public EX_MA_LatchType() // Constructor
	{
		MA_enable = false;
		noperations = false;
	}

	public boolean isMA_enable() { // Getter for Memory Access enable flag
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) { // Setter for Memory Access enable flag
		MA_enable = mA_enable;
	}

	public Instruction getInstruction() { // Getter for instruction
		return instruction;
	}

	public void setInstruction(Instruction inst) { // Setter for instruction
		instruction = inst;
	}

	public int getaluResult() { // Getter for ALU result
		return ALU_RES;
	}

	public void setALU_result(int result) { // Setter for ALU result
		ALU_RES = result;
	}

	public void setIsNop(boolean isNop) { // Setter for no-operation flag
		noperations = isNop;
	}
	public boolean getIsNop() { // Getter for no-operation flag
		return noperations;
	}
	
	


}
