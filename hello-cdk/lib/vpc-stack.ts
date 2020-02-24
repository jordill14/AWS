import { App, Stack, StackProps } from '@aws-cdk/core';
import { Peer, Port, SecurityGroup, SubnetType, Vpc, GatewayVpcEndpointAwsService } from '@aws-cdk/aws-ec2'

export class VpcStack extends Stack {

	readonly vpc: Vpc;
	readonly ingressSecurityGroup: SecurityGroup;
	readonly egressSecurityGroup: SecurityGroup;

	constructor(scope: App, id: string, props?: StackProps) {
		super(scope, id, props);

		// vpc
		this.vpc = new Vpc(this, 'CDK VPC', {
			cidr: '192.168.0.0/16',
			maxAzs: 2,
			subnetConfiguration: [{
				cidrMask: 26,
				name: 'public subnet',
				subnetType: SubnetType.PUBLIC,
			}, {
				cidrMask: 26,
				name: 'private subnet',
				subnetType: SubnetType.PRIVATE,
			}, {
				cidrMask: 26,
				name: 'isolated subnet',
				subnetType: SubnetType.ISOLATED,
			}],
			natGateways: 1,
			gatewayEndpoints: {
				S3: {
					service: GatewayVpcEndpointAwsService.S3
				}
			}
		});

		// ingress security group
		this.ingressSecurityGroup = new SecurityGroup(this, 'ingress-security-group', {
			vpc: this.vpc,
			allowAllOutbound: false,
			securityGroupName: 'IngressSecurityGroup',
		});

		this.ingressSecurityGroup.addIngressRule(Peer.ipv4('192.168.0.0/16'), Port.tcp(3306));

		// egress security group
		this.egressSecurityGroup = new SecurityGroup(this, 'egress-security-group', {
			vpc: this.vpc,
			allowAllOutbound: false,
			securityGroupName: 'EgressSecurityGroup',
		});

		this.egressSecurityGroup.addEgressRule(Peer.anyIpv4(), Port.tcp(80));
	}
}