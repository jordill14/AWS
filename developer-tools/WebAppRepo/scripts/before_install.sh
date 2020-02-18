#!/usr/bin/env bash

# I want to make sure that the directory is clean and has nothing left over from
# previous deployments. The servers auto scale so the directory may or may not
# exist.
if [ -d /tmp/codedeploy-deployment-staging-area ]; then
    rm -rf /tmp/codedeploy-deployment-staging-area
fi
mkdir -vp /tmp/codedeploy-deployment-staging-area