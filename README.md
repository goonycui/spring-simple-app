
## tekton command used to start pipeline

```bash

tkn pipeline start spring-boot-build-and-push \
--param repo-url=https://github.com/goonycui/spring-simple-app \
--param image-reference=scuderiacui/spring-simple-app:1.0 \
--workspace name=source,claimName=source-pvc \
--workspace name=docker-credentials,claimName=docker-credentials-pvc \
--showlog
```

## pvc-cleaner.yaml
this is just to be used to clean up pvc

once the pod is up, can use below command to delete all the pvc in the namespace

```bash
kubectl exec -it pvc-cleaner -- rm -rf /pvc/*
```