package org.nuxeo.box.api.test.folder;

import javax.inject.Inject;

import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.restclientv2.exceptions.BoxRestException;

import org.nuxeo.box.api.test.BoxBaseTest;
import org.nuxeo.box.api.test.BoxServerFeature;
import org.nuxeo.box.api.test.BoxServerInit;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import static org.junit.Assert.assertNotNull;

/**
 * @since 5.9.2
 */
@RunWith(FeaturesRunner.class)
@Features({ BoxServerFeature.class })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = BoxServerInit.class)
public class BoxClientTest extends BoxBaseTest {

    @Inject
    CoreSession session;

    @Ignore
    @Test
    // TODO NXIO-65: activate it after OAuth testing
    public void itCanFetchABoxFolderWithBoxClient() throws BoxServerException, AuthFatalFailureException,
            BoxRestException {
        // Fetching the folder in Nuxeo way
        DocumentModel folder = BoxServerInit.getFolder(1, session);

        // Fetching the folder through Box Client
        BoxFolder boxFolder = boxClient.getFoldersManager().getFolder("e438d0c4-0b7e-4272-986c-513b3b61a796", null);
        assertNotNull(boxFolder);
    }
}
