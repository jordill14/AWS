#!/usr/bin/env node

import 'source-map-support/register';

import { App } from '@aws-cdk/core';
import { VpcStack } from "../lib/vpc-stack";
import { IAMSSMRoleStack } from "../lib/iam-ssm-role-stack";
import { IAMBeanstalkEC2RoleStack } from '../lib/iam-beanstalk-ec2-role-stack';
import { EC2Stack } from '../lib/ec2-stack';
import { S3Stack } from '../lib/s3-stack';
import { RDSStack } from "../lib/rds-stack";
import { BeanstalkStack } from "../lib/beanstalk-stack";
import { LambdaStack } from "../lib/lambda-stack";

const app = new App();

// VPC
const vpcStack = new VpcStack(app, 'VpcStack')

// IAM SSM Role
const iamSSMRoleStack = new IAMSSMRoleStack(app, 'IAMSSMRoleStack');

// IAM Beanstalk EC2 Role
const iamBeanstalkEC2RoleStack = new IAMBeanstalkEC2RoleStack(app, 'IAMBeanstalkEC2RoleStack');

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
new BeanstalkStack(app, 'BeanstalkStack', {
  instanceProfile: iamBeanstalkEC2RoleStack.instanceProfile
});

// Lambda
new LambdaStack(app, 'LambdaStack');

app.synth();
