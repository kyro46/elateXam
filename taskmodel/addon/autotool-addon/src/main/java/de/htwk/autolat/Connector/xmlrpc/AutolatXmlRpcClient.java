package de.htwk.autolat.Connector.xmlrpc;

import java.net.URL;

import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcFault;
import de.htwk.autolat.Connector.AutolatConnectorException;
import de.htwk.autolat.Connector.AutolatConnectorTimeoutException;

/**
 * Adapter for XmlRpcClient class, adding timeout handling.
 *
 * @author Bertram Felgenhauer
 */
public class AutolatXmlRpcClient
{
	private final URL url;
	private final boolean stream;
	private int timeout;

	public AutolatXmlRpcClient(URL url, boolean stream, int timeout)
	{
		this.url = url;
		this.stream = stream;
		this.timeout = timeout;
	}

	public AutolatXmlRpcClient(URL url, boolean stream)
	{
		this.url = url;
		this.stream = stream;
		this.timeout = 20000; // 20 seconds
	}

	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public Object invoke(final String function, final Object[] args)
		throws XmlRpcFault, AutolatConnectorException
	{
		final InvocationResult result = new InvocationResult();

		Thread worker = new Thread() {
			@Override
      public void run() {
				XmlRpcClient client = new XmlRpcClient(url, stream);
				try {
					result.result = client.invoke(function, args);
					result.hasResult = true;
				} catch (XmlRpcFault fault) {
					result.fault = fault;
				}
				catch (RuntimeException exc) {
					// e.g. XmlRpcException
					result.exc = exc;
				}
			}
		};
		worker.start();
		try {
			worker.join(timeout);
		}
		catch (InterruptedException e) {
			worker.interrupt();
		    Thread.currentThread().interrupt();
		}
		worker.interrupt();
		if (result.hasResult)
			return result.result;
		if (result.fault != null)
			throw result.fault;
		if (result.exc != null)
			throw result.exc;
		throw new AutolatConnectorTimeoutException("timeout in '" + function + "' request");
	}

	private class InvocationResult {
		public volatile boolean     hasResult = false;
		public volatile Object      result;
		public volatile XmlRpcFault fault;
		public volatile RuntimeException exc;
	}
}





