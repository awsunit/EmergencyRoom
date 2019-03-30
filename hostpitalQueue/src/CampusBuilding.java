package hw8;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

/**
 * Stores a single row from campus_paths.tsv. The names of the methods are
 * chosen to match the names in the first line of that file.
 */
public class CampusBuilding implements Comparable<CampusBuilding>{
	
	//not an ADT
	
	 //set ALL FIELDS private after testing complete
	
	 @CsvBindByName
	    public String shortName;
	 
	 @CsvBindByName
	    public String longName;
	 

	@CsvCustomBindByName(converter = CoordinateConverter.class)
	    public Coordinate location;
	
	 public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}
	 /**
	  * Sets this CampusBuilding objects String object corresponding to its abbreviated name.
	  * 
	  * @return a String representing the common name of this building/location
	  */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Sets this CampusBuilding objects String object corresponding to its abbreviated name.
	 * 
	 * @param shortName the String representing the common name of this building/location.
	 * @spec.modifies this
	 * @spec.effects post method: this.shortName = shortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Returns a String object corresponding to this CampusBuilding objects full name.
	 * 
	 * @return a String representing the actual name of this building/location
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * Sets this CampusBuilding objects String object corresponding to its full name.
	 * 
	 * @param longName the String representing the actual name of this building/location
	 * @spec.modifies this
	 * @spec.effects post method: this.longName = longName
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}


	/**
	 * A method to retrieve this objects hashCode.
	 * 
	 * @return returns this objects unique hashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += this.shortName.hashCode();
		hashCode += this.longName.hashCode();
		hashCode += this.location.hashCode();
		hashCode *= 3;
		return hashCode;		
	}
	/**
	 * Standard equality check. Compares this CambusBuildings data fields against passed parameter.
	 * 
	 * @param otherBuilding the other building to compare to this one
	 * @return true if all data members of this and otherBuilding are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object otherBuilding) {
		if(!(otherBuilding instanceof CampusBuilding)) {
			return false;
		}
		CampusBuilding o = (CampusBuilding) otherBuilding;
		if(o.getShortName().equals(this.shortName) &&
				o.getLongName().equals(this.longName) &&
				o.getLocation().equals(this.location)) {
			return true;
		}
		return false;
	}
	/**
	 * currently useless, users will have to wait.
	 */
	@Override
	public int compareTo(CampusBuilding o) {
		// perhaps due direction comparions north south etc
		return 0;
	}

	
	 
	 
}
