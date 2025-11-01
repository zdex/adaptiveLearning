#!/bin/bash
cd api
../gradlew bootRun --args='--spring.profiles.active=dev --server.port=8080'
