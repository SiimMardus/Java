/*
    This program reads lines from input string with sentences such as:

    "I am Johnny Jammy and my phone number is 5553 2932."
    "Hey it's me Larry Lonely with 5938-3823 as my digits."
    "Peter Burger here, call me at 55723944. Thanks!"

    After reading the lines, it uses Regular expressions to get a name and a phone number from it.
    The program then adds the name and the number into a phonebook.

 */

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalyzer {

    public String text;

    // Regex for finding names
	public static final String NAME = "([A-Z][\\w-]*(\\s+[A-Z][\\w-]*)+)";

    // Regex for finding numbers
	public static final String NUMBER = "(?<![\\d])(([\\d]){4}|([\\d]){3})[\\-\\s]?(([\\d]){4}|([\\d]){3})(?![\\d])";

	public TextAnalyzer(String text) {
		this.text = text;
	}

    /**
     * Finds names and phone numbers from sentences and puts them into phonebook.
     * @return - Returns the phonebook
     */
	public Map<String, String> getPhoneNumbers() {

	    Map<String, String> phoneBook = new HashMap<>();

        Pattern namePattern = Pattern.compile(NAME);
        Pattern numberPattern = Pattern.compile(NUMBER);

		String[] sentences = this.text.split("\n");

		for (String s : sentences){
            Matcher nameMatcher = namePattern.matcher(s);
            Matcher numberMatcher = numberPattern.matcher(s);

            if (nameMatcher.find() && numberMatcher.find()){
                String correctNumber = numberMatcher.group().replaceAll("[ -]", "");
                phoneBook.put(nameMatcher.group(),correctNumber);
            }
        }

		return phoneBook;
	}



    public static void main(String[] args) {

        String input =
                "I am Johnny Jammy and my phone number is 5553 2932.\n" +
                "Hey it's me Larry Lonely with 5938-3823 as my digits.\n" +
                "Peter Burger here, call me at 55723944. Thanks!";

        TextAnalyzer ta = new TextAnalyzer(input);
        Map<String, String> phoneBook = ta.getPhoneNumbers();
        System.out.println(phoneBook.get("Johnny Jammy"));
        System.out.println(phoneBook.get("Peter Burger"));

    }
}
