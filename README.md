# http4k Examples

![.github/workflows/build.yaml](https://github.com/http4k/http4k-connect-examples/workflows/.github/workflows/build.yaml/badge.svg)

These sample projects are designed to demonstrate how easy it is to use the various features of [http4k-connect](https://github.com/http4k/http4k-connect) in isolation. They are all entirely self-contained and can be run locally or using the included script to run them in a Docker container.

- **connect-pattern** : Code accompanying [the post]() describing the basic Connect pattern and how it is structured. It only relies on [http4k](https://http4k.org) as a basic implementation platform.
- **using-connect-adapters** : How to use the library adapters (in this case AWS KMS) and Fake which ship with http4k-connect.
- **custom-adapter-and-fake** : Implementing your own custom http4k-connect compliant adapter and a Fake HTTP system to connect to, along with examples of testing techniques. Also demonstrates the usage of the Kapt compile-time code-generators in a custom adapter.
