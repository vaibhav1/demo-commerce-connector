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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.bloomreach.commercedxp.api.v2.connector.ConnectorException;
import com.bloomreach.commercedxp.api.v2.connector.form.CategoryForm;
import com.bloomreach.commercedxp.api.v2.connector.model.ItemModel;
import com.bloomreach.commercedxp.api.v2.connector.model.PageResult;
import com.bloomreach.commercedxp.api.v2.connector.model.SimplePageResult;
import com.bloomreach.commercedxp.api.v2.connector.repository.AbstractProductRepository;
import com.bloomreach.commercedxp.api.v2.connector.repository.QuerySpec;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.MyDemoConstants;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoData;
import com.bloomreach.commercedxp.demo.connectors.mydemoconnector.model.MyDemoProductItem;
import com.bloomreach.commercedxp.starterstore.connectors.CommerceConnector;

public class MyDemoProductRepositoryImpl extends AbstractProductRepository {

    @Override
    public ItemModel findOne(CommerceConnector connector, String id, QuerySpec querySpec) throws ConnectorException {
        final MyDemoData data = MyDemoDataLoader.getMyDemoData();
        final List<MyDemoProductItem> productItems = data.getResponse().getProductItems();

        for (MyDemoProductItem item : productItems) {
            if (id.equals(item.getCode())) {
                return item;
            }
        }

        return null;
    }

    @Override
    public PageResult<ItemModel> findAll(CommerceConnector connector, QuerySpec querySpec) throws ConnectorException {
        final long offset = querySpec.getOffset();
        final long limit = (querySpec.getLimit() != null) ? querySpec.getLimit().longValue()
                : MyDemoConstants.DEFAULT_PAGE_LIMIT;
        final List<ItemModel> itemModels = new LinkedList<>();

        final MyDemoData data = MyDemoDataLoader.getMyDemoData();
        final List<MyDemoProductItem> productItems = data.getResponse().getProductItems();

        final long totalSize = productItems.size();
        final long endOffset = Math.min(offset + limit, totalSize);

        for (int index = (int) offset; index < endOffset; index++) {
            itemModels.add(productItems.get(index));
        }

        return new SimplePageResult<>(itemModels, offset, limit, totalSize);
    }

    @Override
    public PageResult<ItemModel> findAllByCategory(CommerceConnector connector, CategoryForm categoryForm,
            QuerySpec querySpec) throws ConnectorException {
        final long offset = querySpec.getOffset();
        final long limit = (querySpec.getLimit() != null) ? querySpec.getLimit().longValue()
                : MyDemoConstants.DEFAULT_PAGE_LIMIT;
        final String categoryId = categoryForm.getId();

        final List<ItemModel> itemModels = new LinkedList<>();

        final MyDemoData data = MyDemoDataLoader.getMyDemoData();
        final List<MyDemoProductItem> productItems = data.getResponse().getProductItems().stream()
                .filter(item -> item.getCategories().contains(categoryId)).collect(Collectors.toList());

        final long totalSize = productItems.size();
        final long endOffset = Math.min(offset + limit, totalSize);

        for (int index = (int) offset; index < endOffset; index++) {
            itemModels.add(productItems.get(index));
        }

        return new SimplePageResult<>(itemModels, offset, limit, totalSize);
    }

}
