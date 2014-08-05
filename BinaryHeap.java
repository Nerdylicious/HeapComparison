/**
 * BinaryHeap.java
 *
 * PURPOSE:			A BinaryHeap class.	
 *
 */

public class BinaryHeap {
	
	private int [] heap;
	private int heapSize;
	private int maxSize;
	
	public BinaryHeap(int maxSize){
		
		this.maxSize = maxSize;
		heap = new int[maxSize];
		heapSize = 0;
	}
	
	public boolean empty(){
		boolean result;
		if(heapSize == 0){
			result = true;
		}
		else{
			result = false;
		}
		return result;
	}
	
	public boolean full(){
		boolean result;
		if(heapSize == maxSize){
			result = true;
		}
		else{
			result = false;
		}
		return result;
		
	}
	
	/**
	* Insert an element into the heap.
	*
	* @param priority	The priority of the element to insert.
    *
	*/ 
	public void insert(int priority){
		
		int i;
		if(full()){
			System.out.println("Error in insert(): Binary heap is full");
			
		}
		else{
			heap[heapSize] = priority;
			i = heapSize;
			heapSize ++;
			
			while((i > 0) && (heap[(i-1) / 2] < heap[i])){
				
				//swap
				int temp = heap[i];
				heap[i] = heap[(i-1) / 2];
				heap[(i-1) / 2] = temp;
				i = (i-1) / 2;
			}	
		}
	}
	
	/**
	* Delete the element with maximum priority in the heap.
	*
	* @return max	The value of the maximum in the heap.
    *
	*/ 
	public int deleteMax(){
		
		int max;
		int i;
		int maxchild;
		int temp;
		
		if(empty()){
			max = -999999;	//sentinel value for error
			System.out.println("Error in deleteMax(): Binary heap is empty");
		}
		else{
			max = heap[0];
			heapSize --;
			heap[0] = heap[heapSize];
			i = 0;
			
			while(((2*i+2) < heapSize) && ((heap[i] < heap[2*i+1]) || (heap[i] < heap[2*i+2]))){
				
				if(heap[2*i+1] > heap[2*i+2]){
					maxchild = 2*i+1;
				}
				else{
					maxchild = 2*i+2;
				}
				temp = heap[i];
				heap[i] = heap[maxchild];
				heap[maxchild] = temp;
				i = maxchild;
				
			}
			if(((2*i+1) < heapSize) && (heap[i] < heap[2*i+1])){
				
				temp = heap[i];
				heap[i] = heap[2*i+1];
				heap[2*i+1] = temp;
				
				i = 2*i+1;
			}
		}
		
		return max;
	}
	
	/**
	* Sift down the element in each step during a heapify.
	*
	* @param i	The index of the element to sift down.
    *
	*/ 
	public void siftdown(int i){
		
		boolean hasChildren = true;
		int maxchild;
		int temp;
		
		//while it has children
		while(hasChildren == true){
			
			//replace with it's larger child
			//if it has 2 children
			if(((2*i+2) < heapSize) && ((heap[i] < heap[2*i+1]) || (heap[i] < heap[2*i+2]))){
				if(heap[2*i+1] > heap[2*i+2]){
					maxchild = 2*i+1;
				}
				else{
					maxchild = 2*i+2;
				}
				temp = heap[i];
				heap[i] = heap[maxchild];
				heap[maxchild] = temp;

				i = maxchild;
				
			}
			//if it has only one child
			else if(((2*i+1) < heapSize) && (heap[i] < heap[2*i+1])){
				temp = heap[i];
				heap[i] = heap[2*i+1];
				heap[2*i+1] = temp;

				i = 2*i+1;
						
			}
			else{
				hasChildren = false;
				
			}
			//if it has no children then don't do anything	
		}
		
	}
	
	/**
	* Heapify the heap (put in heap ordering)
    *
	*/ 
	public void heapify(){
		
		if(empty()){
			System.out.println("Error in heapify(): Binary heap is empty");
			
		}
		else{
			//in heapify, we loop from right to left through the array
			//sifting down in each step
			for(int i = heapSize-1; i >= 0; i --){
				siftdown(i);
			}

		}
	}
	
	/**
	* Union "this" heap with @param heap2
	*
	* @param heap2	The heap to union to this heap. 
    *
	*/ 
	public void union(BinaryHeap heap2){
		
		for(int i = 0; i < heap2.heapSize; i ++){
			heap[heapSize] = heap2.heap[i];
			heapSize ++;
		}
		heapify();
		
	}
	
	public String toString(){
		
		String buffer = "";
		for(int i = 0; i < heapSize; i++){
			buffer += heap[i] + " ";
			
		}
		return buffer;
	}
	
	

}
