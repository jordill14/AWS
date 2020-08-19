import { Construct } from '@aws-cdk/core';
import { RestApi, LambdaIntegration } from "@aws-cdk/aws-apigateway";
import { Function, Runtime, Code } from "@aws-cdk/aws-lambda";
import { Table } from '@aws-cdk/aws-dynamodb';

export class UserService extends Construct {

  constructor(scope: Construct, id: string, table: Table) {
    super(scope, id);

    // api gateway
    const api = new RestApi(this, "users-api", {
      restApiName: "User Service",
      description: "This service serves users."
    });

    // Hello User lambda
    const helloUserHandler = new Function(this, "HelloUserHandler", {
      runtime: Runtime.NODEJS_10_X,
      code: Code.fromAsset("resources/user"), // from "resources/" directory
      handler: "helloUser.handler"            // helloUser.js file
    });

    // Hello User lambda integration
    const helloUserIntegration = new LambdaIntegration(helloUserHandler);

    const apiHelloUser = api.root.addResource('helloUser'); // API Gateway path
    apiHelloUser.addMethod("GET", helloUserIntegration);

    // Create User lambda
    const createUserHandler = new Function(this, "CreateUserHandler", {
      runtime: Runtime.NODEJS_10_X,
      code: Code.fromAsset("resources/user"), // from "resources/" directory
      handler: "createUser.handler",          // createUser.js file
      environment: {
        'TABLE_NAME': 'usersTable'
      }
    });

    // Create User lambda integration
    const createUserIntegration = new LambdaIntegration(createUserHandler);

    const apiCreateUser = api.root.addResource('createUser');
    apiCreateUser.addMethod("POST", createUserIntegration);

    // Read User lambda
    const readUserHandler = new Function(this, "ReadUserHandler", {
      runtime: Runtime.NODEJS_10_X,
      code: Code.fromAsset("resources/user"), // from "resources/" directory
      handler: "readUser.handler",            // readUser.js file
      environment: {
        'TABLE_NAME': 'usersTable'
      }
    });

    // Read User lambda integration
    const readUserIntegration = new LambdaIntegration(readUserHandler);

    const apiReadUser = api.root.addResource('readUser'); // API Gateway path
    apiReadUser.addMethod("GET", readUserIntegration);

    // Grant DynamoDB permissions
    table.grantReadWriteData(createUserHandler);
    table.grantReadData(readUserHandler);
  }
}
