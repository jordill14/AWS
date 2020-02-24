#!/usr/bin/env node

import 'source-map-support/register';

import * as core from '@aws-cdk/core';

import { VpcStack } from "../lib/vpc-stack";
import { S3Stack } from '../lib/s3-stack';
import { RDSStack } from "../lib/rds-stack";

const app = new core.App();

const vpcStack = new VpcStack(app, 'VpcStack')

new S3Stack(app, 'S3Stack', {
    vpc: vpcStack.vpc
});


new RDSStack(app, 'RDSStack', {
    vpc: vpcStack.vpc
});

app.synth();
