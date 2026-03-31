#!/bin/bash

set -e

BUILD_DIR="out"

rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

javac -d "$BUILD_DIR" src/main/*.java
java -cp "$BUILD_DIR" main.MainMenu