# YAKS Step Extension

Sample YAKS extension providing custom step implementations to be used in a Cloud-native BDD feature test.

## What is it!?

YAKS allows you to perform Could-Native BDD testing. Cloud-Native here means that your tests execute within a POD in a 
Kubernetes cluster. All you need to do is to write some BDD feature specs using the [Gherkin syntax from Cucumber](https://cucumber.io/docs/gherkin/).

The YAKS framework provides a great set of default steps that can be used in your feature files out of the box. These default 
steps include interactions with

* Apache Camel
* Camel K
* JMS
* Kafka
* Http REST
* OpenAPI
* JDBC

However you might want to build and use your own steps in order to extend the framework capabilities with your very own needs. Therefore
you can use step extensions that get loaded via [JitPack](https://jitpack.io/) for instance.

## Step extensions

This repository represents a very straight forward extension that provides custom steps to the YAKS runtime.

## How to use it!?

You can fork this repository on github or create your very own repository holding your custom step implementations. Once you have that in place you need to add
a [JitPack](https://jitpack.io/) configuration as well as the runtime dependency pointing to your custom step repository into the `yaks-settings.yaml` in your YAKS project.

_yaks-settings.yaml_
```yaml
repositories:
  - repository:
      id: "central"
      name: "Maven Central"
      url: "https://repo.maven.apache.org/maven2/"
      releases:
        enabled: "true"
        updatePolicy: "daily"
      snapshots:
        enabled: "false"
  - repository:
      id: "jitpack.io"
      name: "JitPack Repository"
      url: "https://jitpack.io"
dependencies:
  - dependency:
      groupId: {github.user}
      artifactId: {repo}
      version: {tag}
```

The `groupId` is your github user name, the `artifactId` the name of the repository and the version either a valid `tag` or master branch.

With this configuration you can run your YAKS test and load the custom steps extension using JitPack:

```bash
$ yaks test custom-steps.feature -s yaks-settings.yaml
```
