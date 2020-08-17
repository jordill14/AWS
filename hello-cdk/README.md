# Welcome to your CDK TypeScript project!

This is a blank project for TypeScript development with CDK.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

## Useful commands

 * `npm run build`                                            compile typescript to js
 * `npm run watch`                                            watch for changes and compile
 * `npm run test`                                             perform the jest unit tests

 * `cdk init --language`                                      initialize your new AWS CDK app

 * `npx npm-check-updates -u`                                 Updating your language dependencies (require nodejs 12.x)

 * `cdk deploy [stack] --require-approval never --profile my` deploy this stack e.g. **LambdaStack** to your default AWS account/region
 * `cdk diff`                                                 compare deployed stack with current state
 * `cdk synth`                                                emits the synthesized CloudFormation template
 * `cdk bootstrap`                                            Run when error raised: This stack uses assets, so the toolkit stack must be deployed to the environment with command **cdk bootstrap aws://aws-account/aws-region**
 * `cdk destroy stack`                                        Delete stack(s)

## Test

```
𝜆 curl -X GET https://4puq7ndw08.execute-api.ap-southeast-2.amazonaws.com/prod/
{"widgets":[]}
```

References
----------

- Getting Started With the AWS CDK, _https://docs.aws.amazon.com/cdk/latest/guide/getting_started.html_
- Creating a serverless application using the AWS CDK, _https://docs.aws.amazon.com/cdk/latest/guide/serverless_example.html_
- Creating a code pipeline using the AWS CDK, _https://docs.aws.amazon.com/cdk/latest/guide/codepipeline_example.html_