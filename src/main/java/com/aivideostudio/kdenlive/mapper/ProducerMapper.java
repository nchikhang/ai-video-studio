package com.aivideostudio.kdenlive.mapper;

import com.aivideostudio.kdenlive.builder.context.BuildContext;
import com.aivideostudio.kdenlive.model.Producer;

public interface ProducerMapper {

    Producer map(BuildContext context,
                 String resource);

}