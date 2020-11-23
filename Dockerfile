#Stage 1 build
FROM adoptopenjdk/openjdk11:alpine as build

RUN apk add --no-cache yarn

# Cache server side deps

## Copy the maven wrapper
COPY server/.mvn /code/server/.mvn/
COPY server/mvnw server/pom.xml /code/server/

## Start installing common to cache lots of the deps.
## This will download heaps of deps already which will then be cached as long as common does not change.
## This is better than building straight from the parent pom as the cache will be blown if any of the sub poms change in that case
COPY server/common/pom.xml server/common/mvnw /code/server/common/
WORKDIR /code/server/common
RUN ./mvnw dependency:go-offline

#Cache UI deps
COPY ui/package.json ui/yarn.lock /code/ui/
WORKDIR /code/ui
RUN yarn --frozen-lockfile

#Cache the other poms. If any of them change, some deps will be downloaded again here but we should still have a lot from the install of common 
#above
COPY server/common/pom.xml /code/server/common/
COPY server/note/pom.xml /code/server/note/
COPY server/note-api-client/pom.xml /code/server/note-api-client/
COPY server/gateway/pom.xml /code/server/gateway/
WORKDIR /code/server
#this is still quite long despite installing a good amount of deps higher. 
RUN ./mvnw dependency:go-offline

# Build Server and UI
COPY . /code

## Build server
WORKDIR /code/server
#strangely --offline does not work here despite doing go-offline before...
RUN ./mvnw install -Dmaven.test.skip=true

## Build ui
WORKDIR /code/ui
RUN node_modules/.bin/ng build --prod --no-progress

#Stage 2 use artifacts from build
FROM adoptopenjdk/openjdk11:alpine

#can we do copy target/**/*.jar by any chance ? Otherwise we need to coyp one by one
COPY --from=0 /code/server/gateway/target/*.jar /app/server/
COPY --from=0 /code/ui/dist /app/