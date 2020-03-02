import 'source-map-support/register';

import cdk = require('@aws-cdk/core');

import { DbStartupShutdownLambdaStack } from '../lib/db-startup-shutdown-lambda-stack';
import { DbStartupShutdownLambdaPipelineStack } from "../lib/db-startup-shutdown-lambda-pipeline-stack";

const accountId = '[YOUR AWS ACCOUNT ID]';
const region = '[YOUR AWS REGION]';
const instanceId = '[YOUR RDS DB INSTANCE ID]';
const instanceARN = '[YOUR RDS DB INSTANCE ARN';

const app = new cdk.App();
const lambdaStack = new DbStartupShutdownLambdaStack(app, 'LambdaStack', {
  env: {
    account: accountId,
    region: region
  },
  instanceId: instanceId,
  instanceARN: instanceARN
});

new DbStartupShutdownLambdaPipelineStack(app, 'LambdaPipelineStack', {
  env: {
    account: accountId,
    region: region
  },
  startUpLambdaCode: lambdaStack.startUpLambdaCode,
  shutDownLambdaCode: lambdaStack.shutDownLambdaCode,
});

app.synth();
