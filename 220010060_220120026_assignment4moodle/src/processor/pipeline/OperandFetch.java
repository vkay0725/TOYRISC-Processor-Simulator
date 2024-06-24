package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Statistics;
import generic.Operand.OperandType;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public void performOF() {
		if (IF_OF_Latch.isOF_enable()) {
			Statistics.setNumberOfOFInstructions(Statistics.getNumberOfOFInstructions() + 1);
			OperationType[] operationType = OperationType.values();
			String instruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());
			System.out.println("OF is enabled with instruction: " + instruction + "..");
			while (instruction.length() != 32) {
				instruction = "0" + instruction;
			}
			String opcode = instruction.substring(0, 5);
			int type_operation = Integer.parseInt(opcode, 2);
			OperationType operation = operationType[type_operation];
			
			if (operation.ordinal() == 24 || operation.ordinal() == 25 || operation.ordinal() == 26 || operation.ordinal() == 27 || operation.ordinal() == 28 ) {
				IF_EnableLatch.setIF_enable(false);
			}
			
			boolean conflict_inst = false;
			Instruction instruction_ex_stage = OF_EX_Latch.getInstruction();
			Instruction instruction_ma_stage = EX_MA_Latch.getInstruction();
			Instruction instruction_rw_stage = MA_RW_Latch.getInstruction();
			Instruction inst = new Instruction();
			
			if (operation == OperationType.add || operation == OperationType.sub || operation == OperationType.mul || operation == OperationType.div || operation == OperationType.and || operation == OperationType.or || operation == OperationType.xor || operation == OperationType.slt || operation == OperationType.sll || operation == OperationType.srl || operation == OperationType.sra) {
				Operand rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				int registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
				rs1.setValue(registerNo);

				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				int registerNo2 = Integer.parseInt(instruction.substring(10, 15), 2);
				rs2.setValue(registerNo2);
				if (conflict(instruction_ex_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (conflict(instruction_ma_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (conflict(instruction_rw_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (conflict_inst) {
					this.handleConflictBubblePCModification();
				} else {
					Operand rd = new Operand();
					rd.setOperandType(OperandType.Register);
					registerNo = Integer.parseInt(instruction.substring(15, 20), 2);
					rd.setValue(registerNo);

					inst.setOperationType(operationType[type_operation]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
				}
			} else if (operation == OperationType.end) {
				inst.setOperationType(operationType[type_operation]);
				IF_EnableLatch.setIF_enable(false);
			} else if (operation == OperationType.jmp) {
				Operand op = new Operand();
				String imm = instruction.substring(10, 32);
				int imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				if (imm_val != 0) {
					op.setOperandType(OperandType.Immediate);
					op.setValue(imm_val);
				} else {
					int registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
					op.setOperandType(OperandType.Register);
					op.setValue(registerNo);
				}

				inst.setOperationType(operationType[type_operation]);
				inst.setDestinationOperand(op);
			} else if (operation == OperationType.beq || operation == OperationType.bne || operation == OperationType.blt || operation == OperationType.bgt) {
				int registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
				int registerNo2 = Integer.parseInt(instruction.substring(10, 15), 2);
				if (conflict(instruction_ex_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (conflict(instruction_ma_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (conflict(instruction_rw_stage, registerNo, registerNo2))
					conflict_inst = true;
				if (conflict_inst) {
					this.handleConflictBubblePCModification();
				} else {
					Operand rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs1.setValue(registerNo);
					
					Operand rs2 = new Operand();
					rs2.setOperandType(OperandType.Register);
					rs2.setValue(registerNo2);
					
					Operand rd = new Operand();
					rd.setOperandType(OperandType.Immediate);
					String imm = instruction.substring(15, 32);
					int imm_val = Integer.parseInt(imm, 2);
					if (imm.charAt(0) == '1') {
						imm = twosComplement(imm);
						imm_val = Integer.parseInt(imm, 2) * -1;
					}
					rd.setValue(imm_val);
					
					inst.setOperationType(operationType[type_operation]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
				}
			} else {
				int registerNo = Integer.parseInt(instruction.substring(5, 10), 2);
				if (conflict(instruction_ex_stage, registerNo, registerNo)) {
					conflict_inst = true;
				}	
				if (conflict(instruction_ma_stage, registerNo, registerNo)) {
					conflict_inst = true;
				}
				if (conflict(instruction_rw_stage, registerNo, registerNo)) {
					conflict_inst = true;
				}
					
				if (conflict_inst) {
					this.handleConflictBubblePCModification();
				} else {
					Operand rs1 = new Operand();
					rs1.setOperandType(OperandType.Register);
					rs1.setValue(registerNo);

					Operand rd = new Operand();
					rd.setOperandType(OperandType.Register);
					registerNo = Integer.parseInt(instruction.substring(10, 15), 2);
					rd.setValue(registerNo);

					Operand rs2 = new Operand();
					rs2.setOperandType(OperandType.Immediate);
					String imm = instruction.substring(15, 32);
					int imm_val = Integer.parseInt(imm, 2);
					if (imm.charAt(0) == '1') {
						imm = twosComplement(imm);
						imm_val = Integer.parseInt(imm, 2) * -1;
					}
					rs2.setValue(imm_val);
					
					inst.setOperationType(operationType[type_operation]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
				}
			}
			
			OF_EX_Latch.setInstruction(inst);
			OF_EX_Latch.setEX_enable(true);
		}
	}

	public static boolean conflict(Instruction instruction, int reg_1, int reg_2) {
		if (instruction == null || instruction.getOperationType() == null) {
			return false;
		}
	
		int instNumber = instruction.getOperationType().ordinal();
	
		if ((instNumber <= 21 && instNumber % 2 == 0) || (instNumber <= 21 && instNumber % 2 != 0) || instNumber == 22 || instNumber == 23) {
			int dest_reg = instruction.getDestinationOperand() != null ? instruction.getDestinationOperand().getValue() : -1;
			if (reg_1 == dest_reg || reg_2 == dest_reg) {
				return true;
			}
		} else if ((instNumber == 6 || instNumber == 7) && (reg_1 == 31 || reg_2 == 31)) {
			return true;
		}
		
		return false;
	}
	

	public static char flip(char bit) {
		if (bit == '0') {
			return '1';
		} else {
			return '0';
		}
	}
	
	public static String twosComplement(String bin) {
		String twos = "", ones = "";
		int df = 0;
		while (df < bin.length()) {
			ones += flip(bin.charAt(df));
			df++;
		}
	
		StringBuilder builder = new StringBuilder(ones);
		boolean b = false;
		int i = ones.length() - 1;
		while (i > 0) {
			if (ones.charAt(i) == '1') {
				builder.setCharAt(i, '0');
			} else {
				builder.setCharAt(i, '1');
				b = true;
				break;
			}
			i--;
		}
		if (!b) {
			builder.append("1", 0, 7);
		}
		twos = builder.toString();
		return twos;
	}
	
	public void handleConflictBubblePCModification () {
		System.out.println("Conflict Observed");
		IF_EnableLatch.setIF_enable(false);
		OF_EX_Latch.setIsNOP(true);
	}

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

}
