/**
 * TestHeap.java
 *
 * PURPOSE:			To create timings for operations on BinaryHeap and BinomialHeap.		
 * 
 */

import java.util.Random;

public class TestHeap {

	public static void main(String[] args) {

		//int n [] = {32, 64, 128, 256, 512, 1024};
		
		//had to increase the size by 1000 on my computer because I was getting
		//really small numbers for the timings
		//it is possible that memory may run out so lower the sizes if this happens
		int n [] = {32*1000, 64*1000, 128*1000, 256*1000, 512*1000, 1024*1000};
		
		long start;
		long end;
		long delta;

		System.out.println("Time in milliseconds:\n");
		System.out.println("Timings for Binary Heap:");
		for(int i = 0; i < n.length; i ++){
			
			BinaryHeap binaryheap1 = new BinaryHeap(2*n[i]);
			BinaryHeap binaryheap2 = new BinaryHeap(n[i]);
			
			//use System.nanoTime() if numbers are too small or all zeroes
			start = System.currentTimeMillis();
			testBinaryHeap(binaryheap1, binaryheap2, n[i]);
			end = System.currentTimeMillis();
			delta = end - start;
			System.out.println("Time for size " + n[i] + ":\t" + delta);

		}
		System.out.println("\nTimings for Binomial Heap:");
		for(int i = 0; i < n.length; i ++){
			
			BinomialHeap binomialheap1 = new BinomialHeap();
			BinomialHeap binomialheap2 = new BinomialHeap();
			
			start = System.currentTimeMillis();
			testBinomialHeap(binomialheap1, binomialheap2, n[i]);
			end = System.currentTimeMillis();

			delta = end - start;
			System.out.println("Time for size " + n[i] + ":\t" + delta);

		}

		System.out.println("\nProcessing Complete");
	}
	
	public static void testBinaryHeap(BinaryHeap binaryheap1, BinaryHeap binaryheap2, int size){
		
		Random rand = new Random();
		int randnum;
		
		//1. insert n random numbers into a heap
		for(int j = 0; j < size; j ++){
			randnum = rand.nextInt(100);
			binaryheap1.insert(randnum);
			
		}
		
		//2. insert n random numbers into a second heap
		for(int k = 0; k < size; k ++){
			randnum = rand.nextInt(100);
			binaryheap2.insert(randnum);
		}
		
		//3. union the two heaps
		//binaryheap is now the union
		binaryheap1.union(binaryheap2);
		
		//4. perform 2n deleteMax operations on the combined heap
		for(int l = 0; l < 2*size; l ++){
			binaryheap1.deleteMax();
		}

	}
	
	public static void testBinomialHeap(BinomialHeap binomialheap1, BinomialHeap binomialheap2, int size){
		
		Random rand = new Random();
		int randnum;

		//1. insert n random numbers into a heap
		for(int j = 0; j < size; j ++){
			randnum = rand.nextInt(100);
			binomialheap1.insert(randnum);
			
		}
		
		//2. insert n random numbers into a second heap
		for(int k = 0; k < size; k ++){
			randnum = rand.nextInt(100);
			binomialheap2.insert(randnum);
		}
		
		//3. union the two heaps
		//binomialheap1 is now the union
		binomialheap1.union(binomialheap2);
		
		//4. perform 2n deleteMax operations on the combined heap
		for(int l = 0; l < 2*size; l ++){
			binomialheap1.deleteMax();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
