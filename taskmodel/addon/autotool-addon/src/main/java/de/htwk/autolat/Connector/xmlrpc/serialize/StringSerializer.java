package de.htwk.autolat.Connector.xmlrpc.serialize;

public class StringSerializer implements Serializer<String>
{
	private static final StringSerializer inst = new StringSerializer();

	public static Serializer<String> getInstance()
	{
		return inst;
	}
	
	public Object serialize(String val)
	{
		return val;
	}
}





/*
 * Warning: This is a generated file. Edit at your own risk.
 * generated by Gen.hs on Thu Jan 21 17:56:48 CET 2010.
 */

