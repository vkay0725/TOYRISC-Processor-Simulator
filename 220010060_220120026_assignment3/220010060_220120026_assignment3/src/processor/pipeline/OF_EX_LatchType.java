package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	
	boolean Is_EX_enab;
	Instruction instruction;
	
	public OF_EX_LatchType(boolean eX_enable) {  
		Is_EX_enab = eX_enable;
	}
	public Instruction getInstruction() { 
		return this.instruction;
	}

	public void setEX_enable(boolean eX_enable) { 
		Is_EX_enab = eX_enable;
	}

	public OF_EX_LatchType() {  
		Is_EX_enab = false;
	}
	public void setInstruction(Instruction instruction) { 
		this.instruction = instruction;
	}
	public boolean isEX_enable() { 
		return Is_EX_enab;
	}
	public OF_EX_LatchType(boolean eX_enable, Instruction instruction) {  
		Is_EX_enab = eX_enable;
		this.instruction = instruction;
	}
	
}