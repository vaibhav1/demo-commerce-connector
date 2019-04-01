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

import java.io.InputStream;

import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoData;
import com.fasterxml.jackson.databind.ObjectMapper;

class MyDemoDataLoader {

    private static final String MY_DEMO_DATA_RESOURCE = "com/bloomreach/commercedxp/demo/connectors/mydemoconnector/demoproducts.json";

    private static class LazyHolder {
        private static MyDemoData data = null;

        static {
            try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(MY_DEMO_DATA_RESOURCE)) {
                final ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(input, MyDemoData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        static final MyDemoData INSTANCE = data;
    }

    private MyDemoDataLoader() {
    }

    static MyDemoData getMyDemoData() {
        return LazyHolder.INSTANCE;
    }
}
