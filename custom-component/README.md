# How to create and use a Camel Component?

[Apache Camel](http://camel.apache.org) is a versatile open-source integration framework based on known [Enterprise Integration Patterns](http://camel.apache.org/enterprise-integration-patterns.html). It already ships with many [components](http://camel.apache.org/components.html) but you are able to write your own, too.

## Creating a Camel Component

Thanks to an archetype it is very easy to get this started. Simply run the following command, answer the remaining questions of the wizard (e.g.: name of the component, schema, etc.) and open the project.

```
mvn archetype:generate -DarchetypeGroupId=org.apache.camel.archetypes -DarchetypeArtifactId=camel-archetype-component -DarchetypeVersion=2.18.3
```

You should get 4 src files ([Component](component/src/main/java/de/stphngrtz/camel/ExampleComponent.java), [Endpoint](component/src/main/java/de/stphngrtz/camel/ExampleEndpoint.java), [Consumer](component/src/main/java/de/stphngrtz/camel/ExampleConsumer.java) and [Producer](component/src/main/java/de/stphngrtz/camel/ExampleProducer.java)), a resource file and a test file. The component is already good to go, as you can see if you run the test.

To use it within a route of another project don't forget to `mvn clean install` the component.

## Using a Camel Component

To setup camel routes with the Java DSL there is an archetype, too.

```
mvn archetype:generate -DarchetypeGroupId=org.apache.camel.archetypes -DarchetypeArtifactId=camel-archetype-java -DarchetypeVersion=2.18.3
```

Add the component to the [pom.xml](usage/pom.xml), modify the [routes](usage/src/main/java/de/stphngrtz/camel/MyRouteBuilder.java) and run the application.

You should see something like this:
```
[... thread #0 - example://foo] route1                         INFO  Hello World! The time is Mon Apr 10 14:47:00 CEST 2017
Hello World! The time is Mon Apr 10 14:47:00 CEST 2017
[... thread #0 - example://foo] route1                         INFO  Hello World! The time is Mon Apr 10 14:47:00 CEST 2017
Hello World! The time is Mon Apr 10 14:47:00 CEST 2017
[... thread #0 - example://foo] route1                         INFO  Hello World! The time is Mon Apr 10 14:47:01 CEST 2017
Hello World! The time is Mon Apr 10 14:47:01 CEST 2017
[... thread #0 - example://foo] route1                         INFO  Hello World! The time is Mon Apr 10 14:47:01 CEST 2017
```

The first *Hello World!* is from my `.log()` statement, the second one is a `System.out.println()` from the components [Producer](component/src/main/java/de/stphngrtz/camel/ExampleProducer.java).
