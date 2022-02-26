package com.l2.ztob;


import lombok.Getter;

public class Pair<L, R>
{
	@Getter
	private final L left;
	@Getter
	private final R right;

	public Pair(L left, R right)
	{
		this.left = left;
		this.right = right;
	}

	static <T, R> Pair<T, R> of(T l, R r)
	{
		return new Pair<>(l, r);
	}

	public L getKey()
	{
		return left;
	}

	public R getValue()
	{
		return right;
	}
}