package week5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Prog03_1 {
	public static Node [] arr = new Node[1000000];
	public static int size = 0;
	public static PrintWriter file;
	public static Queue<Integer> que = new LinkedList<Integer>();
	public static Scanner kb;

	public static void main(String[] args) {
		readData();
		readTwice();
		System.out.println(size);
		System.out.println("a. 한 지점을 입력하세요");
		makeA();
		System.out.println("b. 한 지점을 입력하세요");
		makeB();
		kb.close();
	}
	private static void makeB() {
		System.out.print("$ ");
		kb = new Scanner(System.in);
		String str2 = kb.nextLine();
		int i = 0;
		while(i < size) {
			if(arr[i].area.equals(str2))
				break;
			i++;
		}
		try {
			file = new PrintWriter(new FileWriter("result.txt"));
			dfs(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.close();		
	}
	private static void dfs(int i) {
		if(arr[i].v2 ==1)
			return;
		file.println(arr[i].area + "  " + arr[i].la + "  " + arr[i].lo);
		arr[i].v2 = 1;
		Node tmp = arr[i].next;
		while(tmp != null) {			
			if(tmp.v2 == 0)
				dfs(tmp.num);
			tmp = tmp.next;
		}
	}
	private static void makeA() {
		System.out.print("$ ");
		kb = new Scanner(System.in);
		String str1 = kb.nextLine();
		bfs(str1);
	}
	private static void bfs(String str1) {
		int count = 0;
		int n = 0;
		for (; n < size; n++) {
			if (arr[n].area.equals(str1)) {
				System.out.println(arr[n].area);
				break;
			}
		}
		arr[n].v1 = 1;
		que.add(n);
		while ( count < 10 && !que.isEmpty()) {
			int del = (int) que.remove();
			Node tmp = arr[del].next;
			while (tmp != null) {
				if (arr[tmp.num].v1 == 0) {
					que.add(tmp.num);
					arr[tmp.num].v1 = 1;
					System.out
					.println(arr[tmp.num].area 
							+ "   " + arr[tmp.num].lo + "   " 
							+ arr[tmp.num].la);
				}
				tmp = tmp.next;
			}
			count++;
		}
	}
	private static void readTwice() {
		try {
			Scanner inFile = new Scanner(new File("roadList2.txt"));			
			while(inFile.hasNext()) {
				String kb = inFile.nextLine();
				String pla1 = kb.split("\\t")[0];
				String pla2 = kb.split("\\t")[1];
				int i = 0;
				int x = 0;
				int y = 0;
				while(i < size) {					
					if(arr[i].area.equals(pla1))
						x = i;
					else if(arr[i].area.equals(pla2))
						y = i;
					if(x != 0 && y != 0)
						break;
					i++;
				}
				double weight = calDistance(arr[x].la, arr[x].lo,
						arr[y].la, arr[y].lo);
				AddNode(arr[x],arr[y], weight);
				AddNode(arr[y],arr[x], weight);
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void AddNode(Node n1, Node n2, double weight) {
		if(n1.next == null) {
			n1.next = new Node();
			n1.next.area = n2.area;
			n1.next.lo = n2.lo;
			n1.next.la = n2.la;
			n1.next.w = weight;
			n1.next.num = findNum(n1.next.area);
		}
		else {
			Node tmp = n1.next;
			Node tmp2 = null;
			while(tmp != null) {
				tmp2 = tmp;
				tmp = tmp.next;
			}
			tmp = new Node();
			tmp2.next = tmp;
			tmp.area = n2.area;
			tmp.lo = n2.lo;
			tmp.la = n2.la;
			tmp.w = weight;
			tmp.num = findNum(tmp.area);
		}		
	}
	private static int findNum(String area) {
		int j = -1;
		for(j = 0; j< size; j++) {
			if(arr[j].area.equals(area))
				break;
		}
		if(j == -1) {
			System.out.println("error");
			System.exit(1);
		}
		return j;
	}
	private static double calDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta, dist;
		theta = lon1 - lon2;
		dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1))
				* Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		dist = dist * 1000.0;
		return dist;
	}
	private static double rad2deg(double rad) {
		return (double) (rad * (double) 180 / Math.PI);
	}
	private static double deg2rad(double deg) {
		return (double)(deg* Math.PI / (double)180);
	}
	private static void readData() {
		try {
			Scanner inFile = new Scanner(new File("alabama.txt"));
			while(inFile.hasNextLine()) {
				String str = inFile.nextLine();
				String place = str.split("\\t")[0];
				double lo = Double.parseDouble(str.split("\\t")[1]);
				double la = Double.parseDouble(str.split("\\t")[2]);
				arr[size] = new Node();
				arr[size].area = place;
				arr[size].lo = lo;
				arr[size].la = la;				
				size++;
			}
			for(int i = 0; i < size; i++) {
				arr[i].num = i;
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
