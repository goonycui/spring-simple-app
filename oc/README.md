# Usage
resources in this folder is for local openshift crc testing


# openshift setup
- crc setup
```bash
crc start
eval $(crc oc-env) # to have shell window path ready to work with local openshift env
```
- have tekton installed
```bash
# Install Tekton Pipelines CRDs
oc apply -f https://storage.googleapis.com/tekton-releases/pipeline/latest/release.yaml
# verify installation
oc get crd | grep tekton.dev
```

- image pull from remote registry not working properly due to network issue, so approach to go with local docker registry and let tekton task to pull image from local registry
```bash
docker run -d -p 5000:5000 --name local-registry registry:2
docker tag gcr.io/tekton-releases/github.com/tektoncd/pipeline/cmd/git-init:v0.40.2 localhost:5000/git-init:v0.40.2
docker push localhost:5000/git-init:v0.40.2
```
then change tekton task to use 192.168.1.138:5050/git-init:v0.40.2 (need to use host ip instead of localhost) instead of gcr.io/tekton-releases/github.com/tektoncd/pipeline/cmd/git-init:v0.40.2

# good to test pod
```bash
oc run test-pod --image=busybox --restart=Never
oc exec -it test-pod -- /bin/sh
```

> stopped to try further with openshift crc due to slow and unstable network connection, and also the local docker registry is not working properly with openshift crc