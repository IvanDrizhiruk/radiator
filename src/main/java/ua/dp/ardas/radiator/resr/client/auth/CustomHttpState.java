package ua.dp.ardas.radiator.resr.client.auth;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.auth.AuthScope;

public class CustomHttpState extends HttpState {

	public void setCredentials(final Credentials credentials) {
		super.setCredentials(AuthScope.ANY, credentials);
	}
}