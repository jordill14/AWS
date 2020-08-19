import { Stack, StackProps, Construct } from '@aws-cdk/core';
import { Table, AttributeType } from '@aws-cdk/aws-dynamodb';
import { UserService } from '../services/user-service';

export class DynamodbStack extends Stack {

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    // Dynamodb table
    const table = new Table(this, 'people', {
      partitionKey: { name: 'name', type: AttributeType.STRING},
      tableName: 'usersTable',
    });

    new UserService(this, 'Users', table);
  }
}
