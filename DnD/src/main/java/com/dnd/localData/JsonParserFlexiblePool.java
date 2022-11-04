package com.dnd.localData;

import java.lang.reflect.Field;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.glassfish.grizzly.utils.PoolableObject;

public class JsonParserFlexiblePool extends FlexibleObjectPool<JsonParserAndMapper> {

private LinkedBlockingQueue<JsonParserAndMapper> pool = new LinkedBlockingQueue<>();
private int pollTimeout = 3000;


public JsonParserFlexiblePool() {
    initialize(20, pool);
}

public JsonParserFlexiblePool(int poolSize) {
    initialize(poolSize, pool);
}

public JsonParserFlexiblePool(int poolSize, int pollTimeout) {
    initialize(poolSize, pool);
    this.pollTimeout = pollTimeout;
}

public JsonParserFlexiblePool(int poolSize, int minIdle, int maxIdle, int validationInterval) {
    super( minIdle,  maxIdle,  validationInterval);
    initialize(poolSize, pool);
}

/**
 * Borrow one BOON JSON parser object from the pool.
 *
 * @return returns one BOON JSON parser object
 * @throws PoolDepletionException thrown if there are no BOON JSON parser objects in the pool
 * @throws InterruptedException
 */
@Override
public JsonParserAndMapper borrowObject() throws PoolDepletionException, InterruptedException {
    JsonParserAndMapper jsonParserAndMapper = pool.poll(this.pollTimeout, TimeUnit.MILLISECONDS);

    if (jsonParserAndMapper == null) {
        throw new PoolDepletionException("The JSON parser pool is empty and was not replenished within timeout limits.");
    }

    return jsonParserAndMapper;
}

/**
 * Creates a BOON JSON parser object.
 *
 * @return a new BOON JSON parser object
 */
@Override
protected JsonParserAndMapper createObject() {
    return new JsonParserFactory().useAnnotations().usePropertiesFirst().create();
}

@Override
public PoolableObject poll() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void offer(PoolableObject object) {
	// TODO Auto-generated method stub
	
}

@Override
public void clear() {
	// TODO Auto-generated method stub
	
}

}