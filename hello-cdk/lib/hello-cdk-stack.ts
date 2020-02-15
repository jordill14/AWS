import core = require('@aws-cdk/core');
import s3 = require('@aws-cdk/aws-s3');

export class HelloCdkStack extends core.Stack {
  constructor(scope: core.Construct, id: string, props?: core.StackProps) {
    super(scope, id, props);

    // The code that defines your stack goes here
    new s3.Bucket(this, 'MyFirstBucket', {
      versioned: true,
      encryption: s3.BucketEncryption.KMS_MANAGED
    });
  }
}
