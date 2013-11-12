package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.core.exceptions.PageReplacementStrategyException;

public class TouchCountReplacementStrategy implements PageReplacementStrategy {

		private Collection<TouchBufferFrame> cold;
		private Collection<TouchBufferFrame> hot;
	
        public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException {
        		hotNCold(bufferFrames); //Armar Hot N Cold, con mitad y mitad ordenado por Touch Count
        		
        		hotNColdMovement(); // Mover según parámetro del paper
        		
        		TouchBufferFrame victim = firstColdFrame();
                return victim;
        }

        
        private void hotNCold(Collection<BufferFrame> bufferFrames){
        	int halfSizeCollection = bufferFrames.size()/2;
        	
//        	List<? extends BufferFrame> listBufferFrames = new ArrayList<? extends BufferFrame >(bufferFrames); // NO ANDA, IGUAL LO DEJO
        	
        	List<TouchBufferFrame> listBufferFrames = toListTouchable(bufferFrames);
        	
        	Collections.sort(listBufferFrames);
        	
        	for (TouchBufferFrame bufferFrame : listBufferFrames) {
        		if (hot.size() != halfSizeCollection){
        			hot.add(bufferFrame);
        		}else{
        			cold.add(bufferFrame);
        		}
            }
        }
        
        //Devuelvo el primer COLD Frame reemplazable
        private TouchBufferFrame firstColdFrame() throws PageReplacementStrategyException{
        	for (TouchBufferFrame bufferFrame : cold) {
        		if (bufferFrame.canBeReplaced()){
        			cold.remove(bufferFrame);
        			return bufferFrame;
        		}
        	}
        	throw new PageReplacementStrategyException("No hay Cold Buffer reemplazable");
        }
        
        //Una vez que tengamos cold y hoy, pasar de cold a hot según parámetro del paper
        private void hotNColdMovement(){
        	for(TouchBufferFrame bufferFrame : cold){
        		if(bufferFrame.count > 2){ // Dato del paper el 2
        			bufferFrame.count = 0; //Se tiene que resetear
        			hot.add(bufferFrame);
        			cold.remove(bufferFrame);
        		}
        	}
        }
        
        
        //Pasar a lista para poder ordenarla
        private List<TouchBufferFrame> toListTouchable(Collection<BufferFrame> bufferFrames){
        	List<TouchBufferFrame> result = new ArrayList<TouchBufferFrame>();
        	for (BufferFrame bufferFrame : bufferFrames) {
        		result.add((TouchBufferFrame) bufferFrame);
        	}
        	return result;
        }

        public BufferFrame createNewFrame(Page page) {
                
        	return new TouchBufferFrame(page);    
        }
        
        public String toString() {
                return "Touch Count Replacement Strategy";
        }
        
}