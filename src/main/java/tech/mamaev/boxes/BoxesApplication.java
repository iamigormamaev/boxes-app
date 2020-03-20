package tech.mamaev.boxes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.mamaev.boxes.entity.Box;
import tech.mamaev.boxes.entity.Item;
import tech.mamaev.boxes.model.generated.Storage;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BoxesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoxesApplication.class, args);
    }

    @RestController
    static class BoxController {
        private final ItemsService itemsService;

        public BoxController(ItemsService itemsService) {
            this.itemsService = itemsService;
        }

        @GetMapping("/test")
        public List<Integer> info(@RequestParam Integer box, @RequestParam String color) {
            return itemsService.getItemIds(box, color);
        }
    }

    @Service
    static class ItemsService {

        private final BoxRepository boxRepository;

        public ItemsService(BoxRepository boxRepository) {
            this.boxRepository = boxRepository;
        }

        @Transactional
        public List<Integer> getItemIds(Integer boxId, String color) {
            return boxRepository.findById(boxId).stream()
                    .flatMap(this::getAllItems)
                    .filter(i -> color.equals(i.getColor()))
                    .map(Item::getId)
                    .collect(Collectors.toList());
        }

        private Stream<Item> getAllItems(Box box) {
            var firstLevelItemsStream = box.getItems().stream();
            Stream<Item> innerBoxesItemsStream = Stream.empty();

            if (!box.getBoxes().isEmpty()) {
                innerBoxesItemsStream = box.getBoxes().stream().flatMap(this::getAllItems);
            }

            return Stream.concat(firstLevelItemsStream, innerBoxesItemsStream);
        }
    }

    @Component
    static class DbInitializer implements ApplicationRunner {
        private static final Logger logger = LoggerFactory.getLogger(DbInitializer.class);
        private static final JAXBContext jaxbContext = initJAXBContext();

        private final BoxRepository boxRepository;
        private final ItemRepository itemRepository;

        public DbInitializer(BoxRepository boxRepository, ItemRepository itemRepository) {
            this.boxRepository = boxRepository;
            this.itemRepository = itemRepository;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            var file = initFile(args);
            var storage = (Storage) jaxbContext.createUnmarshaller().unmarshal(file);

            fillRepositories(storage);
            logRepositories();
        }

        private File initFile(ApplicationArguments args) {
            if (args.getSourceArgs().length == 0)
                throw new RuntimeException("Unable to find init file. Pass it's name in first argument");

            var fileName = args.getSourceArgs()[0];
            return new File(fileName);
        }

        private void fillRepositories(Storage storage) {
            var boxes = getBoxes(storage);
            var topLevelItems = getItems(storage);

            boxRepository.saveAll(boxes);
            itemRepository.saveAll(topLevelItems);
        }

        private void logRepositories() {
            logger.info("BOX:");
            boxRepository.findAll().forEach(b -> logger.info(b.toString()));
            logger.info("ITEM:");
            itemRepository.findAll().forEach(i -> logger.info(i.toString()));
        }

        private List<Box> getBoxes(Storage storage) {
            return storage.getBox().stream().map(b -> Box.from(b, null)).collect(Collectors.toList());
        }

        private List<Item> getItems(Storage storage) {
            return storage.getItem().stream().map(i -> Item.from(i, null)).collect(Collectors.toList());
        }

        private static JAXBContext initJAXBContext() {
            JAXBContext context;
            try {
                context = JAXBContext.newInstance("tech.mamaev.boxes.model.generated");
            } catch (Exception ex) {
                throw new RuntimeException("Error while init JAXBContext", ex);
            }

            return context;
        }
    }
}
