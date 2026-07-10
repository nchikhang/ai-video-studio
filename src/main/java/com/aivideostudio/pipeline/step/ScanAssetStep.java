package com.aivideostudio.pipeline.step;

import com.aivideostudio.asset.AssetScanner;
import com.aivideostudio.asset.AssetService;
import com.aivideostudio.asset.AssetValidator;
import com.aivideostudio.pipeline.BasePipeline;
import com.aivideostudio.pipeline.PipelineContext;

public class ScanAssetStep extends BasePipeline {

    @Override
    public void execute(PipelineContext context) throws Exception {

        title("Scanning Assets");

        AssetScanner scanner = new AssetScanner();

        AssetService assets = scanner.scan();

        new AssetValidator().validate(assets);

        context.setAssets(assets);

        success("Backgrounds : " + assets.getBackgrounds().size());

    }

}