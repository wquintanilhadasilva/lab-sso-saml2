# Keycloak

## Obter token JWT

```
curl --request POST \
  --url http://localhost:7081/keycloak/realms/demo/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data grant_type=client_credentials \
  --data client_id=appdemo \
  --data client_secret=eze3Ci4Ln2GopPuZY625oqV9PbBBZUxr \
  --data 'scope=sblabapi/readprivate profile sblabapi/writeprivate email'
```
