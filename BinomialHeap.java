/**
 * BinomialHeap.java
 *
 * PURPOSE:			A BinomialHeap class.		
 *
 * NOTE ABOUT OUTPUT: Output looks like this if you want to print out the entire heap:
 * 
 * root: priority: 7 numChildren: 0
 * root: priority: 6 numChildren: 1
 * child: parent: [6] priority: 5 numChildren: 0
 * root: priority: 4 numChildren: 2
 * child: parent: [4] priority: 2 numChildren: 1
 * child: parent: [2] priority: 1 numChildren: 0
 * child: parent: [4] priority: 3 numChildren: 0
 *
 * The first column says whether it is a node in the root list (root) or it is a
 * child of some root (child). If it is a child, it's parent is given by
 * the number in the brackets [ ], which is the priority of it's parent.
 * There is also information on what it's priority is and how many children
 * it has.
 * 
 * So here the rootlist is: [7, 6, 4] and for example, root with priority 4 has
 * children with priority 2 and priority 3.
 *
 */

public class BinomialHeap {
	
	//head of rootlist
	private Node head;
	
	public BinomialHeap(){
		head = null;
	}
	
	//inner class Node
	private class Node{
		
		private Node parent;
		private int priority;
		private int numChildren;
		private Node child;
		private Node sibling;
		
		//only BinomialHeap can access Node
		private Node(int priority){
			parent = null;
			this.priority = priority;
			numChildren = 0;
			child = null;
			sibling = null;
		}

		
		public String toString(){
			
			return "priority: " + priority + " numChildren: " + numChildren;
		}
		
	}//end of inner Node class
	
	/**
	* Merge the two binomial heaps.
	*
	* @param heap1	The first heap to merge.
	* @param heap2	The heap to merge to the first one.
	*
	*/ 
	private void merge(BinomialHeap heap1, BinomialHeap heap2){
		
		Node head = null;
		
		if(heap1.head != null && heap2.head != null){
			//iterate though both lists and add them to the unioned list
			//in increasing order
			Node h1curr = heap1.head;
			Node h2curr = heap2.head;
			
			//the the head of unioned list
			if(h1curr.numChildren < h2curr.numChildren){
				head = h1curr;
				h1curr = h1curr.sibling;
			}
			else{
				head = h2curr;
				h2curr = h2curr.sibling;		
			}
			//the end of the unioned list
			Node tail = head; 
			while(h1curr != null && h2curr != null){

				if(h1curr.numChildren < h2curr.numChildren){

					tail.sibling = h1curr;
					h1curr = h1curr.sibling;
				}
				else{
					tail.sibling = h2curr;
					h2curr = h2curr.sibling;
				}
				//point to the node we just added
				tail = tail.sibling;	
			}
			//process whatever is left of the remaining list
			if(h1curr != null){

				tail.sibling = h1curr;
				
			}
			else if(h2curr != null){
				
				tail.sibling = h2curr;
			}
			else{
				System.out.println("Made an error in union");
			}
		}
		else{
			//if one of the lists to union is null, then return one that is not null
			if(heap1.head == null && heap2.head != null){
				head = heap2.head;
			}
			else if(heap2.head == null && heap1.head != null){
				head = heap1.head;
			}
			else{
				head = null;
			}
		}
		this.head = head;	
	}
	
	/**
	* To make node2 the parent of node1. This method is useful
	* to union.
	*
	* @param node1	The new child of node2.
	* @param node2	The new parent of node1.
    *
	*/ 
	private void combine(Node node1, Node node2){
		node1.parent = node2;
		node1.sibling = node2.child;
		node2.child = node1;
		node2.numChildren = node2.numChildren + 1;
		
	}
	
