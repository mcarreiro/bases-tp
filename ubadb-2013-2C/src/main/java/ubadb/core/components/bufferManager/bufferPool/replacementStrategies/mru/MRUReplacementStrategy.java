package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.mru;

import java.util.Collection;
import java.util.Date;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.core.exceptions.PageReplacementStrategyException;

public class MRUReplacementStrategy implements PageReplacementStrategy
{       
        public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
        {               
        	MRUBufferFrame victim = null;
    		Date newestReplaceablePageDate = null;
    		
    		for(BufferFrame bufferFrame : bufferFrames)
    		{
    			MRUBufferFrame mruBufferFrame = (MRUBufferFrame) bufferFrame; //safe cast as we know all frames are of this type
    			if(mruBufferFrame.canBeReplaced() && (newestReplaceablePageDate==null || mruBufferFrame.getReferencedDate().after(newestReplaceablePageDate)))
    			{
    				victim = mruBufferFrame;
    				newestReplaceablePageDate = mruBufferFrame.getReferencedDate();
    			}
    		}
    		
    		if(victim == null)
    			throw new PageReplacementStrategyException("No page can be removed from pool");
    		else
    			return victim;
        }
        
        public BufferFrame createNewFrame(Page page) 
        {
        	return new MRUBufferFrame(page);               
        }
        
        public String toString() {
                return "MRU Replacement Strategy";
        }
}