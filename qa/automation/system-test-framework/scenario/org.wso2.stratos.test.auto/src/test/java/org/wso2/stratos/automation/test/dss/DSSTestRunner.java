/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/
package org.wso2.stratos.automation.test.dss;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DSSTestRunner extends TestSuite {


    public static Test suite() {

        TestSuite suite = new TestSuite();


        suite.addTestSuite(CSVDataServiceTestClient.class);
        suite.addTestSuite(ExcelDataServiceTestClient.class);
        suite.addTestSuite(GSpreadDataServiceTestClient.class);
        suite.addTestSuite(MySqlDataServiceTestClient.class);
        suite.addTestSuite(CarbonDataSourceTestClient.class);
        suite.addTestSuite(DTPServiceTestClient.class);
        suite.addTestSuite(EventingServiceTestClient.class);
        suite.addTestSuite(NestedQueryTestClient.class);
        suite.addTestSuite(TestBatchRequestTestClient.class);
        suite.addTestSuite(TestMySqlFileServiceClient.class);
        suite.addTestSuite(ResourcesServiceTestClient.class);
        suite.addTestSuite(SecureDataServiceTestClient.class);
        suite.addTestSuite(InputParametersValidationTest.class);
        suite.addTestSuite(MySqlStoredProcedureTest.class);
        suite.addTestSuite(FaultyServiceTest.class);
        suite.addTestSuite(EditFaultyDataServiceTest.class);
        suite.addTestSuite(ServiceFaultyTest.class);
        suite.addTestSuite(ScheduleTaskAddTest.class);
        suite.addTestSuite(ReScheduleTaskTest.class);

        return suite;
    }

}
