#!/usr/bin/env bash

./gradlew clean buildLambdaZip
pulumi up -f
