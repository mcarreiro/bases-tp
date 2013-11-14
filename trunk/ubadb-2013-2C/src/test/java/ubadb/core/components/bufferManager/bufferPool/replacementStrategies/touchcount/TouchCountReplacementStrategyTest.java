package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.exceptions.PageReplacementStrategyException;
import ubadb.core.testDoubles.DummyObjectFactory;

public class TouchCountReplacementStrategyTest {
	
	private TouchCountReplacementStrategy strategy;
	
	@Before
	public void setUp()
	{
		strategy = new TouchCountReplacementStrategy();
		//        COLD                     HOT
		//[Ultimo-------->Primero][Ultimo------->Primero]
	}
	
	@Test(expected=PageReplacementStrategyException.class)
	public void testNoPageToReplace() throws PageReplacementStrategyException
	{
		BufferFrame frame0 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		BufferFrame frame1 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		
		frame0.pin();
		frame1.pin();
		
		assertEquals(frame0,strategy.findVictim(Arrays.asList(frame0,frame1)) );
	}
	
	@Test
	public void testOnlyOneToReplace() throws PageReplacementStrategyException
	{
		BufferFrame frame0 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame0][]
		BufferFrame frame1 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame0,frame1][]
		BufferFrame frame2 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame1,frame2][frame0]
		
		frame0.pin();
		
		assertEquals(frame2,strategy.findVictim(Arrays.asList(frame0,frame1,frame2)));
	}
	
	@Test
	public void testMultiplePagesToReplaceWithHotNColdMovement() throws Exception
	{
		BufferFrame frame0 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame0][]
		BufferFrame frame1 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame0,frame1][]
		BufferFrame frame2 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame1,frame2][frame0]
		BufferFrame frame3 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame2,frame3][frame1,frame0]
		
//		Thread.sleep(4000);
		frame3.pin();
//		Thread.sleep(4000);
		frame3.pin();
//		Thread.sleep(4000);
		frame3.pin();
		//frame.count = 3
		
		BufferFrame victim = strategy.findVictim(Arrays.asList(frame0,frame1,frame2,frame3));
		//COLDnHOT = [frame1,frame2][frame0,frame3]
		
		assertEquals(frame2,victim);
	}
	
	@Test
	public void testMultiplePagesToReplaceWithHotNColdMovement2() throws Exception
	{
		BufferFrame frame0 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame0][]
		BufferFrame frame1 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame0,frame1][]
		BufferFrame frame2 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame1,frame2][frame0]
		BufferFrame frame3 = strategy.createNewFrame(DummyObjectFactory.PAGE);
		//COLDnHOT = [frame2,frame3][frame1,frame0]
		
//		Thread.sleep(4000);
		frame3.pin();
//		Thread.sleep(4000);
		frame3.pin();
//		Thread.sleep(4000);
		frame3.pin();
		
		//Thread.sleep(4000);
		frame2.pin();
		//Thread.sleep(4000);
		frame2.pin();
//		Thread.sleep(4000);
		frame2.pin();
		//frame.count = 3
		
		BufferFrame victim = strategy.findVictim(Arrays.asList(frame0,frame1,frame2,frame3));
		//COLDnHOT = [frame0,frame1][frame2,frame3]
		
		
		assertEquals(frame1,victim);
	}
}
