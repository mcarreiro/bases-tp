package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;

import java.util.Date;

import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.common.Page;
import ubadb.core.exceptions.BufferFrameException;

public class TouchBufferFrame extends BufferFrame {
        
        public TouchBufferFrame(Page page) {
                super(page);
        }

}