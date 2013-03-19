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
import java.util.List;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import fr.openfarm.bean.response.KeyResponse;

public interface IJMXQuery {
	/**
	 * open a new jmx connection
	 * @param username jmx userName
	 * @param password jmx password
	 * @param url url like service:jmx:rmi:///jndi/rmi://hostname:port/jmxri
	 * @throws IOException
	 */
	void connect(String username, String password, String url) throws IOException;

	/**
	 * get a jmx value, if target property is a compisteObject, key should be specified
	 * @param name target object name
	 * @param attributeName target property
	 * @param key optional key for jmx composite property
	 * @return value of the property
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 * @throws AttributeNotFoundException
	 * @throws InstanceNotFoundException
	 * @throws MBeanException
	 * @throws ReflectionException
	 * @throws IOException
	 */
	String getJmxData(String name, String attributeName, String key) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException, IOException;

	/**
	 * Get a list of pair value/key for composite property or directly a value if targeted property is simple type
	 * @param name Target jmx object 
	 * @param attributeName target property of the object
	 * @return list of keys/values if target property is a composite type, value if not a composite type.
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 * @throws AttributeNotFoundException
	 * @throws InstanceNotFoundException
	 * @throws MBeanException
	 * @throws ReflectionException
	 * @throws IOException
	 */
	List<KeyResponse> getJmxKeys(String name, String attributeName) throws MalformedObjectNameException, NullPointerException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException, IOException;

	/**
	 * close jmx connection
	 * @throws IOException
	 */
	void disconnect() throws IOException;

}
