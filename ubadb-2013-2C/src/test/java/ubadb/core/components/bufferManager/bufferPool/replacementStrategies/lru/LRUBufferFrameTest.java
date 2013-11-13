package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.lru;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.core.testDoubles.DummyObjectFactory;
import ubadb.core.util.TestUtil;

public class LRUBufferFrameTest {
	
	@Test
	public void testCreationDate() throws Exception
	{
		LRUBufferFrame bufferFrame0 = new LRUBufferFrame(DummyObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		LRUBufferFrame bufferFrame1 = new LRUBufferFrame(DummyObjectFactory.PAGE);
		
		assertTrue(bufferFrame0.getReferencedDate().before(bufferFrame1.getReferencedDate()));
	}

}
