package io.micronaut.starter.build.dependencies

import io.micronaut.starter.application.generator.GeneratorContext
import io.micronaut.starter.build.gradle.GradleConfiguration
import io.micronaut.starter.build.gradle.GradleDependency
import io.micronaut.starter.options.Language
import io.micronaut.starter.options.TestFramework
import spock.lang.Specification

class GradleDependencyComparatorSpec extends Specification {

    void "sort based on gradle configuration"() {
        given:
        def ctx = Stub(GeneratorContext) {
            getLanguage() >> Language.JAVA
            getTestFramework() >> TestFramework.JUNIT
        }
        List<GradleDependency> dependencies = [
                dep(Dependency.builder().groupId("io.micronaut").artifactId("micronaut-validation").compile(), ctx),
                dep(Dependency.builder().groupId("io.swagger.core.v3").artifactId("swagger-annotations").compile(), ctx),
                dep(Dependency.builder().groupId("io.micronaut").artifactId("micronaut-runtime").compile(), ctx),
                dep(Dependency.builder().groupId("javax.annotation").artifactId("javax.annotation-api").compile(), ctx),
                dep(Dependency.builder().groupId("io.micronaut").artifactId("micronaut-http-client").compile(), ctx),
                dep(Dependency.builder().groupId("io.micronaut.openapi").artifactId("micronaut-openapi").annotationProcessor(), ctx),
                dep(Dependency.builder().groupId("io.micronaut.sql").artifactId("micronaut-jdbc-hikari").compile(), ctx),
                dep(Dependency.builder().groupId("org.testcontainers").artifactId("testcontainers").test(), ctx),
                dep(Dependency.builder().groupId("mysql").artifactId("mysql-connector-java").runtime(), ctx),
                dep(Dependency.builder().groupId("org.testcontainers").artifactId("junit-jupiter").test(), ctx),
                dep(Dependency.builder().groupId("org.testcontainers").artifactId("mysql").test(), ctx),
                dep(Dependency.builder().groupId("ch.qos.logback").artifactId("logback-classic").runtime(), ctx)
        ]

        when:
        dependencies.sort(GradleDependency.COMPARATOR)

        then:
        "${str(dependencies[0])}" == 'annotationProcessor("io.micronaut.openapi:micronaut-openapi")'
        "${str(dependencies[1])}" == 'implementation("io.micronaut:micronaut-http-client")'
        "${str(dependencies[2])}" == 'implementation("io.micronaut:micronaut-runtime")'
        "${str(dependencies[3])}" == 'implementation("io.micronaut:micronaut-validation")'
        "${str(dependencies[4])}" == 'implementation("io.micronaut.sql:micronaut-jdbc-hikari")'
        "${str(dependencies[5])}" == 'implementation("io.swagger.core.v3:swagger-annotations")'
        "${str(dependencies[6])}" == 'implementation("javax.annotation:javax.annotation-api")'
        "${str(dependencies[7])}" == 'runtimeOnly("ch.qos.logback:logback-classic")'
        "${str(dependencies[8])}" == 'runtimeOnly("mysql:mysql-connector-java")'
        "${str(dependencies[9])}" == 'testImplementation("org.testcontainers:junit-jupiter")'
        "${str(dependencies[10])}" == 'testImplementation("org.testcontainers:mysql")'
        "${str(dependencies[11])}" == 'testImplementation("org.testcontainers:testcontainers")'
    }

    private static String str(GradleDependency dependency) {
        "${dependency.getConfiguration().toString()}(\"${dependency.groupId}:${dependency.artifactId}\")"
    }

    private static GradleDependency dep(Dependency.Builder dependency, GeneratorContext ctx) {
        new GradleDependency(dependency.build(), ctx)
    }
}
