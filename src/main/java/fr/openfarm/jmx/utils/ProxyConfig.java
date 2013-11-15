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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class ProxyConfig implements InitializingBean{
	private static final Log log = LogFactory.getLog(ProxyConfig.class);
	Map<String, JmxLoginPass> jmxAccess;
	
	
	public void addLoginPass(String key, String login, String password){
		
	}
	
	public String getUserName(String host, String port){
		String key = host+":"+port;
		JmxLoginPass loginPass = jmxAccess.get(key);
		if(loginPass == null){
			return null;
		}
		return loginPass.getLogin();
	}
	
	public String getPassword(String host, String port){
		String key = host+":"+port;
		JmxLoginPass loginPass = jmxAccess.get(key);
		if(loginPass == null){
			return null;
		}
		return loginPass.getPassword();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("set Up Proxy Configuration");
		jmxAccess = new HashMap<String, JmxLoginPass>();
		String fileName = System.getProperty("configFilePath");
		if(System.getProperty("configFilePath") != null){
			try{
				XMLConfiguration xmlConfiguration = new XMLConfiguration(fileName);
				SubnodeConfiguration accessList = xmlConfiguration.configurationAt("accessList");
				List<HierarchicalConfiguration> loginPassConfs = accessList.configurationsAt("accessConf");
				for(HierarchicalConfiguration loginPassConf : loginPassConfs){
					JmxLoginPass jmxLoginPass = new JmxLoginPass();
					jmxLoginPass.setLogin(loginPassConf.getString("login"));
					jmxLoginPass.setPassword(loginPassConf.getString("password"));
					jmxAccess.put(loginPassConf.getString("key"), jmxLoginPass);
				}
			} catch(Exception e){
				log.error("Error while reading conf file : "+fileName, e);
			}
		}else{
			log.info("no configuration given to the jvm by param -DconfigFilePath");
		}
		
	}
}
