const startInstance = require('./start');

exports.lambdaHandler = async (event, context) => {

  const mysqlInstanceIdentifier = process.env.MYSQL_INSTANCE_IDENTIFIER;
  const mysqlResult = await startInstance(mysqlInstanceIdentifier);

  const postgresqlInstanceIdentifier = process.env.POSTGRESQL_INSTANCE_IDENTIFIER;
  const postgresqlResult = await startInstance(postgresqlInstanceIdentifier);

  return {
    statusCode: 200,
    body: { mysqlResult, postgresqlResult },
  }
};
