package ubadb.external.bufferManagement;

import java.nio.file.Paths;
import java.util.Vector;

import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.PageReplacementStrategy;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.fifo.FIFOReplacementStrategy;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.lru.LRUReplacementStrategy;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.mru.MRUReplacementStrategy;
import ubadb.core.components.bufferManager.bufferPool.replacementStrategies.touchcount.TouchCountReplacementStrategy;
import ubadb.core.exceptions.BufferManagerException;
import ubadb.external.bufferManagement.etc.PageReferenceTraceSerializer;

public class DataGenerator {
	
	private static int MIN_BUFFER_POOL_SIZE = 50;
	private static int MAX_BUFFER_POOL_SIZE = 500;
	private static int maxConcurrentTraces = 2;
	private static int totalTracesCount = 50;
//	private static String folderNameForNewTrace = "generated/1-moreFileScanToMixed/";
//	private static String folderNameForNewTrace = "generated/2-moreIndexScanToMixed/";
	private static String folderNameForNewTrace = "generated/3-onlyFileScanToMixed/";
	private static String fileNameForNewTrace = folderNameForNewTrace+"mixedTrace";
//	private static String folderName = "generated/1-moreFileScanToMix";
//	private static String folderName = "generated/2-moreIndexScanToMix";
	private static String folderName = "generated/3-onlyFileScanToMix";
	
	private static Vector<PageReplacementStrategy> strategies;
	private static PageReferenceTraceSerializer serializer = new PageReferenceTraceSerializer();
	
	public static void main(String[] args)
	{
		try
		{
			restartStrategies();
			
			traces();
			evaluate();
		}
		catch(Exception e)
		{
			System.out.println("FATAL ERROR (" + e.getMessage() + ")");
			e.printStackTrace();
		}
	}
	
	private static void restartStrategies(){
		strategies = new Vector<PageReplacementStrategy>();
		strategies.add(new FIFOReplacementStrategy());
		strategies.add(new MRUReplacementStrategy());
		strategies.add(new LRUReplacementStrategy());
		strategies.add(new TouchCountReplacementStrategy());
	}
	
	private static void traces() throws Exception{
		MainTraceGenerator.mixTraces(fileNameForNewTrace, folderName, totalTracesCount, maxConcurrentTraces, serializer);
	}
	
	private static void evaluate() throws Exception, InterruptedException, BufferManagerException{
		String[] traceFiles = Paths.get(folderNameForNewTrace).toFile().list();
		
		for (String traceFileName : traceFiles){
			traceFileName = folderNameForNewTrace+traceFileName;
			System.out.print("traceFileName: "+traceFileName+"\n");
			for(int i = MIN_BUFFER_POOL_SIZE; i < MAX_BUFFER_POOL_SIZE; i=i+50){
				System.out.print("BufferSize: "+i+"\n");
				for(PageReplacementStrategy pageReplacementStrategy : strategies){
					System.out.print(pageReplacementStrategy.toString());
					MainEvaluator.evaluate(pageReplacementStrategy, traceFileName, i);
				}
				restartStrategies(); //Tenes que resetear si no te qeuda todo mal en COLD
				System.out.print("\n");
			}
		}
		
	}
}
