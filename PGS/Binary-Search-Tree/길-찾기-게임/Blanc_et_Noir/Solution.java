//https://school.programmers.co.kr/learn/courses/30/lessons/42892

import java.util.*;

//노드 정보를 저장할 노드 클래스 선언
class Node implements Comparable{
	//자신의 왼쪽, 오른쪽 자식노드
    Node left, right;
    
    //자신의 y, x좌표
    int y, x;
    
    Node(int y, int x){
        this.y = y;
        this.x = x;
    }
    
    @Override
    //y좌표가 더 큰 노드가 먼저 우선순위큐에서 반환되도록 한다.
    public int compareTo(Object obj){
        Node n = (Node) obj;
        
        if(this.y>n.y){
            return -1;
        }else if(this.y<n.y){
            return 1;
        }else{
            return 0;
        }
    }
}

//이진 탐색 트리 클래스 선언
class BinarySearchTree{
	//루트 노드
    Node root = null;
    
    //트리의 사이즈
    int size = 0;
    
    //전위, 후위순회 결과를 저장할 리스트 선언
    ArrayList<Integer> preorderList = new ArrayList<Integer>();
    ArrayList<Integer> postorderList = new ArrayList<Integer>();
    
    //특정한 노드를 기준으로 서로 x값을 비교하여 왼쪽 또는 오른쪽 자식 노드에 새로운 노드를 추가하는 메소드 
    public Node add(Node node, int y, int x){
        if(node == null){
            return node = new Node(y, x);
        }else{
            if(x < node.x){
                node.left = add(node.left,y,x);
            }else{
                node.right = add(node.right,y,x);
            }
            return node;
        }
    }
    
    //y, x좌표를 전달받아 이진 탐색 트리에 데이터를 추가하는 메소드
    public void add(int y, int x){
        root = add(root, y, x);
        size++;
    }
    
    //전위 순회후 그 결과를 리스트에 추가하는 메소드
    public void preorder(HashMap<Integer,Integer> index, Node node){
        if(node == null){
            return;
        }else{
            preorderList.add(index.get(node.x));
            preorder(index, node.left);
            preorder(index, node.right);
        }
    }
    
    //후위 순회후 그 결과를 리스트에 추가하는 메소드
    public void postorder(HashMap<Integer,Integer> index, Node node){
        if(node == null){
            return;
        }else{
            postorder(index, node.left);
            postorder(index, node.right);
            postorderList.add(index.get(node.x));
        }
    }
    
    //전위 순회를 시작하는 메소드
    public void preorder(HashMap<Integer,Integer> index){
        preorder(index, root);
    }
    
    //후위 순회를 시작하는 메소드
    public void postorder(HashMap<Integer,Integer> index){
        postorder(index, root);
    }
}

class Solution {
    public int[][] solution(int[][] nodeinfo) {
        BinarySearchTree bst = new BinarySearchTree();
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        HashMap<Integer,Integer> index = new HashMap<Integer,Integer>();
        
        for(int i=0; i<nodeinfo.length; i++){
        	//우선순위큐에 노드 정보를 추가한다.
            pq.add(new Node(nodeinfo[i][1],nodeinfo[i][0]));
            
            //index 해쉬맵은 해당 노드의 번호를 기억하기 위한 객체다.
            index.put(nodeinfo[i][0],i+1);
        }
        
        //우선순위큐에서 y좌표가 큰 노드를 먼저 이진 탐색 트리에 추가한다
        while(!pq.isEmpty()){
            Node node = pq.poll();
            bst.add(node.y, node.x);
        }
        
        //전위 및 후위 순회한 결과를 리스트로 저장함
        bst.preorder(index);
        bst.postorder(index);
        
        //전위 및 후위 순회한 결과 리스트를 배열로 변환함
        int[][] answer = new int[2][bst.size];
        
        for(int i=0; i<bst.size; i++){
            answer[0][i] = bst.preorderList.get(i);
            answer[1][i] = bst.postorderList.get(i);
        }
        
        return answer;
    }
}