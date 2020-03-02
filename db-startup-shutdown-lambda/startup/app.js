const startInstance = require('./start');

exports.lambdaHandler = async (event, context) => {

  const instanceIdentifier = process.env.INSTANCE_IDENTIFIER;
  const mysqlInstanceIdentifier = process.env.MYSQL_INSTANCE_IDENTIFIER;
  const postgresqlInstanceIdentifier = process.env.POSTGRESQL_INSTANCE_IDENTIFIER;

  var result;
  var mysqlResult;
  var postgresqlResult;

  if (instanceIdentifier) {
    result = await startInstance(instanceIdentifier);
  }

  if (mysqlInstanceIdentifier) {
    mysqlResult = await startInstance(mysqlInstanceIdentifier);
  }

  if (postgresqlInstanceIdentifier) {
    postgresqlResult = await startInstance(postgresqlInstanceIdentifier);
  }

  return {
    statusCode: 200,
    body: { ...result, ...mysqlResult, ...postgresqlResult },
  }
};
