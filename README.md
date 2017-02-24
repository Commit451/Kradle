# Kradle

Maven server on Heroku

## About
A Kotlin [Spark](https://github.com/perwendel/spark) server, hosted on Heroku which uses Google Cloud Platform for storage

## Setup
Credential env setup (for Google Cloud Engine)
https://developers.google.com/identity/protocols/application-default-credentials

1. Go to the API Console Credentials page.
2. From the project drop-down, select your project.
3. On the Credentials page, select the Create credentials drop-down, then select Service account key.
4. From the Service account drop-down, select an existing service account or create a new one.
5. For Key type, select the JSON key option, then select Create. The file automatically downloads to your computer.
6. Put the *.json file you just downloaded in a directory of your choosing. This directory must be private (you can't let anyone get access to this), but accessible to your web server code.
7. Set the environment variable GOOGLE_APPLICATION_CREDENTIALS to the path of the JSON file downloaded.

## Local Testing
Run the app locally via
```gradle
./gradlew exec
```

## Docs
- [Google Cloud Engine Storage](https://github.com/GoogleCloudPlatform/java-docs-samples/tree/master/storage)

## Thanks
Thanks to [appengine-maven-repository](https://github.com/renaudcerrato/appengine-maven-repository) which helped a lot as a reference for creating this project


License
--------

    Copyright 2017 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
