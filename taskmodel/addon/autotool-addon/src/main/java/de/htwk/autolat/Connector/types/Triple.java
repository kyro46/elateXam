package de.htwk.autolat.Connector.types;

public class Triple<A, B, C>
{
	private final A first;
	private final B second;
	private final C third;
	
	public Triple(A first, B second, C third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public A getFirst()
	{
		return first;
	}
	
	public B getSecond()
	{
		return second;
	}
	
	public C getThird()
	{
		return third;
	}

	@Override
  public String toString()
	{
		return "Triple(" + first + "," + second + "," + third + ")";
	}
}





/*
 * Warning: This is a generated file. Edit at your own risk.
 * generated by Gen.hs on Thu Jan 21 17:56:48 CET 2010.
 */

