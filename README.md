

## install tekton pipeline in k8s cluster
```bash
kubectl apply --filename https://storage.googleapis.com/tekton-releases/pipeline/latest/release.yaml
```



## tekton command used to start pipeline

```bash
tkn pipeline start spring-boot-build-and-push \
--param repo-url=https://github.com/goonycui/spring-simple-app \
--param image-reference=scuderiacui/spring-simple-app:1.0 \
--workspace name=source,claimName=source-pvc --showlog
```

another way of starting the pipeline
```bash
kubectl create -f pipelinerun.yaml
```
it creates and starts the pipeline run with generated name, then use below command to check the log
```bash
tkn pipelinerun logs <pipelinerun-name> -f
```

## pvc-cleaner.yaml
this is just to be used to clean up pvc

once the pod is up, can use below command to delete all the pvc in the namespace, to avoid fetch-source task not able to remove old files

```bash
kubectl exec -it pvc-cleaner -- rm -rf /pvc/*
kubectl exec -it pvc-cleaner -- rm -rf /pvc/target
```

to check if any other files still in pvc

```bash
kubectl exec -it pvc-cleaner -- ls pvc/
```

## docker push
need to have the docker secret created in the cluster. It cannot be "--from-file=.dockerconfigjson=config.json" as the way tekton mount the secret does not automatically change file name to "config.json", which will be read by kaniko and connect dockerhub. 
```bash
kubectl delete secret docker-credentials  
kubectl create secret generic docker-credentials \
  --from-file=config.json=config.json
```


## debug folder
those kubernetes resources in the folder is just to debug how tekton mountPath works with default (Opaque) and kubernetes.io/dockerconfigjson