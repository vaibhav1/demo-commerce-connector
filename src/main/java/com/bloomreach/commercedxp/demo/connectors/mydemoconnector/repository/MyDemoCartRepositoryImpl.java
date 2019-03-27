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

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.CartForm;
import com.bloomreach.commercedxp.api.v2.connector.model.CartModel;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractCartRepository;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoCartRepositoryImpl extends AbstractCartRepository {

    public MyDemoCartRepositoryImpl() {
        // TODO Auto-generated constructor stub
    }

    public CartModel save(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    public CartModel create(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    public CartModel delete(CommerceConnector connector, String resourceId) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    public CartModel checkIn(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

    public CartModel checkOut(CommerceConnector connector, CartForm resourceForm) throws ConnectorException {
        // TODO Auto-generated method stub
        return null;
    }

}
