package processor.memorysystem;

import generic.*;
import processor.*;

public class MainMemory implements Element{
    int[] memory;

   

    public int getWord(int address)
    {
        return memory[address];
    }

    public String getContentsAsString(int startingAddress, int endingAddress)
    {
        if(startingAddress == endingAddress)
            return "";

        StringBuilder sb = new StringBuilder();
        sb.append("\nMain Memory Contents:\n\n");
        for(int i = startingAddress; i <= endingAddress; i++)
        {
            sb.append(i + "\t\t: " + memory[i] + "\n");
        }
        sb.append("\n");
        return sb.toString();
    }
    public void setWord(int address, int value)
    {
        memory[address] = value;
    }

    public MainMemory()
    {
        memory = new int[65536];
    }

    

    @Override
    public void handleEvent(Event e) {
        if (e.getEventType() == Event.EventType.MemoryRead) {
            MemoryReadEvent event = (MemoryReadEvent) e;
            Simulator.getEventQueue().addEvent(
                    new MemoryResponseEvent(
                            Clock.getCurrentTime(),
                            this,
                            event.getRequestingElement(),
                            getWord(event.getAddressToReadFrom())
                    )
            );
        }
        else if(e.getEventType() == Event.EventType.MemoryWrite) {
            MemoryWriteEvent event = (MemoryWriteEvent) e ;
            this.setWord(event.getAddressToWriteTo(), event.getValue());
            Simulator.getEventQueue().addEvent(
                    new ExecutionCompleteEvent(
                            Clock.getCurrentTime(),
                            this,
                            event.getRequestingElement())
            );
        }
    }
}
