package cn.sxt.List;

/**
 * 自定义一个链表(查、增、删)
 * @author jasonzhu
 *
 */
public class SxtLinkedList<E> {
	private Node first;
	private Node last;
	
	private int size;
	
	/**
	 * 查找元素（调用封装好的方法）
	 * @param index 索引下标
	 * @return 返回元素（缺点，不能用于有重复元素）
	 */
	public E get(int index) {
		checkRange(index);
		Node temp = getNode(index);
		return temp!=null?(E)temp.element:null;
	}
	
	//封装之后的get
	private Node getNode(int index) {
		checkRange(index);
		Node temp = null;
		
		if(index<=(size)>>1) {
			temp = first;
			for(int i=0;i<index;i++) {
				temp = temp.next;
			}
		}else {
			temp = last;
			for(int i=size-1;i>index;i++) {
				temp = temp.previous;
			}
		}
		
		return temp;
	}
	
	/**
	 * 插入（在中间插入）
	 * @param index 插入值的位置 
	 * @param element 插入的值
	 */ 
	public void add(int index, E element) {
		
		checkRange(index);
		
		Node newNode = new Node(element);
		Node temp = getNode(index);
		
		if(temp!=null) {
			Node up = temp.previous;
			
			up.next = newNode;
			newNode.previous = up;
			
			newNode.next = temp;
			temp.previous = newNode;
			
			size++;
		}
	}
	
	/**
	 * 插入（在末尾插入）
	 * @param element 插入的值
	 */
	public void add(E element) {
		Node node = new Node(element);
		
		if(first==null) {
			first = node;
			last = node;
		}else {
			node.previous = last;
			node.next = null;
			
			last.next = node;
			last = node;
		}
		size++;
	}
	
	/**
	 * 删除元素
	 * @param index 需要删除元素的下标
	 */
	public void remove(int index) {
		checkRange(index);
		Node temp = getNode(index);
		if(temp!=null) {
			Node up = temp.previous;
			Node down = temp.next;
			
			if(up!=null) {
				up.next = down;
			}
			if(down!=null) {
				down.previous = up;
			}
			
			//被删除的元素是第一个元素时
			if(index == 0) {
				first = down;
			}
			//被删除的元素是最后一个元素时
			if(index == size-1) {
				last = up;
			}
			size--;
		}
	}
	
	//检查下标是否符合规范
	private void checkRange(int index) {
		if(index<0||index>size-1) {
			throw new RuntimeException("索引数字不合法："+index);
		}
	}
	
	//Object类打印时自动调用toString，这里重写，目的是为了打印list链表的元素
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node temp = first;
		while(temp!=null) {
			sb.append(temp.element+",");
			temp = temp.next;
		}
		sb.setCharAt(sb.length()-1, ']');
		
		return sb.toString();
	}
		
	
	public static void main(String[] args) {
		SxtLinkedList<String> list = new SxtLinkedList<>();
		list.add("a");
		list.add("b");
		System.out.println(list);
		list.remove(0);
		System.out.println(list);
		
	}
}
