import {Injectable} from '@angular/core';
import * as forge from 'node-forge';

@Injectable({providedIn: 'root'})
export class CryptoUtil {

  publicKeyFromPem: forge.pki.rsa.PublicKey;


  constructor() {
    this.publicKeyFromPem = forge.pki.publicKeyFromPem(
      `
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoQM3evv+Ye+bb8TMpedV
owj7KZq3onky/qPwtVIYUNQDHM/icYL79s7Y8pav6e3Ikoimy+QIHloONP34za2s
7gZB+9n4iqEJBWNnCrt3FBe4BgJqUY/oJeUo+jM0e6vp0B9uk5QJzIEasK0R1ECL
U1BHA66tqI8JngZtOicOCDWSonuemvts1UCqmlnxMgJz6tgP1pqmPiAbpM8e8KJ9
mDoH+TON91mcYZO0mnY4/yqvclAUCnF58MPTOTQACU4gEQPw1NpoUOadg6xDoa9A
QfunsYiBy/639+QtZhsh8N9fR+UnFXirnNfvBovwjmUue/Cw3uGgOVMQk44hGyJw
IwIDAQAB
-----END PUBLIC KEY-----
`.trim()) as forge.pki.rsa.PublicKey;
  }

  /** Creates the secret key to use for encryption.
   * Here 16 byte key means 128 bit which is recommended
   *
   * @return aes key generated
   */
  generateAesKey(): forge.Bytes {
    return forge.random.getBytesSync(16);
  }

  /** Encrypts a given message using given key using AES
   *
   * @return aes key generated
   */
  encryptAes(message: forge.Bytes, key: forge.Bytes): forge.Bytes {
    const iv = forge.random.getBytesSync(12);
    const cipher = forge.cipher.createCipher('AES-GCM', key);
    cipher.start({
      iv: iv as forge.Bytes, // should be a 12-byte binary-encoded string or byte buffer
      tagLength: 128 // optional, defaults to 128 bits
    });

    cipher.update(forge.util.createBuffer(message));
    cipher.finish();

    return iv.concat(cipher.output.data).concat(cipher.mode.tag.data);
  }

  /** Encrypts a given message using RSA
   *
   * @return aes key generated
   */
  encryptRsa(input: forge.Bytes): forge.Bytes {
    return this.publicKeyFromPem.encrypt(input, 'RSA-OAEP',
      {
        md: forge.md.sha256.create(),
        mgf1: {
          md: forge.md.sha256.create()
        }
      }
    );
  }

  encode64(input: forge.Bytes | string): string {
    return forge.util.encode64(input);
  }
}
