package com.dc.facade.fd;

import java.io.File;
import java.io.IOException;

public interface TranslationKitFacade {
    File prepareTranslationKit(String customerId, String customerUId) throws IOException;
}
