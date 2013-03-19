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
package fr.openfarm.jmx.utils;

/**
 * common tool class
 * @author Openfarm 
 */
public class jmxTools {
	/**
	 * Build jmx url with hostname and port of target jvm
	 * 
	 * @param hostname hostname or Ip where jvm is set with jmx
	 * @param port jmx port
	 * @return Url that will be used to connect to jmx server
	 */
	public static String buildUrl(String hostname, String port) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("service:jmx:rmi:///jndi/rmi://");
		sbUrl.append(hostname);
		sbUrl.append(":").append(port);
		sbUrl.append("/jmxrmi");
		return sbUrl.toString();
	}

	/**
	 * check if is string is null or empty
	 * @param value String to check
	 * @return true if string is null or empty (equal to "")
	 */
	public static boolean checkNullOrEmpty(String value) {
		if (value == null || value.equals("")) {
			return true;
		}
		return false;
	}
}
