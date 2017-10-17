import java.util.Arrays;

public class HashTable<T> {
	
	private boolean isLinear;
	private HashObject<T>[] hashTable;	
	private final int m = 95791;
	private int n;
	
	public HashTable(boolean useLinearHashing) {
		isLinear = useLinearHashing;
		createNewHashTable();
	}	

	public void insert(T object) {
		HashObject<T> tmpHashObject = new HashObject<T>(object);
		boolean found = false;
		while (tmpHashObject.getProbeCount() < m && !found) {
			int j = getLocation(tmpHashObject);
			if (hashTable[j] == null) {
				n++;
				hashTable[j] = tmpHashObject;
				found = true;
			} else if (hashTable[j].equals(tmpHashObject.getObject())) {
				hashTable[j].addDuplicate();
				found = true;
			} else {
				tmpHashObject.addProbe();
			}			
		}
		if (!found) {
			// throw new RuntimeException("hash table overflow");
		}
	}
	
	public double getLoadFactor( ) {
		return ((double) n / (double) m);
	}
	
	public int getM() {
		return m;
	}
		
	public int getTotalProbes() {
		int probes = 0;
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null) {
				probes += hashTable[i].getProbeCount();				
			}
		}		
		return probes;
	}
	
	@Override
	public String toString() {
		StringBuilder retString = new StringBuilder();
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null) {
				retString.append("Table[");
				retString.append(i);
				retString.append("]: ");
				retString.append(hashTable[i].toString());
				retString.append("\n");
			}			
		}
		return retString.toString();
	}

	public int getNumberOfKeys() {
		return n;
	}
	
	public String getType() {
		if (isLinear) {
			return "Linear";
		} else {
			return "Double";
		}
	}
	
	public int getDuplicates() {
		int duplicates = 0;		
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null) {
				duplicates += hashTable[i].getDuplicateCount();				
			}
		}			
		return duplicates;
	}
	
	private int getLocation(HashObject<T> newObject) {
		if (isLinear) {
			return linearHash(newObject);
		} else {
			return doubleHash(newObject);
		}
	}
	
	private int linearHash(HashObject<T> newObject) {
		int k = getPrimaryHash(newObject.getKey());
		return getPrimaryHash(k + newObject.getProbeCount());
	}
	
	private int doubleHash(HashObject<T> newObject) {
		int k = newObject.getKey();
		int h1 = getPrimaryHash(k);
		int h2 = getSecondaryHash(k);
		return (h1 + newObject.getProbeCount() * h2) % m;
	}
	
	private int getPrimaryHash(int k) {
		return k % m;
	}
	
	private int getSecondaryHash(int k) {
		return 1 + (k % (m - 2));
	}
	
	private void createNewHashTable() {
		hashTable = newHashTable();
		n = 0;
	}
	
	@SuppressWarnings("unchecked")
	private HashObject<T>[] newHashTable() {
		return new HashObject[m];
	}
		
}
