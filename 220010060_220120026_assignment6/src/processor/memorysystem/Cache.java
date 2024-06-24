package processor.memorysystem;import generic.*;
import processor.*;
import configuration.Configuration;

public class Cache implements Element{ 
    CacheLine[] cacheLine;
    int temp; boolean isHit = true;
    public int latency;
    int cache_visit;
	int cacheSize;
    int[] index;
    int msAddr;
    Processor containingProcessor;

   
    public String toString() {
        return Integer.toString(this.latency) + " : latency";
    }
    public int[] getIndexes() {
        return this.index;
    }


    public int cacheRead(int address) {
        String addrString = Integer.toBinaryString(address); 
 
    
String index = "";
int h = 0;
while (h < temp) {
    index = index + "1";
    h++;
}

int y= 0;
while (y < 32 - addrString.length()) {
    addrString = "0" + addrString;
    y=y+1;
}
        
        int temp_ind;
        if(temp == 0) {
            temp_ind = 0;
        }
        else {
            temp_ind = address & Integer.parseInt(index, 2); // 
        }
        
        
        System.out.println("In the Cache " + address);
        int addrTag = Integer.parseInt(
            addrString.substring(0, addrString.length() - temp), 2
            );

        if(addrTag == cacheLine[temp_ind].tag[1]) {
            cacheLine[temp_ind].LRUctr = 0; isHit = true;
            return cacheLine[temp_ind].data[1];
        }
        else if(addrTag == cacheLine[temp_ind].tag[0]) {
            cacheLine[temp_ind].LRUctr = 1;isHit = true;
            return cacheLine[temp_ind].data[0];
         } else {
            isHit = false;
            return -1;
        }
    }

    

    public boolean isInCache() {
        return this.isHit;
    }

   
    public Cache(Processor ContainingProcessor, int Latency, int cSize) {
        this.cacheSize = cSize;
        this.latency = Latency;
        this.temp = 0; 
        this.containingProcessor = ContainingProcessor;
        this.cacheLine = new CacheLine[cSize/8];
		for(int i = 0; i < cSize/8; i++) 
			this.cacheLine[i] = new CacheLine();
    }


    public CacheLine[] getCaches() {
        return this.cacheLine;
    }

    public Processor getProcessor() {
        return this.containingProcessor;
    }

    public void cacheWrite(int address, int value) {
        String addrString = Integer.toBinaryString(address); 

        
        
        String index = "";
    int t = 0;
    while (t < temp) {
    index = index + "1";
    t= t+1;
}
int p = 0;
while(p < 32 - addrString.length()) {
    addrString = "0" + addrString;
    p=p+1;
}  
            
        int temp_ind;
        if(temp == 0)
            temp_ind = 0;
        else 
            temp_ind = address & Integer.parseInt(index, 2);

        int tag = Integer.parseInt(addrString.substring(0, addrString.length() - temp),2);
        cacheLine[temp_ind].setData(tag, value);
    }

    public void setProcessor(Processor processor) {
        this.containingProcessor = processor;
    }

    

    public void handleCacheMiss(int address) {
		Simulator.getEventQueue().addEvent(
            new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency,this,containingProcessor.getMainMemory(),  address )
        );           
	}




    @Override
	public void handleEvent(Event e){ 
        if(e.getEventType() == Event.EventType.MemoryResponse) {
            MemoryResponseEvent responseE = (MemoryResponseEvent) e;
            cacheWrite(this.msAddr, responseE.getValue());

        } else if(e.getEventType() == Event.EventType.MemoryRead){
            
            System.out.println("Handling Cache Memory Read!");
            MemoryReadEvent readE = (MemoryReadEvent) e;
            int data = cacheRead(readE.getAddressToReadFrom());
            if(isHit == true){
                Simulator.getEventQueue().addEvent(    new MemoryResponseEvent( Clock.getCurrentTime() + this.latency,  this,  readE.getRequestingElement(),  data)    );
            } else {
                System.out.println("Missed!");
                this.msAddr = readE.getAddressToReadFrom();
                readE.setEventTime(Clock.getCurrentTime() + Configuration.mainMemoryLatency + 1);
                Simulator.getEventQueue().addEvent(readE);
                handleCacheMiss(readE.getAddressToReadFrom());
            }
        } else if(e.getEventType() == Event.EventType.MemoryWrite) {
            System.out.println("Handling Cache Memory Write");
            MemoryWriteEvent writeE = (MemoryWriteEvent) e;
            cacheWrite(writeE.getAddressToWriteTo(), writeE.getValue());
            containingProcessor.getMainMemory().setWord(writeE.getAddressToWriteTo(), writeE.getValue());

            Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, containingProcessor.getMainMemory(), 	writeE.getRequestingElement()  )); 
		}
	}
}