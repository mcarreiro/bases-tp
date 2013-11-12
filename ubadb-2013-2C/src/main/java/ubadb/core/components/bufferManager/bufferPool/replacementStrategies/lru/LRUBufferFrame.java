package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.lru;

import java.util.Date;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.exceptions.BufferFrameException;

public class LRUBufferFrame extends BufferFrame 
{                
		private Date referencedDate;
	
        public LRUBufferFrame(Page page) {
                super(page);     
                referencedDate = new Date();
        }
        
        public Date getReferencedDate()
    	{
    		return referencedDate;
    	}
        
        public void pin(){
        	super.pin();
        	referencedDate = new Date();
        }
        
        public void unpin() throws BufferFrameException{
        	super.unpin();
        	referencedDate = new Date();
        }


}