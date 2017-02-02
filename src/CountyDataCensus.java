import java.util.Calendar;

public class CountyDataCensus {
	public static void main(String[] args){
		String currentDir = System.getProperty("user.dir");
		CensusData cd1990 = new CensusData(currentDir + "/data/Census1990.csv", 1990);
		CensusData cd2000 = new CensusData(currentDir + "/data/Census2000.csv", 2000);
		CensusData cd2010 = new CensusData(currentDir + "/data/Census2010.csv", 2010);
		
		Calendar election2008 = Calendar.getInstance();
		election2008.set(2008, Calendar.NOVEMBER, 4);
		Calendar election2016 = Calendar.getInstance();
		election2016.set(2016, Calendar.NOVEMBER, 8);
		
		ElectionData ed2008 = new ElectionData(currentDir+"/data/Election2008.csv", election2008);
		ElectionData ed2016 = new ElectionData(currentDir+"/data/Election2016.csv", election2016);
		
		
		System.out.println("County\t\t" + "F2008\t\t" + "F2016\t\tR2008\tR2016");
		for(String county : cd2010.countyMap.keySet()){
			System.out.print(county+"\t");
			if(county.length() < 8){
				//System.out.print("\t");
			}
			Double v1 = CensusData.interpolateDasGupta(cd2000, cd2010, county, "Female Population", election2008);
			Double v2 = CensusData.interpolateDasGupta(cd2000, cd2010, county, "Female Population", election2016);
			Double e1 = ed2008.get(county, "PercentVotedRepublican");
			Double e2 = ed2016.get(county, "PercentVotedRepublican");
			//System.out.println(String.format("%.4f\t%.4f\t%.4f\t%.4f", v1, v2, e1, e2));
			System.out.println(String.format("%.4f\t%.4f", v2-v1, e2-e1));
		}
	}
}
