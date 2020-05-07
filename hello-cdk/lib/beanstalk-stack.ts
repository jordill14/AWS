import { StackProps, Stack, Construct } from '@aws-cdk/core';
import { Instance, InstanceClass, InstanceSize, InstanceType, Peer, Port, SecurityGroup, Vpc, AmazonLinuxImage, AmazonLinuxGeneration } from "@aws-cdk/aws-ec2";
import { CfnApplication, CfnEnvironment, CfnApplicationVersion } from '@aws-cdk/aws-elasticbeanstalk';
import { Asset } from '@aws-cdk/aws-s3-assets';

interface BeanstalkStackProps extends StackProps {
  vpc: Vpc;
}

export class BeanstalkStack extends Stack {

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    // Construct an S3 asset from the ZIP located from directory up
    // Download Beanstalk examples from https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/tutorials.html
    const elbZipArchive = new Asset(this, 'MyElbAppZip', {
      path: `${__dirname}/../app-nodejs.zip`,
    });

    const appName = 'MyApp';
    const app = new CfnApplication(this, 'Application', {
      applicationName: appName,
    });

    // Example of some options which can be configured
    const optionSettingProperties: CfnEnvironment.OptionSettingProperty[] = [
      {
        namespace: 'aws:autoscaling:launchconfiguration',
        optionName: 'InstanceType',
        value: 't3.small',
      },
      {
        namespace: 'aws:autoscaling:launchconfiguration',
        optionName: 'IamInstanceProfile',
        // Here you could reference an instance profile by ARN (e.g. myIamInstanceProfile.attrArn)
        // For the default setup, leave this as is (it is assumed this role exists)
        // Environment failed to launch as it entered Terminated state https://stackoverflow.com/a/55033663/6894670

        // An work around approach is in Elastic Beanstalk console, create Java Sample Application at first, then
        // "aws-elasticbeanstalk-ec2-role" ec2 role, "aws-elasticbeanstalk-service-role" elasticbeanstalk role,
        // and "AWSServiceRoleForAutoScaling" autoscaling Service-Linked role. Next create Node.js Sample Application,
        // then AWSElasticBeanstalkMulticontainerDocker, AWSElasticBeanstalkWebTier and AWSElasticBeanstalkWorkerTier
        // policies added in "aws-elasticbeanstalk-ec2-role"; AWSElasticBeanstalkEnhancedHealth and AWSElasticBeanstalkService
        // policies added in "aws-elasticbeanstalk-service-role".

        // After this Elastic Beanstalk created, AWSServiceRoleForElasticBeanstalk and AWSServiceRoleForElasticLoadBalancing
        // Service-Linked roles are also created. Destroy Elastic Beanstalk app won't delete ALL these roles
        value: 'aws-elasticbeanstalk-ec2-role',
      },
      {
        namespace: 'aws:elasticbeanstalk:container:nodejs',
        optionName: 'NodeVersion',
        value: '10.16.3',
      },
    ];

    // Create an app version from the S3 asset defined above
    // The S3 "putObject" will occur first before CF generates the template
    const appVersionProps = new CfnApplicationVersion(this, 'AppVersion', {
      applicationName: appName,
      sourceBundle: {
        s3Bucket: elbZipArchive.s3BucketName,
        s3Key: elbZipArchive.s3ObjectKey,
      },
    });

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const elbEnv = new CfnEnvironment(this, 'Environment', {
      environmentName: 'MySampleEnvironment',
      applicationName: app.applicationName || appName,
      // Elastic Beanstalk supported platforms in Node.js
      // https://docs.aws.amazon.com/elasticbeanstalk/latest/platforms/platforms-supported.html#platforms-supported.nodejs
      solutionStackName: '64bit Amazon Linux 2018.03 v4.14.3 running Node.js',
      optionSettings: optionSettingProperties,
      // This line is critical - reference the label created in this same stack
      versionLabel: appVersionProps.ref,
    });

    // Also very important - make sure that `app` exists before creating an app version
    appVersionProps.addDependsOn(app);
  }
}
