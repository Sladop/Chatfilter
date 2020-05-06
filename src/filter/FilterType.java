package filter;

/**
 * This class defines some simple enum types for the upcomming steps when a
 * message went through the filter.
 * 
 * @author Philipp Ochs
 * @version 1.0
 *
 */
public enum FilterType {
	/**
	 * This type is for muting an user for a chatmessage.
	 */
	MUTE,

	/**
	 * This type is for sending a global message within all neccesarry datas like
	 * match percent and the message to all teammembers.
	 */
	SUPPORT,

	/**
	 * This type is for doing nothing. The message should be clear from insults.
	 */
	NULL;

	float matchPercent = 0;
	String matchString;

	public void setMatchPercent(float matchPercent) {
		this.matchPercent = matchPercent;
	}

	public float getMatchPercent() {
		return matchPercent;
	}

	public void setMatchString(String matchString) {
		this.matchString = matchString;
	}

	public String getMatchString() {
		return matchString;
	}
}
