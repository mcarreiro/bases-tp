package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;

import java.util.Date;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.exceptions.BufferFrameException;

public class TouchBufferFrame extends BufferFrame implements Comparable<TouchBufferFrame>{
        
		public Integer count;
		public Date lastTouch;
	
        public TouchBufferFrame(Page page) {
                super(page);
                count = 0;
                lastTouch = new Date();
        }
        
        public void pin(){
        	super.pin();
        	increaseCount();
        }
        
        public void unpin() throws BufferFrameException{
        	super.unpin();
        	increaseCount();
        }
        
        public void increaseCount(){
        	Date now = new Date();
        	
        	@SuppressWarnings("unused")
			long difference = (long) ((now.getTime() - lastTouch.getTime())/1000);
        	
        	if((now.getTime() - lastTouch.getTime())/1000 >= 3){
        		count++;
        		lastTouch = new Date();
        	}
        }

		@Override
		public int compareTo(TouchBufferFrame arg0) {
			return count.compareTo(((TouchBufferFrame)arg0).count);
		}

}