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
package fr.openfarm.bean.request;


/**
 * @author Openfarm
 *
 */
public class GetKeysForObjectRequest {
	private ServerJMX serverJMX;
	private String jmxObject;
	private String jmxProperty;

	
	
	public GetKeysForObjectRequest(String hostName, String port, String objectName, String property) {
		serverJMX = new ServerJMX();
		serverJMX.setHostName(hostName);
		serverJMX.setPort(port);
		this.jmxProperty=property;
		this.jmxObject=objectName;
	}
	public ServerJMX getServerJMX() {
		return serverJMX;
	}
	public String getJmxObject() {
		return jmxObject;
	}
	public String getJmxProperty() {
		return jmxProperty;
	}
	public void setServerJMX(ServerJMX serverJMX) {
		this.serverJMX = serverJMX;
	}
	public void setJmxObject(String jmxObject) {
		this.jmxObject = jmxObject;
	}
	public void setJmxProperty(String jmxProperty) {
		this.jmxProperty = jmxProperty;
	}
}
