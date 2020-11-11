# Notello application
Simple application allowing to record notes organised by notebooks

# Technical Stack

## Backend
Microservices based on Spring

## Frontend
Angular

## Authentication 
Auth0



how to resign token for internal microservices : 
- access token got from auth0 on the client directly (auth code grant)
- call auth0 from gateway to resign token with different key
- or just accept initial access token directly in internal microservices


TODO : 
- Backend : 
-- plug in notebook functionnality
-- secure internal services with different token ? 
- UI :
-- user angular material
- Mobile : 
-- create flutter app
- Other : 
-- deploy : heroku ? azure/aws docker ?