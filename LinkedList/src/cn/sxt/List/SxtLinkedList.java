package cn.sxt.List;

/**
 * �Զ���һ������(�顢����ɾ)
 * @author jasonzhu
 *
 */
public class SxtLinkedList<E> {
	private Node first;
	private Node last;
	
	private int size;
	
	/**
	 * ����Ԫ�أ����÷�װ�õķ�����
	 * @param index �����±�
	 * @return ����Ԫ�أ�ȱ�㣬�����������ظ�Ԫ�أ�
	 */
	public E get(int index) {
		checkRange(index);
		Node temp = getNode(index);
		return temp!=null?(E)temp.element:null;
	}
	
	//��װ֮���get
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
	 * ���루���м���룩
	 * @param index ����ֵ��λ�� 
	 * @param element �����ֵ
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
	 * ���루��ĩβ���룩
	 * @param element �����ֵ
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
	 * ɾ��Ԫ��
	 * @param index ��Ҫɾ��Ԫ�ص��±�
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
			
			//��ɾ����Ԫ���ǵ�һ��Ԫ��ʱ
			if(index == 0) {
				first = down;
			}
			//��ɾ����Ԫ�������һ��Ԫ��ʱ
			if(index == size-1) {
				last = up;
			}
			size--;
		}
	}
	
	//����±��Ƿ���Ϲ淶
	private void checkRange(int index) {
		if(index<0||index>size-1) {
			throw new RuntimeException("�������ֲ��Ϸ���"+index);
		}
	}
	
	//Object���ӡʱ�Զ�����toString��������д��Ŀ����Ϊ�˴�ӡlist�����Ԫ��
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
