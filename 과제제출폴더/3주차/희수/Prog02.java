package week4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Prog02 {

    public static int size = 0;
    public static Tree root;
	private static Scanner inFile;
	public static Scanner kb = new Scanner(System.in);
		
	public static void main(String[] args) {
		
		while(true) {
			System.out.print("$ ");
    		String command = kb.next();
    		
    		if(command.equals("read")) {	
    			readFile("shuffled_dict.txt");
    		}
    		else if(command.equals("size")) {
    			System.out.println(size);
    		}
    		else if(command.equals("find")) {
    			find();
    		}
    		else if(command.equals("add")) {
    			add();    			
    		}
    		else if(command.equals("delete")) {
    			delete();    			
    		}
    		else if(command.equals("deleteall")) {
    			deleteall();
    		}
    		else if(command.equals("exit"))
    			break;
		}
		kb.close();
	}

	private static void deleteall() {
		int count = 0;
		try {
			Scanner defi = new Scanner(new File("to_be_deleted_words.txt"));
			while(defi.hasNext()) {
				String str = defi.next();
				Tree dee = findWord(new Tree(str,null,null));
				if(dee != null) {
					deleteNode(dee);							
				}
				count++;
			}
			System.out.println(count + " words were deleted successfully.");
			defi.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void delete() {	
		String baba = kb.next();
		Tree delete = findWord(new Tree(baba,null,null));
		if(delete == null)
			System.out.println("Not Found.");
		else {
			deleteNode(delete);
			size--;
			System.out.println("Deleted successfully.");
		}		
	}

	private static void deleteNode(Tree node) {
		if(node == null)
			return;
		if(node.left==null && node.right==null) {			// 자식이 0
			if(node != root) {
				if(node.pa.left == node)
					node.pa.left = null;
				else
					node.pa.right = null;
			}
			else
				root = null;
		}
		else if(node.left != null && node.right != null) {	// 자식이 2
			Tree swap = Seccessor(node.right);
			node = swap;
			deleteNode(swap);
		}
		else {												// 자식이 1
			Tree x = node;
			Tree y;
			if(node.left == null)
				y = node.right;		
			else
				y = node.left;
			if(y != null)
				y.pa = x.pa;		
			if(x.pa == null)		
				root = node;
			else if(x.pa.left == node)
				x.pa.left = y;
			else
				x.pa.right = y;
		}
	}

	private static Tree Seccessor(Tree node) {
		while(node.left != null)
			node = node.left;
		return node;
	}
	private static void add() {		
		System.out.print("Word : ");
		String voca = kb.next();
		System.out.print("Class : ");
		kb.nextLine();
		String part = kb.nextLine();
		System.out.print("Meaning : ");
		String comm = kb.nextLine();
		makeTree(new Tree(voca,part,comm));
		size++;
	}

	private static void find() {		
		String str = kb.next();
		Tree node = findWord(new Tree(str,null,null));    			
		if(node == null)
			System.out.println("empty");
		else
			System.out.println(node.comm);		
	}

	private static Tree findWord(Tree chk) {
		Tree node = root;
		while(node!=null) {
			if(chk.word.compareToIgnoreCase(node.word)==0)
				return node;
			else if(chk.word.compareToIgnoreCase(node.word)>0)
				node = node.right;
			else
				node = node.left;
		}
		return null;
	}

	private static void readFile(String FileName) {
		String str;
		try {
			inFile = new Scanner(new File(FileName));
			while(inFile.hasNext()) {
				str = inFile.nextLine();
				int start = str.indexOf( "(" );
				int end = str.indexOf( ")" );
				if(start<0 || end < 0)
					return;
				String voca = str.substring(0, start - 1);
				String part = str.substring(start - 1, end + 1);
				String comment = str.substring(end + 1);
				makeTree(new Tree(voca, part, comment));
				size++;
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			System.out.println( "No file." );
			System.exit(0);
		}
	}

	private static void makeTree(Tree tree) {
		Tree node = root;
		Tree tmp = null;
		while(node!=null) {
			tmp = node;
			if(tree.word.compareToIgnoreCase(node.word) > 0)
				node = node.right;
			else
				node = node.left;
		}
		if(tmp==null)
			root = tree;
		else if(tree.word.compareToIgnoreCase(tmp.word)>0) {
			tmp.right = tree;
			tree.pa = tmp;
		}
		else {
			tmp.left = tree;
			tree.pa = tmp;
		}
	}

}

