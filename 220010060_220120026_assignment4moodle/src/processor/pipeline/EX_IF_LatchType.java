package processor.pipeline;

public class EX_IF_LatchType {

	boolean enable;
	int PCounter;
	
	public EX_IF_LatchType()
	{
		enable = false;
	}

	public void setIF_Enable(boolean iS_enable, int newPC) {
		enable = iS_enable;
		PCounter = newPC;
	}

	public boolean getIF_Enable() {
		return enable;
	}

	public int getPC() {
		return PCounter;
	}

	public void setIF_Enable(boolean iS_enable) {
		enable = iS_enable;
	}

	

}
