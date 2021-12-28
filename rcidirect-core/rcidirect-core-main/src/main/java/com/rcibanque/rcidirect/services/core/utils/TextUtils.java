package com.rcibanque.rcidirect.services.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.WordUtils;

public class TextUtils {

	private static final char COMA = ',';

	private static final char[] CAPITALIZED_DELIMITERS = new char[]{' ', '-'};


	private TextUtils() {}


	/**
	 * For every word of the provided text, capitalise the first character to upper case followed by lower case
	 *
	 * @param pText
	 * @return String, or null if input is null
	 */
	public static String getFirstCharUpperFollowedByLowerCase(String pText) {

		String res = pText;

		if (res != null) {
			res = WordUtils.capitalizeFully(StringUtils.trimToEmpty(res), CAPITALIZED_DELIMITERS);
		}

		return res;
	}


	/**
	 * Trim and capitalise
	 *
	 * @param pText
	 * @return String, or null if input is null
	 */
	public static String trimAndCapitilize(String pText) {

		String res = pText;

		if (res != null) {
			res = StringUtils.upperCase(StringUtils.trimToEmpty(pText));
		}

		return res;
	}


	/**
	 * Generate random alphanumeric string
	 *
	 * @param pLength Length of string
	 * @return Random alphanumeric string
	 */
	public static String generateRandomString(int pLength) {

		RandomStringGenerator generator = new RandomStringGenerator.Builder()
				.withinRange('0', 'z')
				.filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
				.build();

		String res = generator.generate(pLength);

		res = StringUtils.upperCase(res);

		return res;
	}


	/**
	 * Create a single string from a collection of strings, with provided separator
	 *
	 * @param pValues String values (null values ignored)
	 * @param pSeparator Separator
	 * @return Single string
	 */
	public static String withSeparator(Iterable<String> pValues, String pSeparator) {
		StringBuilder res = new StringBuilder();
		boolean separator = false;

		Iterator<String> it = IteratorUtils.nullableIterable(pValues).iterator();
		while(it.hasNext()) {
			String value = it.next();
			if(StringUtils.isNotEmpty(value)) {

				if(separator) {
					res.append(pSeparator);
				}
				res.append(value);
				separator = true;
			}
		}

		return res.toString();
	}


	/**
	 * Create a single string from a collection of strings, with line feed separator
	 *
	 * @param pValues String values (null values ignored)
	 * @return Single string
	 * @see #withSeparator(Iterable, String)
	 */
	public static String withSeparatorLF(Iterable<String> pValues) {

		return withSeparator(pValues, StringUtils.LF);
	}


	/**
	 * Create a single string from an array of strings, with space separator
	 *
	 * @param pValues String array (null values ignored)
	 * @return Single string
	 * @see #withSeparator(Iterable, String)
	 */
	public static String withSeparatorSpace(String... pValues) {

		return withSeparator(IteratorUtils.nullableIterable(pValues), StringUtils.SPACE);
	}


	/**
	 * @param pValues Collection (example: [1, 2, 3])
	 * @return CSV values (example: "1, 2, 3"), empty if values is empty or null
	 */
	public static String getCSVFromIntegers(Collection<Integer> pValues) {

		return getCSV(pValues, Integer.class);
	}

	/**
	 * @param pValues Collection (example: [1, 2, 3])
	 * @return CSV values (example: "1, 2, 3"), empty if values is empty or null
	 */
	public static String getCSVFromLongs(Collection<Long> pValues) {

		return getCSV(pValues, Long.class);
	}

	/**
	 * @param pValues Collection (example: ["abc", "def", "ghi])
	 * @return CSV values (example: "abc, def, ghi"), empty if values is empty or null
	 */
	public static String getCSVFromStrings(Collection<String> pValues) {

		return getCSV(pValues, String.class);
	}

	private static <T> String getCSV(Collection<T> pValues, Class<T> pType) {
		StringBuilder res = new StringBuilder(StringUtils.EMPTY);

		Iterator<T> it = IteratorUtils.nullableIterable(pValues).iterator();
		while(it.hasNext()) {
			T typedValue = it.next();
			String value = null;

			if(pType.equals(Integer.class)) {
				value = ConvertUtils.toString((Integer) typedValue);
			}
			else if(pType.equals(Long.class)) {
				value = ConvertUtils.toString((Long) typedValue);
			}
			else if(pType.equals(String.class)) {
				value = (String) typedValue;
			}
			else {
				// Should not happen
				throw new IllegalArgumentException("Unsupported type");
			}

			if(value != null) {
				res.append(value);
				if(it.hasNext()) {
					res.append(COMA).append(StringUtils.SPACE);
				}
			}
			else {
				throw new IllegalArgumentException(value);
			}
		}
		return res.toString();
	}


	/**
	 * @param pValues CSV values (example: "1, 2, 3")
	 * @return List (example: [1, 2, 3]), empty if values is empty or null
	 */
	public static List<Integer> getIntegersFromCSV(String pValues) {

		return getFromCSV(pValues, Integer.class);
	}

	/**
	 * @param pValues CSV values (example: "1, 2, 3")
	 * @return List (example: [1, 2, 3]), empty if values is empty or null
	 */
	public static List<Long> getLongsFromCSV(String pValues) {

		return getFromCSV(pValues, Long.class);
	}

	/**
	 * @param pValues CSV values (example: "abc, def, ghi")
	 * @return List (example: ["abc", "def", "ghi"]), empty if values is empty or null
	 */
	public static List<String> getStringsFromCSV(String pValues) {

		return getFromCSV(pValues, String.class);
	}

	@SuppressWarnings("unchecked")
	private static <T> List<T> getFromCSV(String pValues, Class<T> pType) {
		List<T> res = new ArrayList<>();

		if(StringUtils.isNotBlank(pValues)) {
			for(String value : StringUtils.split(pValues, COMA)) {

				if(StringUtils.isNotBlank(value)) {
					T typedValue = null;

					if(pType.equals(Integer.class)) {
						typedValue = (T) ConvertUtils.parseInteger(value);
					}
					else if(pType.equals(Long.class)) {
						typedValue = (T) ConvertUtils.parseLong(value);
					}
					else if(pType.equals(String.class)) {
						typedValue = (T) StringUtils.trim(value);
					}
					else {
						// Should not happen
						throw new IllegalArgumentException("Unsupported type");
					}

					if(typedValue != null) {
						res.add(typedValue);
					}
					else {
						throw new IllegalArgumentException(value);
					}
				}
			}
		}
		return res;
	}


	/**
	 * Replaces place-holders in the template with values from the map.
	 * The keys used in the map must be the place-holders in the template.
	 * <br><br>
	 * <u>Example:</u> Template has text "The quick @@COLOUR@@ fox", the map should contain the entry "@@COLOUR@@" : "brown"
	 *
	 * @param pTemplate Template
	 * @param pValues Map
	 * @return Updated template
	 */
	public static String replacePlaceHolders(String pTemplate, Map<String, String> pValues) {

		String res = pTemplate;

		if(StringUtils.isNotBlank(pTemplate)) {

			for (Entry<String, String> entry : IteratorUtils.nullableIterable(pValues)) {

				res = res.replace(entry.getKey(), entry.getValue());
			}
		}

		return res;
	}

}
