#!/bin/bash
echo "Running Web tests..."
mvn clean test -pl web -DsuiteXmlFile=../suites/web.xml -Denv=dev
