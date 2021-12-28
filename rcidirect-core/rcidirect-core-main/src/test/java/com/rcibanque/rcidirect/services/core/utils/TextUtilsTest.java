package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class TextUtilsTest {

	@Test
	public void testFirstCharUpperFollowedByLowerCase() {

		String expected = "John Smith";

		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(StringUtils.lowerCase(expected)));
		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(StringUtils.upperCase(expected)));

		expected = "John Smith-Turner";

		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(StringUtils.lowerCase(expected)));
		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(StringUtils.upperCase(expected)));

		expected = null;
		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(null));

		expected = StringUtils.EMPTY;
		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(StringUtils.EMPTY));
		assertThat(expected).isEqualTo(TextUtils.getFirstCharUpperFollowedByLowerCase(StringUtils.EMPTY + StringUtils.EMPTY));

	}


	@Test
	public void testTrimAndCapitilize() {

		String expected = "JOHN SMITH";

		assertThat(expected).isEqualTo(TextUtils.trimAndCapitilize(StringUtils.EMPTY + StringUtils.lowerCase(expected) + StringUtils.EMPTY));

		expected = null;
		assertThat(expected).isEqualTo(TextUtils.trimAndCapitilize(null));

		expected = StringUtils.EMPTY;
		assertThat(expected).isEqualTo(TextUtils.trimAndCapitilize(StringUtils.EMPTY));
		assertThat(expected).isEqualTo(TextUtils.trimAndCapitilize(StringUtils.EMPTY + StringUtils.EMPTY));

	}


	@Test
	public void testGenerateRandomString() {

		String res = TextUtils.generateRandomString(10);

		assertThat(res.matches("[A-Z0-9]{10}")).isTrue();
	}


	@Test
	public void testWithSeparatorLF() {

		assertThat(StringUtils.EMPTY).isEqualTo(TextUtils.withSeparatorLF(null));

		List<String> values = new ArrayList<>();

		assertThat(StringUtils.EMPTY).isEqualTo(TextUtils.withSeparatorLF(values));

		values.add("A");
		values.add(null);
		values.add("B");
		values.add("C");

		assertThat("A" + StringUtils.LF + "B" + StringUtils.LF + "C").isEqualTo(TextUtils.withSeparatorLF(values));
	}


	@Test
	public void testWithSeparatorSpace() {

		assertThat(StringUtils.EMPTY).isEqualTo(TextUtils.withSeparatorSpace());

		assertThat("A" + StringUtils.SPACE + "B" + StringUtils.SPACE + "C").isEqualTo(TextUtils.withSeparatorSpace("A", "B", null, "C"));
	}


	@Test
	public void testCSVFromIntegers() {

		List<Integer> values = new ArrayList<>();
		values.add(1);
		values.add(1);
		values.add(2);
		values.add(3);
		values.add(4);

		String res = TextUtils.getCSVFromIntegers(values);

		assertThat("1, 1, 2, 3, 4").isEqualTo(res);

		// Other
		assertThat("").isEqualTo(TextUtils.getCSVFromIntegers(null));
		assertThat("").isEqualTo(TextUtils.getCSVFromIntegers(new ArrayList<>()));
	}

	@Test
	public void testCSVFromLongs() {

		List<Long> values = new ArrayList<>();
		values.add(1L);
		values.add(1L);
		values.add(2L);
		values.add(3L);
		values.add(4L);

		String res = TextUtils.getCSVFromLongs(values);

		assertThat("1, 1, 2, 3, 4").isEqualTo(res);

		// Other
		assertThat("").isEqualTo(TextUtils.getCSVFromLongs(null));
		assertThat("").isEqualTo(TextUtils.getCSVFromLongs(new ArrayList<>()));
	}

	@Test
	public void testCSVFromStrings() {

		List<String> values = new ArrayList<>();
		values.add("abc");
		values.add("d e f");
		values.add("ghi");

		String res = TextUtils.getCSVFromStrings(values);

		assertThat("abc, d e f, ghi").isEqualTo(res);

		// Other
		assertThat("").isEqualTo(TextUtils.getCSVFromStrings(null));
		assertThat("").isEqualTo(TextUtils.getCSVFromStrings(new ArrayList<>()));
	}


	@Test
	public void testIntegersFromCSV() {

		List<Integer> res = TextUtils.getIntegersFromCSV("1,1,   2  ,3, 4  ");

		List<Integer> expected = new ArrayList<>();
		expected.add(1);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);

		assertThat(expected).isEqualTo(res);

		// Other
		assertThat(TextUtils.getIntegersFromCSV("")).isEqualTo(new ArrayList<>());
		assertThat(TextUtils.getIntegersFromCSV(null)).isEqualTo(new ArrayList<>());

		try {
			TextUtils.getIntegersFromCSV("abc");
			assertThat(true).isFalse(); // Should have failed
		}
		catch(Exception e) {
			// Do nothing
		}
	}

	@Test
	public void testLongsFromCSV() {

		List<Long> res = TextUtils.getLongsFromCSV("1,1,   2  ,3, 4  ");

		List<Long> expected = new ArrayList<>();
		expected.add(1L);
		expected.add(1L);
		expected.add(2L);
		expected.add(3L);
		expected.add(4L);

		assertThat(expected).isEqualTo(res);

		// Other
		assertThat(new ArrayList<>()).isEqualTo(TextUtils.getLongsFromCSV(""));
		assertThat(new ArrayList<>()).isEqualTo(TextUtils.getLongsFromCSV(null));

		try {
			TextUtils.getLongsFromCSV("abc");
			assertThat(true).isFalse(); // Should have failed
		}
		catch(Exception e) {
			// Do nothing}
		}
	}

	@Test
	public void testStringsFromCSV() {

		List<String> res = TextUtils.getStringsFromCSV("a,bc ,  def ,ghi");

		List<String> expected = new ArrayList<>();
		expected.add("a");
		expected.add("bc");
		expected.add("def");
		expected.add("ghi");

		assertThat(expected).isEqualTo(res);

		// Other
		assertThat(new ArrayList<>()).isEqualTo(TextUtils.getStringsFromCSV(""));
		assertThat(new ArrayList<>()).isEqualTo(TextUtils.getStringsFromCSV(null));
	}


	@Test
	public void testReplacePlaceHolders() {

		Map<String, String> map = new HashMap<>();
		map.put("@@PACE@@", "quick");
		map.put("@@COLOUR@@", "brown");
		map.put("@@ANIMAL@@", "fox");
		map.put("@@CHARACTER@@", "smart");
		map.put("@@UNUSED@@", "not needed");

		String template = "The @@PACE@@ @@COLOUR@@ @@ANIMAL@@ has seen it all, because the @@ANIMAL@@ is @@CHARACTER@@";

		String res = TextUtils.replacePlaceHolders(template, map);

		assertThat("The quick brown fox has seen it all, because the fox is smart").isEqualTo(res);


		assertThat(TextUtils.replacePlaceHolders(null, null)).isNull();
		assertThat(TextUtils.replacePlaceHolders(null, new HashMap<>())).isNull();
		assertThat(StringUtils.EMPTY).isEqualTo(TextUtils.replacePlaceHolders(StringUtils.EMPTY, null));
		assertThat(StringUtils.EMPTY).isEqualTo(TextUtils.replacePlaceHolders(StringUtils.EMPTY, new HashMap<>()));
	}
}
