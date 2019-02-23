package com.ubaas.service;


public class Graph {
	class EdgeNode{
		int adjvex;
		EdgeNode nextEdge;
	}

	class VexNode{
		int data;
		EdgeNode firstEdge;
		boolean isVisted;
		public boolean isVisted() {
			return isVisted;
		}
		public void setVisted(boolean isVisted) {
			this.isVisted = isVisted;
		}

	}

	VexNode[] vexsarray ;
	int[] visited = new int[100];
	boolean[] isVisited = new boolean[100];
	int[][] bian = new int[4][4];
	int[][] bianOpenif = new int[4][4];

	public void linkLast(EdgeNode target,EdgeNode node) {
		while (target.nextEdge!=null) {
			target=target.nextEdge;
		}
		target.nextEdge=node;
	}

	public int getPosition(int data) {
		for(int i=0;i<vexsarray.length;i++) {
			if (data==vexsarray[i].data) {
				return i;
			}
		}
		return -1;
	}


	public void buildGraph(int[] vexs) {
		int vLen = vexs.length;
		//int eLen = edges.length;
		vexsarray = new VexNode[vLen];

		for(int i=0;i<vLen;i++) {
			vexsarray[i] = new VexNode();
			vexsarray[i].data = vexs[i];
			vexsarray[i].firstEdge = null;
		}
		
		for(int j = 0; j < 4; j++){
			for(int k = 0; k < 4; k++){
				bian[j][k] = 0;
				bianOpenif[j][k] = 0;
			}
		}
		
//		for(int i=0;i<eLen;i++) {
//
//			int a = edges[i][0];
//			int b = edges[i][1];
//
//			int start = getPosition(a);
//			int end = getPosition(b);
//
//			EdgeNode edgeNode = new EdgeNode();
//			edgeNode.adjvex = end;
//
//			if (vexsarray[start].firstEdge == null) {
//				vexsarray[start].firstEdge = edgeNode;
//			} else {
//				linkLast(vexsarray[start].firstEdge,edgeNode);
//			}
//		}
	}

	public void addEdge(int a , int b , int x){//娣诲姞杈�,A浠ｈ〃浣嶇疆
		EdgeNode b1 = new EdgeNode();
		b1.adjvex = vexsarray[b].data;
		
		if(vexsarray[a].firstEdge==null){//鎶婅竟娣诲姞鍒癆鑺傜偣鍚庨潰
			vexsarray[a].firstEdge=b1;
		}else{
			linkLast(vexsarray[a].firstEdge,b1);
		}
		bian[a][b] = x+1;
		bian[b][a] = x+1;
		bianOpenif[a][b] = 1;
		bianOpenif[b][a] = 1;
		EdgeNode a1 = new EdgeNode();
		a1.adjvex = vexsarray[a].data;
		
		if(vexsarray[b].firstEdge==null){//鎶婅竟娣诲姞鍒癆鑺傜偣鍚庨潰
			vexsarray[b].firstEdge=a1;
		}else{
			linkLast(vexsarray[b].firstEdge,a1);
		}
	}

	public int getOpenif(int x, int y){
		return bianOpenif[x][y];
	}
	public int getBian(int x, int y){
		return bian[x][y];
	}
	public void printGraph() {
		for(int i=0;i<vexsarray.length;i++) {
			System.out.printf("%d--",vexsarray[i].data);
			EdgeNode node = vexsarray[i].firstEdge;
			while (node!=null) {
				System.out.printf("%d(%d)--",node.adjvex,vexsarray[node.adjvex].data);
				node = node.nextEdge;
			}
			System.out.println("\n");
		}
	}
}

