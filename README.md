> [!IMPORTANT]
> This operation has been moved to [nuxeo-labs-utils](https://github.com/nuxeo-sandbox/nuxeo-labs-utils)

<hr>

# About

Verifies the presence of binary data within the repository via the MD5 hash.  This operation will
check the underlying stores to verify the presence of data.  If the data is present, the digest
of the file will be returned.  If the data is not found, a null or empty (204) response will be
returned.

Operation: `Blob.VerifyBinaryHash`

# Requirements

Build requires the following software:
- git
- maven

# Build

```
mvn clean install
```

# Deploy

Install the marketplace package on your Nuxeo instance.

# Support

**These features are not part of the Nuxeo Production platform.**

These solutions are provided for inspiration and we encourage customers to use them as code samples and learning resources.

This is a moving project (no API maintenance, no deprecation process, etc.) If any of these solutions are found to be useful for the Nuxeo Platform in general, they will be integrated directly into platform, not maintained here.

# Licensing

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

# About Nuxeo

Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Netflix, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris.

More information is available at [www.nuxeo.com](http://www.nuxeo.com).
