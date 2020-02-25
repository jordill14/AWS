import { StackProps, Stack, Construct } from '@aws-cdk/core';
import { Role, ServicePrincipal, ManagedPolicy } from '@aws-cdk/aws-iam';

export class IAMStack extends Stack {

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const role = new Role(this, 'IAM Role', {
      assumedBy: new ServicePrincipal('ec2.amazonaws.com'),
      roleName: 'CustomAmazonSSMManagedInstanceCore'
    });

    role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AmazonSSMManagedInstanceCore'));
  }
}