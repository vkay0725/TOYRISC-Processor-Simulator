package processor.pipeline;

import generic.*;
import generic.Instruction.OperationType;
import processor.Clock;
import processor.Processor;
import processor.memorysystem.Cache;
import generic.Event.EventType;

public class MemoryAccess implements Element{
    Processor containingProcessor;
    EX_MA_LatchType EX_MA_Latch;
    MA_RW_LatchType MA_RW_Latch;
    IF_OF_LatchType IF_OF_Latch;
    OF_EX_LatchType OF_EX_Latch;

    Cache cache;

   
       

    public void performMA() {
        
        if(EX_MA_Latch.isEnableMALatch() && !EX_MA_Latch.occupiedMA()) {
            if(EX_MA_Latch.noOpInstructionSet) {
                MA_RW_Latch.nopTask = true;
            }
            else {
                MA_RW_Latch.PCounter = EX_MA_Latch.currentInstructionPointer;
                int ALU_output = EX_MA_Latch.obtainALUResult();
                MA_RW_Latch.nopTask = false;
                Instruction instruction = EX_MA_Latch.getInstruction();
                MA_RW_Latch.setInstruc(instruction);
                MA_RW_Latch.ALUupdate(ALU_output);
                
                if (instruction != null) {

                    OperationType Operation_Type = instruction.getOperationType();
                    if (Operation_Type.toString().equals("load")) {
                        EX_MA_Latch.updateMAStatus(true);
                        Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime(),this,this.cache, ALU_output));
                        EX_MA_Latch.setEnableMALatch(false);
                    } else if (Operation_Type.toString().equals("store")) {
                        int SOP = containingProcessor.getRegisterFile().getValue(instruction.getSourceOperand1().getValue());
                        EX_MA_Latch.updateMAStatus(true);
                        Simulator.storeresp = Clock.getCurrentTime();
                        Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + this.cache.latency,this,this.cache,ALU_output,SOP) );
                        EX_MA_Latch.setEnableMALatch(false);
                    }
                    MA_RW_Latch.setInstruc(instruction);
                }
                else {
                }
            }
            EX_MA_Latch.setEnableMALatch(false);
            if(EX_MA_Latch.getInstruction().getOperationType().toString().equals("end")) {
                EX_MA_Latch.setEnableMALatch(false);
            }
            MA_RW_Latch.setEnabledRW(true);
        }
    }

    public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, Cache cache) {
        this.IF_OF_Latch = iF_OF_Latch;
        this.cache = cache;
        this.EX_MA_Latch = eX_MA_Latch;
        this.MA_RW_Latch = mA_RW_Latch;
        this.containingProcessor = containingProcessor;
        this.OF_EX_Latch = oF_EX_Latch;}

    @Override
    public void handleEvent(Event e) {
        if(e.getEventType() == EventType.MemoryResponse) {
            MemoryResponseEvent event = (MemoryResponseEvent) e ;
            MA_RW_Latch.ALUupdate(event.getValue());
            MA_RW_Latch.PCounter = EX_MA_Latch.currentInstructionPointer;
            MA_RW_Latch.setEnabledRW(true);
            EX_MA_Latch.updateMAStatus(false);
        }
        else {
            EX_MA_Latch.updateMAStatus(false);
        }
    }
}
