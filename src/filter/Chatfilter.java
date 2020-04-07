package filter;

import java.util.List;
import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * This class provides some simple methods for checking a string for words in a
 * String List blacklist. <br>
 * This is using a constructor for the blacklist and one method for checking the
 * String this class in addition to some private methods for testing some smaler
 * parts.
 * 
 * @author Philipp Ochs
 * @version 1.0
 * 
 * @see <a href="https://bigoof.de/">Das ist eine coole Party auf die ihr
 *      solltet!</a>
 * @see <a href=
 *      "https://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/similarity/LevenshteinDistance.html">Documentation
 *      for the Levenshtein distance</a>
 * 
 */
public class Chatfilter {
	LevenshteinDistance distance = new LevenshteinDistance();
	List<String> blacklist;
	List<String> insignificants;

	/**
	 * Constructor to give this class the blacklist as a List of Strings.
	 * 
	 * @param blacklist this is the blacklist of the words that gonna be filtered by
	 *                  the chatfilter.
	 */
	public Chatfilter(List<String> blacklist, List<String> insignificants) {
		this.blacklist = blacklist;
		this.insignificants = insignificants;
	}

	/**
	 * This method is to check the incomming String input for the blacklist. If
	 * input contains parts or a hole word in the blacklist the method returns
	 * false, if no match is found, else true. <br>
	 * The accuracy of the matchfunktion is controlled by the float accuracy which u
	 * can set from 0 to 1.
	 * <p>
	 * In specific this method just iterates over the blacklist and calls
	 * {@link #hasMatch(String, String, int)} Method. This returns <code>true</code>
	 * when match ist found.
	 * 
	 * @param input    is the String to be checked.
	 * @param accuracy is the accuracy for the match method. It counts from 0 to 1.
	 * @return This method returns true when a match is found, false, when not.
	 */
	public FilterType checkInput(String input, float muteAccuracy, float supportAccuracy) {
		float matchPercent = 0;
		String matchString = null;

		input = input.toLowerCase();

		for (String blacklistString : blacklist) {
			for (String inputWord : input.split(" ")) {
				if (getMatchPercent(blacklistString, inputWord) > matchPercent) {
					matchPercent = getMatchPercent(blacklistString, inputWord);
					matchString = blacklistString;

				}
			}
			for (int i = 0; i < killInsignificantChars(input).length(); i++) {
				if (sentenseMatch(blacklistString, killInsignificantChars(input), i) > matchPercent) {
					matchPercent = sentenseMatch(blacklistString, killInsignificantChars(input), i);
					matchString = blacklistString;
				}
			}
		}

		if (matchPercent >= muteAccuracy) {
			FilterType.MUTE.setMatchPercent(matchPercent);
			FilterType.MUTE.setMatchString(matchString);
			return FilterType.MUTE;
		} else if (matchPercent >= supportAccuracy && matchPercent < muteAccuracy) {
			FilterType.SUPPORT.setMatchPercent(matchPercent);
			FilterType.SUPPORT.setMatchString(matchString);
			return FilterType.SUPPORT;
		} else {
			FilterType.NULL.setMatchPercent(matchPercent);
			FilterType.NULL.setMatchString(matchString);
			return FilterType.NULL;
		}
	}

	/**
	 * TODO DOCCEN!
	 * 
	 * @param input
	 * FUNKT IWIE NICHT
	 * @param inputSentense
	 * @return
	 */
	private float sentenseMatch(String blacklistString, String input, int charNum) {
		String nextWord = null;
		for (int i = 0; i < blacklistString.length(); i++) {
			if ((charNum + i) < input.length()) {
				nextWord += input.charAt(charNum + i);
			}
		}
		return getMatchPercent(blacklistString, nextWord);
	}

	/**
	 * This method is to check the input String with every single String of the
	 * blacklist.
	 * 
	 * @param blacklistedString is the blacklisted String.
	 * @param input             is the word that should be checked.
	 * @param accuracy          is the accuracy of the match that should be checked.
	 * @return Returns {@code true} when a match is found, else {@code false}.
	 */
	private float getMatchPercent(String blacklistString, String input) {

		input = killInsignificantChars(input);
		float matchDistance = distance.apply(blacklistString, input);
		float matchPercent = 100 - ((matchDistance / (float) input.length()) * 100);

		// Just for Debugging!
		// System.out.println("Distance: " + matchDistance + " Length: " +
		// input.length());
		// System.out.println("Prozent: " + (100 - ((matchDistance / (float)
		// input.length()) * 100)));
		return matchPercent;
	}

	/**
	 * This method is to kill all characters, which are insignificant for the
	 * Chatfilter such as points, spaces,...
	 * 
	 * @param input Chatinput String within all special characters.
	 */
	private String killInsignificantChars(String input) {
		for (String insignificantChar : insignificants) {
			input = input.replace(insignificantChar, "");
		}
		return input;
	}
}
