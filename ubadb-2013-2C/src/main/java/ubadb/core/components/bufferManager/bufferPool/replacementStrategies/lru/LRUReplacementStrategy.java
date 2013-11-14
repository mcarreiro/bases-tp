package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.lru;

import java.util.Collection;
import java.util.Date;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.core.exceptions.PageReplacementStrategyException;

public class LRUReplacementStrategy implements PageReplacementStrategy
{       
        public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
        {               
        	LRUBufferFrame victim = null;
    		Date oldestReplaceablePageDate = null;
    		
    		for(BufferFrame bufferFrame : bufferFrames)
    		{
    			LRUBufferFrame lruBufferFrame = (LRUBufferFrame) bufferFrame; //safe cast as we know all frames are of this type
    			if(lruBufferFrame.canBeReplaced() && (oldestReplaceablePageDate==null || lruBufferFrame.getReferencedDate().before(oldestReplaceablePageDate)))
    			{
    				victim = lruBufferFrame;
    				oldestReplaceablePageDate = lruBufferFrame.getReferencedDate();
    			}
    		}
    		
    		if(victim == null)
    			throw new PageReplacementStrategyException("No page can be removed from pool");
    		else
    			return victim;
        }
        
        public BufferFrame createNewFrame(Page page) 
        {
        	return new LRUBufferFrame(page);                     
        }
        
        public String toString() {
        	return "LRU Replacement Strategy";
        }
}