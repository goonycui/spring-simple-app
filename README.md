

## install ingress controller if need ingress for any testing
```bash
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
helm install ingress-nginx ingress-nginx/ingress-nginx --namespace ingress-nginx --create-namespace
```
> if above command timeout, then download to local first and then install - https://github.com/kubernetes/ingress-nginx/releases/download/helm-chart-4.12.1/ingress-nginx-4.12.1.tgz

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

# kubernetes dashboard for ui view
- install
```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
kubectl get pods -n kubernetes-dashboard # wait for all the pods to be up
```

- access
create dashboard-admin.yaml and apply
then get access token 
```bash
kubectl -n kubernetes-dashboard create token admin-user
# it generates a long token for the admin-user
```
start proxy
```bash
kubectl proxy
```
then access the dashboard via http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
- select Token
- paste the token generated above


