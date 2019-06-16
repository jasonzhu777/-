package cn.sxt.mycollection;

/**
 * 自定义一个HashMap(简单的增加节点以及查询节点操作，以及自动扩容数组长度)
 * @author jasonzhu
 *
 */
public class SxtHashMap<K,V> {
	
	Node[] table; //位桶数组 bucket array
	int size;
	
	public SxtHashMap() {
		table = new Node[4];
	}
	/**
	 * 查询
	 * @param 键 key
	 * @return 键值 value
	 */
	public V get(K key) {
		int hash = myHash(key.hashCode(),table.length);
		V value = null;
		
		//遍历数组
		if(table[hash]!=null) {
			Node temp = table[hash];
			
			//遍历链表
			while(temp!=null) {
				if(temp.key.equals(key)) {
					value = (V) temp.value;
					break;
				}else {
					temp = temp.next;
				}
			}
		}
		return value;
	}
	
	/**
	 * 增加键值对
	 * @param key 
	 * @param value
	 */
	public void put(K key,V value) {
		
		//定义新的节点对象
		Node newNode = new Node();
		newNode.hash = myHash(key.hashCode(),table.length);
		newNode.key = key;
		newNode.value = value;
		newNode.next = null;
		
		Node temp = table[newNode.hash];
		
		Node iterLast = null; //正在遍历的最后一个元素
		boolean keyRepeat = false;
		
		if(temp==null) {
			
			//此处数组元素为空，则直接将新节点放进去
			size++;
			table[newNode.hash] = newNode;
			
			//数组扩容
			if(size>=0.75*table.length) {
				//如果桶数组中的元素达到（0.75*数组 length），就重新调整数组大小变成原来的2倍大小。
				Node[] newTable = new Node[table.length<<1]; //左移操作
				System.arraycopy(table, 0, newTable, 0, table.length);
				table = newTable;
			}
		}else {
			//此处数组元素不为空。则遍历对应链表
			while(temp!=null) {
				//判断key如果重复，则覆盖
				if(temp.key.equals(key)) {
					keyRepeat = true;
					temp.value = value;
					break;
				}else {
					//key不重复，则遍历下一个
					iterLast = temp;
					temp = temp.next;
				}
			}
			
			if(!keyRepeat) { //没有发生key重复的情况，则添加到链表最后
				iterLast.next = newNode;
				size++;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		
		//遍历bucket数组
		for(int i=0;i<table.length;i++) {
			Node temp = table[i];
			
			//遍历链表
			while(temp!=null) {
				sb.append(temp.key+":"+temp.value+",");	
				temp = temp.next;
			}
		}
		sb.setCharAt(sb.length()-1, '}');
		return sb.toString();
	}	
	
	/**
	 * 计算hash值
	 * @param hashCode
	 * @param length
	 * @return 取模运算的哈希值，等同于（hashCode%length）
	 */
	private int myHash(int hashCode, int length) {
		return hashCode&(length-1);
	}
	
	public static void main(String[] args) {
		SxtHashMap m = new SxtHashMap();
		m.put(10, "aa");
		m.put(20, "bb");
		m.put(30, "cc");

		m.put(53, "gg");
		m.put(69, "hh");
		m.put(85, "kk");
		System.out.println(m);
		System.out.println(m.get(20));
		System.out.println(m.get(85));
		System.out.println(m.get(10));
	}
	
}
