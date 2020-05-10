import { StackProps, Stack, Construct } from '@aws-cdk/core';
import { Role, ServicePrincipal, ManagedPolicy } from '@aws-cdk/aws-iam';

export class IAMSSMRoleStack extends Stack {

  readonly role: Role;

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const roleName = 'CustomAmazonSSMManagedInstanceCore';
    this.role = new Role(this, 'IAM Role for Session Manager', {
      assumedBy: new ServicePrincipal('ec2.amazonaws.com'),
      roleName: roleName
    });

    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AmazonSSMManagedInstanceCore'));
  }
}