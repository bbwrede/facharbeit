#!/bin/sh

javac Common/*.java Server/*.java -d bin
cd bin
java Server debug
