package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.mru;

import java.util.Collection;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.core.exceptions.PageReplacementStrategyException;

public class MRUReplacementStrategy implements PageReplacementStrategy
{       
        public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException
        {               

        	return null;
        }
        
        public BufferFrame createNewFrame(Page page) 
        {
                return null;                
        }
        
        public String toString() {
                return null;
        }
}