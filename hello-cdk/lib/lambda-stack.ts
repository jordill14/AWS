import { Stack, StackProps, Construct } from '@aws-cdk/core';
import { WidgetService } from '../services/widget-service';

export class LambdaStack extends Stack {

  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    new WidgetService(this, 'Widgets');
  }
}
