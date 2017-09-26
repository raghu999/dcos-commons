package com.mesosphere.sdk.scheduler.uninstall;

import com.google.protobuf.TextFormat;
import com.mesosphere.sdk.offer.OfferRecommendation;
import com.mesosphere.sdk.scheduler.plan.AbstractStep;
import com.mesosphere.sdk.scheduler.plan.PodInstanceRequirement;
import com.mesosphere.sdk.scheduler.plan.Status;
import org.apache.mesos.Protos;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Common parent for all uninstall steps. Uninstall steps do not fully interact
 * with the mesos offer cycle and as such have stubs for most AbstractStep methods.
 */
public abstract class UninstallStep extends AbstractStep {
    private Status status = Status.PENDING;

    public UninstallStep(String name) {
        super(name);
    }

    @Override
    public Optional<PodInstanceRequirement> getPodInstanceRequirement() {
        return Optional.empty();
    }

    @Override
    public void updateOfferStatus(Collection<OfferRecommendation> recommendations) {
    }

    @Override
    public Optional<PodInstanceRequirement> getAsset() {
        return getPodInstanceRequirement();
    }

    @Override
    public List<String> getErrors() {
        return Collections.emptyList();
    }

    @Override
    public void update(Protos.TaskStatus status) {
        logger.debug("Step {} ignoring irrelevant TaskStatus: {}", getName(), TextFormat.shortDebugString(status));
    }

    @Override
    protected Status getStatusInternal() {
        return status;
    }

    @Override
    public void restart() {
        status = Status.PENDING;
    }

    @Override
    public void forceComplete() {
        status = Status.COMPLETE;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }
}

