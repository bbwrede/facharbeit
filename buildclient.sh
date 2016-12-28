#!/bin/bash

javac Client/*.java Common/*.java -d bin
cd bin
java Controller 
