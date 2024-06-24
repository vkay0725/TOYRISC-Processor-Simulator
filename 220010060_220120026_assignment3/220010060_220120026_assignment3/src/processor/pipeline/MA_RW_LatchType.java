package processor.pipeline;

import generic.Instruction;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	Instruction instruction;
	int alu_Res;
	int ld_Res;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public MA_RW_LatchType(boolean rW_enable, Instruction instruction, int LdResult, int aLuResult)
	{    
		this.ld_Res = LdResult;
		RW_enable = rW_enable;
		this.instruction = instruction;
		this.alu_Res = aLuResult;
	}

	public void setldResult(int LdResult)
	{
		ld_Res = LdResult;
	}
	

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
    
	public void setaluResult(int aLuResult)
	{
		alu_Res = aLuResult;
	}
	
	public void setInstruction(Instruction iNstruction)
	{
		instruction = iNstruction;
	}

	public int getldResult()
	{
		return ld_Res;
	}

	public Instruction getInstruction()
	{
		return instruction;
	}

	
	public int getaluResult()
	{
		return alu_Res;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}
	public MA_RW_LatchType(boolean rW_enable)
	{
		RW_enable = rW_enable;
	}
}