package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.service.FruitStrategy;
import core.basesyntax.basesyntax.service.OperationHandler;
import java.util.Map;

public class FruitStrategyImpl implements FruitStrategy {
    private Map<String, OperationHandler> handlerMap;

    public FruitStrategyImpl(Map<String, OperationHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public OperationHandler get(String type) {
        return handlerMap.get(type);
    }
}