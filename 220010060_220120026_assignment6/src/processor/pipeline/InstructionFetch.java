package processor.pipeline;

import generic.*;
import processor.Clock;
import processor.Processor;
import processor.memorysystem.Cache;

public class InstructionFetch implements Element {
    Cache cache;
    int currentPC;
    EX_IF_LatchType EX_IF_Latch;
    IF_EnableLatchType IF_EnableLatch;
    IF_OF_LatchType IF_OF_Latch;
    Processor containingProcessor;

public void performIF() {
        if(IF_EnableLatch.isFetchStageEnable()) {
            if(IF_EnableLatch.isOccupiedIF()) return;
            currentPC = containingProcessor.getRegisterFile().getProgramCounter();
            if(EX_IF_Latch.Branch) {
                currentPC = currentPC + EX_IF_Latch.disable - 1;
                EX_IF_Latch.Branch = false;}
            Simulator.ins_count++;
            Simulator.getEventQueue().addEvent(new MemoryReadEvent( Clock.getCurrentTime()+this.cache.latency, this, this.cache,currentPC) );

            IF_EnableLatch.setOccupiedIF(true);
        }

    }

    public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch, Cache cache) {
  
  this.cache = cache;
  this.EX_IF_Latch = eX_IF_Latch;
  this.IF_EnableLatch = iF_EnableLatch;
  this.containingProcessor = containingProcessor;
  this.IF_OF_Latch = iF_OF_Latch;
} 

    @Override

    public void handleEvent(Event e) {
        if(IF_OF_Latch.isOccupiedfetch()) {
            e.setEventTime(Clock.getCurrentTime() + 1);
            Simulator.getEventQueue().addEvent(e);
        }
        else {
            MemoryResponseEvent event = (MemoryResponseEvent) e ;
            if(!EX_IF_Latch.Branch)	{
                IF_OF_Latch.setInstruction(event.getValue());
            }
            else {
                IF_OF_Latch.setInstruction(0);
            }
            IF_OF_Latch.instructionPCounter = this.currentPC;
            containingProcessor.getRegisterFile().setProgramCounter(this.currentPC + 1);
            IF_OF_Latch.setEnabledOF(true);
            IF_EnableLatch.setOccupiedIF(false);

        }
    }
}
