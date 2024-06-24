package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	int ld_res;
	int alu_res;
	boolean enable_RW;
	Instruction instruction;
	boolean NOperations;
	
	public MA_RW_LatchType()
	{
		enable_RW = false;
		NOperations = false;
	}

	public boolean isRW_enable() {
		return enable_RW;
	}

	public void setRW_enable(boolean rW_enable) {
		enable_RW = rW_enable;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction inst) {
		instruction = inst;
	}

	public void setldResult(int result) {
		ld_res = result;
	}

	public int getldResult() {
		return ld_res;
	}

	public int getaluResult() {
		return alu_res;
	}

	public void setaluResult(int result) {
		alu_res = result;
	}
	
	public boolean getIsNop() {
		return NOperations;
	}
	
	public void setIsNop(boolean isNop) {
		NOperations = isNop;
	}
}
