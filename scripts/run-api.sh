#!/bin/bash
echo "Running API tests..."
mvn clean test -pl api -DsuiteXmlFile=../suites/api.xml -Denv=dev
