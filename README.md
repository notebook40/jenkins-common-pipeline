# jenkins-common-pipeline

`Jenkins` common pipeline with `shared library`

## Why to use shared library

As Pipeline is adopted for more and more projects in an organization, common patterns are likely to emerge. Oftentimes it is useful to share parts of Pipelines between various projects to reduce redundancies and keep code "DRY". ([Extending with Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/) - Jenkins official website)

## About this common pipeline

This common pipeline helps you to build a maven jar project and build a docker image. Since the common pipeline would build a docker image, I run Jenkins in Docker (it makes me easier to deploy Jenkins). The following are the stages of the common pipeline:

* `init build` - Print pipeline parameters, and checkout codes from scm.
* `compile app` - Compile application with `maven`.
* `unit test` - Call unit test with `maven`.
* `integration test` - Call integration test with `maven`. This function is not tested.
* `package` - Package the application with `maven`.
* `build image` - Build docker image for the application. The default image name is `${imageNamePrefix}${project.artifactId}`. `${project.artifactId}` is the artifact ID in pom.xml. `${imageNamePrefix}` is the `imageNamePrefix` pipeline parameter.

The common pipeline assumes the final name of the `pom` of the application to be built is set to `${project.artifactId}`.

```xml
  <build>
    ...
    <finalName>${project.artifactId}</finalName>
  </build>
```

## Usage

1. The shared library needs to be configured in Jenkins. See [Extending with Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/) for the details.

2. Call the method `commonPipeline` in your `Jenkinsfile`. The following is an example.

```groovy
// Import the shared library
// "notebook40" is the name of the shared library configured in Jenkins.
// "master" is the version of the shared library which is same with the branch
// name of the github repository.
@Library("notebook40@master") _

// Call the script commonPipeline.groovy to define the pipeline to be run.
// The script accepts mapped pipeline parameters.
commonPipeline(
    "imageName": "notebook40/jasypt-encryption"
)
```

The following are the supported pipeline parameters:

Parameter | Description
--- | ---
imageNamePrefix | The prefix of the default image name to be build. If `imageName` pipeline parameter is specified, this parameter will be ignored.
maven.image.name | The docker image name of maven.
maven.image.args | The arguments to run maven image.
skipUnitTest | Skip unit test stage or not. The default value is false.
skipIntegrationTest | Skip integration test stage or not. The default value is true.
skipBuildImage | Skip build image stage or not. The default value is false.
tagImageWithBuildNumber | Tag image with `${version}.${buildNumber}` or not. If `skipBuildImage` is true, this parameter will be ignored.
