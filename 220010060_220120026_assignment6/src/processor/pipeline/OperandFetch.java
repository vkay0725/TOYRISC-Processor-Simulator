package processor.pipeline;

import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Operand.OperandType;
import processor.Processor;
public class OperandFetch {
    Processor containingProcessor;
    IF_EnableLatchType IF_EnableLatch;
    IF_OF_LatchType IF_OF_Latch;
    OF_EX_LatchType OF_EX_Latch;
    EX_MA_LatchType EX_MA_Latch;
    MA_RW_LatchType MA_RW_Latch;

    public static String twoscomplement(String inputBinary) {
        String twos = "";String ones = "";
        int flagbit = 0;
        
        while (flagbit < inputBinary.length()) {
            ones += opposite(inputBinary.charAt(flagbit));flagbit++;
        }
    
        boolean availableop = false;
        StringBuilder builder = new StringBuilder(ones);
        int i = ones.length() - 1;
        
       
        while (i >= 0) {
            if (ones.charAt(i) == '1') {
                builder.setCharAt(i, '0');
            } else {
                builder.setCharAt(i, '1');
                availableop = true;
                break;
            }
            i--;
        } 
        twos = builder.toString();return twos; }


    public void performOF() {
        if(OF_EX_Latch.checkoccupiedEXis()) IF_OF_Latch.setOccupiedfetch(true);
        else IF_OF_Latch.setOccupiedfetch(false);
     
        if(IF_OF_Latch.isEnabledOF() && !OF_EX_Latch.checkoccupiedEXis())
        {
            int inst = IF_OF_Latch.getInstruction();
            
            OperationType[] operationType = OperationType.values();
            String binaryinstruc = Integer.toBinaryString(inst);
            
           
            while (binaryinstruc.length() < 32) {
                binaryinstruc = "0" + binaryinstruc;
            }
            String opcode = binaryinstruc.substring(0, 5);
            
            int opcodei = Integer.parseInt(opcode, 2);
            
            OperationType operation = operationType[opcodei];
            
            Instruction instruc = new Instruction();
            Operand rs1;Operand rs2; Operand rd; String valueCreate; int registernum; int valueConstant;
			if (operation.toString().equals("end")) {
                instruc.setOperationType(operationType[opcodei]);
            } 
             else if (operation.toString().equals("beq")  || operation.toString().equals("bgt") || operation.toString().equals("blt") || operation.toString().equals("store")|| operation.toString().equals("bne") ) {
                rs1 = new Operand(); 
                rs1.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(5, 10), 2);
                rs1.setValue(registernum);
                rs2 = new Operand();
                rs2.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(10, 15), 2);
                rs2.setValue(registernum);
                rd = new Operand();
                rd.setOperandType(OperandType.Immediate);
                valueCreate = binaryinstruc.substring(15, 32);
                valueConstant = Integer.parseInt(valueCreate, 2);
                if (valueCreate.charAt(0) == '1') {
                    valueCreate = twoscomplement(valueCreate);
                    valueConstant = Integer.parseInt(valueCreate, 2) * -1;
                }
                rd.setValue(valueConstant);instruc.setOperationType(operationType[opcodei]);
                instruc.setSourceOperand1(rs1);
                instruc.setSourceOperand2(rs2);
                instruc.setDestinationOperand(rd);
            }
            
            
            else if (operation.toString().equals("jmp")) {
                Operand op = new Operand();
                valueCreate = binaryinstruc.substring(10, 32);
                valueConstant = Integer.parseInt(valueCreate, 2);
                if (valueCreate.charAt(0) == '1') {
                    valueCreate = twoscomplement(valueCreate);
                    valueConstant = Integer.parseInt(valueCreate, 2) * -1;
                }
                if (valueConstant != 0) {
                    op.setOperandType(OperandType.Immediate);
                    op.setValue(valueConstant);
                } else {
                    registernum = Integer.parseInt(binaryinstruc.substring(5, 10), 2);
                    op.setOperandType(OperandType.Register);
                    op.setValue(registernum);
                }
            
                instruc.setOperationType(operationType[opcodei]);
                instruc.setDestinationOperand(op);
            }
            
            
            else if (operation.toString().equals("or") || operation.toString().equals("add") || operation.toString().equals("and") ||operation.toString().equals("slt") ||  operation.toString().equals("div") || operation.toString().equals("srl")||   operation.toString().equals("sub") || operation.toString().equals("xor") || operation.toString().equals("mul") || operation.toString().equals("sll")||  operation.toString().equals("sra")) {
                rs1 = new Operand();
                rs1.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(5, 10), 2);
                rs1.setValue(registernum);
            
