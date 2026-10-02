#!/bin/bash
echo "Running all smoke tests..."
mvn clean test -DsuiteXmlFile=suites/smoke.xml -Denv=dev
