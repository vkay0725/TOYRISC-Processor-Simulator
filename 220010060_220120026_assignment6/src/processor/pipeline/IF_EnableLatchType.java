package processor.pipeline;

public class IF_EnableLatchType {
    boolean occupiedIF;
    boolean fetchStageEnable;

    public IF_EnableLatchType() {
        fetchStageEnable = true;
        occupiedIF = false;
    }
    public boolean isFetchStageEnable() {return fetchStageEnable;
    }

    public void setFetchStageEnable(boolean iF_enable) {fetchStageEnable = iF_enable;
    }


    public boolean isOccupiedIF() {return occupiedIF;
    }

    public void setOccupiedIF(boolean ifOccupied) {
        occupiedIF = ifOccupied;
    }
}
