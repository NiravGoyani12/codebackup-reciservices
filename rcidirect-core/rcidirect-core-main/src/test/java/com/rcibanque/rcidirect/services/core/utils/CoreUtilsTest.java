package com.rcibanque.rcidirect.services.core.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CoreUtilsTest {


	@Test
	public void testGetUniqueElement() {

		List<String> list = null;
		String res = CoreUtils.getUniqueElement(list);
		assertThat(res).isNull();

		list = new ArrayList<>();
		res = CoreUtils.getUniqueElement(list);
		assertThat(res).isNull();

		list.add("One");
		res = CoreUtils.getUniqueElement(list);
		assertThat(res).isNotNull();
		assertThat("One").isEqualTo(res);

		list.add("two");
		res = CoreUtils.getUniqueElement(list);
		assertThat(res).isNull();
	}


	@Test
	public void testNewEmptyList() {

		List<Object> res = CoreUtils.newEmptyList(null);
		assertThat(res).isNotNull();
		assertThat(res).hasSize(0);

		List<String> list = new ArrayList<>();
		list.add("One");
		list.add("two");
		res = CoreUtils.newEmptyList(list);
		assertThat(res).isNotNull();
		assertThat(res).hasSize(0);
	}


	@Test
	public void testIsIn() {

		assertThat(CoreUtils.isIn("ABC", "ABC", "DEF")).isTrue();
		assertThat(CoreUtils.isIn("GHI", "ABC", "DEF")).isFalse();

		assertThat(CoreUtils.isIn("GHI")).isFalse();
		assertThat(CoreUtils.isIn(null)).isFalse();

		assertThat(CoreUtils.isIn(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))).isTrue();
		assertThat(CoreUtils.isIn(Integer.valueOf(9), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))).isFalse();

		assertThat(CoreUtils.isIn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE)).isTrue();
		assertThat(CoreUtils.isIn(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE)).isFalse();
	}


	@Test
	public void testHasInterect() {

		Collection<Integer> intColl1 = Arrays.asList(Integer.valueOf(1),Integer.valueOf(2), Integer.valueOf(3));
		Collection<Integer> intColl2 = Arrays.asList(Integer.valueOf(3),Integer.valueOf(4), Integer.valueOf(5));
		Collection<Integer> intColl3 = Arrays.asList(Integer.valueOf(6),Integer.valueOf(7), Integer.valueOf(8));

		assertThat(CoreUtils.hasIntersection(intColl1, intColl2)).isTrue();
		assertThat(CoreUtils.hasIntersection(intColl1, intColl3)).isFalse();

		assertThat(CoreUtils.hasIntersection(intColl1, null)).isFalse();
		assertThat(CoreUtils.hasIntersection(null, intColl2)).isFalse();
		assertThat(CoreUtils.hasIntersection(null, null)).isFalse();
	}


	@Test
	public void testUnion() {

		assertThat(CoreUtils.union(null, null)).isNotNull();

		Integer val = Integer.valueOf(1);

		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(val);
		Collection<Integer> c1 = CoreUtils.union(list1, null);
		assertThat(c1).hasSize(1);
		assertThat(c1).contains(val);

		List<Integer> list2 = null;
		Collection<Integer> c2 = CoreUtils.union(list2, val);
		assertThat(c2).hasSize(1);
		assertThat(c2).contains(val);

		List<Integer> list3 = new ArrayList<Integer>();
		list3.add(val);
		Collection<Integer> c3 = CoreUtils.union(list3, val);
		assertThat(c3).hasSize(1);
		assertThat(c3).contains(val);
	}

}
