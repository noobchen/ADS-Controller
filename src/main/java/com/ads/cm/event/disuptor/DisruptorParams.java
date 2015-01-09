package com.ads.cm.event.disuptor;


public class DisruptorParams {
    private String RingBufferSize;

    public DisruptorParams(String ringBufferSize) {
        super();
        RingBufferSize = ringBufferSize;
    }

    public String getRingBufferSize() {
        return RingBufferSize;
    }

    public void setRingBufferSize(String ringBufferSize) {
        RingBufferSize = ringBufferSize;
    }
}
