#! /bin/bash
# Check if pipeline run name is provided
if [ -z "$1" ]; then
  echo "Please provide the pipeline run name as an argument."
  exit 1
fi

PIPELINE_RUN_NAME=$1


#PIPELINE_RUN_NAME="spring-boot-build-and-push-run-b7rjp"
kubectl delete pipelinerun $PIPELINE_RUN_NAME
kubectl delete pods -l tekton.dev/pipelineRun=$PIPELINE_RUN_NAME
kubectl delete taskruns -l tekton.dev/pipelineRun=$PIPELINE_RUN_NAME
