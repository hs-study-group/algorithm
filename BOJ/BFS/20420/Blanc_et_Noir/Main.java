//https://www.acmicpc.net/problem/20420

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

class Node{
	int y, x, r, l;
	Node(int y, int x, int l, int r){
		this.y = y;
		this.x = x;
		this.l = l;
		this.r = r;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static int encode(char ch) {
		switch(ch) {
			case 'U':return 0;
			case 'R':return 1;
			case 'D':return 2;
			default:return 3;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		String answer = "No";
		
		int[][] dist = {{-1,0},{0,1},{1,0},{0,-1}};
		
		int R = Integer.parseInt(temp[0]);
		int C = Integer.parseInt(temp[1]);
		int K = Integer.parseInt(temp[2]);
		
		int[][] m = new int[R][C];
		boolean[][][][] v = new boolean[R][C][K+1][K+1];
		
		for(int i=0; i<R; i++) {
			temp = br.readLine().split("");
			for(int j=0; j<temp.length; j++) {
				m[i][j] = encode(temp[j].charAt(0));
			}
		}
		
		Queue<Node> q = new LinkedList<Node>();
		v[0][0][K][K] = true;
		q.add(new Node(0,0,K,K));

		while(!q.isEmpty()) {
			Node n = q.poll();

			if(n.y==R-1&&n.x==C-1) {
				answer = "Yes";
				break;
			}
			
			int d = m[n.y][n.x];
			int y = n.y + dist[d][0];
			int x = n.x + dist[d][1];
			
			if(y>=0&&y<R&&x>=0&&x<C) {
				if(!v[y][x][n.l][n.r]) {
					v[y][x][n.l][n.r] = true;
					q.add(new Node(y,x,n.l,n.r));
				}
			}
			
			for(int i=1; i<=3; i++) {
				if(n.r>=i) {
					int nd = (d+i)%4;
					y = n.y + dist[nd][0];
					x = n.x + dist[nd][1];
					if(y>=0&&y<R&&x>=0&&x<C&&!v[y][x][n.l][n.r-i]) {
						v[y][x][n.l][n.r-i] = true;
						q.add(new Node(y,x,n.l,n.r-i));
					}
				}
			}
			
			for(int i=1; i<=3; i++) {
				if(n.l>=i) {
					int nd = (d-i+4)%4;
					y = n.y + dist[nd][0];
					x = n.x + dist[nd][1];
					if(y>=0&&y<R&&x>=0&&x<C&&!v[y][x][n.l-i][n.r]) {
						v[y][x][n.l-i][n.r] = true;
						q.add(new Node(y,x,n.l-i,n.r));
					}
				}
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}