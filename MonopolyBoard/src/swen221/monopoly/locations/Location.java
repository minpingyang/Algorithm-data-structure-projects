package swen221.monopoly.locations;

/**
 * Represents any location on the monopoly board. This includes streets,
 * utilities, special areas and stations.
 */
public abstract class Location {
	private String name;
	/**
	 * Construct a location with a given name. Every location on the Monopoly
	 * board has a unique name which identifies it.
	 * 
	 * @param name
	 */
	public Location(String name) {
		this.name = name;
	}

	/**
	 * Get the name of this location on the board.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Check whether this location has an owner or not. Note that not all
	 * locations can actually have owners.
	 */
	public abstract boolean hasOwner();
	
	@Override
	public int hashCode() {
		final int prime = 11;
		int amount = 1;
		amount = prime*amount + ((name == null) ? 0 : name.hashCode());
		return amount;
	}
	@Override
	public boolean equals(Object object) {	
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		Location other = (Location) object;
		if(getClass() != object.getClass()){
			return false;
		}
		if(name == null){
			if (other.name != null) {
				return false;
			}
		}else if (!name.equals(other.name)) {
			return false;
		}
		return true;
		
	}
	@Override
	public String toString() {
		return name;
	}
	
	
}
