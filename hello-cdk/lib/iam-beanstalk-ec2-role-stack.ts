import { StackProps, Stack, Construct } from '@aws-cdk/core';
import { Role, ServicePrincipal, ManagedPolicy } from '@aws-cdk/aws-iam';

export class IAMBeanstalkEC2RoleStack extends Stack {

  readonly role: Role;

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const roleName = 'custom-aws-beanstalk-ec2-role';
    this.role = new Role(this, 'IAM Role for Elastic Beanstalk application', {
      assumedBy: new ServicePrincipal('ec2.amazonaws.com'),
      roleName: roleName,
    });

    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AWSElasticBeanstalkMulticontainerDocker'));
    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AWSElasticBeanstalkWebTier'));
    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AWSElasticBeanstalkWorkerTier'));
    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AmazonSSMManagedInstanceCore'));
  }
}