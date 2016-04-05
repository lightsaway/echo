#Echo service
* It respondes with what u send
* It counts the requests
* It injects received time in response

##Purpose
* To check what requests are send
* To check loadbalancing

##Building docker
```javascript
gradle clean bootRepackage
docker build -t lightsaway/echo:latest .
```

##Using in compose
Below is example how to use it in compose file

```yaml
echo:
  image: lightsaway/echo:latest
  ports:
   - "8889:8080"
echo2:
  image: lightsaway/echo:latest
  ports:
   - "8887:8080"
echo3:
  image: lightsaway/echo:latest
  ports:
   - "8886:8080"
loadbalancer:
   image: some.balancer.app/fill-name-here
   hostname: balancer
   ports:
     - "8070:8080"
   links:
     - echo
     - echo2
     - echo3
```