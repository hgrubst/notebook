# Notello application
Simple application allowing to record notes organised by notebooks

# Technical Stack

## Backend
Microservices based on Spring

## Frontend
Angular

## Authentication 
Auth0

# Deployment

## Heroku
App currently runs on Heroku
Heroku is usually suited to single apps per repo so we have to work more to make it work : 
- 2 heroku apps are created for this repo (heroku create x2) : notello-gw and notello-note
- We have to rename the git remote (that is called heroku) as we know have 2 apps. We have renamed them heroku-gw and heroku-note
- A single Procfile is created and expects a variable called PATH_TO_JAR to be set. This is the path to the jar that will be run by java -jar
- The UI is served through the GW and gw pom builds the UI when deployed to heroku (this is activated through maven profiles).
- Another option to work with multiple apps through single repo is to push with git subtree. We could have a dedicated UI served by an node process for example. This would require configuration of CORS and serving through spring is enough for now.

how to resign token for internal microservices : 
- access token got from auth0 on the client directly (auth code grant)
- call auth0 from gateway to resign token with different key
- or just accept initial access token directly in internal microservices


TODO : 
- Backend : 
-- plug in notebook functionnality
-- secure internal services with different token ? 
- Mobile : 
-- create flutter app
- Other : 
-- deploy : heroku ? azure/aws docker ?