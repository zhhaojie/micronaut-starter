/*
 * Copyright 2021 original authors
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
package io.micronaut.starter.build.dependencies;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public final class Dependency {

    private final Scope scope;
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String versionProperty;
    private final boolean requiresLookup;
    private final int order;
    private final boolean annotationProcessorPriority;

    private Dependency(Scope scope, String groupId, String artifactId, String version, String versionProperty, boolean requiresLookup, boolean annotationProcessorPriority, int order) {
        this.scope = scope;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.versionProperty = versionProperty;
        this.requiresLookup = requiresLookup;
        this.annotationProcessorPriority = annotationProcessorPriority;
        this.order = order;
    }

    public Scope getScope() {
        return scope;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    @Nullable
    public String getVersion() {
        return version;
    }

    @Nullable
    public String getVersionProperty() {
        return versionProperty;
    }

    public int getOrder() {
        return order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean requiresLookup() {
        return requiresLookup;
    }

    public Dependency resolved(Coordinate coordinate) {
        return new Dependency(scope, coordinate.getGroupId(), artifactId, coordinate.getVersion(), null, false, annotationProcessorPriority, order);
    }

    public boolean isAnnotationProcessorPriority() {
        return annotationProcessorPriority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dependency that = (Dependency) o;

        return Objects.equals(getGroupId(), that.getGroupId()) &&
                Objects.equals(getArtifactId(), that.getArtifactId()) &&
                Objects.equals(getScope(), that.getScope());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getArtifactId(), getScope());
    }

    public static class Builder {

        private Scope scope;
        private String groupId;
        private String artifactId;
        private String version;
        private String versionProperty;
        private boolean requiresLookup;
        private int order = 0;
        private boolean template = false;
        private boolean annotationProcessorPriority = false;

        public Builder scope(@NonNull Scope scope) {
            if (template) {
                return copy().scope(scope);
            } else {
                this.scope = scope;
                return this;
            }
        }

        public Builder compile() {
            return scope(new Scope(Source.MAIN, Arrays.asList(Phase.COMPILATION, Phase.RUNTIME)));
        }

        public Builder compileOnly() {
            return scope(new Scope(Source.MAIN, Collections.singletonList(Phase.COMPILATION)));
        }

        public Builder runtime() {
            return scope(new Scope(Source.MAIN, Collections.singletonList(Phase.RUNTIME)));
        }

        public Builder test() {
            return scope(new Scope(Source.TEST, Arrays.asList(Phase.COMPILATION, Phase.RUNTIME)));
        }

        public Builder testCompileOnly() {
            return scope(new Scope(Source.TEST, Collections.singletonList(Phase.COMPILATION)));
        }

        public Builder testRuntime() {
            return scope(new Scope(Source.TEST, Collections.singletonList(Phase.RUNTIME)));
        }

        public Builder annotationProcessor() {
            return scope(new Scope(Source.MAIN, Collections.singletonList(Phase.ANNOTATION_PROCESSING)));
        }

        public Builder annotationProcessor(boolean requiresPriority) {
            this.annotationProcessorPriority = requiresPriority;
            return annotationProcessor();
        }

        public Builder testAnnotationProcessor() {
            return scope(new Scope(Source.TEST, Collections.singletonList(Phase.ANNOTATION_PROCESSING)));
        }

        public Builder testAnnotationProcessor(boolean requiresPriority) {
            this.annotationProcessorPriority = requiresPriority;
            return testAnnotationProcessor();
        }

        public Builder groupId(@Nullable String groupId) {
            if (template) {
                return copy().groupId(groupId);
            } else {
                this.groupId = groupId;
                return this;
            }
        }

        public Builder artifactId(@NonNull String artifactId) {
            if (template) {
                return copy().artifactId(artifactId);
            } else {
                this.artifactId = artifactId;
                return this;
            }
        }

        public Builder lookupArtifactId(@NonNull String artifactId) {
            if (template) {
                return copy().lookupArtifactId(artifactId);
            } else {
                this.artifactId = artifactId;
                this.requiresLookup = true;
                return this;
            }
        }

        public Builder version(@Nullable String version) {
            if (template) {
                return copy().version(version);
            } else {
                this.version = version;
                return this;
            }
        }

        public Builder versionProperty(@Nullable String versionProperty) {
            if (template) {
                return copy().versionProperty(versionProperty);
            } else {
                this.versionProperty = versionProperty;
                return this;
            }
        }

        public Builder order(int order) {
            if (template) {
                return copy().order(order);
            } else {
                this.order = order;
                return this;
            }
        }

        public Builder template() {
            this.template = true;
            return this;
        }

        public Dependency build() {
            Objects.requireNonNull(scope, "The dependency scope must be set");
            Objects.requireNonNull(artifactId, "The artifact id must be set");

            return buildInternal();
        }

        public DependencyCoordinate buildCoordinate() {
            return buildCoordinate(false);
        }

        public DependencyCoordinate buildCoordinate(boolean showVersionProperty) {
            Objects.requireNonNull(artifactId, "The artifact id must be set");

            return new DependencyCoordinate(buildInternal(), showVersionProperty);
        }

        private Dependency buildInternal() {
            return new Dependency(scope, groupId, artifactId, version, versionProperty, requiresLookup, annotationProcessorPriority, order);
        }

        private Builder copy() {
            Builder builder = new Builder().scope(scope);
            if (requiresLookup) {
                builder.lookupArtifactId(artifactId);
            } else {
                builder.groupId(groupId).artifactId(artifactId);
                if (versionProperty != null) {
                    builder.versionProperty(versionProperty);
                } else {
                    builder.version(version);
                }
            }
            return builder.order(order);
        }
    }
}
