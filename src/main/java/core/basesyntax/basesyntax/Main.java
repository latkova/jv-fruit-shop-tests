package core.basesyntax.basesyntax;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.FileReaderService;
import core.basesyntax.basesyntax.service.FileWriterService;
import core.basesyntax.basesyntax.service.FruitRecordParser;
import core.basesyntax.basesyntax.service.FruitService;
import core.basesyntax.basesyntax.service.FruitStrategy;
import core.basesyntax.basesyntax.service.OperationHandler;
import core.basesyntax.basesyntax.service.ReportService;
import core.basesyntax.basesyntax.service.impl.AddOperationHandler;
import core.basesyntax.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.basesyntax.service.impl.FileWriterImpl;
import core.basesyntax.basesyntax.service.impl.FruitRecordParserImpl;
import core.basesyntax.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.basesyntax.service.impl.FruitStrategyImpl;
import core.basesyntax.basesyntax.service.impl.ReportServiceCsv;
import core.basesyntax.basesyntax.service.impl.SubtractOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String BALANCE = "b";
    public static final String SUPPLY = "s";
    public static final String RETURN = "r";
    public static final String PURCHASE = "p";
    private static final String INPUT_FILE = "src/main/java/core/basesyntax/resources/fruitsFrom";
    private static final String OUTPUT_FILE = "src/main/java/core/basesyntax/resources/report.csv";

    public static void main(String[] args) {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put(BALANCE, new BalanceOperationHandler());
        map.put(SUPPLY, new AddOperationHandler());
        map.put(RETURN, new AddOperationHandler());
        map.put(PURCHASE, new SubtractOperationHandler());

        FileReaderService fileReader = new FileReaderImpl();
        FruitRecordParser fruitRecordParser = new FruitRecordParserImpl();
        List<FruitRecordDto> dtos = fruitRecordParser.parse(fileReader.readFromFile(INPUT_FILE));
        FruitStrategy fruitStrategy = new FruitStrategyImpl(map);
        FruitService fruitService = new FruitServiceImpl(fruitStrategy);
        fruitService.saveDto(dtos);
        ReportService reportMaker = new ReportServiceCsv();
        FileWriterService fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(reportMaker.createReport(Storage.fruits), OUTPUT_FILE);
    }
}