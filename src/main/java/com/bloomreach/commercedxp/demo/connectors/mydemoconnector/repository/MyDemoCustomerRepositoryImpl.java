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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.CustomerForm;
import com.bloomreach.commercedxp.api.v2.connector.model.CustomerModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractCustomerRepository;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoCustomerModel;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoCustomerRepositoryImpl extends AbstractCustomerRepository {

    private Map<String, MyDemoCustomerModel> customerModels = new ConcurrentHashMap<>();

    @Override
    public CustomerModel save(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer E-Mail address.");
        }

        final MyDemoCustomerModel customerModel = customerModels.get(resourceForm.getEmail());

        if (customerModel == null) {
            throw new ConnectorException("404", "Customer not found.");
        }

        customerModel.setFirstName(resourceForm.getFirstName());
        customerModel.setLastName(resourceForm.getLastName());

        return customerModel;
    }

    @Override
    public CustomerModel create(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer's E-Mail address.");
        }

        final MyDemoCustomerModel customerModel = new MyDemoCustomerModel(UUID.randomUUID().toString());
        customerModel.setEmail(resourceForm.getEmail());
        customerModel.setFirstName(resourceForm.getFirstName());
        customerModel.setLastName(resourceForm.getLastName());
        customerModels.put(resourceForm.getEmail(), customerModel);

        return customerModel;
    }

    @Override
    public CustomerModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CustomerModel checkIn(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer's E-Mail address.");
        }

        final MyDemoCustomerModel customerModel = customerModels.get(resourceForm.getEmail());

        if (customerModel == null) {
            throw new ConnectorException("401", "Customer not authenticated.");
        }

        return customerModel;
    }

    @Override
    public CustomerModel checkOut(CommerceConnector connector, CustomerForm resourceForm) throws ConnectorException {
        if (StringUtils.isBlank(resourceForm.getEmail())) {
            throw new IllegalArgumentException("Blank customer's E-Mail address.");
        }

        return customerModels.get(resourceForm.getEmail());
    }
}
