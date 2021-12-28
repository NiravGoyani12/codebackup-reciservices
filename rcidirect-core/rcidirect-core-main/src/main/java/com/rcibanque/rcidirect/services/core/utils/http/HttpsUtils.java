package com.rcibanque.rcidirect.services.core.utils.http;

public class HttpsUtils {

	private HttpsUtils() {}


	/**
	 * Invoke this method if there are problems with creating HTTPS connection - you will get a large amount of
	 * extra log data which hopefully points you to the fault ;-)
	 */
	public static void initialiseJaxWsLogging() {

		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "20000");
		System.setProperty("javax.net.debug", "SSL,trustmanager");
	}
}
