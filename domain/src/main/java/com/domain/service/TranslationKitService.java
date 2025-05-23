package com.domain.service;

import java.io.File;
import java.io.IOException;

public interface TranslationKitService {
    File prepareTranslationKit(String customerId, String customerUId) throws IOException;
}
