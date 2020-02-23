import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import * as core from '@aws-cdk/core';

import S3Stack = require('../lib/s3-stack');

test('Empty Stack', () => {
    
    const app = new core.App();

    // WHEN
    const stack = new S3Stack.S3Stack(app, 'MyTestStack', {} as S3Stack.S3StackProps);
    
    // THEN
    expectCDK(stack).to(matchTemplate({
        "Resources": {}
    }, MatchStyle.EXACT))
});
