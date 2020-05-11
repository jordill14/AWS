import { StackProps, Stack, Construct } from '@aws-cdk/core';
import { Role, ServicePrincipal, ManagedPolicy, CfnInstanceProfile } from '@aws-cdk/aws-iam';

export class IAMBeanstalkEC2RoleStack extends Stack {

  readonly role: Role;
  readonly instanceProfile: CfnInstanceProfile;

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    // EC2 role Beanstalk application assume as
    const roleName = 'custom-aws-elasticbeanstalk-ec2-role';
    this.role = new Role(this, 'IAM Role for Elastic Beanstalk application', {
      assumedBy: new ServicePrincipal('ec2.amazonaws.com'),
      roleName: roleName,
    });

    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AWSElasticBeanstalkMulticontainerDocker'));
    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AWSElasticBeanstalkWebTier'));
    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AWSElasticBeanstalkWorkerTier'));
    this.role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AmazonSSMManagedInstanceCore'));

    // Instance Profile which will allow the application to use the EC2 role
    this.instanceProfile = new CfnInstanceProfile(this, 'Elastic Beanstalk Instance Profile', {
      roles: [this.role.roleName],
    });
  }
}