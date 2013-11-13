package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.core.testDoubles.DummyObjectFactory;
import ubadb.core.util.TestUtil;

public class TouchCountBufferFrameTest {
	@Test
	public void testCreationDate() throws Exception
	{
		TouchBufferFrame bufferFrame0 = new TouchBufferFrame(DummyObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		TouchBufferFrame bufferFrame1 = new TouchBufferFrame(DummyObjectFactory.PAGE);
		
		assertEquals(bufferFrame0.count,bufferFrame1.count);
		assertEquals(bufferFrame0.count,(Integer)0);
		
		assertTrue(bufferFrame0.lastTouch.before(bufferFrame1.lastTouch));
	}
	
	@Test
	public void testIncreaseCountAcordingToTimePassed() throws Exception
	{
		TouchBufferFrame bufferFrame0 = new TouchBufferFrame(DummyObjectFactory.PAGE);
		bufferFrame0.pin();
		Thread.sleep(5000); //5000 Miliseconds == 5 Seconds
		bufferFrame0.pin();
		
		assertEquals(bufferFrame0.count,(Integer)2);
	}
}
