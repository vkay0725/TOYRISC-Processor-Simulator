package processor.pipeline;
import generic.Instruction;
import generic.Operand.OperandType;
import processor.Processor;

public class Execute {
	Processor PROCSS;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	private boolean b;
	
	
	public void performEX() {
			boolean jmpRes = false;
			
			if(OF_EX_Latch.isEX_enable()) {
				Instruction instruction = OF_EX_Latch.getInstruction();
				EX_MA_Latch.setInstruction(instruction);
				String opType = instruction.getOperationType().toString();
				int nowPc = PROCSS.getRegisterFile().programCounter -1;
				
				int aluResult = 0;
				b = opType.equals("addi")   || opType.equals("divi") || opType.equals("andi") || opType.equals("load") || opType.equals("xori") || opType.equals("slti") || opType.equals("slli") || opType.equals("muli")|| opType.equals("srli") || opType.equals("srai") || opType.equals("ori")  || opType.equals("subi") || opType.equals("store");
				
				if(b) {
					int rs1 = PROCSS.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
					int immed = instruction.getSourceOperand2().getValue();
					int rd = PROCSS.getRegisterFile().getValue(instruction.getDestinationOperand().getValue());
					
					if(opType.equals("addi")) {
						aluResult = rs1 + immed;
					} else if(opType.equals("subi")) {
						aluResult = rs1 - immed;
					} else if(opType.equals("muli")) {
						aluResult = rs1 * immed;
					}else if(opType.equals("ori")) {
						aluResult = rs1 | immed;
					} else if(opType.equals("divi")) {
						aluResult = rs1 / immed; PROCSS.getRegisterFile().setValue(31, rs1%immed);
					}else if(opType.equals("slli")) { 
						aluResult = rs1 << immed;
					} else if(opType.equals("srli")) { 
						aluResult = rs1 >>> immed;
					} else if(opType.equals("srai")) { 
						aluResult = rs1 >> immed;
					} else if(opType.equals("load")) {
						aluResult = rs1 + immed;
					}  else if(opType.equals("andi")) {
						aluResult = rs1 & immed;
					}  else if(opType.equals("xori")) {
						aluResult = rs1 ^ immed;
					} else if(opType.equals("slti")) {
						aluResult = 0; if(immed > rs1) aluResult = 1;
					} else if(opType.equals("store")) {
						aluResult = rd + immed;
					} else {
						System.out.print("Issue detected ");
					}
				}else if(opType.equals("jmp")) {
					int immed = 0;
					OperandType jmpType = instruction.getDestinationOperand().getOperandType();
					
					if(jmpType == OperandType.Immediate)
						immed = instruction.getDestinationOperand().getValue();
					else
						immed = PROCSS.getRegisterFile().getValue(instruction.getDestinationOperand().getValue());
					aluResult = immed + nowPc ;
					jmpRes = true;
					EX_IF_Latch.setIsBranch_enable(true, aluResult);
				} else if(opType.equals("add") || opType.equals("sub")  ||  opType.equals("and")  || opType.equals("xor")|| opType.equals("mul") || opType.equals("slt") || opType.equals("sll") || opType.equals("div") || opType.equals("srl") || opType.equals("or")|| opType.equals("sra")) {
					
					int rs2 = PROCSS.getRegisterFile().getValue(instruction.getSourceOperand2().getValue());
					int rs1 = PROCSS.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
					if(opType.equals("add")) {
						aluResult = rs1 + rs2;
					}else if(opType.equals("or")) {
						aluResult = rs1 | rs2;
					} else if(opType.equals("xor")) {
						aluResult = rs1 ^ rs2;
					} else if(opType.equals("slt")) {
						aluResult = 0; if(rs2 > rs1) aluResult = 1;
					} else if(opType.equals("sub")) {
						aluResult = rs1 - rs2;
					} else if(opType.equals("mul")) {
						aluResult = rs1 * rs2;
					} else if(opType.equals("sll")) {
						aluResult = rs1 << rs2;
					} else if(opType.equals("div")) {
						aluResult = rs1 / rs2; PROCSS.getRegisterFile().setValue(31, rs1%rs2);
					} else if(opType.equals("and")) { 
						aluResult = rs1 & rs2;
					}  else if(opType.equals("srl")) {
						aluResult = rs1 >>> rs2;
					} else if(opType.equals("sra")) {
						aluResult = rs1 >> rs2;
					} else {
						System.out.print("Issue detected");
					}
				}  else if(opType.equals("end")) {
					//end
				} else {
					int rd = PROCSS.getRegisterFile().getValue(instruction.getSourceOperand2().getValue());
					int immed = instruction.getDestinationOperand().getValue();
					int rs1 = PROCSS.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());

					if(opType.equals("beq")) {
						if(rs1 == rd) {
							aluResult = nowPc + immed; jmpRes = true; EX_IF_Latch.setIsBranch_enable(true, aluResult);
						}
					} else if(opType.equals("bne")) { 
						if(rs1 != rd) {
							aluResult = nowPc + immed; jmpRes = true; EX_IF_Latch.setIsBranch_enable(true, aluResult);
						}
					}  else if(opType.equals("blt")) { 
						if(rs1 < rd) {
							aluResult = nowPc + immed; jmpRes = true; EX_IF_Latch.setIsBranch_enable(true, aluResult);
						}
					} else if(opType.equals("bgt")) { 
						if(rs1 > rd) {
							aluResult = nowPc + immed; jmpRes = true; EX_IF_Latch.setIsBranch_enable(true, aluResult);
						}
						
					} else {
						System.out.print("Issue detected");
					}
				}
				EX_MA_Latch.setaluResult(aluResult);
			}
			OF_EX_Latch.setEX_enable(false);
			if(!jmpRes) {
				EX_MA_Latch.setMA_enable(true);
			}
		}
		public Execute(Processor PROCSS, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
		{   this.EX_MA_Latch = eX_MA_Latch;
			this.PROCSS = PROCSS;
			this.OF_EX_Latch = oF_EX_Latch;
			this.EX_IF_Latch = eX_IF_Latch;
		}
		
		

	}