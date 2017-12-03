/*
This is an implementation of the doubly linked list.
Every node consists of the value, pointer to the next node and the previous node
 */

import java.util.*;
import java.util.Scanner;
import java.io.IOException;

// Defining the list node
class ListNode  {
	private String element;
	private ListNode  next;
	private ListNode  prev;
	
	//Constructors 
	public ListNode() {
		this(null);
	}

	public ListNode(String item) {
		this(item,null,null);
	}

	public ListNode(String item, ListNode  n, ListNode  p) {
		element = item;
		next = n;
		prev = p;
	}
	
	public ListNode  getNext() {//Returns the next element
		return next;
	}
	
	public ListNode  getPrev() {//Returns the prev element
		return prev;
	}
	
	public String getElement() {//Returns the element of the list node
		return element;
	}
	
	public void setNext(ListNode  n) {// Changes the next pointer
		next =n;
	}

	public void setPrev(ListNode  p) {//Changes the previous pointer
		prev = p;
	}

	public void setValue(String item) {//Set the value of a node
		element = item;
	}
}

public class DoublyLinkedList{
	public int num_nodes;
	public ListNode head;
	public ListNode tail;

	public DoublyLinkedList() {
		num_nodes = 0;
	}

	public boolean isEmpty(){
		return num_nodes == 0;
	}

	public int size(){
		return num_nodes;
	}
	
	public void addFirst(String item){// Add a node at the beginning of the list
		if (num_nodes == 0){
			ListNode node = new ListNode (item);
			head = node;
			tail = node;
			num_nodes++;
		}
		else {
			ListNode node = new ListNode (item, head, null);
			head.setPrev(node);
			head = node;
			num_nodes++;
		}
	}

	public void addAfter(ListNode n, String item){// Add a node after position n
		if (n != null){
			ListNode node = new ListNode (item, n.getNext(),n);
			n.setNext(node);
			n = n.getNext().getNext();
			n.setPrev(node);
			if (n == tail)
				tail = node;
		}
		else {
			addFirst(item);
		}
		num_nodes++;
	}
	
	public void addLast(String item){// Adds a node at the end of the list 
		if (num_nodes == 0){
			ListNode  node = new ListNode  (item);
			head = node;
			tail = node;
			num_nodes++;
		}
		else{
			ListNode  node = new ListNode  (item, null, tail);
			tail.setNext(node);
			tail = node;
			num_nodes++;
		}
	}
	
	public String getFirst() throws IOException { // Returns the head
		if (num_nodes == 0){
			throw new IOException();
		}
		else return head.getElement();
	}
	
	public String removeFirst() { //Removes the head 
		if (head == null){
			System.out.println("The list is Empty, hence can not remove first");
			return null;
		}
		else {
			String result = head.getElement();
			head = head.getNext();
			head.setPrev(null);
			num_nodes--;
			return result;
		}
	}

	public String removeAfter(ListNode  n) {// Removes the node after the node n
		if (n != null){
			if (n != tail){
				String temp = n.getNext().getElement();
				n.setNext(n.getNext().getNext());
				n = n.getNext();
				n.setPrev(n.getPrev().getPrev());
				num_nodes--;
				return temp;
			}
			else{
				System.out.println("There is no next node to remove");
				return null;
			}
		}
		else {
			removeFirst();
		}
		return null;
	}

	public String remove(String item) {// Removes and returns a value from a list
		for (ListNode  n = head;n != null; n = n.getNext()){
			if (n.getElement().equals(item)){
				String temp = item;
				if(n == head) {
					head = n.getNext();
				}
				else if(n == tail) {
					tail = n.getPrev();
				}
				else {
					n.getPrev().setNext(n.getNext());
					n.getNext().setPrev(n.getPrev());
				}
				num_nodes--;
				return temp;
			}
		}
		return null;
	}

	public boolean contains(String item) {
		boolean val = false;
		for (ListNode  n = head; n != null; n = n.getNext()) {
			if (n.getElement().equals(item)) {
				val = true;
				break;
			}
		}
		return val;
	}

	//Testing the implementation
	public static void main(String [] args) throws IOException 
	{
		DoublyLinkedList list = new DoublyLinkedList();
		System.out.println("Part 1"); 
		list.addFirst("aaa"); 
		list.addFirst("bbb"); 
		list.addFirst("ccc"); 
		for(ListNode temp = list.head;temp!=null;temp=temp.getNext())
		{	System.out.println(temp.getElement());
		} 
		System.out.println("Part 2");
		ListNode current = list.head; 
		list.addAfter(current, "xxx"); 
		list.addAfter(current, "yyy"); 
		for(ListNode temp = list.head;temp!=null;temp=temp.getNext())
		{	
			System.out.println(temp.getElement());
		} 
		System.out.println("Part 3"); 
		current = list.head;
		if (current != null) 
		{
      		current = current.getNext();
       		list.removeAfter(current);
    	}
    	for(ListNode temp = list.head;temp!=null;temp=temp.getNext())
		{	
			System.out.println(temp.getElement());
		} 
    	System.out.println("Part 4");
    	String l = list.removeAfter(null);
    	System.out.println(l);
    	System.out.println(list.head.getElement());
	}

}
