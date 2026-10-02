#!/bin/bash
echo "Running Android tests..."
mvn clean test -pl android -DsuiteXmlFile=suites/android.xml -Denv=dev
