package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.mru;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ubadb.core.testDoubles.DummyObjectFactory;
import ubadb.core.util.TestUtil;

public class MRUBufferFrameTest {
	
	@Test
	public void testCreationDate() throws Exception
	{
		MRUBufferFrame bufferFrame0 = new MRUBufferFrame(DummyObjectFactory.PAGE);
		Thread.sleep(TestUtil.PAUSE_INTERVAL); //Sleep to guarantee that the second frame is created some time after the first one
		MRUBufferFrame bufferFrame1 = new MRUBufferFrame(DummyObjectFactory.PAGE);
		
		assertTrue(bufferFrame0.getReferencedDate().before(bufferFrame1.getReferencedDate()));
	}

}
