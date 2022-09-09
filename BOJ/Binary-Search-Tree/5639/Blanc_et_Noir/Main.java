//https://www.acmicpc.net/problem/5639

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//이진탐색트리의 각 노드는 값과 왼쪽, 오른쪽 두 개의 자식 노드를 가질 수 있음
class Node{
	int val;
	Node left, right;
	Node(int val){
		this.val = val;
		left = null;
		right = null;
	}
}

class BinarySearchTree{
	BufferedWriter bw = null;
	int size = 0;
	Node root;
	
	//이진탐색트리 생성자
	BinarySearchTree(){
		//초기에는 root 노드가 null임
		root = null;
	}

	//이진탐색트리에 데이터를 추가하는 메소드
	public void add(int val) {
		root = add(root, val);
	}
	
	//이진탐색트리에 데이터를 재귀적으로 추가하는 메소드
	public Node add(Node node, int val) {
		//리프 노드라면
		if(node==null) {
			//노드를 새로 생성함
			node = new Node(val);
			//트리의 크기를 1증가시킴
			size++;
		//추가하고자 하는 값이 현재 탐색중인 노드의 값보다 작다면
		}else if(val<node.val) {
			//왼쪽 자식노드에 새로운 노드를 추가함
			node.left = add(node.left,val);
		//추가하고자하는 값이 현재 탐색중인 노드의 값보다 크다면
		}else if(val>node.val) {
			//오른쪽 자식노드에 새로운 노드를 추가함
			node.right = add(node.right,val);
		}
		//새로 생성된 노드를 반환함
		return node;
	}
	
	//postorder 방식으로 트리를 순회하는 메소드
	public void postorder() throws IOException {
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		postorder(root);
		bw.flush();
		bw.close();
	}
	
	//posrtorder 방식으로 트리를 재귀적으로 순회하는 메소드
	public void postorder(Node node) throws IOException {
		//해당 노드가 null이라면 그대로 종료함
		if(node == null) {
			return;
		//해당 노드가 null이 아니라면
		}else {
			//왼쪽, 오른쪽 자식노드에 대해서 postorder 순회를 재귀적으로 수행함
			postorder(node.left);
			postorder(node.right);
			
			//자식노드를 모두 탐색하고 나서 자기 자신의 값을 출력함
			bw.write(node.val+"\n");
			return;
		}
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws Exception{
		BinarySearchTree bst = new BinarySearchTree();
		
		String temp = null;
		
		//트리에 추가할 값들을 입력받음
		while(true) {
			temp = br.readLine();
			
			//해당 문제에서는 입력받을 횟수를 제공하지 않으므로 EOF인지 아닌지 판단함으로써 입력을 종료함
			if(temp==null||temp.equals("")) {
				break;
			}
			
			//이진탐색트리에 값을 추가함
			bst.add(Integer.parseInt(temp));
		}
		
		//이진탐색트리를 순회함
		bst.postorder();
		
		br.close();
	}
}