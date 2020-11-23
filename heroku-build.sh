#!/bin/bash

case $APP_NAME in
    notello-gw) 
        echo "Building $APP_NAME"
        yarn --cwd ui run build:prod
        server/mvnw package -Dmaven.skip.tests=true
    ;;
    *) 
        echo "Unknow app name $APP_NAME"
        exit 1
    ;;
esac


