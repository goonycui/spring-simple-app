#! /bin/bash
PIPELINE_RUN_NAME="spring-boot-build-and-push-run-b7rjp"
kubectl delete pipelinerun $PIPELINE_RUN_NAME
kubectl delete pods -l tekton.dev/pipelineRun=$PIPELINE_RUN_NAME
kubectl delete taskruns -l tekton.dev/pipelineRun=$PIPELINE_RUN_NAME
