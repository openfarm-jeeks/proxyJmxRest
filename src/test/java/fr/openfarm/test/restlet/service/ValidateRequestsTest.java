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
package fr.openfarm.test.restlet.service;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import fr.openfarm.bean.request.GetJmxDataRequest;
import fr.openfarm.bean.request.GetKeysForObjectRequest;
import fr.openfarm.bean.request.ServerJMX;
import fr.openfarm.restlet.service.impl.GetJmxData;
import fr.openfarm.restlet.service.impl.GetKeysForObject;
import fr.openfarm.restlet.service.utils.RestletMessageConstants;

/**
 * 
 * @author Openfarm
 *
 */
public class ValidateRequestsTest {
	private static final String methodName = "validateRequest";
	
	@Test
	public void testGetJmxDataRequest(){
		Class<GetJmxData> targetClass = GetJmxData.class;
		Class<?> argClasses[] = new Class[]{GetJmxDataRequest.class};
		
		GetJmxData targetObject = new GetJmxData();
		GetJmxDataRequest request = new GetJmxDataRequest("", "1099", "objName", "prop", "key");
		
		try {
			Method method = targetClass.getDeclaredMethod(methodName, argClasses);
			method.setAccessible(true);
			
			String result = (String) method.invoke(targetObject, new Object[]{null});
			Assert.assertEquals("test with hostname empty failed", RestletMessageConstants.NO_REQUEST,  result);
			
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test with hostname empty failed", RestletMessageConstants.NO_HOSTNAME_SPECIFIED,  result);
			
			request.getServerJMX().setHostName(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test with hostname null failed", RestletMessageConstants.NO_HOSTNAME_SPECIFIED,  result);
			
			request.setServerJMX(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test serverJMX null failed", RestletMessageConstants.HOST_NOT_SET,  result);
			
			request.setServerJMX(new ServerJMX());
			request.getServerJMX().setHostName("host");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test port null failed", RestletMessageConstants.NO_PORT_SPECIFIED,  result);
			
			request.getServerJMX().setPort("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test port empty failed", RestletMessageConstants.NO_PORT_SPECIFIED,  result);
			
			request.getServerJMX().setPort("1099");
			request.setJmxObject("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxObject empty failed", RestletMessageConstants.NO_TARGET_OBJECT_SPECIFIED,  result);
			
			request.setJmxObject(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxObject null failed", RestletMessageConstants.NO_TARGET_OBJECT_SPECIFIED,  result);
			
			request.setJmxObject("obj");
			request.setJmxProperty("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxPropertry empty failed", RestletMessageConstants.NO_TARGET_PROPERTY_SPECIFIED,  result);
			
			request.setJmxProperty(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxPropertry null failed", RestletMessageConstants.NO_TARGET_PROPERTY_SPECIFIED,  result);
			
			request.setJmxProperty("props");
			request.setJmxKey("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertNull("if key empty no error message",  result);
			
			request.setJmxKey(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertNull("if key null no error message",  result);
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

	}
	
	@Test
	public void testGetKeysForObjectRequest(){
		Class<GetKeysForObject> targetClass = GetKeysForObject.class;
		Class<?> argClasses[] = new Class[]{GetKeysForObjectRequest.class};
		
		GetKeysForObject targetObject = new GetKeysForObject();
		GetKeysForObjectRequest request = new GetKeysForObjectRequest("", "1099", "objName", "prop");
		
		try {
			Method method = targetClass.getDeclaredMethod(methodName, argClasses);
			method.setAccessible(true);
			
			String result = (String) method.invoke(targetObject, new Object[]{null});
			Assert.assertEquals("test with hostname empty failed", RestletMessageConstants.NO_REQUEST,  result);
			
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test with hostname empty failed", RestletMessageConstants.NO_HOSTNAME_SPECIFIED,  result);
			
			request.getServerJMX().setHostName(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test with hostname null failed", RestletMessageConstants.NO_HOSTNAME_SPECIFIED,  result);
			
			request.setServerJMX(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test serverJMX null failed", RestletMessageConstants.HOST_NOT_SET,  result);
			
			request.setServerJMX(new ServerJMX());
			request.getServerJMX().setHostName("host");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test port null failed", RestletMessageConstants.NO_PORT_SPECIFIED,  result);
			
			request.getServerJMX().setPort("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("test port empty failed", RestletMessageConstants.NO_PORT_SPECIFIED,  result);
			
			request.getServerJMX().setPort("1099");
			request.setJmxObject("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxObject empty failed", RestletMessageConstants.NO_TARGET_OBJECT_SPECIFIED,  result);
			
			request.setJmxObject(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxObject null failed", RestletMessageConstants.NO_TARGET_OBJECT_SPECIFIED,  result);
			
			request.setJmxObject("obj");
			request.setJmxProperty("");
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxPropertry empty failed", RestletMessageConstants.NO_TARGET_PROPERTY_SPECIFIED,  result);
			
			request.setJmxProperty(null);
			result = (String) method.invoke(targetObject, request);
			Assert.assertEquals("jmxPropertry null failed", RestletMessageConstants.NO_TARGET_PROPERTY_SPECIFIED,  result);
			
			request.setJmxProperty("props");
			result = (String) method.invoke(targetObject, request);
			Assert.assertNull("if key empty no error message",  result);
			
			Assert.assertNull("if key null no error message",  result);
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

	}	
}
