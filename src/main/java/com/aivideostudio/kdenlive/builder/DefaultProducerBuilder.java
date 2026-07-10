package com.aivideostudio.kdenlive.builder;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.mapper.DefaultProducerMapper;
import com.aivideostudio.kdenlive.mapper.ProducerMapper;
//import com.aivideostudio.pipeline.model.Asset;

import java.util.List;

public class DefaultProducerBuilder implements ProducerBuilder {

    private final ProducerMapper producerMapper;

    public DefaultProducerBuilder() {
        this(new DefaultProducerMapper());
    }

    public DefaultProducerBuilder(ProducerMapper producerMapper) {
        this.producerMapper = producerMapper;
    }

    @Override
    public void build(BuildContext context) {

        System.out.println("ProducerBuilder");

        /*List<Asset> assets =
                context.getPipelineContext()
                       .getRenderContext()
                       .getAssets();

        if (assets == null || assets.isEmpty()) {
            return;
        }

        for (Asset asset : assets) {

            producerMapper.map(
                    context,
                    asset.getPath()
            );

        }*/

    }

}