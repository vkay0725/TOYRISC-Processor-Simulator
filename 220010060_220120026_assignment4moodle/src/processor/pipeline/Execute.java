package processor.pipeline;
import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand.OperandType;
import generic.Statistics;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType iF_OF_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public void performEX() {
		boolean isBranch = false;
		if (OF_EX_Latch.getIsNOP()) {
			EX_MA_Latch.setIsNop(true);
			OF_EX_Latch.setIsNOP(false);
			EX_MA_Latch.setInstruction(null);
		} else if (OF_EX_Latch.isEX_enable()) {
			Instruction instruction = OF_EX_Latch.getInstruction();
			EX_MA_Latch.setInstruction(instruction);
			OperationType op_type = instruction.getOperationType();
			String opType = op_type.toString();
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;
			boolean b = opType.equals("addi") || opType.equals("subi") || opType.equals("muli") || opType.equals("divi") || opType.equals("andi") || opType.equals("ori") || opType.equals("xori") || opType.equals("slti") || opType.equals("slli") || opType.equals("srli") || opType.equals("srai") || opType.equals("load");
			if (op_type.equals(OperationType.beq) || op_type.equals(OperationType.blt) ||op_type.equals(OperationType.bgt) || op_type.equals(OperationType.bne) || op_type.equals(OperationType.jmp) || op_type.equals(OperationType.end)) {
				Statistics.setNumberOfBranchTaken(Statistics.getNumberOfBranchTaken() + 2);
				IF_EnableLatch.setIF_enable(false);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(false);
			}
			
			int alu_result = 0;

			if (opType.equals("add") || opType.equals("sub") || opType.equals("mul") || opType.equals("div") || opType.equals("and") || opType.equals("or") || opType.equals("xor") || opType.equals("slt") || opType.equals("sll") || opType.equals("srl") || opType.equals("sra")) {
				int op1 = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
				int op2 = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand2().getValue());
				
				 if (opType.equals("sub")) {
					alu_result = op1 - op2;
				} else if (opType.equals("add")) {
					alu_result = op1 + op2;
				} 
				else if (opType.equals("mul")) {
					alu_result = op1 * op2;
				} else if (opType.equals("or")) {
					alu_result = op1 | op2;
				} else if (opType.equals("xor")) {
					alu_result = op1 ^ op2;
				}
				else if (opType.equals("div")) {
					alu_result = op1 / op2;
					int remainder = op1 % op2;
					containingProcessor.getRegisterFile().setValue(31, remainder);
				} else if (opType.equals("and")) {
					alu_result = op1 & op2;
				}  else if (opType.equals("slt")) {
					if (op1 < op2)
						alu_result = 1;
					else
						alu_result = 0;
				} else if (opType.equals("sll")) {
					alu_result = op1 << op2;
				} else if (opType.equals("srl")) {
					alu_result = op1 >>> op2;
				} else if (opType.equals("sra")) {
					alu_result = op1 >> op2;
				}
			}

			else if (op_type.equals(OperationType.jmp)) {
				OperandType optype = instruction.getDestinationOperand().getOperandType();
				int imm = 0;
				if (optype == OperandType.Register) {
					imm = containingProcessor.getRegisterFile()
							.getValue(instruction.getDestinationOperand().getValue());
				} else {
					imm = instruction.getDestinationOperand().getValue();
				}
				alu_result = imm + currentPC;
				EX_IF_Latch.setIF_Enable(true, alu_result);
			} 
			
					
			else if (b) {
				int i = instruction.getSourceOperand1().getValue();
				int op1 = containingProcessor.getRegisterFile().getValue(i);
				int op2 = instruction.getSourceOperand2().getValue();
			
				if (opType.equals("addi")) {
					alu_result = op1 + op2;
				} else if (opType.equals("subi")) {
					alu_result = op1 - op2;
				} else if (opType.equals("muli")) {
					alu_result = op1 * op2;
				} else if (opType.equals("divi")) {
					alu_result = op1 / op2;
					int remainder = op1 % op2;
					containingProcessor.getRegisterFile().setValue(31, remainder);
				}  else if (opType.equals("ori")) {
					alu_result = op1 | op2;
				} else if (opType.equals("xori")) {
					alu_result = op1 ^ op2;
				} else if (opType.equals("slli")) {
					alu_result = op1 << op2;
				} else if (opType.equals("srli")) {
					alu_result = op1 >>> op2;
				}
				else if (opType.equals("andi")) {
					alu_result = op1 & op2;
				}else if (opType.equals("slti")) {
					if (op1 < op2)
						alu_result = 1;
					else
						alu_result = 0;
				}  else if (opType.equals("srai")) {
					alu_result = op1 >> op2;
				} else if (opType.equals("load")) {
					alu_result = op1 + op2;
				}
			}
			
				
				 else if (op_type.equals(OperationType.store)) {
				int op1 = containingProcessor.getRegisterFile()
						.getValue(instruction.getDestinationOperand().getValue());
				int op2 = instruction.getSourceOperand2().getValue();
				alu_result = op1 + op2;
			} else if (op_type.equals(OperationType.beq) || op_type.equals(OperationType.bne) || op_type.equals(OperationType.blt) || op_type.equals(OperationType.bgt)) {
				int op1 = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
				int op2 = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand2().getValue());
				int imm = instruction.getDestinationOperand().getValue();
			
				if (op_type.equals(OperationType.beq)) {
					if (op1 == op2) {
						isBranch = true;
						alu_result = imm + currentPC;
						EX_IF_Latch.setIF_Enable(true, alu_result);
					}
				} else if (op_type.equals(OperationType.bne)) {
					if (op1 != op2) {
						isBranch = true;
						alu_result = imm + currentPC;
						EX_IF_Latch.setIF_Enable(true, alu_result);
					}
				} else if (op_type.equals(OperationType.blt)) {
					if (op1 < op2) {
						isBranch = true;
						alu_result = imm + currentPC;
						EX_IF_Latch.setIF_Enable(true, alu_result);
					}
				} else if (op_type.equals(OperationType.bgt)) {
					if (op1 > op2) {
						isBranch = true;
						alu_result = imm + currentPC;
						EX_IF_Latch.setIF_Enable(true, alu_result);
					}
				}
			}
			
			EX_MA_Latch.setALU_result(alu_result);
			if(isBranch==true){
				
				IF_OF_Latch.setIsNOP(true);
			}
			EX_MA_Latch.setMA_enable(true);
		}
	}
}