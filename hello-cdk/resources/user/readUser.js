/*
  This code uses callbacks to handle asynchronous function responses.
  It currently demonstrates using an async-await pattern.
  AWS supports both the async-await and promises patterns.
  For more information, see the following:

  https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function
  https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_promises
  https://docs.aws.amazon.com/sdk-for-javascript/v2/developer-guide/calling-services-asynchronously.html
  https://docs.aws.amazon.com/lambda/latest/dg/nodejs-prog-model-handler.html
*/
const AWS = require('aws-sdk');

var TableName = process.env.TABLE_NAME
var region = process.env.AWS_REGION

AWS.config.update({ region: region })

const dynamodbClient = new AWS.DynamoDB.DocumentClient();

exports.handler = (event, context, callback) => {

  const Key = {};
  Key['name'] = event.queryStringParameters.name;

  dynamodbClient.get({ TableName, Key }, function (err, data) {

    if (err) {
      console.log('error', err);
      callback(err, null);
    } else {
      var response = {
        statusCode: 200,
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Methods': 'GET,POST,OPTIONS'
        },
        body: JSON.stringify(data.Item),
        isBase64Encoded: false
      };

      console.log('success: returned ${data.Item}');
      callback(null, response);
    }
  });
};