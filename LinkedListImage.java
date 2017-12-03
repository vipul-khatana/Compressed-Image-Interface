//

// This is the implementation of the CompressedImageInterface using Linked Lists

public class LinkedListImage implements CompressedImageInterface {
    public DoublyLinkedList [] arrlist;
    int height, width;
	
	//This is the constructor of an object belonging to the LinkedListImage class. 
	//It constructs the array of linked lists from the input file.
	public LinkedListImage(String filename)
	{
        try {
            ReadFile file = new ReadFile(filename);
                String[] filen = file.OpenFile();
            arrlist = new DoublyLinkedList[filen.length];
            for(int i = 0; i < filen.length; i++) {
                arrlist[i] = new DoublyLinkedList();
            }

            int counter1 =0;
            int counter2 = 0;
            height = filen.length -1;
            width = (filen[1].length() + 1)/2;
            arrlist[0].addFirst(String.valueOf(width));
            arrlist[0].addFirst(String.valueOf(height));

             for (int j = 1; j<=height;j++) {// Iterating the array to make an array of linked lists
                for(int i =0;i<filen[1].length();i = i+2){
			if(filen[j].charAt(i) == '0'){
			    counter1 = i/2;
			    if(i+2 < filen[1].length()) {
				while(filen[j].charAt(i+2) == '0'){
				    i=i+2;
				    if(i+2 >= filen[1].length())
					break;
				}
			    }
			    counter2 = i/2;
			    arrlist[j].addLast(String.valueOf(counter1));
			    arrlist[j].addLast(String.valueOf(counter2));
			}
                }
             }
	}
        catch(Exception e){
            System.out.println("File not found");
        }
         for (int i = 1; i <= height; i++) //We first add -1 as the initial node for all the lists
         {
            arrlist[i].addLast("-1"); 
         } 
    }

    //This is the second constructor. It builds an array of linked lists from a given
    //matrix representation of an image
    public LinkedListImage(boolean[][] grid, int width, int height)
    {
        arrlist = new DoublyLinkedList[height + 1];
            for(int i = 0; i < (height + 1); i++) {
                arrlist[i] = new DoublyLinkedList();
            }
        int counter1 =0;
        int counter2 = 0;
        arrlist[0].addFirst(String.valueOf(width));
        arrlist[0].addFirst(String.valueOf(height));

         for (int j = 0; j<height;j++){ // Iterating the array to make an array of linked lists
            for(int i =0;i<width;i++){
                if(!grid[j][i]){
                    counter1 = i;
                    if(i+1 < width) {
                        while(!grid[j][i+1]){
                            i++;
                            if(i + 1 >= width)
                                break;
                        }
                    }
                    counter2 = i;
                    arrlist[j+1].addLast(String.valueOf(counter1));
                    arrlist[j+1].addLast(String.valueOf(counter2));
                }
            }
         }
         for (int i = 1; i <= height; i++){ //We first add -1 as the initial node for all the lists
            arrlist[i].addLast("-1"); 
         }
    }
    
    // Returns pixel value at (i,j)
    public boolean getPixelValue(int x, int y){
        boolean state = true;
        ListNode  n = arrlist[x+1].head;
        for(; Integer.parseInt(n.getElement()) < y; n = n.getNext()){ 
          if(Integer.parseInt(n.getElement()) == -1){
                break;
            }
            state = !state;
        }
        if (Integer.parseInt(n.getElement()) == y)
            state = false;
        return state;         
    }

