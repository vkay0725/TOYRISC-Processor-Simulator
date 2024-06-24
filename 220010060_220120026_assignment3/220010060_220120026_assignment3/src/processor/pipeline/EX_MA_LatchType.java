package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	int aluResult;
	boolean MA_enable;
	Instruction instruction;

	public boolean isMA_enable() {
		return MA_enable;
	}
	
	public Instruction getInstruction()
	{
		return instruction;
	}

	public int getaluResult()
	{
		return aluResult;
	}

	public EX_MA_LatchType(boolean mA_enable, int aLuResult) 
	{
		MA_enable = mA_enable;
		this.aluResult = aLuResult;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	
	
	public EX_MA_LatchType(boolean mA_enable)
	{
		MA_enable = mA_enable;
	}
	public void setaluResult(int aLuResult)
	{
		aluResult = aLuResult;
	}

	public EX_MA_LatchType()
	{
		MA_enable = false;
	}	
	
	public void setInstruction(Instruction iNstruction)
	{
		instruction = iNstruction;
	}

	
	public EX_MA_LatchType(boolean mA_enable, int aLuResult, Instruction instruction) 
	{
		MA_enable = mA_enable;
		this.instruction = instruction;
		this.aluResult = aLuResult;
	}
}