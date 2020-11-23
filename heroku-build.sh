#!/bin/bash

env

case $APP_NAME in
    notello-gw) 
        echo "Building $APP_NAME"
    ;;
    *) 
        echo "Unknow app name $APP_NAME"
        exit 1
    ;;
esac


