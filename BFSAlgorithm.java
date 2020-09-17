/* Talha BAYBURTLU - 150118066
 * This program's aim is to find minimum hops for first laptop(node) to 
 * every laptop(node) by using BFS algorithm .*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

public class BFSAlgorithm {
	private static int adjacencyMatrix[][]; // Adjacency matrix
	
	public static void main(String[] args) {
		File inFile = new File(args[0]);
		Scanner inFileScanner = null;
		Writer outFileWriter = null;
		
		try { // Opening input text document.
			inFileScanner = new Scanner(inFile);
		} catch(Exception ex) { 
			System.out.println(args[0] + " couldn't find!");
			System.exit(-1);
		}
		
		try { // Creating output text document.
			outFileWriter = new FileWriter("output" + args[0].charAt(args[0].length() - 5) + ".txt");
		} catch (IOException e) {
			System.out.println("output.txt couldn't create!");
			System.exit(-1);
		}
		
		String nextLine = null;
		double info[][] = null;
		boolean encounteredWithSize = false;
		
		for (int i = 0 ; inFileScanner.hasNextLine();) { // Reading every line from input text file.
			nextLine = inFileScanner.nextLine();
			if (nextLine.startsWith("#") || nextLine.equals("") || nextLine.equals(" ")) // Passing comment sections.
				continue;
			else if (!encounteredWithSize) { // Taking number of total nodes.
				info = new double[Integer.parseInt(nextLine + "")][3];
				encounteredWithSize = true;
				continue;
			}
			 
			String[] nums = nextLine.split("\t");
			for (int k = 0; k < 3 ; k++ ) // Taking x,y and r values.
				info[i][k] = Double.parseDouble(nums[k]);
			
			i++;
		}
		
		
		adjacencyMatrix = new int[info.length][info.length];
		for (int i = 0 ; i < adjacencyMatrix.length ; i++) // Filling adjacency matrix based on nodes intersects or not.
			for (int k = i + 1 ; k < info.length ; k++)
				if (Math.pow(info[i][0] - info[k][0],2) + Math.pow(info[i][1] - info[k][1], 2) <= Math.pow(info[i][2] + info[k][2], 2)) {
					adjacencyMatrix[i][k] = 1;
					adjacencyMatrix[k][i] = 1;
				}
		
		
		try {
			int hops[] = bfs(0);
			for (int i = 0 ; i < adjacencyMatrix.length ; i++)
				outFileWriter.append(hops[i] + "\n");
		} catch (IOException e) {
			System.out.println("IOException");
			System.exit(-2);
		}
		
		System.out.println("Output created at output" + args[0].charAt(args[0].length() - 5) + ".txt.");
		
		try { //Closing writer and scanner.
			inFileScanner.close();
			outFileWriter.close();
		} catch (IOException e) {
			System.out.println("File(s) could not close.");
		}
	}
	
	public static int[] bfs(int start) { // Records every number of hops from start node to end node and returns end node's minimum number of hops.
		ArrayList<Integer> visitedIndexes = new ArrayList<Integer>(); // Holds visited indexes of adjacency matrix for current node.
		int hops[] = new int[adjacencyMatrix.length]; // Holds number of hops for every node.
		
		// Adding start node to queue.
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(start);
		visitedIndexes.add(start); 
		
		while (!queue.isEmpty()) {
			int current = queue.peek(); // Taking tail node from queue for searching purposes.
			queue.poll(); // Removing head node from queue for emptying purposes.
			
			for (int i = 0; i < adjacencyMatrix.length ; i++) {
				if (!visitedIndexes.contains(i) && adjacencyMatrix[current][i] == 1) { // Searching every node that is neighbor of current node and not visited yet.
					hops[i] = hops[current] + 1; // Updating neighbor number of hops value as current node's number of hops plus one.
					queue.add(i);
					visitedIndexes.add(i);
				}
			}
		}
		return hops;
	}
}
