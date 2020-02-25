import { StackProps, Stack, Construct } from '@aws-cdk/core';
import { Instance, InstanceClass, InstanceSize, InstanceType, Peer, Port, SecurityGroup, Vpc, AmazonLinuxImage, AmazonLinuxGeneration } from "@aws-cdk/aws-ec2";
import { Role, ServicePrincipal, ManagedPolicy } from '@aws-cdk/aws-iam';

interface EC2StackProps extends StackProps {
  vpc: Vpc;
}

export class EC2Stack extends Stack {

  constructor(scope: Construct, id: string, props: EC2StackProps) {
    super(scope, id, props);

    // Open port 22 for SSH connection from anywhere
    const securityGroup = new SecurityGroup(this, 'Security Group', {
      vpc: props.vpc,
      securityGroupName: "ssh-limited-access-sg",
      description: 'Allow ssh access to ec2 instances from restricted places',
      allowAllOutbound: true
    });

    securityGroup.addIngressRule(Peer.ipv4("155.144.114.41/32"), Port.tcp(22), 'allow limited ssh access')

    // Latest Amazon Linux AMI
    const ami = new AmazonLinuxImage({ generation: AmazonLinuxGeneration.AMAZON_LINUX_2 });

    // IAM Role
    const role = new Role(this, 'IAM Role', {
      assumedBy: new ServicePrincipal('ec2.amazonaws.com'),
      roleName: 'CustomAmazonSSMManagedInstanceCore'
    });

    role.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AmazonSSMManagedInstanceCore'));

    // Instance details
    const ec2Instance = new Instance(this, 'EC2 Instance', {
      vpc: props.vpc,
      instanceType: InstanceType.of(InstanceClass.T3, InstanceSize.NANO),
      machineImage: ami,
      securityGroup: securityGroup,
      role: role
    });
  }
}