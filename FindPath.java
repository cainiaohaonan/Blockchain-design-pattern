package com.ubaas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.ubaas.service.Graph.EdgeNode;


public class FindPath {
	//浠ｈ〃鏌愯妭鐐规槸鍚﹀湪stack涓�,閬垮厤浜х敓鍥炶矾  
	public Map<Integer,Boolean> states=new HashMap();  

	//瀛樻斁鏀惧叆stack涓殑鑺傜偣  
	public Stack<Integer> stack=new Stack();  
	List<Integer> list = new ArrayList<Integer>();
	//鎵撳嵃stack涓俊鎭�,鍗宠矾寰勪俊鎭�  
	public void printPath(){  
		//			StringBuilder sb=new StringBuilder();  
		//			for(Integer i :stack){  
		//				sb.append(i+"->");  
		//			}  
		//			sb.delete(sb.length()-2,sb.length());  
		//			System.out.println(sb.toString());
		Stack<Integer> stack1=new Stack();
		System.out.println("鏍堟槸鍚︿负绌�:"+ stack1.isEmpty());
		for(Integer i :stack){  
			stack1.push(i);  
		} 
		for(Integer i :stack1){
			System.out.print(i);
			list.add(i);
		}
		System.out.println();
	}  

	//寰楀埌x鐨勯偦鎺ョ偣涓簓鐨勫悗涓�涓偦鎺ョ偣浣嶇疆,涓�-1璇存槑娌℃湁鎵惧埌  
	public int getNextNode(Graph graph,int x,int y){  
		int next_node=-1;  
		EdgeNode edge=graph.vexsarray[x].firstEdge;  
		if(null!=edge&&y==-1){  
			int n=edge.adjvex;  
			//鍏冪礌杩樹笉鍦╯tack涓�  
			if(!states.get(n))  
				return n;  
			return -1;  
		}  

		while(null!=edge){  
			//鑺傜偣鏈闂�  
			if(edge.adjvex==y){  
				if(null!=edge.nextEdge){  
					next_node=edge.nextEdge.adjvex;  

					if(!states.get(next_node))  
						return next_node;  
				}  
				else  
					return -1;  
			}  
			edge=edge.nextEdge;  
		}  
		return -1;  
	}



	public void visit(Graph graph,int x,int y){  
		//鍒濆鍖栨墍鏈夎妭鐐瑰湪stack涓殑鎯呭喌  
		for(int i=0;i<graph.vexsarray.length;i++){  
			states.put(i,false);  
		}  
		//stack top鍏冪礌  
		int top_node;  
		//瀛樻斁褰撳墠top鍏冪礌宸茬粡璁块棶杩囩殑閭绘帴鐐�,鑻ヤ笉瀛樺湪鍒欑疆-1,姝ゆ椂浠ｈ〃璁块棶璇op鍏冪礌鐨勭涓�涓偦鎺ョ偣  
		int adjvex_node=-1;  
		int next_node;  
		stack.add(x);  
		states.put(x,true);  

		while(!stack.isEmpty()){  
			top_node=stack.peek();  
			//鎵惧埌闇�瑕佽闂殑鑺傜偣  
			if(top_node==y){  
				//鎵撳嵃璇ヨ矾寰�  
				printPath();  
				adjvex_node=stack.pop();  
				states.put(adjvex_node,false);  
			}  
			else{  
				//璁块棶top_node鐨勭advex_node涓偦鎺ョ偣  
				next_node=getNextNode(graph,top_node,adjvex_node);  
				if(next_node!=-1){  
					stack.push(next_node);  
					//缃綋鍓嶈妭鐐硅闂姸鎬佷负宸插湪stack涓�  
					states.put(next_node,true);  
					//涓存帴鐐归噸缃�  
					adjvex_node=-1;  
				}  
				//涓嶅瓨鍦ㄤ复鎺ョ偣锛屽皢stack top鍏冪礌閫�鍑�   
				else{  
					//褰撳墠宸茬粡璁块棶杩囦簡top_node鐨勭adjvex_node閭绘帴鐐�  
					adjvex_node=stack.pop();  
					//涓嶅湪stack涓�  
					states.put(adjvex_node,false);  
				}  
			}  
		}  
	}  
}
