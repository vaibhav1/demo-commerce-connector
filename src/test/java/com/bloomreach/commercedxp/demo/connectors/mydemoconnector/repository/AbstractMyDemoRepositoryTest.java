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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnectorComponent;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;

public abstract class AbstractMyDemoRepositoryTest {

    protected CommerceConnectorComponent createMockCommerceConnectorComponent(final String id,
            final String serviceBaseUrl, final String methodName, final Map<String, String> requestHeaders,
            final String requestBody) {
        final CommerceConnectorComponent component = createNiceMock(CommerceConnectorComponent.class);
        expect(component.getId()).andReturn(id).anyTimes();
        expect(component.getServiceBaseUrl()).andReturn(serviceBaseUrl).anyTimes();
        expect(component.getHeaders()).andReturn(requestHeaders).anyTimes();
        expect(component.getMethodType()).andReturn(methodName).anyTimes();
        expect(component.getRequestBody()).andReturn(requestBody).anyTimes();
        replay(component);
        return component;
    }

    protected CommerceConnector createMockCommerceConnector(final String resourceSpace,
            CommerceConnectorComponent... components) {
        final Map<String, CommerceConnectorComponent> componentMap = new HashMap<>();

        if (components != null) {
            for (CommerceConnectorComponent component : components) {
                componentMap.put(component.getId(), component);
            }
        }

        return createMockCommerceConnector(resourceSpace, componentMap);
    }

    protected CommerceConnector createMockCommerceConnector(final String resourceSpace,
            final Map<String, CommerceConnectorComponent> componentMap) {
        CommerceConnector connector = createNiceMock(CommerceConnector.class);
        expect(connector.getResourceSpace()).andReturn(resourceSpace).anyTimes();
        expect(connector.getComponentById(isA(String.class))).andDelegateTo(new CommerceConnector() {
            @Override
            public String getId() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getModuleName() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getResourceSpace() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Map<String, String> getProperties() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getProperty(String propName) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Collection<CommerceConnectorComponent> getComponents() {
                throw new UnsupportedOperationException();
            }

            @Override
            public CommerceConnectorComponent getComponentById(String id) {
                return componentMap.get(id);
            }
        }).anyTimes();
        replay(connector);
        return connector;
    }
}
