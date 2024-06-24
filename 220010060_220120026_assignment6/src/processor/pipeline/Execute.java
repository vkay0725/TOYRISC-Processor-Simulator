package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import processor.Processor;

import java.util.Arrays;

public class Execute {
    Processor containingProcessor;
    OF_EX_LatchType OF_EX_Latch;
    EX_MA_LatchType EX_MA_Latch;
    EX_IF_LatchType EX_IF_Latch;
    IF_OF_LatchType IF_OF_Latch;
    MA_RW_LatchType MA_RW_Latch;
    IF_EnableLatchType IF_EnableLatch;

    

    public void performEX() {
        if (EX_MA_Latch.occupiedMA())
            OF_EX_Latch.markEXbusy(true);
        else
            OF_EX_Latch.markEXbusy(false);
        if (OF_EX_Latch.enabledExis() && !EX_MA_Latch.occupiedMA()) {
            boolean isitbranch = false;
            if (OF_EX_Latch.NOPisit) {
                EX_MA_Latch.noOpInstructionSet = true;
            } else {
                EX_MA_Latch.noOpInstructionSet = false;
                Instruction instrucCommand = OF_EX_Latch.getInstuc();
                EX_MA_Latch.setInstruction(instrucCommand);
                OperationType opTypeCmd = instrucCommand.getOperationType();
                int commandindices = Arrays.asList(OperationType.values()).indexOf(opTypeCmd);
                int pcnoww = containingProcessor.getRegisterFile().getProgramCounter() - 1;
                String opType = opTypeCmd.toString();
                boolean b = opType.equals("addi") || opType.equals("subi") || opType.equals("muli") || opType.equals("divi")
                || opType.equals("andi") || opType.equals("ori") || opType.equals("xori") || opType.equals("slti")
                || opType.equals("slli") || opType.equals("srli") || opType.equals("srai") || opType.equals("load");
                int resultALU = 0;

                if (opType.equals("add") || opType.equals("sub") || opType.equals("mul") || opType.equals("div")
					|| opType.equals("and") || opType.equals("or") || opType.equals("xor") || opType.equals("slt")
					|| opType.equals("sll") || opType.equals("srl") || opType.equals("sra")) {
				int op1 = containingProcessor.getRegisterFile().getValue(instrucCommand.getSourceOperand1().getValue());
				int op2 = containingProcessor.getRegisterFile().getValue(instrucCommand.getSourceOperand2().getValue());


				if (opType.equals("mul")) {
                    resultALU = op1 * op2;
                }  else if (opType.equals("xor")) {
                    resultALU = op1 ^ op2;
                } else if (opType.equals("srl")) {
                    resultALU = op1 >>> op2;
                }else if (opType.equals("add")) {
                    resultALU = op1 + op2;
                } else if (opType.equals("sub")) {
                    resultALU = op1 - op2;
                }  else if (opType.equals("and")) {
                    resultALU = op1 & op2;
                } else if (opType.equals("sra")) {
                    resultALU = op1 >> op2;
                } else if (opType.equals("or")) {
                    resultALU = op1 | op2;
                }else if (opType.equals("div")) {
                    resultALU = op1 / op2;
                    int remainder = op1 % op2;
                    containingProcessor.getRegisterFile().setValue(31, remainder);
                } else if (opType.equals("slt")) {
                    if (op1 < op2)
                        resultALU = 1;
                    else
                        resultALU = 0;
                } else {
                    
                }
                
			}else if (opTypeCmd.equals(OperationType.jmp)) {
                Operand.OperandType OPERNDTYPE = instrucCommand.getDestinationOperand().getOperandType();
                int immival = 0;
                if (OPERNDTYPE == Operand.OperandType.Register) {
                    immival = containingProcessor.getRegisterFile().getValue(
                            instrucCommand.getDestinationOperand().getValue());
                } else {
                    immival = instrucCommand.getDestinationOperand().getValue();
                }
                resultALU = immival;
                isitbranch = true;
            } 
             else if (opTypeCmd.equals(OperationType.store)) {
                    int destOperandValue = instrucCommand.getDestinationOperand().getValue();
                    int sourceOperand2Value = containingProcessor.getRegisterFile().getValue(instrucCommand.getSourceOperand2().getValue());
                    resultALU = destOperandValue + sourceOperand2Value;
                }
                
                else if (b) {
                    int i = instrucCommand.getSourceOperand1().getValue();
                    
                    int op2 = instrucCommand.getSourceOperand2().getValue();
                    int op1 = containingProcessor.getRegisterFile().getValue(i);
    
                    if (opType.equals("xori")) {
                        resultALU = op1 ^ op2;
                    } else if (opType.equals("load")) {
                        resultALU = op1 + op2;
                    } else if (opType.equals("divi")) {
                        resultALU = op1 / op2;
                        int remainder = op1 % op2;
                        containingProcessor.getRegisterFile().setValue(31, remainder);
                    } else if (opType.equals("srai")) {
                        resultALU = op1 >> op2;
                    } else if (opType.equals("ori")) {
                        resultALU = op1 | op2;
                    } else if (opType.equals("srli")) {
                        resultALU = op1 >>> op2;
                    } else if (opType.equals("subi")) {
                        resultALU = op1 - op2;
                    } else if (opType.equals("addi")) {
                        resultALU = op1 + op2;
                    } else if (opType.equals("slli")) {
                        resultALU = op1 << op2;
                    } else if (opType.equals("muli")) {
                        resultALU = op1 * op2;
                    } else if (opType.equals("slti")) {
                        if (op1 < op2)
                            resultALU = 1;
                        else
                            resultALU = 0;
                    } else if (opType.equals("andi")) {
                        resultALU = op1 & op2;
                    } else {
            
                    }
                    
                } 
            
                
                
                else if (opTypeCmd.equals(OperationType.beq)||opTypeCmd.equals(OperationType.bgt)||opTypeCmd.equals(OperationType.bne)||opTypeCmd.equals(OperationType.blt)) {
                    int sourceOperand1Value = containingProcessor.getRegisterFile().getValue(instrucCommand.getSourceOperand1().getValue());
                    int sourceOperand2Value = containingProcessor.getRegisterFile().getValue(instrucCommand.getSourceOperand2().getValue());
                    int destOperandValue = instrucCommand.getDestinationOperand().getValue();
                    if (opTypeCmd.equals(OperationType.bne)) {
                        if (sourceOperand1Value != sourceOperand2Value) {
                            resultALU = destOperandValue;
                            isitbranch = true;
                        }
                    } else if (opTypeCmd.equals(OperationType.blt)) {
                        if (sourceOperand1Value < sourceOperand2Value) {
                            resultALU = destOperandValue;
                            isitbranch = true;
                        }
                    } else if (opTypeCmd.equals(OperationType.beq)) {
                        if (sourceOperand1Value == sourceOperand2Value) {
                            resultALU = destOperandValue;
                            isitbranch = true;
                        }
                    } else if (opTypeCmd.equals(OperationType.bgt)) {
                        if (sourceOperand1Value > sourceOperand2Value) {
                            resultALU = destOperandValue;
                            isitbranch = true;
                        }
                    } else {
                        
                    }
                    
                }
                if (isitbranch) {
                    EX_IF_Latch.Branch = true;

                    EX_IF_Latch.disable = resultALU - 1;
                    IF_EnableLatch.setFetchStageEnable(true);
                    IF_OF_Latch.setEnabledOF(false);
                    Instruction nop = new Instruction();
                    nop.setOperationType(OperationType.nop);
                    OF_EX_Latch.updatesinstuc(nop);
                }
                EX_MA_Latch.updateALUResult(resultALU);
                EX_MA_Latch.currentInstructionPointer = OF_EX_Latch.PCounter;
                if (OF_EX_Latch.getInstuc().getOperationType().toString().equals("end")) {
                    OF_EX_Latch.doEXenable(false);
                }
            }
            OF_EX_Latch.doEXenable(false);
            EX_MA_Latch.setEnableMALatch(true);
          
        }
    }

    public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
            EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType iF_OF_Latch, MA_RW_LatchType mA_RW_Latch,
            IF_EnableLatchType iF_EnableLatch) {
       
        this.OF_EX_Latch = oF_EX_Latch;
        this.EX_IF_Latch = eX_IF_Latch;
        this.containingProcessor = containingProcessor;
        this.IF_OF_Latch = iF_OF_Latch;
        this.IF_EnableLatch = iF_EnableLatch;
        this.MA_RW_Latch = mA_RW_Latch;
        this.EX_MA_Latch = eX_MA_Latch;
        
    }
}
