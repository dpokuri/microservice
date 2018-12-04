#!/bin/sh

repo="asia.gcr.io/prismatic-fact-201508/tix-images-be"
#commit_id=$(cat /var/lib/jenkins/workspace/tix-images-be/commit-id)
cd deploy

if [ "$1" == "dev" ]; then
    commit_id=build-id-${BUILD_NUMBER}
    echo "commit id is: $commit_id"
    cd dev
    /usr/local/bin/kustomize edit set imagetag $repo:$commit_id
    /usr/local/bin/kustomize build . >> deployment.yaml
    cat deployment.yaml
    /usr/bin/kubectl apply --namespace development --validate=false -f deployment.yaml
    #/usr/bin/kubectl set image deployment/tix-images-be tix-images-be=asia.gcr.io/prismatic-fact-201508/tix-images-be:$commit_id --namespace development

else
    commit_id=${IMAGE_TAG}
    echo "commit id is: $commit_id"
    cd prod
    /usr/local/bin/kustomize edit set imagetag $repo:$commit_id
    /usr/local/bin/kustomize build . >> deployment.yaml
    cat deployment.yaml
    /usr/bin/kubectl apply --validate=false -f deployment.yaml
fi 
