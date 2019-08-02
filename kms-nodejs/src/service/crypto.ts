// import entire SDK
import AWS from 'aws-sdk';

import { awsAccessKeyId, awsSecretAccessKey, awsRegion, kmsKeyId } from '../config';

export function encrypt(buffer: Buffer) {
  const kms = new AWS.KMS({
    // after put aws_access_key_id, aws_secret_access_key, aws_session_token into AWS default profile
    // accessKeyId: awsAccessKeyId,
    // secretAccessKey: awsSecretAccessKey,
    // sessionToken: awsSessionToken
    region: awsRegion
  });

  return new Promise((resolve, reject) => {
    const params = {
      // The identifier of the Customer Managed Keys (CMK) to use for encryption. You can use Key ID,
      // Amazon Resource Name (ARN), or Alias that refers to the CMK.
      KeyId: kmsKeyId,
      Plaintext: buffer // The data to encrypt
    };
    kms.encrypt(params, (err, data) => {
      if (err) {
        console.log('error thrown while encrypting: ' + JSON.stringify(err));
        reject(err);
      } else {
        console.log('encrypting ...');
        resolve(data.CiphertextBlob);
      }
    });
  });
}

export function decrypt(buffer: Buffer) {
  const kms = new AWS.KMS({
    // after put aws_access_key_id, aws_secret_access_key, aws_session_token into AWS default profile
    // accessKeyId: awsAccessKeyId,
    // secretAccessKey: awsSecretAccessKey,
    // sessionToken: awsSessionToken
    region: awsRegion
  });

  return new Promise((resolve, reject) => {
    const params = {
      CiphertextBlob: buffer // The data to decrypt
    };
    kms.decrypt(params, (err, data) => {
      if (err) {
        console.log('error thrown while decrypting: ' + JSON.stringify(err));
        reject(err);
      } else {
        console.log('decrypting ...');
        resolve(data.Plaintext);
      }
    });
  });
}
