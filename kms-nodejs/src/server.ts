import * as hapi from "hapi";
import * as inert from "inert";

import { UAParser } from 'ua-parser-js';

import { encrypt, decrypt } from "./service/crypto";

const server: hapi.Server = new hapi.Server({
  //  In a docker container, localhost may not be accessible outside of the container
  // and using host: '0.0.0.0' is needed.
  host: '0.0.0.0',
  port: 3000,
  routes: {
    // CORS support
    cors: {
      origin: ['*'], // an array of origins or 'ignore' - ‘Access-Control-Allow-Origin’
      headers: ['Authorization', 'Content-Type'], // an array of headers - 'Access-Control-Allow-Headers'
      exposedHeaders: ['Accept'], // an array of exposed headers - 'Access-Control-Expose-Headers'
      additionalExposedHeaders: ['Accept'], // an array of additional exposed headers for previous property
      maxAge: 60, // nubmer of seconds - 'Access-Control-Max-Age'
      credentials: true // boolean of allow user credentials - 'Access-Control-Allow-Credentials'
    }
  }
});

//
server.route({
  method: 'GET',
  path: '/',
  handler: (request, h) => {
    let word = 'abcdefg013456789';

    encrypt(Buffer.from(word, 'utf-8'))
      .then(decrypt)
      .then(plaintext => {
        console.log('Encrypt / Decrypt word is: [' + plaintext.toString() + ']');
      });

    return 'Hello from AWS KMS encrypt / decrypt demo';
  }
});

server.route({
  method: 'GET',
  path: '/favicon.ico',
  handler: (request, h) => {
    return h.file('./public/favicon.ico')
  }
});

server.events.on('response', function (request) {
  Object.keys(request.headers).forEach(key => {
    server.log(key + ': ' + request.headers[key]);
  });

  // must wait for response returned so can display request payload
  server.log('payload: ' + JSON.stringify(request.payload));

  let uaParser = new UAParser(request.headers['user-agent']);
  server.log('user-agent: ' + JSON.stringify(uaParser.getResult()));
});

//
async function start() {

  try {
    await server.register(inert);

    const options = {
      ops: {
        interval: 1000
      },
      reporters: {
        console: [
          {
            module: 'good-squeeze',
            name: 'Squeeze',
            args: [{ log: '*', response: '*' }]
          }, {
            module: 'good-console'
          }, 'stdout'
        ]
      }
    };

    await server.register({
      plugin: require('good'),
      options
    });

    await server.start();
  }
  catch (err) {
    console.log(err);
    process.exit(1);
  }

  console.log('Server running at: ', server.info.uri);
}

start();
