/*
+---------------------------------------------------------------------------+
| Revive Adserver                                                           |
| http://www.revive-adserver.com                                            |
|                                                                           |
| Copyright: See the COPYRIGHT.txt file.                                    |
| License: GPLv2 or later, see the LICENSE.txt file.                        |
+---------------------------------------------------------------------------+
*/

package org.openx.channel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.openx.config.GlobalSettings;
import org.openx.publisher.PublisherTestCase;

/**
 * Base class for all channel web service tests
 *
 * @author Andriy Petlyovanyy <apetlyovanyy@lohika.com>
 * @author David Keen <david.keen@openx.org>
 */
public class ChannelTestCase extends PublisherTestCase {

    // Method names
	protected static final String GET_CHANNEL_LIST_BY_WEBSITE_ID_METHOD = "ox.getChannelListByWebsiteId";
    protected static final String GET_CHANNEL_LIST_BY_AGENCY_ID_METHOD = "ox.getChannelListByAgencyId";
	protected static final String GET_CHANNEL_METHOD = "ox.getChannel";
	protected static final String ADD_CHANNEL_METHOD = "ox.addChannel";
	protected static final String DELETE_CHANNEL_METHOD = "ox.deleteChannel";
	protected static final String MODIFY_CHANNEL_METHOD = "ox.modifyChannel";
	protected static final String GET_CHANNEL_TARGETING = "ox.getChannelTargeting";
	protected static final String SET_CHANNEL_TARGETING = "ox.setChannelTargeting";

	protected static final String CHANNEL_ID = "channelId";
	protected static final String AGENCY_ID = "agencyId";
	protected static final String WEBSITE_ID = "websiteId";
    protected static final String PUBLISHER_ID = "publisherId";
	protected static final String CHANNEL_NAME = "channelName";
	protected static final String DESCRIPTION = "description";
	protected static final String COMMENTS = "comments";

    protected Integer websiteId;

    @Override
	protected void setUp() throws Exception {
		super.setUp();
        websiteId = createPublisher();
	}

    @Override
	protected void tearDown() throws Exception {
        deletePublisher(websiteId);
		super.tearDown();
	}

	/**
	 * @return channel id
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public Integer createChannel() throws XmlRpcException, MalformedURLException {
		return createChannel(getChannelParams("test"));
	}

	/**
	 * @return channel id
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public Integer createChannel(Map<String, Object> params)
			throws XmlRpcException, MalformedURLException {
		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getServiceUrl()));

		Object[] paramsWithId = new Object[] { sessionId, params };
		final Integer result = (Integer) client.execute(ADD_CHANNEL_METHOD,
				paramsWithId);
		return result;
	}

	/**
	 * @param id id of channel you want to remove
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public boolean deleteChannel(Integer id) throws XmlRpcException,
			MalformedURLException {
		// set URL
		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getServiceUrl()));

		return (Boolean) client.execute(DELETE_CHANNEL_METHOD, new Object[] {
				sessionId, id });
	}

    @Override
	public Object execute(String method, Object[] params)
			throws XmlRpcException, MalformedURLException {
		// set URL
		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getServiceUrl()));

		return client.execute(method, params);
	}

	public Map<String, Object> getChannelParams(String prefix) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AGENCY_ID, agencyId);
		params.put(DESCRIPTION, prefix + DESCRIPTION);
		params.put(CHANNEL_NAME, prefix + CHANNEL_NAME);
        params.put(COMMENTS, prefix + COMMENTS);
		params.put(WEBSITE_ID, websiteId);
		return params;
	}
}
