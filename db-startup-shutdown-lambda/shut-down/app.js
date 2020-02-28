const stopInstance = require('./stop');

exports.lambdaHandler = async (event, context) => {

  const mysqlInstanceIdentifier = process.env.MYSQL_INSTANCE_IDENTIFIER;
  const mysqlResult = await stopInstance(mysqlInstanceIdentifier);

  const postgresqlInstanceIdentifier = process.env.POSTGRESQL_INSTANCE_IDENTIFIER;
  const postgresqlResult = await stopInstance(postgresqlInstanceIdentifier);

  return {
    statusCode: 200,
    body: { mysqlResult, postgresqlResult },
  }
};
