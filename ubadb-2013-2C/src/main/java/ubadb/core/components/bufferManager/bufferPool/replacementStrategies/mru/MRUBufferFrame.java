package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.mru;
import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;

public class MRUBufferFrame extends BufferFrame 
{
                
        public MRUBufferFrame(Page page) {
                super(page);            
        }

}