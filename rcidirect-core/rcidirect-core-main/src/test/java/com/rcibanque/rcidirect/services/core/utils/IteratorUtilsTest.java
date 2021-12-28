package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class IteratorUtilsTest {


	@Test
	public void testGetIterable() {

		assertThat(IteratorUtils.nullableIterable((Iterable<Object>) null)).isNotNull();
		assertThat(IteratorUtils.nullableIterable((Object[]) null)).isNotNull();
		assertThat(IteratorUtils.nullableIterable((Map<Object, Object>) null)).isNotNull();
	}


	@Test
	public void testGetFirstElement() {

		assertThat(IteratorUtils.firstElement(null) == null).isTrue();
		assertThat(IteratorUtils.firstElement(new ArrayList<>()) == null).isTrue();

		assertThat(Integer.valueOf(1)).isEqualTo(IteratorUtils.firstElement(Arrays.asList(1, 2, 3)));
		assertThat(Integer.valueOf(3)).isNotEqualTo(IteratorUtils.firstElement(Arrays.asList(1, 2, 3)));
	}


	@Test
	public void testGetLastElement() {

		assertThat(IteratorUtils.lastElement(null) == null).isTrue();
		assertThat(IteratorUtils.lastElement(new ArrayList<>()) == null).isTrue();

		assertThat(Integer.valueOf(3)).isEqualTo(IteratorUtils.lastElement(Arrays.asList(1, 2, 3)));
		assertThat(Integer.valueOf(1)).isNotEqualTo(IteratorUtils.lastElement(Arrays.asList(1, 2, 3)));
	}


}
