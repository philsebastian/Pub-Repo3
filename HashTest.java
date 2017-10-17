import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class HashTest {

	private int inputType;
	private double maxLoadFactor;
	private boolean debug;
	
	@SuppressWarnings("rawtypes")
	private HashTable linearTable;
	@SuppressWarnings("rawtypes")
	private HashTable doubleTable;
	
	private HashTest(int inputType, double loadFactor, boolean debug) {
		setInput(inputType);
		setLoad(loadFactor);
		setDebug(debug);
	}
		
	private void runTest() {
		switch (inputType) {
			case 1:
				runIntTest();
				break;
			case 2:
				runLongTest();
				break;
			case 3:
				runWordTest();
				break;
		}
	}
	
	private void printResults() {
		if (debug) {
			printTables();
		}
		StringBuilder result = new StringBuilder();
		result.append("A good table size is found: 95791");
		result.append("\n");
		result.append("Data source type: ");
		switch (inputType) {
			case 1:
				result.append("random number generator");
				break;
			case 2: 
				result.append("current millisecond");
				break;
			case 3:
				result.append("word-list");
				break;			
		}
		result.append("\n");
		result.append("\n");
		result.append(outputSummary(linearTable));
		result.append(outputSummary(doubleTable));	
		System.out.print(result.toString());
	}
	
	@SuppressWarnings("rawtypes")
	private String outputSummary(HashTable theTable) {
		StringBuilder retString = new StringBuilder();
		retString.append("Using ");
		retString.append(theTable.getType());
		retString.append(" Hashing...");
		retString.append("\n");
		retString.append("Inserted ");
		retString.append(theTable.getNumberOfKeys());
		retString.append(" elements, of which ");
		retString.append(theTable.getDuplicates());
		retString.append(" duplicates");
		retString.append("\n");
		retString.append("load factor = ");
		retString.append(maxLoadFactor);
		retString.append(", Avg. no. of probes ");
		double avgProbes = ((double) theTable.getTotalProbes() / (double) theTable.getNumberOfKeys()); 
		retString.append(avgProbes);
		retString.append("\n");
		
		return retString.toString();
	}
	
	private void printTables() {
		printTableFile(linearTable);
		printTableFile(doubleTable);
	}
	
	@SuppressWarnings("rawtypes")
	private void printTableFile(HashTable thisTable) {
		String fileName = thisTable.getType().toLowerCase() + "-dump"; 
		try {
			FileWriter file = new FileWriter(fileName, false); 
			BufferedWriter writer = new BufferedWriter(file);
			writer.write(thisTable.toString());
			writer.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void runIntTest() {
		linearTable = new HashTable<Integer>(true);
		doubleTable = new HashTable<Integer>(false);
		
		Random rand = new Random(1234);
		double linearLoad = linearTable.getLoadFactor();
		double doubleLoad = doubleTable.getLoadFactor();
		
		while (linearLoad < maxLoadFactor || doubleLoad < maxLoadFactor) { // TODO -- Generating one more -- hmmm
			int currentInt = rand.nextInt();
			if (linearLoad < maxLoadFactor) {
				linearTable.insert(currentInt);
				linearLoad = linearTable.getLoadFactor();
			}
			if (doubleLoad < maxLoadFactor) {
				doubleTable.insert(currentInt);	
				doubleLoad = doubleTable.getLoadFactor();
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void runLongTest() {
		linearTable = new HashTable<Long>(true);
		doubleTable = new HashTable<Long>(false);
		
		double linearLoad = linearTable.getLoadFactor();
		double doubleLoad = doubleTable.getLoadFactor();
		
		while (linearLoad < maxLoadFactor || doubleLoad < maxLoadFactor) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			long currentTime = System.currentTimeMillis();
			if (linearLoad < maxLoadFactor) {
				linearTable.insert(currentTime);
				linearLoad = linearTable.getLoadFactor();
			}
			if (doubleLoad < maxLoadFactor) {
				doubleTable.insert(currentTime);
				doubleLoad = doubleTable.getLoadFactor();
			}
		}
	}
	
	private void runWordTest() {
		linearTable = new HashTable<String>(true);
		doubleTable = new HashTable<String>(false);		
		processFile("word-list");		
	}
	
	/**
	 * Method modified from Lab1
	 * @param fileName - Name of file to review
	 */
	@SuppressWarnings("unchecked")
	public void processFile(String fileName) {
		
		BufferedReader reader = null;
		String line;
		StringTokenizer stringLine;
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException err) {
			System.err.println(err.getMessage());
			System.exit(1);
		}
		
		try {
			line = reader.readLine();
			double linearLoad = linearTable.getLoadFactor();
			double doubleLoad = doubleTable.getLoadFactor();
			
			while (line != null && linearLoad < maxLoadFactor && doubleLoad < maxLoadFactor) {
				stringLine = new StringTokenizer(line);
				
				while (stringLine.hasMoreTokens() && (linearLoad < maxLoadFactor || doubleLoad < maxLoadFactor)) {
					String thisToken = stringLine.nextToken();
					if (linearLoad < maxLoadFactor) {
						linearTable.insert(thisToken);
						linearLoad = linearTable.getLoadFactor();
					}
					if (doubleLoad < maxLoadFactor) {
						doubleTable.insert(thisToken);	
						doubleLoad = doubleTable.getLoadFactor();
					}
				}				
				line = reader.readLine();
			}
			
		} catch (IOException err) {
			System.err.println(err);
			System.exit(1);
		}
	}
	
	private void setDebug(boolean newDebug) {
		debug = newDebug;
	}
	private void setInput(int newInput) {
		inputType = newInput;
	}
	private void setLoad(double newLoad) {
		maxLoadFactor = newLoad;
	}
	
	public static void main(String[] args) {
/*		if (args.length < 2 || args.length > 3) {
			// TODO - Figure this out
		}
		int input = Integer.parseInt(args[0]);
		double load = Double.parseDouble(args[1]);
		boolean debug = false;
		if (args.length == 3 && args[2].equals("1")) {
			debug = true;
		}*/
		
		int input = 3;
		double load = 0.5;
		boolean debug = true;				
		
		HashTest thisTest = new HashTest(input, load, debug);
		thisTest.runTest();
		thisTest.printResults();
	}

}
