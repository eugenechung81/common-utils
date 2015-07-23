package com.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class CollectionUtils
{
	public static <T> T getFirst(Collection<T> collection)
	{
		if (collection == null || collection.isEmpty())
		{
			return null;
		}
		return collection.iterator().next();
	}

	public static <T> boolean isEmpty(Collection<T> collection)
	{
		return collection == null || collection.isEmpty();
	}

	public static <K, V> boolean isEmpty(Map<K, V> map)
	{
		return map == null || map.isEmpty();
	}

	public static <T, F> List<F> transform(List<T> filter, Function<T, F> func)
	{
		return Lists.transform(new ArrayList<>(filter), func);
	}

	public static <T> List<T> filter(List<T> groups, Predicate<T> predicate)
	{
		return new ArrayList<>(Collections2.filter(groups, predicate));
	}

	public static <T> void sort(List<T> filter, Comparator<T> comparator)
	{
		Collections.sort(filter, comparator);
	}
}