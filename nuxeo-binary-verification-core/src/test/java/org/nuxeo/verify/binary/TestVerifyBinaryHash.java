package org.nuxeo.verify.binary;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.impl.blob.FileBlob;
import org.nuxeo.ecm.core.blob.BlobManager;
import org.nuxeo.ecm.core.blob.BlobProvider;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("org.nuxeo.verify.binary.nuxeo-binary-verification-core")
public class TestVerifyBinaryHash {

  @Inject
  protected CoreSession session;

  @Inject
  protected AutomationService automationService;

  @Test
  public void testBinaryVerification() throws OperationException, IOException {
    BlobManager mgr = Framework.getService(BlobManager.class);
    for (Entry<String, BlobProvider> prov : mgr.getBlobProviders().entrySet()) {
      try {
        FileBlob blob = new FileBlob(getClass().getResourceAsStream("/jndi.properties"));
        prov.getValue().writeBlob(blob);
        break;
      } catch (Exception ex) {
      }
    }
    final String binDigest = "d8e9507a0602d446390c10aa5641aa02";
    OperationContext ctx = new OperationContext(session);
    Map<String, Object> params = new HashMap<>();
    params.put("digest", binDigest);
    String digest = (String) automationService.run(ctx, VerifyBinaryHash.ID, params);
    assertEquals(binDigest, digest);
  }
}
