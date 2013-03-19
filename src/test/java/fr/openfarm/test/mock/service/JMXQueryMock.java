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
package fr.openfarm.test.mock.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.openfarm.bean.response.KeyResponse;
import fr.openfarm.jmx.service.IJMXQuery;
import fr.openfarm.jmx.utils.jmxTools;

/**
 * Mocked object to simulate queries to jmx server
 * @author Openfarm
 *
 */
public class JMXQueryMock implements IJMXQuery {
	private static final Log log  = LogFactory.getLog(JMXQueryMock.class);

	@Override
	public void connect(String username, String password, String url) throws IOException {
		log.debug("JMXQueryMock connection");
	}


	@Override
	public String getJmxData(String name, String attributeName, String key) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		// TODO Auto-generated method stub
		return "value1";
	}

	/**
	 * @param name if name =simple return simple value, if name=multi return list of key/value, if name empty or null return null
	 * @return depending on name send back different results
	 */
	@Override
	public List<KeyResponse> getJmxKeys(String name, String attributeName) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		// TODO Auto-generated method stub
		List<KeyResponse> result = new ArrayList<KeyResponse>();
		if(jmxTools.checkNullOrEmpty(name)){
			return null;
		} 
		if(name.equals("simple")){
			KeyResponse keyResponse = new KeyResponse();
			keyResponse.setValue("value1");
			result.add(keyResponse);
		} else if(name.equals("multi")){
			for(int i=1; i<5; i++){
				KeyResponse keyResponse = new KeyResponse();
				keyResponse.setKey("key"+i);
				keyResponse.setValue("value"+i);
				result.add(keyResponse);
			}
		}
		return result;
	}

	@Override
	public void disconnect() throws IOException {
		log.debug("JMXQueryMock deco");
	}

}
