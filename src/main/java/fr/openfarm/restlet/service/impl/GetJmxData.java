/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 */
package fr.openfarm.restlet.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import fr.openfarm.bean.request.GetJmxDataRequest;
import fr.openfarm.bean.response.ErrorJmx;
import fr.openfarm.bean.response.GetJmxDataResponse;
import fr.openfarm.jmx.service.JMXQuery;
import fr.openfarm.jmx.utils.ProxyConfig;
import fr.openfarm.jmx.utils.jmxTools;
import fr.openfarm.restlet.service.utils.RestletMessageConstants;

/**
 * @author Openfarm
 * receive Restlet request for getting data from a jmx property
 */
public class GetJmxData extends ServerResource {
	private static final Log log  = LogFactory.getLog(GetKeysForObject.class);
	private ProxyConfig proxyConfig;
	
	/**
	 * Get value for an jmxObject
	 * hostname : target jmx hostname
	 * port : target jmx port
	 * objectName : target jmx object
	 * propertry : property to get value from jmx object
	 * key : (optional), choose a key if property is a jmx compositeObject
	 * @return rest response according target object and property
	 */
	@Get
	public GetJmxDataResponse getJmxData() {
		String hostname = getQuery().getValues("hostname");
		String port = getQuery().getValues("port");
		String objectName = getQuery().getValues("objectName");
		String property = getQuery().getValues("property");
		String key = getQuery().getValues("key");


		GetJmxDataRequest request = new GetJmxDataRequest(hostname, port, objectName, property, key);
		GetJmxDataResponse jmxResponse = new GetJmxDataResponse();

		String validateResult = validateRequest(request);
		// If request incomplete return error message
		if (validateResult != null) {
			ErrorJmx errorJmx = new ErrorJmx();
			errorJmx.setMessage(validateResult);
			jmxResponse.setError(errorJmx);
			return jmxResponse;
		}
		String jmxUrl = jmxTools.buildUrl(request.getServerJMX().getHostName(), request.getServerJMX().getPort());
		JMXQuery jmxQuery = new JMXQuery();
		try {
			String login = proxyConfig.getUserName(request.getServerJMX().getHostName(),request.getServerJMX().getPort());
			String password = proxyConfig.getPassword(request.getServerJMX().getHostName(),request.getServerJMX().getPort());
			
			jmxQuery.connect(login, password, jmxUrl);
			String strResponse = jmxQuery.getJmxData(request.getJmxObject(), request.getJmxProperty(), request.getJmxKey());
			jmxResponse.setResponse(strResponse);
		} catch (Exception e) {
			ErrorJmx errorJmx = new ErrorJmx();
			errorJmx.setMessage("Exception while performing jmxRequest : " + e.getMessage());
			log.error("Error performing request ", e);
			jmxResponse.setError(errorJmx);
		} finally {
			try {
				jmxQuery.disconnect();
			} catch (IOException e) {
				// log.error("error while disconnecting jmx connection", e);
			}
		}
		return jmxResponse;
	}

	/**
	 * check input parameters
	 * @param request input request to be validated
	 * @return null if ok, string error if a field is not validated
	 */
	private String validateRequest(GetJmxDataRequest request) {
		if (request == null) {
			return RestletMessageConstants.NO_REQUEST;
		}
		if (request.getServerJMX() == null) {
			return RestletMessageConstants.HOST_NOT_SET;
		}
		if (jmxTools.checkNullOrEmpty(request.getServerJMX().getHostName())) {
			return RestletMessageConstants.NO_HOSTNAME_SPECIFIED;
		}
		if (jmxTools.checkNullOrEmpty(request.getServerJMX().getPort())) {
			return RestletMessageConstants.NO_PORT_SPECIFIED;
		}
		if (jmxTools.checkNullOrEmpty(request.getJmxObject())) {
			return RestletMessageConstants.NO_TARGET_OBJECT_SPECIFIED;
		}
		if (jmxTools.checkNullOrEmpty(request.getJmxProperty())) {
			return RestletMessageConstants.NO_TARGET_PROPERTY_SPECIFIED;
		}
		return null;
	}

	public void setProxyConfig(ProxyConfig proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

}