import { Stack, StackProps, Construct, RemovalPolicy, Duration } from '@aws-cdk/core';
import { Bucket, BucketEncryption, BlockPublicAccess, StorageClass } from '@aws-cdk/aws-s3';
import { Vpc, GatewayVpcEndpointAwsService } from '@aws-cdk/aws-ec2';

export interface S3StackProps extends StackProps {
  vpc: Vpc;
}

export class S3Stack extends Stack {

    constructor(scope: Construct, id: string, props: S3StackProps) {
        super(scope, id, props);

        // props.vpc.addGatewayEndpoint('s3-trigger-gateway', {
        //     service: GatewayVpcEndpointAwsService.S3,
        //     subnets: [{
        //         subnetName: this._isolatedSubnetName1
        //     }]
        // });

        new Bucket(this, 'MyFirstBucket', {
            versioned: true,
            bucketName: 'sample-bucket-cdk-tutorial',
            encryption: BucketEncryption.KMS_MANAGED,
            publicReadAccess: false,
            blockPublicAccess: BlockPublicAccess.BLOCK_ALL,
            removalPolicy: RemovalPolicy.DESTROY,
            lifecycleRules: [{
                expiration: Duration.days(365),
                transitions: [{
                    storageClass: StorageClass.INFREQUENT_ACCESS,
                    transitionAfter: Duration.days(30)
                },{
                    storageClass: StorageClass.GLACIER,
                    transitionAfter: Duration.days(90)
                }]
              }]    
        });
    }
}
