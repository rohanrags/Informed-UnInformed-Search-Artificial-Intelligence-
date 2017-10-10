package ai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class homework {

	private boolean res=false;

	class Node {
		int i,j,num;
		Node parent;

		public Node() {}

		public Node(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public Node(int i, int j, Node parent) {
			this.i = i;
			this.j = j;
			this.parent = parent;
		}

		public Node(int i, int j, int num, Node parent) {
			this.i = i;
			this.j = j;
			this.num = num;
			this.parent = parent;
		}
	}

	public static void main(String[] args) throws IOException {
		String search = null;
		int n,p,i,j;
		/*Scanner sc = new Scanner(new File("./src/ai/inputd34.txt"));*/
		Scanner sc = new Scanner(new File("input.txt"));
		homework hm = new homework();
		search = sc.next();
		n = sc.nextInt();
		p=sc.nextInt();
		int[][] a = new int[n][n];
		for(i=0;i<n;i++) {
			if(sc.hasNext()) {
				String temp = sc.next();
				for(j=0;j<n;j++) {
					a[i][j]=Character.getNumericValue(temp.charAt(j));
				}
				
			}			
		}
		sc.close();

		switch(search) {
		case "DFS" : hm.dfs(a,n,p);
		break;
		case "BFS" : hm.bfs(a,n,p);
		break;
		case "SA" :	hm.sa(a,n,p);
		break;
		default : 
		}
	}

	/***************************UTILITY FUNCTIONS**************************************/

	/*private void print(int[][] a, int n) {
		int x,y;
		for(x=0;x<n;x++) {
			for(y=0;y<n;y++) {
				System.out.print(a[x][y]);
			}
			System.out.println("");
		}
	}*/

	/***************************DFS**************************************/

	private void dfs(int[][] a, int n, int p) throws IOException {
		/*BufferedWriter writer = new BufferedWriter(new FileWriter("./src/ai/output.txt"));*/
		long start_time = System.nanoTime();
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		Stack<Node> stack = new Stack<Node>();
		addInitialNodesToStack(a,n,stack); //Add initial row nodes

		while(!stack.isEmpty() && ((System.nanoTime()-start_time)/1000000000.0)<270){
			Node node = stack.pop(); // Remove head
			if(node.num==p) {
				//Found solution
				res=true;
				/*print_dfs_soln(a,node,n);*/			
				while(node!=null) {
					a[node.i][node.j]=1;
					node=node.parent;
				}
				/*System.out.println("OK");*/
				writer.write("OK\n");
				for(int i=0;i<n;i++) {
					for(int j=0;j<n;j++) {
						writer.write(String.valueOf(a[i][j]));
					}
					writer.write("\n");
				}
				
				break;
			}
			AddSafeNodestoStack(a,n,p,stack,node); //Adding safe Nodes to Queue
		}

		if(res==false) {
			/*System.out.println("FAIL");*/
			writer.write("FAIL\n");
		}
		
		writer.close();
	}
	
	private void AddSafeNodestoStack(int[][] a, int n, int p, Stack<Node> s, Node node) {
		int[][] temp = new int[n][n];
		int i,j;
		//copy a into temp
		for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				temp[i][j]=a[i][j];
			}
		}

		Node tempNode = node;
		//calculating safe locations in temp : 0-safe, 1-unsafe, 2-trees of all lizards

		while (node!=null) {

			//left row
			for(j=node.j;j>=0;j--) {
				if(temp[node.i][j]==2) 
					break;
				else if(temp[node.i][j]==1)
					continue;
				else
					temp[node.i][j]=1;
			}

			//left-top code
			for(i=node.i,j=node.j;i>=0 && j>=0;i--,j--) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			//top
			for(i=node.i;i>=0;i--) {
				if(temp[i][node.j]==2) 
					break;
				else if(temp[i][node.j]==1) 
					continue;
				else
					temp[i][node.j]=1;
			}

			//right-top
			for(i=node.i,j=node.j;i>=0 && j<n;i--,j++) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			//right
			for(j=node.j;j<n;j++) {
				if(temp[node.i][j]==2) 
					break;
				else if(temp[node.i][j]==1) 
					continue;
				else
					temp[node.i][j]=1;
			}

			//right-down
			for(i=node.i,j=node.j;i<n && j<n;i++,j++) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			//down
			for(i=node.i;i<n;i++) {
				if(temp[i][node.j]==2) 
					break;
				else if(temp[i][node.j]==1) 
					continue;
				else
					temp[i][node.j]=1;
			}

			//down-left
			for(i=node.i,j=node.j;i<n && j>=0;i++,j--) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			node=node.parent;
		}


		boolean loc=false;
		for(i=tempNode.i;i<n;i++) {
			for(j=n-1;j>=0;j--) {
				if(temp[i][j]==0) {
					if(loc==false)
						loc=true;
					Node nod = new Node(i,j,tempNode.num+1,tempNode);
					s.push(nod);
				}
			}

			if(loc==true){
				break;
			}
		}

	}

	private void addInitialNodesToStack(int[][] a, int n, Stack<Node> stack) {
		int i=0,j=0;
		boolean loc=false;
		for(i=0;i<n;i++) {
			for(j=n-1;j>=0;j--) {
				if(a[i][j]!=2) {
					if(loc==false) 
						loc=true;
					Node node = new Node(i,j,1,null);
					stack.push(node);
				}	
			}
			if(loc==true){
				break;
			}
		}
	}

	/***************************DFS**************************************/

	/***************************BFS**************************************/

	private void bfs(int[][] a, int n, int p) throws IOException {
		long start_time = System.nanoTime();
		/*BufferedWriter writer = new BufferedWriter(new FileWriter("./src/ai/output.txt"));*/
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		Queue<Node> q = new LinkedList<Node>();
		addInitialNodesToQueue(a,n,q); //Add initial row nodes		
		
		while(!q.isEmpty() && ((System.nanoTime()-start_time)/1000000000.0)<270){
			Node node = q.poll(); // Remove head
			if(node.num==p) {
				//Found solution
				res=true;
				/*print_bfs_soln(a,node,n);*/
				
				while(node!=null) {
					a[node.i][node.j]=1;
					node=node.parent;
				}
				
				/*System.out.println("OK");*/
				writer.write("OK\n");
				for(int i=0;i<n;i++) {
					for(int j=0;j<n;j++) {
						writer.write(String.valueOf(a[i][j]));
					}
					writer.write("\n");
				}
				
				break;
			}
			AddSafeNodestoQueue(a,n,p,q,node); //Adding safe Nodes to Queue
		}

		if(res==false) {
			/*System.out.println("FAIL");*/
			writer.write("FAIL\n");
		}
		
		writer.close();
	}

	private void AddSafeNodestoQueue(int[][] a, int n, int p, Queue<Node> q, Node node) {
		int[][] temp = new int[n][n];
		int i,j;
		//copy a into temp
		for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				temp[i][j]=a[i][j];
			}
		}

		Node tempNode = node;
		//calculating safe locations in temp : 0-safe, 1-unsafe, 2-trees of all lizards

		while (node!=null) {

			//left row
			for(j=node.j;j>=0;j--) {
				if(temp[node.i][j]==2) 
					break;
				else if(temp[node.i][j]==1)
					continue;
				else
					temp[node.i][j]=1;
			}

			//left-top code
			for(i=node.i,j=node.j;i>=0 && j>=0;i--,j--) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			//top
			for(i=node.i;i>=0;i--) {
				if(temp[i][node.j]==2) 
					break;
				else if(temp[i][node.j]==1) 
					continue;
				else
					temp[i][node.j]=1;
			}

			//right-top
			for(i=node.i,j=node.j;i>=0 && j<n;i--,j++) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			//right
			for(j=node.j;j<n;j++) {
				if(temp[node.i][j]==2) 
					break;
				else if(temp[node.i][j]==1) 
					continue;
				else
					temp[node.i][j]=1;
			}

			//right-down
			for(i=node.i,j=node.j;i<n && j<n;i++,j++) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			//down
			for(i=node.i;i<n;i++) {
				if(temp[i][node.j]==2) 
					break;
				else if(temp[i][node.j]==1) 
					continue;
				else
					temp[i][node.j]=1;
			}

			//down-left
			for(i=node.i,j=node.j;i<n && j>=0;i++,j--) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) 
					continue;
				else
					temp[i][j]=1;
			}

			node=node.parent;
		}

		//Checking all safe locations and adding to queue.
		/*for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				if(temp[i][j]==0) {
					Node nod = new Node(i,j,tempNode.num+1,tempNode);
					q.add(nod);
				}
			}
		}*/
		boolean loc=false;
		for(i=tempNode.i;i<n;i++) {
			for(j=0;j<n;j++) {
				if(temp[i][j]==0) {
					if(loc==false)
						loc=true;
					Node nod = new Node(i,j,tempNode.num+1,tempNode);
					q.add(nod);
				}
			}

			if(loc==true){
				break;
			}
		}

	}

	private void addInitialNodesToQueue(int[][] a, int n, Queue<Node> q) {
		int i=0,j=0;
		boolean loc=false;
		for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				if(a[i][j]!=2) {
					if(loc==false) 
						loc=true;
					Node node = new Node(i,j,1,null);
					q.add(node);
				}	
			}
			if(loc==true){
				break;
			}
		}
	}

	/***************************BFS**************************************/

	/***************************SA***************************************/
	private void sa(int[][] a, int n, int p) throws IOException {

		int[][] temp = new int[n][n];
		double temperature=35;
		int cur_soln_conflict,new_soln_conflict;
		long start_time = System.nanoTime();
		/*long end_time = (long) (start_time + 270*1000000000.0);*/
		res=false;
		/*System.out.println("Actual start time : " + start_time);
		System.out.println("Expected end time : " + end_time);	*/	
		
		List<Node> curSoln, newSoln = null;
		
		int i,j,liz_space_count=0;
		//copy a into temp
		for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				temp[i][j]=a[i][j];
				if(a[i][j]==0) 
					liz_space_count++;	
			}
		}
		
		/*BufferedWriter writer = new BufferedWriter(new FileWriter("./src/ai/output.txt"));*/
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		
		if(liz_space_count<p) {
			/*System.out.println("FAIL");*/
			writer.write("FAIL");
			writer.close();
			return;
		}

		
		temp = generate_random_soln(temp,n,p);
		curSoln = generate_random_nodes(temp, n, p);
		cur_soln_conflict = calculate_conflicts(temp,curSoln,n,p);
		/*print(temp,n);
		System.out.println();*/		
		while(temperature>0 && ((System.nanoTime()-start_time)/1000000000.0)<270) {
			int[][] newTemp = get_new_soln(temp,curSoln,n,p);
			/*print(newTemp,n);System.out.println();*/
			newSoln = generate_random_nodes(newTemp, n, p);
			new_soln_conflict = calculate_conflicts(newTemp,newSoln,n,p);
			if(new_soln_conflict==0) {
				res=true;
				break;
			}
			//print(temp,n);
			//System.out.println(cur_soln_conflict + "|" + new_soln_conflict);
			boolean d = decide_probability(cur_soln_conflict,new_soln_conflict,temperature);
			if(d) {
				curSoln=newSoln;
				for(i=0;i<n;i++) {
					for(j=0;j<n;j++) {
						temp[i][j]=newTemp[i][j];
					}
				}
				cur_soln_conflict=new_soln_conflict;
			}
			temperature = temperature*0.97;
			//System.out.println("*"+temperature);
		}
		
		if(res) {
			/*System.out.println("OK");*/
			writer.write("OK\n");
			for (Node node : newSoln) {
				a[node.i][node.j]=1;
			}
			for(i=0;i<n;i++) {
				for(j=0;j<n;j++) {
					writer.write(String.valueOf(a[i][j]));
				}
				writer.write("\n");
			}
		} else {
			/*System.out.println("FAIL");*/
			writer.write("FAIL");
		}
		
		/*long actual_end_time = System.nanoTime();*/
		/*System.out.println("Actual end time : " + actual_end_time);
		System.out.println("Temperature : " + temperature);*/
		writer.close();
	}

	private boolean decide_probability(int oldC, int newC, double temperature) {		
		if(newC<oldC)
			return true;
		
	    double C = Math.exp(-(newC-oldC)/(temperature));
	    double R = Math.random();
	 
	    if (R <= C) {
	        return true;
	    }
	    
	    return false;	
	}

	private int[][] get_new_soln(int[][] temp, List<Node> nodes, int n, int p) {
		
		Random r = new Random();
		int i,j,change_liz=r.nextInt(p),change_liz_i=r.nextInt(n),change_liz_j=r.nextInt(n);
		int[][] newTemp = new int[n][n];
		
		for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				newTemp[i][j]=temp[i][j];
			}
		}
		
		Node t = nodes.get(change_liz);
		newTemp[t.i][t.j]=0;
		
		while(newTemp[change_liz_i][change_liz_j]!=0) {
			change_liz_i=r.nextInt(n);
			change_liz_j=r.nextInt(n);
		}
		newTemp[change_liz_i][change_liz_j]=1;		
		return newTemp;
	}

	private int calculate_conflicts(int[][] temp, List<Node> nodes, int n, int p) {
		
		int num=0,attack=0,i,j;
		Node newNode;
		
		while(num<nodes.size()){
			
			newNode = nodes.get(num);

			//left row
			for(j=newNode.j-1;j>=0;j--) {
				if(temp[newNode.i][j]==2) 
					break;
				else if(temp[newNode.i][j]==1) {
					attack++;
					break;
				}
			}

			//left-top code
			for(i=newNode.i-1,j=newNode.j-1;i>=0 && j>=0;i--,j--) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) {
					attack++;
					break;
				}
			}

			//top
			for(i=newNode.i-1;i>=0;i--) {
				if(temp[i][newNode.j]==2) 
					break;
				else if(temp[i][newNode.j]==1) {
					attack++;
					break;
				}
			}

			//right-top
			for(i=newNode.i-1,j=newNode.j+1;i>=0 && j<n;i--,j++) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) {
					attack++;
					break;
				}
			}

			//right
			for(j=newNode.j+1;j<n;j++) {
				if(temp[newNode.i][j]==2) 
					break;
				else if(temp[newNode.i][j]==1) {
					attack++;
					break;
				}
			}

			//right-down
			for(i=newNode.i+1,j=newNode.j+1;i<n && j<n;i++,j++) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) {
					attack++;
					break;
				}
			}

			//down
			for(i=newNode.i+1;i<n;i++) {
				if(temp[i][newNode.j]==2) 
					break;
				else if(temp[i][newNode.j]==1) {
					attack++;
					break;
				}
			}

			//down-left
			for(i=newNode.i+1,j=newNode.j-1;i<n && j>=0;i++,j--) {
				if(temp[i][j]==2) 
					break;
				else if(temp[i][j]==1) {
					attack++;
					break;
				}
			}
			
			num++;
		}
		
		return attack;
	}

	
	private int[][] generate_random_soln(int[][] temp, int n, int p) {

		int i,j,count=p;
		Random r = new Random();

		while(count>0) {
			i=r.nextInt(n);
			j=r.nextInt(n);
			if(temp[i][j]==0) { //what if there are no spaces to keep a lizzy, infinite loop
				temp[i][j]=1;
				count--;
			}
		}
		return temp;
	}
	
	private List<Node> generate_random_nodes(int[][] temp, int n, int p) {
		
		List<Node> newNodes = new ArrayList<>();
		
		int i,j,count=0;
		for(i=0;i<n;i++) {
			for(j=0;j<n;j++) {
				if(temp[i][j]==1) {
					Node newNode = new Node(i,j,++count,null);
					newNodes.add(newNode);
				}
			}
		}
		
		return newNodes;
	}

	/***************************SA**************************************/
}
