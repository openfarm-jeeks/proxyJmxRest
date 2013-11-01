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
package fr.openfarm.bean.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("JMXMultiResponse")
public class GetMultiObjectKeysResponse {
	@XStreamAlias("objectKeys")
	private List<MultiObjectKeys> multiObjectKeys;
	@XStreamAlias("error")
	private ErrorJmx error;
	public ErrorJmx getError() {
		return error;
	}

	public List<MultiObjectKeys> getMultiObjectKeys() {
		return multiObjectKeys;
	}

	public void setMultiObjectKeys(List<MultiObjectKeys> multiObjectKeys) {
		this.multiObjectKeys = multiObjectKeys;
	}

	public void setError(ErrorJmx error) {
		this.error = error;
	}
	
}
