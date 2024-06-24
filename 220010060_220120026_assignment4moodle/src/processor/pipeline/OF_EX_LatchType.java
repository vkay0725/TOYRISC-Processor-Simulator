package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	boolean NOperations;
	boolean enable_EX;
	Instruction inst;
	
	public OF_EX_LatchType()
	{
		NOperations = false;
		enable_EX = false;
	}

	public boolean isEX_enable() {
		return enable_EX;
	}

	public void setEX_enable(boolean EX_enable) {
		enable_EX = EX_enable;
	}

	public void setInstruction(Instruction Inst) {
		this.inst = Inst;
	}

	public Instruction getInstruction() {
		return this.inst;
	}
	
	public boolean getIsNOP() {
		return NOperations;
	}
	
	public void setIsNOP(boolean is_NOP) {
		NOperations = is_NOP;
	}

}