    // Sets the pixel value at (i,j)
    public void setPixelValue(int x, int y, boolean val){
      if (getPixelValue(x,y) == val){
            return;
        }
        else{ 
            boolean bool = true;
            ListNode temp = arrlist[x+1].head;
            int start = -1;
            
            while (start != y){
		start++;
                if (start == Integer.parseInt(temp.getElement())){
                    bool = !bool;
                    temp = temp.getNext();
                    continue;
                } 
            }
            if (bool){
                if (start == Integer.parseInt(temp.getElement())){
                    temp.setValue(String.valueOf(Integer.parseInt(temp.getElement()) - 1));
                }
                if (start == Integer.parseInt(temp.getElement()) - 1){
			if (temp.getPrev() != null && start == Integer.parseInt(temp.getPrev().getElement()) + 1){
                        arrlist[x+1].removeAfter(temp.getPrev().getPrev());
                        temp = temp.getNext();
                        arrlist[x+1].removeAfter(temp.getPrev().getPrev());
                    }
                    else
                    temp.setValue(String.valueOf(Integer.parseInt(temp.getElement()) - 1));
                }
                if (temp.getPrev() != null && start == Integer.parseInt(temp.getPrev().getElement()) + 1){
                    temp = temp.getPrev();
                    temp.setValue(String.valueOf(Integer.parseInt(temp.getElement()) + 1));
                }
                else{
                    arrlist[x+1].addAfter(temp.getPrev(),String.valueOf(start));
                    arrlist[x+1].addAfter(temp.getPrev(),String.valueOf(start));                    
                }
            }
            else{
                if (start == Integer.parseInt(temp.getElement())){
                    if ( temp.getElement().equals(temp.getPrev().getElement())){
                        arrlist[x+1].removeAfter(temp.getPrev().getPrev());
                        temp = temp.getNext();
                        arrlist[x+1].removeAfter(temp.getPrev().getPrev());
                    }
                    else{
                        temp.setValue(String.valueOf(Integer.parseInt(temp.getElement()) + 1));   
                    }
                }
                else{
                    arrlist[x+1].addAfter(temp.getPrev(),String.valueOf(start-1));
                    arrlist[x+1].addAfter(temp.getPrev(),String.valueOf(start+1)); 
                }
            }
        }
    }

    // Returns the number of black pixels in each row of the matrix.
    public int[] numberOfBlackPixels(){ 
        int []counter = new int[height];
        for(int i = 1; i <= height; i++) {
            int count = 0;
            int start = -1, end = -1;
            for(ListNode  n = arrlist[i].head; n!=arrlist[i].tail; n = n.getNext()) {
                if(start == -1) {
                    start = Integer.parseInt(n.getElement());
                }
                else {
                    end = Integer.parseInt(n.getElement());
                    count += end - start + 1;
                    start = -1;
                }
            }
            counter[i-1] = count;
        }
        return counter;
    }

    // Reverses the complete doubly linked list, head becomes the tail and vice versa
    public DoublyLinkedList reverse(DoublyLinkedList a) {
        DoublyLinkedList temp = new DoublyLinkedList ();
        int start = 0;
        for(ListNode  n = a.head; n!=null; n = n.getNext().getNext()) {
            if (start == Integer.parseInt(n.getElement())) {
                start = Integer.parseInt(n.getNext().getElement()) + 1;
            }
            else if (n.getElement().equals("-1")) {
                temp.addLast(String.valueOf(start));
                temp.addLast(String.valueOf(width-1));
                break;
            }
            else if (start >= width -1) {
                break;
            }
            else {
                temp.addLast(String.valueOf(start));
                temp.addLast(String.valueOf(Integer.parseInt(n.getElement())-1));
                start = Integer.parseInt(n.getNext().getElement()) + 1;
            }
        }
        temp.addLast("-1");
        return temp;
    }

    // Reverses the color of every pixel in the image. 
    public void invert(){
        for(int i = 1; i <= height; i++){
            DoublyLinkedList  temp = new DoublyLinkedList ();
            int start = 0;
            for(ListNode  n = arrlist[i].head; n!=null; n = n.getNext().getNext()) {
                if (start == Integer.parseInt(n.getElement())) {
                    start = Integer.parseInt(n.getNext().getElement()) + 1;
                }
                else if (n.getElement().equals("-1")) {
                    temp.addLast(String.valueOf(start));
                    temp.addLast(String.valueOf(width-1));
                    break;
                }
                else if (start >= width -1) {
                    break;
                }
                else {
                    temp.addLast(String.valueOf(start));
                    temp.addLast(String.valueOf(Integer.parseInt(n.getElement())-1));
                    start = Integer.parseInt(n.getNext().getElement()) + 1;
                }
            }
            temp.addLast("-1");
            arrlist[i].head = temp.head;
            arrlist[i].tail = temp.tail;
            arrlist[i].num_nodes = temp.num_nodes;
        }
    } 

    public int max(int a, int b)
    {
        if ( a >  b)
            return a;
        else 
            return b;
    } 

