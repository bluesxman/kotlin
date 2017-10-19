/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.resolve.jvm

import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations
import org.jetbrains.kotlin.load.java.JvmAnnotationNames
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.DeclarationReturnTypeSanitizer
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.WrappedTypeFactory
import org.jetbrains.kotlin.types.typeUtil.replaceAnnotations

object JvmDeclarationReturnTypeSanitizer : DeclarationReturnTypeSanitizer {
    override fun sanitizeReturnType(inferred: KotlinType, wrappedTypeFactory: WrappedTypeFactory, trace: BindingTrace): KotlinType =
            // NB this deferred type wrapper is required to avoid recursion in declaration type resolution
            wrappedTypeFactory.createRecursionIntolerantDeferredType(trace) {
                // NB can't check for presence of EnhancedNullability here,
                // because it will also cause recursion in declaration type resolution.
                inferred.replaceAnnotations(FilteredAnnotations(inferred.annotations) {
                    it != JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION
                })
            }
}