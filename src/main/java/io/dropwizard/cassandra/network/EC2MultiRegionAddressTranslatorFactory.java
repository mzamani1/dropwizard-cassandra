package io.dropwizard.cassandra.network;

import com.datastax.driver.core.policies.AddressTranslator;
import com.datastax.driver.core.policies.EC2MultiRegionAddressTranslator;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A factory for configuring and building {@link EC2MultiRegionAddressTranslator} instances.
 */
@JsonTypeName("ec2MultiRegion")
public class EC2MultiRegionAddressTranslatorFactory implements AddressTranslatorFactory {
    @Override
    public AddressTranslator build() {
        return new EC2MultiRegionAddressTranslator();
    }
}