    public int min(int a, int b)
        if ( a <  b)
            return a;
        else 
            return b;
    }

    // Performs the intersection of two doubly linked list containing boolean values
    public DoublyLinkedList intersection(DoublyLinkedList a, DoublyLinkedList b) {
        int start = -1, end = -1;
        DoublyLinkedList c = new DoublyLinkedList();
        for(ListNode n = a.head, m = b.head; n != null && m != null;) {
            if(n.getNext() != null && m.getNext() != null) {
                start = max(Integer.parseInt(n.getElement()), Integer.parseInt(m.getElement()));
                end = min(Integer.parseInt(n.getNext().getElement()), Integer.parseInt(m.getNext().getElement()));
                if(start <= end) {
                    c.addLast(String.valueOf(start));
                    c.addLast(String.valueOf(end));
                    n = n.getNext().getNext();
                    m = m.getNext().getNext();
                }
                else {
                    if(Integer.parseInt(n.getElement()) < Integer.parseInt(m.getElement())) {
                        n = n.getNext().getNext();
                    }
                    else {
                        m = m.getNext().getNext();
                    }
                }
            }
            else {
                break;
            }
        }
        c.addLast("-1");
        return c;
    } 

    // Performs the union of two doubly linked lists containing boolean values
    public DoublyLinkedList union(DoublyLinkedList a, DoublyLinkedList b) {
        int start = -1, end = -1;
        DoublyLinkedList c = new DoublyLinkedList();
        for(ListNode n = a.head, m = b.head; n != null && m != null;) {
            if(n.getNext() != null || m.getNext() != null) {
                if (n.getNext() == null) {
                    c.addLast(m.getElement());
                    c.addLast(m.getNext().getElement());
                    m = m.getNext().getNext();
                    continue;
                }
                if (m.getNext() == null) {
                    c.addLast(n.getElement());
                    c.addLast(n.getNext().getElement());
                    n = n.getNext().getNext();
                    continue;
                }
                start = max(Integer.parseInt(n.getElement()), Integer.parseInt(m.getElement()));
                end = min(Integer.parseInt(n.getNext().getElement()), Integer.parseInt(m.getNext().getElement()));
                if(start <= end) {
                    int start1 = min(Integer.parseInt(n.getElement()), Integer.parseInt(m.getElement()));
                    int end1 = max(Integer.parseInt(n.getNext().getElement()), Integer.parseInt(m.getNext().getElement()));
                    c.addLast(String.valueOf(start1));
                    c.addLast(String.valueOf(end1));
                    n = n.getNext().getNext();
                    m = m.getNext().getNext();
                }
                else {
                    if(Integer.parseInt(n.getElement()) < Integer.parseInt(m.getElement())) {
                        c.addLast(n.getElement());
                        c.addLast(n.getNext().getElement());
                        n = n.getNext().getNext();
                    }
                    else {
                        c.addLast(m.getElement());
                        c.addLast(m.getNext().getElement());
                        m = m.getNext().getNext();
                    }
                }
            }
            else 
		break;
        }
        c.addLast("-1");
        return c;
    } 

    // Performs the functionality of 'AND' operator between two doubly linked list
    public void performAnd(CompressedImageInterface img){
        LinkedListImage img2 = (LinkedListImage) img;
        for(int i = 1; i < arrlist.length; i++) {
            DoublyLinkedList uni = union(arrlist[i], img2.arrlist[i]);
            arrlist[i].head = uni.head;
            arrlist[i].tail = uni.tail;
            arrlist[i].num_nodes = uni.num_nodes;
        }
    }
    
    // 'OR' operator between two doubly linked lists
    public void performOr(CompressedImageInterface img){
        LinkedListImage img2 = (LinkedListImage) img;
        for(int i = 1; i < arrlist.length; i++) {
            DoublyLinkedList intersect = intersection(arrlist[i], img2.arrlist[i]);
            arrlist[i].head = intersect.head;
            arrlist[i].tail = intersect.tail;
            arrlist[i].num_nodes = intersect.num_nodes;
        }
    }
    
    // 'XOR' operator between two doubly linked lists
    public void performXor(CompressedImageInterface img){
        LinkedListImage img2 = (LinkedListImage) img;
        for(int i = 1; i < arrlist.length; i++) {
            DoublyLinkedList intersect1 = union(arrlist[i], img2.arrlist[i]);
            DoublyLinkedList intersect2 = union(reverse(arrlist[i]), reverse(img2.arrlist[i]));
            DoublyLinkedList uni = intersection(intersect1, intersect2);
            DoublyLinkedList rev = reverse(uni);
            arrlist[i].head = rev.head;
            arrlist[i].tail = rev.tail;
            arrlist[i].num_nodes = rev.num_nodes;
        }
       
    }
    
    // String representation of the uncompressed format of the image 
    public String toStringUnCompressed(){
	String st = new String();
        st = "";
        st = st + (arrlist[0].head.getElement()) + " " + arrlist[0].head.getNext().getElement() + ",";
        for(int j =1; j < arrlist.length; j++) {
            int start = 0;
            for(ListNode  n = arrlist[j].head; n != null; n = n.getNext().getNext()) {
                if(n.getElement().equals("-1")) {
                    for(int k = start; k < Integer.parseInt(arrlist[0].head.getNext().getElement()); k++) {
                        st = st + " 1";
                    }
                    st = st + ",";
                    break;
                }
                else if(start >= Integer.parseInt(arrlist[0].head.getNext().getElement())) {
                    st = st + ",";
                    break;
                }
                else {
                    for(int k = start; k < Integer.parseInt(n.getElement()); k++)
                        st = st + " 1";
                    for(int k = Integer.parseInt(n.getElement()); k <= Integer.parseInt(n.getNext().getElement()); k++)
                        st = st + " 0";
                    start = Integer.parseInt(n.getNext().getElement()) + 1;
                }
            }
        }
        st = st.substring(0,st.length()-1);
        return st;
    }
    
    // String representation of the compressed form of the image
    public String toStringCompressed(){
        String st = new String();
        st = "";
        st = st + (arrlist[0].head.getElement()) + " " + arrlist[0].head.getNext().getElement() + ", ";
        for(int j = 1; j < arrlist.length; j++){
	    ListNode  temp = arrlist[j].head;
            while(!(temp == null)){
		if(temp.getElement().equals("-1"))
                    st = st + "-1, ";
                else
                    st = st + temp.getElement() + " ";
                temp = temp.getNext();
            }
        }
        st = st.substring(0,st.length()-2);
        return st;
    }

    // Testing the implementation
    public static void main(String[] args) {
    	boolean success = true;

    	// check constructor from file
    	CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

    	// check toStringCompressed
    	String img1_compressed = ((LinkedListImage)img1).toStringCompressed();
    	String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
    	success = success && (img_ans.equals(img1_compressed));
    	if (!success){
    		System.out.println("Constructor (file) or toStringCompressed ERROR");
    		return;
    	}

    	// check getPixelValue
    	boolean[][] grid = new boolean[16][16];
    	for (int i = 0; i < 16; i++)
    		for (int j = 0; j < 16; j){
                try{
        			grid[i][j] = img1.getPixelValue(i, j);                
                }
                catch (PixelOutOfBoundException e){
                    System.out.println("Errorrrrrrrr");
                }
    		}

    	// check constructor from grid
    	CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
    	String img2_compressed = img2.toStringCompressed();
    	success = success && (img2_compressed.equals(img_ans));
    	if (!success){
    		System.out.println("Constructor (array) or toStringCompressed ERROR");
    		return;
    	}

    	// check Xor
        try{
        	img1.performXor(img2);        
        }
        catch (BoundsMismatchException e){
            System.out.println("Errorrrrrrrr");
        }
    	for (int i = 0; i < 16; i++){
    		for (int j = 0; j < 16; j++){
                try{
        			success = success && (!img1.getPixelValue(i,j));                
                }
                catch (PixelOutOfBoundException e){
                    System.out.println("Errorrrrrrrr");
                }
    		}
	}
    	if (!success){
    		System.out.println("performXor or getPixelValue ERROR");
    		return;
    	}
    	// check setPixelValue
    	for (int i = 0; i < 16; i++){
            try{
    	    	img1.setPixelValue(i, 0, true);            
            }
            catch (PixelOutOfBoundException e){
                System.out.println("Errorrrrrrrr");
            }
        }

    	//check numberOfBlackPixels
    	int[] img1_black = img1.numberOfBlackPixels();
    	success = success && (img1_black.length == 16);
    	for (int i = 0; i < 16 && success; i++)
    		success = success && (img1_black[i] == 15);
    	if (!success)
    	{
    		System.out.println("setPixelValue or numberOfBlackPixels ERROR");
    		return;
    	}

    	// check invert
        img1.invert();
        for (int i = 0; i < 16; i++){
            try{
                success = success && !(img1.getPixelValue(i, 0));            
            }
            catch (PixelOutOfBoundException e){
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success){
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

    	// check Or
        try{
            img1.performOr(img2);        
        }
        catch (BoundsMismatchException e){
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++){
                try{
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e){
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success){
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try{
            img1.performAnd(img2);    
        }
        catch (BoundsMismatchException e){
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try{
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));             
                }
                catch (PixelOutOfBoundException e){
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success){
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }



    	// check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));
	    
        if (!success){
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}
