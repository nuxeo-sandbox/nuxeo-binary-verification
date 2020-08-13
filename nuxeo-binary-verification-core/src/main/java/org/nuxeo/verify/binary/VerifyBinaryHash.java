package org.nuxeo.verify.binary;

import java.util.Map;
import java.util.Map.Entry;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.blob.BlobManager;
import org.nuxeo.ecm.core.blob.BlobProvider;
import org.nuxeo.ecm.core.blob.binary.Binary;
import org.nuxeo.ecm.core.blob.binary.BinaryManager;
import org.nuxeo.ecm.core.blob.binary.CachingBinaryManager;
import org.nuxeo.ecm.core.blob.binary.LazyBinary;
import org.nuxeo.runtime.api.Framework;

/**
 *
 */
@Operation(
  id = VerifyBinaryHash.ID,
  category = Constants.CAT_BLOB,
  label = "Check Binary by Hash",
  description = "Check to see if the binary is found within the system. Returns the same digest if found, null otherwise."
)
public class VerifyBinaryHash {
  public static final String ID = "Blob.VerifyBinaryHash";

  @Context
  protected CoreSession session;

  @Param(name = "digest", required = true)
  protected String digest;

  @Param(name = "provider", required = false)
  protected String provider;

  @OperationMethod
  public String run() {
    BlobManager mgr = Framework.getService(BlobManager.class);
    Map<String, BlobProvider> providers = mgr.getBlobProviders();
    if (provider != null) {
      BlobProvider bp = providers.get(provider);
      BinaryManager bmgr = bp.getBinaryManager();
      if (bmgr != null) {
        return checkDigest(bmgr);
      }
    } else {
      for (Entry<String, BlobProvider> prov : providers.entrySet()) {
        BlobProvider bp = prov.getValue();
        BinaryManager bmgr = bp.getBinaryManager();
        if (bmgr == null) {
          continue;
        }
        return checkDigest(bmgr);
      }
    }
    return null;
  }

  private String checkDigest(BinaryManager bmgr) {
    Binary bin = bmgr.getBinary(digest);
    if (bin != null) {
      if (bin instanceof LazyBinary) {
        if (((LazyBinary) bin).getFile() != null) {
          return digest;
        }
      }
      return digest;
    }
    return null;
  }
}
