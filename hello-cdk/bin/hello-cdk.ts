#!/usr/bin/env node

import 'source-map-support/register';

import { App } from '@aws-cdk/core';
import { VpcStack } from "../lib/vpc-stack";
import { IAMStack } from "../lib/iam-stack";
import { EC2Stack } from '../lib/ec2-stack';
import { S3Stack } from '../lib/s3-stack';
import { RDSStack } from "../lib/rds-stack";
import { BeanstalkStack } from "../lib/beanstalk-stack";

const app = new App();

// VPC
const vpcStack = new VpcStack(app, 'VpcStack')

// IAM
new IAMStack(app, 'IAMStack');

// EC2 Instance
new EC2Stack(app, 'EC2Stack', {
	vpc: vpcStack.vpc
});

// S3 Bucket
new S3Stack(app, 'S3Stack', {
  vpc: vpcStack.vpc
});

// RDS
new RDSStack(app, 'RDSStack', {
  vpc: vpcStack.vpc
});

// Elastic Beanstalk
// new BeanstalkStack(app, 'BeanstalkStack', { vpc: vpcStack.vpc });
new BeanstalkStack(app, 'BeanstalkStack');

app.synth();
