package processor.pipeline;

public class IF_OF_LatchType {
    boolean enable_OF;
    boolean isNOP;
    int instruction;

    public IF_OF_LatchType() {
        enable_OF = false;
        isNOP = false;
    }

    public void setOF_enable(boolean enable_OF) {
        this.enable_OF = enable_OF;
    }

    public boolean isOF_enable() {
        return enable_OF;
    }

    public int getInstruction() {
        return instruction;
    }

    public void setInstruction(int instruction) {
        this.instruction = instruction;
    }

    public boolean getIsNOP() {
        return isNOP;
    }

    public void setIsNOP(boolean isNOP) {
        this.isNOP = isNOP;
    }
}

