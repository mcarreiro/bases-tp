package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.lru;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;

public class LRUBufferFrame extends BufferFrame 
{                
        public LRUBufferFrame(Page page) {
                super(page);            
        }


}