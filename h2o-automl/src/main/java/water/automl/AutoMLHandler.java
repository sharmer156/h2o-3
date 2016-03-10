package water.automl;

import ai.h2o.automl.AutoML;
import water.DKV;
import water.Key;
import water.api.Handler;
import water.api.KeyV3;
import water.fvec.Frame;

public class AutoMLHandler extends Handler {
  public AutoMLV3 automl(int version, AutoMLV3 args) {
    Frame frame = DKV.getGet(args.dataset);
    if( null==frame )
      throw new IllegalArgumentException("No such frame: " + args.dataset);
    AutoML aml = new AutoML(
            Key.<AutoML>make(),
            args.dataset,
            frame,
            args.response,
            args.loss,
            args.maxTime,
            -1,     // min accuracy or stopping crit ... "loss threshold"
            args.ensemble,
            args.exclude,
            args.tryMutations);
    aml.learn();
    args.result = new KeyV3.ModelKeyV3(aml.getLeaderKey());
    return args;
  }
}
