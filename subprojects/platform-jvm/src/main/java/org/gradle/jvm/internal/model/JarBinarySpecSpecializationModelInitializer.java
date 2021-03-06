/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.jvm.internal.model;

import org.gradle.jvm.JarBinarySpec;
import org.gradle.jvm.internal.JarBinarySpecInternal;
import org.gradle.model.internal.core.ModelProjection;
import org.gradle.model.internal.core.ModelReference;
import org.gradle.model.internal.core.ModelView;
import org.gradle.model.internal.core.MutableModelNode;
import org.gradle.model.internal.inspect.ManagedModelInitializer;
import org.gradle.model.internal.manage.instance.ManagedProxyFactory;
import org.gradle.model.internal.manage.projection.ManagedModelProjection;
import org.gradle.model.internal.manage.schema.ModelSchemaStore;
import org.gradle.model.internal.manage.schema.ModelStructSchema;
import org.gradle.platform.base.internal.BinarySpecFactory;

import java.util.Collections;
import java.util.List;

public class JarBinarySpecSpecializationModelInitializer<T> extends ManagedModelInitializer<T> {

    private static final ManagedProxyFactory PROXY_FACTORY = new ManagedProxyFactory();

    public JarBinarySpecSpecializationModelInitializer(ModelStructSchema<T> modelSchema, ModelSchemaStore schemaStore) {
        super(modelSchema, schemaStore);
    }

    @Override
    public List<? extends ModelReference<?>> getInputs() {
        return Collections.singletonList(ModelReference.of(BinarySpecFactory.class));
    }

    @Override
    public void execute(MutableModelNode modelNode, List<ModelView<?>> inputs) {
        super.execute(modelNode, inputs);
        BinarySpecFactory binarySpecFactory = (BinarySpecFactory) inputs.get(0).getInstance();
        JarBinarySpecInternal jarBinarySpec = (JarBinarySpecInternal) binarySpecFactory.create(JarBinarySpec.class, modelNode, modelNode.getPath().getName());
        modelNode.setPrivateData(JarBinarySpecInternal.class, jarBinarySpec);
    }

    @Override
    public List<? extends ModelProjection> getProjections() {
        return Collections.singletonList(new ManagedModelProjection<T>(modelSchema, schemaStore, PROXY_FACTORY));
    }
}