	/**
	* Union "this" binomial heap to @param heap.
    *
	* @param heap	The heap to union to "this" heap.
	* 
	*               Important:
	*               This heap is destroyed before this method returns.
    *
	*/ 
	public void union(BinomialHeap heap){
		
		merge(this, heap);
		//not using this heap anymore
		heap.head = null;
		
		//only union if list not empty
		if(this.head != null){
			Node prev = null;
			Node curr = this.head;
			Node next = curr.sibling;
			
			while(next != null){
				//case 1 and case 2
				//case 1: Bi then Bj
				//case 2: Three in a row of Bi
				if((curr.numChildren != next.numChildren) || (next.sibling != null && next.sibling.numChildren == curr.numChildren)){
					
					//move along
					prev = curr;
					curr = next;

				}
				else{
					//case 3 and case 4
					//case 3: Bi then Bi (not three in a row), 1st Bi has lower priority, make it child of 2nd Bi
					//case 4: Bi then Bi (not three in a row), 1st Bi has higher priority, make it parent or 2nd Bi
					if(curr.priority > next.priority){
					
						//case 3
						curr.sibling = next.sibling;
						//order of arguments matters
						//combine(new parent, child of new parent)
						combine(next, curr);
					}
					else{
						//case 4
						if(prev == null){
							this.head = next;
						}
						else{
							prev.sibling = next;
						}
						combine(curr, next);
						curr = next;
					}
					
				}
				next = curr.sibling;
			}
		}
	}
	
	/**
	* Insert a new node into the existing heap.
	*
	* @param priority	The priority of the new node.
    *
	*/ 
	public void insert(int priority){
		
		BinomialHeap newheap = new BinomialHeap();
		Node b0 = new Node(priority);
		newheap.head = b0;
		union(newheap);

	}
	
	/**
	* Reverse the child heap and union it to the existing heap.
	*
	* @param max	The node with maximum priority, it is used to
	*               get the child list of the node we deleteMaxed.
    *
	*/ 
	private void reverseHeap(Node max){
		
		//put max's children into a new heap
		BinomialHeap childHeap = new BinomialHeap();
		childHeap.head = max.child;
				
		BinomialHeap reversedHeap = new BinomialHeap();
		Node curr = childHeap.head;
		Node next = null;
		
		while(curr != null){
			//need to save sibling before it is changed 
			//since curr need to iterate to next node
			next = curr.sibling;	
			curr.sibling = reversedHeap.head;
			reversedHeap.head = curr;
			
			curr = next;
		}
		
		//union the new heap with the old heap
		union(reversedHeap);

		
	}

	/**
	* Delete the node in the heap that has maximum priority.
    * 
    * @return maxValue	The priority of the node with maximum priority.
	*/ 
	public int deleteMax(){
		//find root in rootlist with highest priority and unlink it from rootlist
		Node prev = null;
		Node curr = this.head;
		//set max initially to head
		Node max = this.head;	
		Node prevMax = null;
		int maxValue = -999999;	//sentinel value
		
		if(this.head != null){
				
			while(curr != null){
				
				
				if(curr.priority > max.priority){
					max = curr;
					maxValue = max.priority;
					prevMax = prev;
				}
				
				prev = curr;
				curr = curr.sibling;
			}
			
			maxValue = max.priority;
			
			//max is in middle of list or end of list
			if(max != null && prevMax != null){
				prevMax.sibling = max.sibling;
				reverseHeap(max);
				
			}
			//max is first node in list
			else if(max != null && prevMax == null){
				this.head = max.sibling;
				reverseHeap(max);
			}
		}
		
		return maxValue;
	}
	

	/**
	* Prints the rootlist of a node in one call. Over several
	* recursive calls, it prints the entire subtree of @param child.
	*
	* @param child	The child to print it's rootlist.
    *
	*/ 
	private String printChildRootList(Node child){
		String buffer = "";
		while(child != null){
			buffer += "child: parent: [" + child.parent.priority + "] " + child.toString() + "\n";
			if(child.child != null){
				buffer += printChildRootList(child.child);
			}
			child = child.sibling;

		}
		return buffer;
		
	}
	
	public String toString(){
		String buffer = "";
		Node curr = head;
		while(curr != null){
			buffer += "root: " + curr.toString() + "\n";
			if(curr.child != null){
				buffer += printChildRootList(curr.child);
			}
			curr = curr.sibling;
		}
		return buffer;
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
