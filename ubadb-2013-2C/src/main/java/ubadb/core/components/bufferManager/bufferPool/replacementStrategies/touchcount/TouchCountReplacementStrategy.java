package ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount;

import java.util.Collection;
import java.util.LinkedList;

import ubadb.core.common.Page;
import ubadb.core.components.bufferManager.bufferPool.BufferFrame;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.core.exceptions.PageReplacementStrategyException;

public class TouchCountReplacementStrategy implements PageReplacementStrategy {

		private LinkedList<TouchBufferFrame> cold;
		private LinkedList<TouchBufferFrame> hot;
	
        public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException {
       		
        		hotNColdMovement(); // Mover según parámetro del paper
        		
        		TouchBufferFrame victim = firstColdFrame();
                return victim;
        }

        
        //Devuelvo el primer COLD Frame reemplazable
        private TouchBufferFrame firstColdFrame() throws PageReplacementStrategyException{
        	for (TouchBufferFrame bufferFrame : cold) {
        		if (bufferFrame.canBeReplaced()){
        			cold.remove(bufferFrame);
        			
        			if(Math.abs(cold.size() - hot.size())>0){ //Si HOT es más grande
        				cold.addLast(hot.removeLast()); //Mantener balanceo
        			}//Si no, es que son iguales
        			
        			return bufferFrame;
        		}
        	}
        	throw new PageReplacementStrategyException("No hay Cold Buffer reemplazable");
        }
        
        //Una vez que tengamos cold y hoy, pasar de cold a hot según parámetro del paper
        private void hotNColdMovement(){
        	for(TouchBufferFrame bufferFrame : cold){
        		if(bufferFrame.count > 2 ){ // Dato del paper
        			bufferFrame.count = 0; //Se tiene que resetear
        			hot.addFirst(bufferFrame);
        			cold.remove(bufferFrame);
        			cold.addLast(hot.removeLast()); //Mantener balanceo
        		}
        	}
        	
        }
        

        public BufferFrame createNewFrame(Page page) {
        	TouchBufferFrame bufferFrame = new TouchBufferFrame(page);
        	
        	cold.addFirst(bufferFrame);
        	
        	if (cold.size() >= 2){ //Mantener balanceo, máxima diferencia 1
        		TouchBufferFrame coldToHodFrame = cold.pop();
        		hot.addLast(coldToHodFrame);
        	}
        	
        	return bufferFrame;     
        	
        }
        
        public String toString() {
                return "Touch Count Replacement Strategy";
        }
        
}