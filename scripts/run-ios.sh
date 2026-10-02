#!/bin/bash
echo "Running iOS tests..."
mvn clean test -pl ios -DsuiteXmlFile=../suites/ios.xml -Denv=dev
