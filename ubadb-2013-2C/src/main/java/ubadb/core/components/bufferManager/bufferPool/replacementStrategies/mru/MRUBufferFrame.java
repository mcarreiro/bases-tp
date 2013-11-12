package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.mru;
import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.exceptions.BufferFrameException;

import java.util.Date;

public class MRUBufferFrame extends BufferFrame 
{
		private Date referencedDate;
	
        public MRUBufferFrame(Page page) {
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