package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Operand;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

    public char flip(char c) {
		if (c == '0') {
			return '1';
		} else {
			return '0';
		}
	}

	public void performOF() {
        if (IF_OF_Latch.isOF_enable()) {
            Instruction newInst = new Instruction();
            String newInstruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());
            int cSize = newInstruction.length();
            cSize = 32 - cSize;
            for (int i = 1; i <= cSize; i++)
                newInstruction = '0' + newInstruction;
            String opCode = newInstruction.substring(0, 5);
            OperationType[] opConv = OperationType.values();
            OperationType opInst = opConv[Integer.parseInt(opCode, 2)];
            newInst.setOperationType(opInst);

            if (opInst == OperationType.jmp) {
                Operand imme = new Operand();
                Operand rd = new Operand();
                imme.setOperandType(OperandType.Immediate);
                rd.setOperandType(OperandType.Register);
                rd.setValue(Integer.parseInt(newInstruction.substring(5, 10), 2));
                newInst.setSourceOperand1(rd);
                String immedi = newInstruction.substring(10, 32);
                imme.setValue(Integer.parseInt(immedi, 2));
                if (immedi.charAt(0) == '1') {
                    immedi = twosComplement(immedi);
                    imme.setValue(-1 * Integer.parseInt(immedi, 2));
                }

                newInst.setDestinationOperand(imme);
            }else if (opInst == OperationType.add || opInst == OperationType.sub || opInst == OperationType.mul || opInst == OperationType.div || opInst == OperationType.and || opInst == OperationType.or ||
                    opInst == OperationType.xor || opInst == OperationType.slt || opInst == OperationType.sll || opInst == OperationType.srl || opInst == OperationType.sra) {
                Operand rs1 = new Operand();
                Operand rs2 = new Operand();
                Operand rd = new Operand();
                rs2.setOperandType(OperandType.Register);
                rs1.setOperandType(OperandType.Register);
                rd.setOperandType(OperandType.Register);
                rs1.setValue(Integer.parseInt(newInstruction.substring(5, 10), 2));
                rs2.setValue(Integer.parseInt(newInstruction.substring(10, 15), 2));
                rd.setValue(Integer.parseInt(newInstruction.substring(15, 20), 2));
                newInst.setSourceOperand1(rs1);
                newInst.setSourceOperand2(rs2);
                newInst.setDestinationOperand(rd);
            } else if (opInst == OperationType.beq || opInst == OperationType.bgt || opInst == OperationType.blt ||
            opInst == OperationType.bne) {
        Operand rs1 = new Operand(); Operand rd = new Operand();  Operand imme = new Operand();
        rs1.setOperandType(OperandType.Register);
        rd.setOperandType(OperandType.Register);
        imme.setOperandType(OperandType.Immediate);
        rs1.setValue(Integer.parseInt(newInstruction.substring(5, 10), 2));
        rd.setValue(Integer.parseInt(newInstruction.substring(10, 15), 2));
        String immedi = newInstruction.substring(15, 32);
        imme.setValue(Integer.parseInt(immedi, 2));
        if (immedi.charAt(0) == '1') {
            immedi = twosComplement(immedi);
            imme.setValue(-1 * Integer.parseInt(immedi, 2));
        }
        newInst.setSourceOperand1(rs1);
        newInst.setSourceOperand2(rd);
        newInst.setDestinationOperand(imme);
    }else if (opInst == OperationType.addi  || opInst == OperationType.store || opInst == OperationType.muli ||
                    opInst == OperationType.ori || opInst == OperationType.slli || opInst == OperationType.andi || opInst == OperationType.slti ||
                    opInst == OperationType.srai  || opInst == OperationType.subi || opInst == OperationType.xori || opInst == OperationType.divi || opInst == OperationType.srli  ||
                    opInst == OperationType.load) {
                Operand rs1 = new Operand();
                Operand rs2 = new Operand();
                Operand rd = new Operand();
                rs2.setOperandType(OperandType.Register);
                rs1.setOperandType(OperandType.Register);
                String immed = "";
                rs1.setValue(Integer.parseInt(newInstruction.substring(5, 10), 2));
                rs2.setValue(Integer.parseInt(newInstruction.substring(10, 15), 2));
                immed = newInstruction.substring(15, 32);
                rd.setOperandType(OperandType.Immediate);
                if (immed.charAt(0) == '1') {
                    immed = twosComplement(immed);
                    rd.setValue(-1 * Integer.parseInt(immed, 2));
                } else
                    rd.setValue(Integer.parseInt(immed, 2));
                newInst.setSourceOperand1(rs1);
                newInst.setSourceOperand2(rd);
                newInst.setDestinationOperand(rs2);
            }else if (opInst == OperationType.end) {
                // Do nothing for end operation
            }
            OF_EX_Latch.setEX_enable(true);
            OF_EX_Latch.setInstruction(newInst);
            IF_OF_Latch.setOF_enable(false);
           
        }
    }

    public String twosComplement(String bin) {
        String twos = "";
        String ones = "";
        int p = 0;
        while (p < bin.length()) {
        ones += flip(bin.charAt(p));
        p=p+1;
        }


    StringBuilder builder = new StringBuilder(ones);
    boolean addExtra = false;

    int i = ones.length() - 1;
while (i > 0) {
if (ones.charAt(i) == '1') {
    builder.setCharAt(i, '0');
} else {
    builder.setCharAt(i, '1');
    addExtra = true;
    break;
}
i=i-1;
}


    if (addExtra == false)
        builder.append("1", 0, 7);

    twos = builder.toString();
    return twos;
}

}
