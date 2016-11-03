//Stephen Gordon
import java.io.*;

public class LinkedList
{
   private class Node implements Comparable<Integer>
   {
      private Integer data; 
      private Node next; 
      private Node prev; //Goes back, required for insertion sort, Doubly Linked List time!
   
      public Node(Integer data) 
      { 
         this.data = data; 
         next = null;
         prev = null;
      }
      @Override //I don't think this is used for this but keep just in case
      public int compareTo(Integer potato){
  		if(potato >= this.data){
  			return 0;
  		} else{
  			return 1;
  		}
  	}
   }	
   
   private Node head;
   
   public void add( Integer data )
   {
      Node t = head;
      Node n = new Node( data );
      if( head == null )
      {
         head = n;
         return;
      }
      while( t.next != null )
         t = t.next;
      t.next = n;
      n.prev = t;
   }
   
   public int length(){ //added this
		int count = 0;
		Node temp = head;
		while(temp!=null){
			temp = temp.next;
			count++;
		}//end while
		return count;
	}
   //Following two sorts screw up the .prev of the list after sorting, project didn't need this to be fixed.
   public void bubbleSort( ) // Quite honestly the worst thing in existence
   {
	   boolean flag = true;
	   int temp;
	   int count = 0; //Reduces amount of list to traverse so it doesn't needlessly traverse sorted stuff
	   int length = length();
	   System.out.println("Processing Bubble Sort...");
	   while(flag){
			Node curr = head;
			Node next = head.next;
			flag = false;
			for(int i = 0; i < length-(1+count); i++){ // Never ever ever call a method in a for loop declaration
				if(curr.data > next.data){
					//Sort stuff here
					temp = curr.data;
					curr.data = next.data;
					next.data = temp;
					flag = true;
				} //end if
				curr = curr.next;
				next = next.next;
			}//end for
			count++;
		} //end flag while
	   System.out.println("Bubble Sort complete!");
   }
   
   public void selectionSort( )
   {
		Node min;
		int temp;
		System.out.println("Processing Selection Sort...");
		for(Node node1 = head; node1!=null; node1 = node1.next){ //Traverses through list, setting min to the current node until the end
			min = node1;
			for(Node node2 = node1; node2!= null; node2 = node2.next){ // Traverses through list to find smallest number than min.
				if(min.data > node2.data){ 
					min = node2; //Sets min to node2, repeats until min is smallest number.
				} //end if
			}//end for
			if(min != node1){ //Swaps the two numbers in the list
				temp = min.data;
				min.data = node1.data;
				node1.data = temp;
			} //end if
		}//end for
		System.out.println("Selection Sort complete!");
   } //end method
   
   public void insertionSort( )
   {
	   	System.out.println("Processing Insertion Sort...");
		int value; // Value to insert;
		
		for(Node current = head.next; current != null; current = current.next){
			value = current.data;
			//This requires the usage of doubly linked list to traverse backward
			while(current.prev != null && current.prev.data > value){
				current.data = current.prev.data;
				current = current.prev;
			} //end while
			current.data = value;
			//Never forget terrible hours wasted on trying to do this with singly linked list
		} //end for
		System.out.println("Insertion Sort complete!");
   } //end method
   
   public static LinkedList ReadFile(LinkedList list){
	   BufferedReader b = null;
	   String line = "this will be replaced";
	   int parsedInt;
	   try{
		   b = new BufferedReader(new FileReader("sample.txt")); //Switch back to sample.txt
		   while(line != null){
			   line = b.readLine();
			   if(line == null){
				   break; //kills loop if the line read is null
			   } //end if
			   parsedInt = Integer.parseInt(line);
			   list.add(parsedInt);
		   } //end while
	   }///end try
	   catch(IOException e){
			System.out.println("Error");
			e.printStackTrace();
		} //end catch
	   return list;
   } //end method
   
   public static boolean WriteToOutFile( String s, String fname )
   {
      BufferedWriter w = null;
      try
      {
    	 System.out.println("Writing to " + fname + "...");
         w = new BufferedWriter( new FileWriter( new File( fname ) ) );
         w.write( s );
         w.close( );
         System.out.println("File " + fname + " written!");
      }
      catch( IOException e )
      {
         System.out.println( "Unable to write to file" );
         return false;
      }
      catch( Exception e )
      {
         System.out.println( "Unknown error occured" );
         e.printStackTrace( );
         return false;
      }
      
      return true;
   }
   
   public String toString( )
   {
      String r = "";
      Node t = head;
      while( t != null )
      {
         r += t.data + "\r\n";   //Windows is really really stupid...
         t = t.next;
      }
      return r;
   } //end method

   
   public static void main(String args[]){
	   //Linked Lists
	   LinkedList bubble = new LinkedList();
	   LinkedList selection = new LinkedList();
	   LinkedList insertion = new LinkedList();
	   //Other stuff
	   long start;
	   long end;
	   System.out.println("Reading File...");
	   ReadFile(bubble);
	   ReadFile(selection);
	   ReadFile(insertion);
	   System.out.println("File reading complete!");
	   System.out.println("-------------------------------------------------");
	   
	   start = System.currentTimeMillis();
	   bubble.bubbleSort();
	   end = System.currentTimeMillis();
	   System.out.println("\nTotal bubble sort duration: " + (end -start) + " ms\n");
	   WriteToOutFile(bubble.toString(), "bubbleSorted.txt");
	   System.out.println("-------------------------------------------------");
	   
	   start = System.currentTimeMillis();
	   selection.selectionSort();
	   end = System.currentTimeMillis();
	   System.out.println("\nTotal selection sort duration: " + (end -start) + " ms\n");
	   WriteToOutFile(selection.toString(), "selectionSorted.txt");
	   System.out.println("-------------------------------------------------");
	   
	   start = System.currentTimeMillis();
	   insertion.insertionSort();
	   end = System.currentTimeMillis();
	   System.out.println("\nTotal insertion sort duration: " + (end -start) + " ms\n");
	   WriteToOutFile(insertion.toString(), "insertionSorted.txt");
	   System.out.println("-------------------------------------------------");
	   System.out.println("Done");
	   
   } //end main method
}