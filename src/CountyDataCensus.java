
public class CountyDataCensus {
	public static void main(String[] args){
		CensusData cd = new CensusData("Z:/Documents/1990.csv", 1990);
		System.out.println(cd.get("Franklin", "Female Population"));
	}
}
