package com.interview.imageRepository;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;

public interface Constants {
  Integer SUCCESS_CODE = HTTPResponse.SC_OK;
  String CRYPTO_KEY = "Image needs to be encrypted and decrypted";
}
