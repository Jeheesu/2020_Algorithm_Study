package week4;

public class Tree {
	public String word;
	public String part;
	public String comm;	
	public Tree left;
	public Tree right;
	public Tree pa;
	
	public Tree(String word,String part, String comm) {
		this.word = word;
		this.part = part;
		this.comm = comm;		
		left = null;
		right = null;
		pa = null;
	}
}
