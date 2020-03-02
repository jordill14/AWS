const stopInstance = require('./stop');

exports.lambdaHandler = async (event, context) => {

  const instanceIdentifier = process.env.INSTANCE_IDENTIFIER;
  const mysqlInstanceIdentifier = process.env.MYSQL_INSTANCE_IDENTIFIER;
  const postgresqlInstanceIdentifier = process.env.POSTGRESQL_INSTANCE_IDENTIFIER;

  var result;
  var mysqlResult;
  var postgresqlResult;

  if (instanceIdentifier) {
    result = await stopInstance(instanceIdentifier);
  }

  if (mysqlInstanceIdentifier) {
    mysqlResult = await stopInstance(mysqlInstanceIdentifier);
  }

  if (postgresqlInstanceIdentifier) {
    postgresqlResult = await stopInstance(postgresqlInstanceIdentifier);
  }

  return {
    statusCode: 200,
    body: { ...result, ...mysqlResult, ...postgresqlResult },
  }
};
