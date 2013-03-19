====
           Licensed to the Apache Software Foundation (ASF) under one
           or more contributor license agreements.  See the NOTICE file
           distributed with this work for additional information
           regarding copyright ownership.  The ASF licenses this file
           to you under the Apache License, Version 2.0 (the
           "License"); you may not use this file except in compliance
           with the License.  You may obtain a copy of the License at

             http://www.apache.org/licenses/LICENSE-2.0

           Unless required by applicable law or agreed to in writing,
           software distributed under the License is distributed on an
           "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
           KIND, either express or implied.  See the License for the
           specific language governing permissions and limitations
           under the License.
====

Setting logs directory: /var/log/proxyJMX/proxyJMX.log to modify it, change file log4j.xml in the war : WEB-INF directory




Examples of use :
 
Get keys for a composite jmx object
http://$proxyUrl/proxyJMX/getKeysForObject?hostname=$jmxHostname&port=$jmxPort&objectName=java.lang:type=Memory&property=HeapMemoryUsage

Get a jmx value
http://$proxyUrl/proxyJMX/getJmxData?hostname=$jmxHostname&port=$jmxPort&objectName=java.lang:type=Memory&property=HeapMemoryUsage&key=used