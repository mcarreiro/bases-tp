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
		
		public TouchCountReplacementStrategy(){
			cold = new LinkedList<TouchBufferFrame>();
			hot = new LinkedList<TouchBufferFrame>();
		}
		
        public BufferFrame findVictim(Collection<BufferFrame> bufferFrames) throws PageReplacementStrategyException {
        		hotNColdMovement(); // Mover según parámetro del paper.
        		
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
        			}//Si no, es que son iguales. No puede haber más de 1 de diferencia
        			
        			return bufferFrame;
        		}
        	}
        	throw new PageReplacementStrategyException("No hay Cold Buffer reemplazable");
        }
        
        //Una vez que tengamos cold y hoy, pasar de cold a hot según parámetro del paper
        private void hotNColdMovement(){
			LinkedList<TouchBufferFrame> coldToRemove = new LinkedList<TouchBufferFrame>();
			LinkedList<TouchBufferFrame> hotToRemove = new LinkedList<TouchBufferFrame>();
        	for(TouchBufferFrame bufferFrame : cold){
        		if(bufferFrame.count >= 2 ){ // Dato del paper
        			bufferFrame.count = 0; //Se tiene que resetear
        			
        			coldToRemove.addLast(bufferFrame);
        			
        			TouchBufferFrame toColdToMantainBalanceFrame = hot.removeLast();
					toColdToMantainBalanceFrame.count = 1; //Si no en la proxima iteracion lo va a mandar a Hot de vuelta
					hotToRemove.addLast(toColdToMantainBalanceFrame); //Mantener balanceo
        		}  		
        	}
        	
    		//Elimino los que tienen count>2 de cold
    		cold.removeAll(coldToRemove);
    		//Concateno de forma tal que quede al principio coldToRemove y después hot
    		coldToRemove.addAll(hot);
    		//Hot es esta nueva lista
    		hot = coldToRemove;
    		
    		//MantenerBalanceo agregando los que saqué de Hot al final de cold 
    		cold.addAll(hotToRemove);
        }

        public BufferFrame createNewFrame(Page page) {
        	TouchBufferFrame bufferFrame = new TouchBufferFrame(page);
        	
        	cold.addFirst(bufferFrame);
        	
        	if (Math.abs(cold.size() - hot.size())>1){ //Mantener balanceo, máxima diferencia 1
        		TouchBufferFrame coldToHodFrame = cold.removeLast();
        		hot.addLast(coldToHodFrame);
        	}
        	
        	return bufferFrame;     
        }
        
        public String toString() {
            return "Touch Count Replacement Strategy";
        }
}