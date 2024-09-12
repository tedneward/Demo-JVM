#!/bin/bash

echo Cleaning up any previous runs...
rm JNIExample.class
rm JNIExample.h
rm libjniexample.so
cargo clean
