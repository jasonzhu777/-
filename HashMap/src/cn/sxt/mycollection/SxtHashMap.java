package cn.sxt.mycollection;

/**
 * �Զ���һ��HashMap(�򵥵����ӽڵ��Լ���ѯ�ڵ�������Լ��Զ��������鳤��)
 * @author jasonzhu
 *
 */
public class SxtHashMap<K,V> {
	
	Node[] table; //λͰ���� bucket array
	int size;
	
	public SxtHashMap() {
		table = new Node[4];
	}
	/**
	 * ��ѯ
	 * @param �� key
	 * @return ��ֵ value
	 */
	public V get(K key) {
		int hash = myHash(key.hashCode(),table.length);
		V value = null;
		
		//��������
		if(table[hash]!=null) {
			Node temp = table[hash];
			
			//��������
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
	 * ���Ӽ�ֵ��
	 * @param key 
	 * @param value
	 */
	public void put(K key,V value) {
		
		//�����µĽڵ����
		Node newNode = new Node();
		newNode.hash = myHash(key.hashCode(),table.length);
		newNode.key = key;
		newNode.value = value;
		newNode.next = null;
		
		Node temp = table[newNode.hash];
		
		Node iterLast = null; //���ڱ��������һ��Ԫ��
		boolean keyRepeat = false;
		
		if(temp==null) {
			
			//�˴�����Ԫ��Ϊ�գ���ֱ�ӽ��½ڵ�Ž�ȥ
			size++;
			table[newNode.hash] = newNode;
			
			//��������
			if(size>=0.75*table.length) {
				//���Ͱ�����е�Ԫ�شﵽ��0.75*���� length���������µ��������С���ԭ����2����С��
				Node[] newTable = new Node[table.length<<1]; //���Ʋ���
				System.arraycopy(table, 0, newTable, 0, table.length);
				table = newTable;
			}
		}else {
			//�˴�����Ԫ�ز�Ϊ�ա��������Ӧ����
			while(temp!=null) {
				//�ж�key����ظ����򸲸�
				if(temp.key.equals(key)) {
					keyRepeat = true;
					temp.value = value;
					break;
				}else {
					//key���ظ����������һ��
					iterLast = temp;
					temp = temp.next;
				}
			}
			
			if(!keyRepeat) { //û�з���key�ظ������������ӵ��������
				iterLast.next = newNode;
				size++;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		
		//����bucket����
		for(int i=0;i<table.length;i++) {
			Node temp = table[i];
			
			//��������
			while(temp!=null) {
				sb.append(temp.key+":"+temp.value+",");	
				temp = temp.next;
			}
		}
		sb.setCharAt(sb.length()-1, '}');
		return sb.toString();
	}	
	
	/**
	 * ����hashֵ
	 * @param hashCode
	 * @param length
	 * @return ȡģ����Ĺ�ϣֵ����ͬ�ڣ�hashCode%length��
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
