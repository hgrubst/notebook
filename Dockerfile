FROM adoptopenjdk/openjdk11:alpine

COPY server /code
COPY ui /code

WORKDIR /code/server
RUN ./mvnw package -Dmaven.skip.tests=true

WORKDIR /code/ui
RUN yarn --frozen-lockfile
RUN ./bin/ng build:prod

FROM adoptopenjdk/openjdk11:alpine

COPY --from=0 /code/server/gateway/target/*.jar /app/server
COPY --from=0 /code/ui/dist /app/ui