package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;

import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.common.Page;
import ubadb.core.exceptions.BufferFrameException;

public class TouchBufferFrame extends BufferFrame implements Comparable<TouchBufferFrame>{
        
		public Integer count;
	
        public TouchBufferFrame(Page page) {
                super(page);
                count = 0;
        }
        
        public void pin(){
        	super.pin();
        	count++;
        }
        
        public void unpin() throws BufferFrameException{
        	super.unpin();
        	count++;
        }

		@Override
		public int compareTo(TouchBufferFrame arg0) {
			return count.compareTo(((TouchBufferFrame)arg0).count);
		}

}