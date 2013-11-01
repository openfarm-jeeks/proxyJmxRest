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
package fr.openfarm.jmx.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.openfarm.bean.response.ErrorJmx;
import fr.openfarm.bean.response.GetMultiObjectKeysResponse;
import fr.openfarm.bean.response.GetWildCardMultiDataResponse;
import fr.openfarm.bean.response.KeyResponse;
import fr.openfarm.bean.response.MultiObjectKeys;
import fr.openfarm.bean.response.ObjectNameAndKey;

/**
 * Object that drive jmx requests
 * @author Openfarm
 *
 */
public class JMXQuery implements IJMXQuery
{
	private static final Log log  = LogFactory.getLog(JMXQuery.class);
	private static final String USERNAME_KEY = "username";
	private static final String PASSWORD_KEY = "password";
	private MBeanServerConnection connection;
	private JMXConnector connector = null;
	

	@Override
	public void connect(String username, String password, String url) throws IOException {
		Map<String, Object> environment = null;
		if("".equals(username)){
			username = null;
		}
		if("".equals(password)){
			username = null;
		}
		if (username != null && password != null) {
            environment = new HashMap<String, Object>();
            environment.put(JMXConnector.CREDENTIALS, new String[] {username, password});
            environment.put(USERNAME_KEY, username);
            environment.put(PASSWORD_KEY, password);
		}
		
		JMXServiceURL jmxUrl = new JMXServiceURL(url);
		if(environment != null) {
			log.info("connect with user/pass");
			connector = JMXConnectorFactory.connect(jmxUrl, environment);
		} else {
			log.info("connect without user/pass");
			connector = JMXConnectorFactory.connect(jmxUrl);
		}
		connection = connector.getMBeanServerConnection();
	}


	@Override
	public String getJmxData(String name, String attributeName, String key) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		ObjectName objectName = new ObjectName(name);
		Set<ObjectName> objectNameList = connection.queryNames(objectName, null);
		if(objectNameList != null && objectNameList.size()>0){
			objectName = objectNameList.iterator().next();
			Object attr = connection.getAttribute(objectName, attributeName);
			if (attr instanceof CompositeDataSupport) {
				CompositeDataSupport cds = (CompositeDataSupport) attr;
				if (key == null) {
					throw new IllegalArgumentException("Key is null for composed data " + name);
				}
				return cds.get(key).toString();
				
			} else {
				return attr.toString();
			}
		} 
		return null;
	}
	
	@Override
	public GetWildCardMultiDataResponse getWildCardJmxData(String name, String attributeName, String key) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		ObjectName objectName = new ObjectName(name);
		Set<ObjectName> objectNameList = connection.queryNames(objectName, null);
		GetWildCardMultiDataResponse response = new GetWildCardMultiDataResponse();
		List<ObjectNameAndKey> objectAndkeyList= new ArrayList<ObjectNameAndKey>();
		for(ObjectName iterName:objectNameList){
			objectName = iterName;
			ObjectNameAndKey objectAndKey = new ObjectNameAndKey();
			objectAndKey.setObjectName(objectName.getCanonicalName());
			Object attr = connection.getAttribute(objectName, attributeName);
			if (attr instanceof CompositeDataSupport) {
				CompositeDataSupport cds = (CompositeDataSupport) attr;
				if (key == null) {		
					ErrorJmx error = new ErrorJmx();
					error.setMessage("Key is null for composed data " + name);
					response.setError(error);
					return response;
				}
				KeyResponse keyResponse = new KeyResponse();
				keyResponse.setKey(cds.get(key).toString());
				objectAndKey.setKeyResponse(keyResponse);
				
			} else {
				KeyResponse keyResponse = new KeyResponse();
				keyResponse.setKey(attr.toString());
				objectAndKey.setKeyResponse(keyResponse);
			}
			objectAndkeyList.add(objectAndKey);
		} 
		response.setResponse(objectAndkeyList);
		return response;
	}

	@Override
	public List<KeyResponse> getJmxKeys(String name, String attributeName) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException{
		List<KeyResponse> response = new ArrayList<KeyResponse>();
		ObjectName objectName = new ObjectName(name);
		Set<ObjectName> objectNameList = connection.queryNames(objectName, null);
		if(objectNameList != null && objectNameList.size()>0){
			objectName = objectNameList.iterator().next();
			Object attr = connection.getAttribute(objectName, attributeName);
			if (attr instanceof CompositeDataSupport) {
				CompositeDataSupport cds = (CompositeDataSupport) attr;
				CompositeType type = cds.getCompositeType();
				Set<String> listKey = type.keySet();
				for(Object key : listKey){
					if(key instanceof String){
						Object value = cds.get((String) key);
						KeyResponse keyResponse = new KeyResponse();
						keyResponse.setKey(key.toString());
						keyResponse.setValue(value.toString());
						response.add(keyResponse);
					}
				}		
			} else {
				KeyResponse keyResponse = new KeyResponse();
				keyResponse.setValue(attr.toString());
				response.add(keyResponse);
			}
		}
		return response;
	}

	@Override
	public GetMultiObjectKeysResponse getWildcardJmxKeys(String name, String attributeName) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException{
		GetMultiObjectKeysResponse reponse= new GetMultiObjectKeysResponse();
		ArrayList<MultiObjectKeys> multiObjectKeysList = new ArrayList<MultiObjectKeys>();
		
		ObjectName objectName = new ObjectName(name);
		Set<ObjectName> objectNameList = connection.queryNames(objectName, null);
		for(ObjectName iterObject:objectNameList){
			objectName = iterObject;
			List<KeyResponse> keyList = new ArrayList<KeyResponse>();
			Object attr = connection.getAttribute(objectName, attributeName);
			if (attr instanceof CompositeDataSupport) {
				CompositeDataSupport cds = (CompositeDataSupport) attr;
				CompositeType type = cds.getCompositeType();
				Set<String> listKey = type.keySet();
				for(Object key : listKey){
					if(key instanceof String){
						Object value = cds.get((String) key);
						KeyResponse keyResponse = new KeyResponse();
						keyResponse.setKey(key.toString());
						keyResponse.setValue(value.toString());
						keyList.add(keyResponse);
					}
				}		
			} else {
				KeyResponse keyResponse = new KeyResponse();
				keyResponse.setValue(attr.toString());
				keyList.add(keyResponse);
			}
			MultiObjectKeys multiObjectKeys = new MultiObjectKeys();
			multiObjectKeys.setJmxKeys(keyList);
			multiObjectKeys.setObjectName(objectName.getCanonicalName());
			multiObjectKeysList.add(multiObjectKeys);
		}
		reponse.setMultiObjectKeys(multiObjectKeysList);
		return reponse;
	}


	
	@Override
	public void disconnect() throws IOException
	{	
		if(connector != null){
			connector.close();
		}
	}



}