                rs2 = new Operand();
                rs2.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(10, 15), 2);
                rs2.setValue(registernum);
            
                rd = new Operand();
                rd.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(15, 20), 2);
                rd.setValue(registernum);
            
                instruc.setOperationType(operationType[opcodei]);
                instruc.setSourceOperand1(rs1);
                instruc.setSourceOperand2(rs2);
                instruc.setDestinationOperand(rd);
            }
            
            
            else if (operation.toString().equals("addi") || operation.toString().equals("xori") ||  operation.toString().equals("slti") || operation.toString().equals("slli") ||  operation.toString().equals("divi") || operation.toString().equals("srai") || operation.toString().equals("srli")|| operation.toString().equals("andi") ||  operation.toString().equals("subi") ||  operation.toString().equals("ori") || operation.toString().equals("load") ||operation.toString().equals("muli")) {
                
                rs1 = new Operand();
                rs1.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(5, 10), 2);
                rs1.setValue(registernum);
                rd = new Operand();
                rd.setOperandType(OperandType.Register);
                registernum = Integer.parseInt(binaryinstruc.substring(10, 15), 2);
                rd.setValue(registernum);
                rs2 = new Operand();
                rs2.setOperandType(OperandType.Immediate);
                valueCreate = binaryinstruc.substring(15, 32);
                valueConstant = Integer.parseInt(valueCreate, 2);
                if (valueCreate.charAt(0) == '1') {
                    valueCreate = twoscomplement(valueCreate);
                    valueConstant = Integer.parseInt(valueCreate, 2) * -1;
                }
                rs2.setValue(valueConstant);
            
                instruc.setOperationType(operationType[opcodei]);
                instruc.setSourceOperand1(rs1);
                instruc.setSourceOperand2(rs2);
                instruc.setDestinationOperand(rd);
            } 
            
            
            else {
                instruc.setOperationType(OperationType.nop);
            }
             OF_EX_Latch.NOPisit = false;
                OF_EX_Latch.updatesinstuc(instruc);
                OF_EX_Latch.PCounter = IF_OF_Latch.instructionPCounter;
                OF_EX_Latch.doEXenable(true);
                IF_EnableLatch.setFetchStageEnable(true);

            if(operation.toString().equals("end")) {
                IF_OF_Latch.setEnabledOF(false);
                IF_EnableLatch.setFetchStageEnable(false);
            }
            IF_OF_Latch.setEnabledOF(false); OF_EX_Latch.doEXenable(true);
        }
    }

    public OperandFetch(Processor containingProcessor,IF_OF_LatchType iF_OF_Latch,OF_EX_LatchType oF_EX_Latch,  EX_MA_LatchType eX_MA_Latch,  MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType if_enableLatch) {
        this.IF_EnableLatch = if_enableLatch;
     this.EX_MA_Latch = eX_MA_Latch;
        this.MA_RW_Latch = mA_RW_Latch;
        this.OF_EX_Latch = oF_EX_Latch;
        this.containingProcessor = containingProcessor;
        this.IF_OF_Latch = iF_OF_Latch;
        }

        private static char opposite(char c) {
            if (c == '0') {
                return '1';
            } else {
                return '0';
            }
        }

}
