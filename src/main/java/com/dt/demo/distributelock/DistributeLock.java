package com.dt.demo.distributelock;

import java.util.concurrent.TimeUnit;

public interface DistributeLock {

    public void  acquire() throws Exception;

    /**
     * @param time
     * @param timeUnit
     * @return
     * @throws Exception
     */
    public boolean  acquire(long time, TimeUnit timeUnit) throws Exception;

    public boolean release() throws  Exception;

}
