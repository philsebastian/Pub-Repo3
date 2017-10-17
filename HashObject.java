
public class HashObject<T> {
	private T theObject;
	private int duplicateCount;
	private int probeCount;
	
	public HashObject(T theObject) {
		resetCounts();
		setTheObject(theObject);
	}
	
	@Override
	public String toString() { // TODO
		StringBuilder retString = new StringBuilder();
		retString.append(getObject().toString());
		retString.append(" ");
		retString.append(getDuplicateCount());
		retString.append(" ");
		retString.append(getProbeCount() + 1);
		return retString.toString();
	}

	@Override
	public boolean equals(Object object) {
		return theObject.equals(object);
	}
	public int getKey() {
		return Math.abs(theObject.hashCode());
	}
	public T getObject() {
		return theObject;
	}
	
	public void addDuplicate() {
		duplicateCount++;
	}
	public void addProbe() {
		probeCount++;
	}
	public int getProbeCount() {
		return probeCount;
	}	
	public int getDuplicateCount() {
		return duplicateCount;
	}
	
	private void setTheObject(T newObject) {
		theObject = newObject;
	}
	private void resetCounts() {
		setProbeCount(0);
		setDuplicateCount(0);
	}
	private void setProbeCount(int newCount) {
		probeCount = newCount;
	}
	private void setDuplicateCount(int newCount) {
		duplicateCount = newCount;
	}
}
