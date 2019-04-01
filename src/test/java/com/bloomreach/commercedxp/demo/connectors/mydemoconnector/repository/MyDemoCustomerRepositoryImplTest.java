/**
 * Copyright 2019 BloomReach Inc. (https://www.bloomreach.com/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.bloomreach.commercedxp.demo.connectors.mydemoconnector.repository;

import org.junit.Before;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.model.CustomerModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.CustomerRepository;
import com.bloomreach.commercedxp.common.v2.connector.form.SimpleCustomerForm;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyDemoCustomerRepositoryImplTest extends AbstractMyDemoRepositoryTest {

    private CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        customerRepository = new MyDemoCustomerRepositoryImpl();
    }

    @Before
    public void testCheckInWithoutSignup() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemoSpace");

        SimpleCustomerForm resourceForm = new SimpleCustomerForm("john@example.com", "password", "mystore");

        try {
            customerRepository.checkIn(mockConnector, resourceForm);
            fail("Not supposed to sign-in by non-registered user.");
        } catch (ConnectorException expected) {
        }
    }

    @Before
    public void testCheckInOutAfterSignup() throws Exception {
        final CommerceConnector mockConnector = createMockCommerceConnector("mydemoSpace");

        SimpleCustomerForm resourceForm = new SimpleCustomerForm("John", "", "Doe", "john@example.com", "password", "password", null);
        CustomerModel customerModel = customerRepository.create(mockConnector, resourceForm);

        assertEquals("John", customerModel.getFirstName());
        assertEquals("Doe", customerModel.getLastName());
        assertEquals("john@example.com", customerModel.getEmail());

        resourceForm = new SimpleCustomerForm("john@example.com", "password", "mystore");
        customerModel = customerRepository.checkIn(mockConnector, resourceForm);

        assertEquals("John", customerModel.getFirstName());
        assertEquals("Doe", customerModel.getLastName());
        assertEquals("john@example.com", customerModel.getEmail());

        customerModel = customerRepository.checkOut(mockConnector, resourceForm);

        assertEquals("John", customerModel.getFirstName());
        assertEquals("Doe", customerModel.getLastName());
        assertEquals("john@example.com", customerModel.getEmail());
    }
}
